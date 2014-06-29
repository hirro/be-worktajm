/*
 * Copyright 2014 Jim Arnell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arnellconsulting.worktajm.config;

import java.util.Arrays;
import java.util.EnumSet;
import javax.inject.Inject;
import javax.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 * <!-- CORS configuration for Tomcat -->
 * <filter>
 * <filter-name>CorsFilter</filter-name>
 * <filter-class>org.apache.catalina.filters.CorsFilter</filter-class>
 * <init-param>
 * <param-name>cors.allowed.methods</param-name>
 * <param-value>GET,POST,HEAD,OPTIONS,PUT,DELETE</param-value>
 * </init-param>
 * <init-param>
 * <param-name>cors.allowed.headers</param-name>
 * <param-value>Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Jim,Auth-Token</param-value>
 * </init-param>
 * </filter>
 * <filter-mapping>
 * <filter-name>CorsFilter</filter-name>
 * <url-pattern>/*</url-pattern>
 * </filter-mapping>
 *
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    @Inject
    private Environment env;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        log.info("Web application configuration, using profiles: {}", Arrays.toString(env.getActiveProfiles()));
        EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);

        initCatalinaCORSFilter(servletContext, disps);

        log.info("Web application fully configured");
    }

    private void initEBayCORSFilter(ServletContext servletContext, EnumSet<DispatcherType> disps) {
        log.info("Initiating initEBayCORSFilter filter");
        FilterRegistration.Dynamic corsFilter = servletContext.addFilter("CORS", new org.apache.catalina.filters.CorsFilter());
        corsFilter.addMappingForUrlPatterns(disps, true, "/*");
        corsFilter.setInitParameter("cors.logging.enabled", "true");
        corsFilter.setInitParameter("cors.allowed.method", "GET,POST,HEAD,PUT,DELETE");
        corsFilter.setInitParameter(
                "cors.allowed.headers",
                "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers");
        corsFilter.setInitParameter("cors.allowOrigin", "*");
        corsFilter.setInitParameter("cors.support.credentials", "true");
        corsFilter.setInitParameter(
                "cors.exposed.headers",
                "Access-Control-Allow-Origin,Access-Control-Allow-Credentials, Authorization");
        corsFilter.setAsyncSupported(true);
    }

    private void initCatalinaCORSFilter(ServletContext servletContext, EnumSet<DispatcherType> disps) {

        log.info("Initiating initCatalinaCORSFilter filter");
        FilterRegistration.Dynamic corsFilter = servletContext.addFilter("CORS", new org.apache.catalina.filters.CorsFilter());
        corsFilter.addMappingForUrlPatterns(disps, true, "/*");

        corsFilter.setInitParameter("cors.allowed.origins", "*");
        corsFilter.setInitParameter("cors.allowed.methods", "GET,POST,HEAD,PUT,DELETE");
        corsFilter.setInitParameter(
                "cors.allowed.headers", 
                "Origin, accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, authorization");        
        corsFilter.setInitParameter("cors.support.credentials", "true");
        corsFilter.setAsyncSupported(true);
    }

    private void initTransactionCompanyCORSFilter(ServletContext servletContext, EnumSet<DispatcherType> disps) {

        log.info("Initiating initTransactionCompanyCORSFilter filter");
        FilterRegistration.Dynamic corsFilter = servletContext.addFilter(
                "CORS",
                new com.thetransactioncompany.cors.CORSFilter());
        corsFilter.addMappingForUrlPatterns(disps, true, "/*");
        corsFilter.setInitParameter("cors.logging.enabled", "true");
        corsFilter.setInitParameter("cors.allowed.method", "GET,POST,HEAD,PUT,DELETE");
        corsFilter.setInitParameter(
                "cors.allowed.headers",
                "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers");
        corsFilter.setInitParameter("cors.allowOrigin", "*");
        corsFilter.setAsyncSupported(true);
    }

}
