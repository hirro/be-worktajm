/*
 * Copyright 2014 jiar.
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
package com.arnellconsulting.tps.cors;

import com.arnellconsulting.tps.api.ProjectController;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * http://jpgmr.wordpress.com/2013/12/12/cross-origin-resource-sharing-cors-requests-with-spring-mvc/
 * @author jiar
 */
public class CORSFilter extends OncePerRequestFilter {

   private static final Logger LOG = LoggerFactory.getLogger(CORSFilter.class);
   private Properties prop = new Properties();

   @Override
   protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain) throws ServletException, IOException {

      LOG.debug("doFilterInternal");
      Set<String> allowedOrigins = new HashSet<String>();

      if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {         
         String originHeader = request.getHeader("Origin");
         LOG.debug("doFilterInternal - CORS requested, origin {}", originHeader);

         response.addHeader("Access-Control-Allow-Origin", originHeader);
         response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
         response.addHeader("Access-Control-Allow-Headers", "Content-Type");
         response.addHeader("Access-Control-Max-Age", "1800");
      }

      filterChain.doFilter(request, response);
   }
}
