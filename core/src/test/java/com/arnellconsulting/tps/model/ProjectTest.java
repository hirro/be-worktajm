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


package com.arnellconsulting.tps.model;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author jiar
 */
@RunWith(JUnit4.class)
public class ProjectTest {
   public static final String PROJECT_NAME = "Project name";
   public static final String DESCRIPTION = "Description";
   private transient Project project;

   @Before
   public void setUp() {
      project = new Project();
      project.setName(PROJECT_NAME);
      project.setDescription(DESCRIPTION);
   }

   @Test
   public void testGetName() {
      assertEquals(PROJECT_NAME, project.getName());
   }

   @Test
   public void testGetDescription() {
      assertEquals(DESCRIPTION, project.getDescription());
   }

}
