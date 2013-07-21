/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arnellconsulting.tps.repository;

import com.arnellconsulting.tps.model.Project;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jiar
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/testContext.xml")
@Transactional
@Slf4j
public class ProjectRepositoryTest extends TestCase {
   
	@Autowired
	ProjectRepository repository;   

	@Test
   public void testInsert() {
      Project p = new Project();
      p.setName("Name");
      repository.save(p);
   }
}
