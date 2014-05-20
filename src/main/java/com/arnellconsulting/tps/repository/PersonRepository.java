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

package com.arnellconsulting.tps.repository;

import com.arnellconsulting.tps.domain.Person;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author hirro
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
   public Person findByEmail(String email);
   
   /**
    * Used to find users to clean up (created by e2e tests).
    * @param date entries older than this will be deleted.
    * @param suffix the suffix that e2e test users have.
    * @return list of persons that should be deleted.
    */
   public List<Person> findByCreatedBeforeAndEmailEndingWith(Date date, String suffix);
}
