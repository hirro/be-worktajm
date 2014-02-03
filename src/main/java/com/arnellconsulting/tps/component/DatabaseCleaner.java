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

package com.arnellconsulting.tps.component;

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.repository.PersonRepository;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author jiar
 */
@Component
public class DatabaseCleaner {

   private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseCleaner.class);
   private static final long CLEANUP_RATE = 60 * 1000;
   private static final String E2E_EMAIL_SUFFIX = "@protractor.org";

   private final transient PersonRepository repository;

   @Autowired
   public DatabaseCleaner(final PersonRepository repository) {
      this.repository = repository;
   }

   @Scheduled(fixedRate=CLEANUP_RATE)
   public void cleanIntegrationTests() {
      Date date = DateTime.now().minusHours(1).toDate();
      List<Person> persons = repository.findByCreatedBeforeAndEmailEndingWith(date, E2E_EMAIL_SUFFIX);
      LOGGER.debug("cleanIntegrationTests - Found [{}] integration test users to remove (older than {})", persons.size(), date);
      repository.deleteInBatch(persons);      
   }
}
