/*
 * Copyright 2014 Pivotal Software, Inc..
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
package com.arnellconsulting.tps.domain.util;

import org.joda.time.LocalDateTime;

/**
 *
 * @author jiar
 */
public class JsonUtils {

    public static final LocalDateTime parseTimeString(final String timeString) {
        LocalDateTime result = null;

        // Try parse as standard time string
        try {
            result = LocalDateTime.parse(timeString);
        } catch (IllegalArgumentException e) {
            //
        }

        // Try parse as long
        try {
            long startTimeMillis = Long.parseLong(timeString);
            result = new LocalDateTime(startTimeMillis);
        } catch (NumberFormatException e) {
        }

        return result;
    }

}
