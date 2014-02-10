/*
 * Copyright 2014 Jim Arnell
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.arnellconsulting.tps.configuration;

import com.thetransactioncompany.cors.CORSFilter;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * This replaces the following WEB-INF file.
 * 
 * <code>
 * <?xml version="1.0" encoding="ISO-8859-1"?>
 * <web-app xmlns="http://java.sun.com/xml/ns/j2ee"
 *        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *        xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd"
 *        version="2.4">
 *
 *    <!-- The master configuration file for this Spring web application -->
 *    <context-param>
 *       <param-name>contextConfigLocation</param-name>
 *       <param-value>classpath:META-INF/spring/applicationContext.xml</param-value>
 *    </context-param>
 *
 *    <!-- Loads the Spring web application context -->
 *    <listener>
 *       <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
 *    </listener>
 *
 *    <!-- Enables use of HTTP methods PUT and DELETE -->
 *    <filter>
 *       <filter-name>httpMethodFilter</filter-name>
 *       <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
 *    </filter>
 *
 *    <filter-mapping>
 *       <filter-name>httpMethodFilter</filter-name>
 *       <servlet-name>Spring MVC Dispatcher Servlet</servlet-name>
 *    </filter-mapping>
 *
 *    <!-- Enables Spring Security -->
 *    <filter>
 *       <filter-name>springSecurityFilterChain</filter-name>
 *       <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
 *    </filter>
 *
 *    <filter-mapping>
 *       <filter-name>springSecurityFilterChain</filter-name>
 *       <servlet-name>Spring MVC Dispatcher Servlet</servlet-name>
 *    </filter-mapping>
 *
 *    <!-- CORS configuration for Tomcat -->    
 *    <filter>
 *       <filter-name>CorsFilter</filter-name>
 *       <filter-class>org.apache.catalina.filters.CorsFilter</filter-class>
 *       <init-param>
 *          <param-name>cors.allowed.methods</param-name>
 *          <param-value>GET,POST,HEAD,OPTIONS,PUT,DELETE</param-value>
 *       </init-param>
 *       <init-param>
 *          <param-name>cors.allowed.headers</param-name>
 *          <param-value>Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Jim,Auth-Token</param-value>
 *       </init-param>
 *    </filter>
 *    <filter-mapping>
 *       <filter-name>CorsFilter</filter-name>
 *       <url-pattern>/*</url-pattern>
 *    </filter-mapping>
 *
 *    <!-- The front controller of this Spring Web application, responsible for handling all application requests -->
 *    <servlet>
 *       <servlet-name>Spring MVC Dispatcher Servlet</servlet-name>
 *       <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
 *       <init-param>
 *          <param-name>contextConfigLocation</param-name>
 *          <param-value></param-value>
 *       </init-param>
 *       <load-on-startup>1</load-on-startup>
 *    </servlet>
 *   
 *    <!-- Map all *.spring requests to the DispatcherServlet for handling -->
 *    <servlet-mapping>
 *       <servlet-name>Spring MVC Dispatcher Servlet</servlet-name>
 *       <url-pattern>/</url-pattern>
 *    </servlet-mapping>
 *
 * </web-app>
 *
 * </code>
 * @author Jim Arnell
 */
@Configuration
public class WebInitializer implements WebApplicationInitializer {
    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
    private static final String DISPATCHER_SERVLET_MAPPING = "/";   

   @Override
   public void onStartup(ServletContext servletContext) throws ServletException {

      // Create the 'root' Spring application context
      AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
      rootContext.register(WorktajmApplicationContext.class);

      ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
         DISPATCHER_SERVLET_NAME, 
         new DispatcherServlet(rootContext));
      dispatcher.setLoadOnStartup(1);
      dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING);


      //////////////////////////////////////////////////////////////////////////
      // Register filters
      //////////////////////////////////////////////////////////////////////////

      EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

      // CORS
      FilterRegistration.Dynamic corsFilter = servletContext.addFilter("corsFilter", CORSFilter.class);
      corsFilter.addMappingForUrlPatterns(dispatcherTypes, false, "/*");      

      // Character encoding filter
      FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("characterEncoding", getUtf8EncodingFilter());
      characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");

      // Spring Security
      FilterRegistration.Dynamic security = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
      security.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
      
      servletContext.addListener(new ContextLoaderListener(rootContext));
   }

   private CharacterEncodingFilter getUtf8EncodingFilter() {
      CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
      characterEncodingFilter.setEncoding("UTF-8");
      characterEncodingFilter.setForceEncoding(true);
      return characterEncodingFilter;      
   }
}
