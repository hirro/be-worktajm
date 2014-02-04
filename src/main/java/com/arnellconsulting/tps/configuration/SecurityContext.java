/*
 * Copyright 2014 Arnell Consulting AB.
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
package com.arnellconsulting.tps.configuration;

import com.arnellconsulting.tps.repository.PersonRepository;
import com.arnellconsulting.tps.security.TpsUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <code>
 * <security:global-method-security secured-annotations="enabled"/>
 * <security:http realm="Protected API" use-expressions="true" auto-config="false" create-session="stateless"
 * entry-point-ref="unauthorizedEntryPoint" authentication-manager-ref="authenticationManager">
 * <security:custom-filter ref="authenticationTokenProcessingFilter" position="FORM_LOGIN_FILTER" />
 * <security:intercept-url pattern="/registration/**" access="permitAll"/>
 * <security:intercept-url pattern="/authenticate/**" access="permitAll"/>
 * <security:intercept-url method="GET" pattern="/person/**" access="isAuthenticated()"/>
 * </security:http>
 *
 * <security:authentication-manager alias="authenticationManager">
 * <security:authentication-provider user-service-ref="customUserDetailsService">
 * <!-- <security:password-encoder hash="sha-256" /> -->
 *      <
 * /security:authentication-provider>
 * </security:authentication-manager>
 *
 * <bean id="unauthorizedEntryPoint" class="com.arnellconsulting.tps.security.UnauthorizedEntryPoint" />
 * <bean id="customUserDetailsService" class="com.arnellconsulting.tps.security.TpsUserDetailsService"/>
 * <bean class="com.arnellconsulting.tps.security.AuthenticationTokenProcessingFilter"
 * id="authenticationTokenProcessingFilter">
 * <constructor-arg ref="authenticationManager" />
 * <constructor-arg ref="customUserDetailsService" />
 * </bean>
 * </code>
 *
 * @author jiar
 */
@Configuration
@EnableWebSecurity
public class SecurityContext extends WebSecurityConfigurerAdapter {

   @Autowired
   PersonRepository personRepository;

   @Override
   public void configure(WebSecurity web) throws Exception {
      web
              //Spring Security ignores request to static resources such as CSS or JS files.
              .ignoring()
              .antMatchers("/static/**");
   }

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth
              .userDetailsService(userDetailsService());
   }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
               .and()
                  .authorizeRequests()
                  .antMatchers("/**").permitAll();
    }
   
    @Bean
    public UserDetailsService userDetailsService() {
        return new TpsUserDetailsService(personRepository);
    }

}
