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



package com.arnellconsulting.worktajm.repository;

import com.arnellconsulting.worktajm.config.PersistenceContext;
import com.arnellconsulting.worktajm.config.TestContext;
import com.arnellconsulting.worktajm.config.WebAppContext;
import com.arnellconsulting.worktajm.domain.Person;
import com.arnellconsulting.worktajm.domain.Project;
import com.arnellconsulting.worktajm.model.TestConstants;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hirro
 *
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebAppContext.class, PersistenceContext.class })
@WebAppConfiguration
@Transactional
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class ProjectRepositoryTest extends TestCase {
   @Autowired
   private transient ProjectRepository projectRepository;

   @Autowired
   private transient PersonRepository personRepository;

   @Test
   public void test() {
      // TODO
   }

   
   public void testInsert() {
//      // Create and save person
//      Person person = personRepository.save(TestConstants.createPersonA());      
//      
//      // Create and save project
//      final Project project = TestConstants.createProjectA();
//      project.setPerson(person);
//      final Project persistedProject = projectRepository.save(project);
//
//      assertEquals(project.getName(), persistedProject.getName());
   }
}
*/