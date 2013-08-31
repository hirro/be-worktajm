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
