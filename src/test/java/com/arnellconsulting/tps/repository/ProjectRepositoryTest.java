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



package com.arnellconsulting.tps.repository;

import com.arnellconsulting.tps.config.PersistenceContext;
import com.arnellconsulting.tps.config.TestContext;
import com.arnellconsulting.tps.config.WebAppContext;
import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.model.TestConstants;

import junit.framework.TestCase;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jiar
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebAppContext.class, PersistenceContext.class })
@WebAppConfiguration
@Transactional
@Slf4j
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class ProjectRepositoryTest extends TestCase {
   @Autowired
   private transient ProjectRepository repository;

   @Test
   public void testInsert() {
      final Project project = TestConstants.createProjectA();
      final Project persistedProject = repository.save(project);

      assertEquals(project.getName(), persistedProject.getName());
   }
}
