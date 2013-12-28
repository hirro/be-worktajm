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

package com.arnellconsulting.tps.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author hirro
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid parameter")
class InvalidParameterExeception extends Exception {

   /**
    * Constructs an instance of
    * <code>InvalidParameterExeception</code> with the specified detail message.
    *
    * @param msg the detail message.
    */
   public InvalidParameterExeception(final String msg) {
      super(msg);
   }
}
