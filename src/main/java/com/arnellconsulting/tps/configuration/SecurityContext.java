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

import com.arnellconsulting.tps.repository.PersonRepository;
import com.arnellconsulting.tps.security.TpsUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <code>
 * 
 * <security:global-method-security secured-annotations="enabled"/>
 * <security:http realm="Protected API" 
 *                use-expressions="true" 
 *                auto-config="false" 
 *                create-session="stateless"
 *                entry-point-ref="unauthorizedEntryPoint" 
 *                authentication-manager-ref="authenticationManager">
 * <security:custom-filter ref="authenticationTokenProcessingFilter" 
 *                         position="FORM_LOGIN_FILTER" />
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
 * <bean id="unauthorizedEntryPoint" 
 *       class="com.arnellconsulting.tps.security.UnauthorizedEntryPoint" />
 * <bean id="customUserDetailsService" 
 *       class="com.arnellconsulting.tps.security.TpsUserDetailsService"/>
 * <bean id="authenticationTokenProcessingFilter"
 *       class="com.arnellconsulting.tps.security.AuthenticationTokenProcessingFilter">
 *    <constructor-arg ref="authenticationManager" />
 *    <constructor-arg ref="customUserDetailsService" />
 * </bean>
 * 
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
              .debug(true)
              //Spring Security ignores request to static resources such as CSS or JS files.
              .ignoring()
              .antMatchers("/static/**");
   }

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth
              .userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
   }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and()
            .httpBasic()
         .and()
            .authorizeRequests()
               .antMatchers("/**").permitAll();
    }
   
    @Bean
    public UserDetailsService userDetailsService() {
        return new TpsUserDetailsService(personRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }    
}
