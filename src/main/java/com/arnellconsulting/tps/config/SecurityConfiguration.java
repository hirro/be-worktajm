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
package com.arnellconsulting.tps.config;

import com.arnellconsulting.tps.repository.PersonRepository;
import com.arnellconsulting.tps.security.TpsUserDetailsService;
import javax.inject.Inject;
import com.arnellconsulting.tps.rest.CORSAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@AutoConfigureAfter(value = {DatabaseConfiguration.class})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

   @Inject
   private Environment env;
   
   @Bean
   public AuthenticationEntryPoint corsAuthorizationEntryPoint() {
       return new CORSAuthenticationEntryPoint();
   }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }    
    
   @Autowired
   PersonRepository personRepository;

   @Override
   public void configure(WebSecurity web) throws Exception {
      web
         .debug(false)
         //Spring Security ignores request to static resources such as CSS or JS files.
         .ignoring()
         .antMatchers("/static/**");
   }

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth
         .userDetailsService(userDetailsService())
            .passwordEncoder(passwordEncoder());
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .httpBasic()
            .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers("/person/**").authenticated()
                    .antMatchers("/project/**").authenticated()
                    .antMatchers("/customer/**").authenticated()
                    .antMatchers("/timeEntry/**").authenticated()
                    .antMatchers("/authenticate/**").permitAll()
                    .antMatchers("/registration/**").permitAll()
                    .antMatchers("/**").denyAll();
   }
   
   @Bean
   public UserDetailsService userDetailsService() {
      return new TpsUserDetailsService(personRepository);
   }

    private AuthenticationEntryPoint worktajmAuthenticationEntry() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   @EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
   private static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {
   }    
}
