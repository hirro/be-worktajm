// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.arnellconsulting.tps.model;

import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.model.ProjectDataOnDemand;
import com.arnellconsulting.tps.model.ProjectIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ProjectIntegrationTest_Roo_IntegrationTest {
    
    declare @type: ProjectIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: ProjectIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: ProjectIntegrationTest: @Transactional;
    
    @Autowired
    private ProjectDataOnDemand ProjectIntegrationTest.dod;
    
    @Test
    public void ProjectIntegrationTest.testCountProjects() {
        Assert.assertNotNull("Data on demand for 'Project' failed to initialize correctly", dod.getRandomProject());
        long count = Project.countProjects();
        Assert.assertTrue("Counter for 'Project' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void ProjectIntegrationTest.testFindProject() {
        Project obj = dod.getRandomProject();
        Assert.assertNotNull("Data on demand for 'Project' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Project' failed to provide an identifier", id);
        obj = Project.findProject(id);
        Assert.assertNotNull("Find method for 'Project' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Project' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void ProjectIntegrationTest.testFindAllProjects() {
        Assert.assertNotNull("Data on demand for 'Project' failed to initialize correctly", dod.getRandomProject());
        long count = Project.countProjects();
        Assert.assertTrue("Too expensive to perform a find all test for 'Project', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Project> result = Project.findAllProjects();
        Assert.assertNotNull("Find all method for 'Project' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Project' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void ProjectIntegrationTest.testFindProjectEntries() {
        Assert.assertNotNull("Data on demand for 'Project' failed to initialize correctly", dod.getRandomProject());
        long count = Project.countProjects();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Project> result = Project.findProjectEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Project' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Project' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void ProjectIntegrationTest.testFlush() {
        Project obj = dod.getRandomProject();
        Assert.assertNotNull("Data on demand for 'Project' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Project' failed to provide an identifier", id);
        obj = Project.findProject(id);
        Assert.assertNotNull("Find method for 'Project' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyProject(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Project' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ProjectIntegrationTest.testMergeUpdate() {
        Project obj = dod.getRandomProject();
        Assert.assertNotNull("Data on demand for 'Project' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Project' failed to provide an identifier", id);
        obj = Project.findProject(id);
        boolean modified =  dod.modifyProject(obj);
        Integer currentVersion = obj.getVersion();
        Project merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Project' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ProjectIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Project' failed to initialize correctly", dod.getRandomProject());
        Project obj = dod.getNewTransientProject(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Project' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Project' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Project' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void ProjectIntegrationTest.testRemove() {
        Project obj = dod.getRandomProject();
        Assert.assertNotNull("Data on demand for 'Project' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Project' failed to provide an identifier", id);
        obj = Project.findProject(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Project' with identifier '" + id + "'", Project.findProject(id));
    }
    
}
