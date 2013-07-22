/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arnellconsulting.tps.model;

import com.arnellconsulting.tps.common.RegistrationStatus;
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
   private Project project;
  
   @Before
   public void setup() {
      project = new Project();
      project.setName("Project name");
      project.setDescription("Description");
   }

   @Test
   public void testGetName() {
      assertEquals("Project name", project.getName());
   }

   @Test
   public void testGetDescription() {
      assertEquals("Description", project.getDescription());
   }

}