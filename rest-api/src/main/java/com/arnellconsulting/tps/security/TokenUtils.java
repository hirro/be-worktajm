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

public class TokenUtils {
   public static final String MAGIC_KEY = "obfuscate";

   //~--- methods -------------------------------------------------------------

   public static String computeSignature(UserDetails userDetails, long expires) {
      StringBuilder signatureBuilder = new StringBuilder();

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

   public static String createToken(UserDetails userDetails) {

      /* Expires in one hour */
      long expires = System.currentTimeMillis() + 1000L * 60 * 60;
      StringBuilder tokenBuilder = new StringBuilder();

      tokenBuilder.append(userDetails.getUsername());
      tokenBuilder.append(":");
      tokenBuilder.append(expires);
      tokenBuilder.append(":");
      tokenBuilder.append(TokenUtils.computeSignature(userDetails, expires));

      return tokenBuilder.toString();
   }

   public static boolean validateToken(String authToken, UserDetails userDetails) {
      String[] parts = authToken.split(":");
      long expires = Long.parseLong(parts[1]);
      String signature = parts[2];

      if (expires < System.currentTimeMillis()) {
         return false;
      }

      return signature.equals(TokenUtils.computeSignature(userDetails, expires));
   }

   //~--- get methods ---------------------------------------------------------

   public static String getUserNameFromToken(String authToken) {
      if (null == authToken) {
         return null;
      }

      String[] parts = authToken.split(":");

      return parts[0];
   }
}
