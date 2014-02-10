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

      Set<String> allowedOrigins = new HashSet<String>();
      final String corsHeader = request.getHeader("Access-Control-Request-Method");
      final String httpMethod = request.getMethod();
      final String originHeader = request.getHeader("Origin");
      LOG.debug("doFilterInternal - httpMethod [{}] corsHeader: [{}]", httpMethod, corsHeader);

      if (corsHeader != null && "OPTIONS".equals(httpMethod)) {         
         LOG.debug("doFilterInternal - CORS requested, origin {}", originHeader);

         response.addHeader("Access-Control-Allow-Origin", originHeader);
         response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
         response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, jim");
         response.addHeader("Access-Control-Max-Age", "1800");
      } else {
         response.addHeader("Access-Control-Allow-Origin", originHeader);         
      }

      filterChain.doFilter(request, response);
   }
}
