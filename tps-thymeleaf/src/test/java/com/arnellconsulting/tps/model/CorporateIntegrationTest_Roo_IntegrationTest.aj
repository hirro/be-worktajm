// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.arnellconsulting.tps.model;

import com.arnellconsulting.tps.model.Corporate;
import com.arnellconsulting.tps.model.CorporateDataOnDemand;
import com.arnellconsulting.tps.model.CorporateIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect CorporateIntegrationTest_Roo_IntegrationTest {
    
    declare @type: CorporateIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: CorporateIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: CorporateIntegrationTest: @Transactional;
    
    @Autowired
    CorporateDataOnDemand CorporateIntegrationTest.dod;
    
    @Test
    public void CorporateIntegrationTest.testCountCorporates() {
        Assert.assertNotNull("Data on demand for 'Corporate' failed to initialize correctly", dod.getRandomCorporate());
        long count = Corporate.countCorporates();
        Assert.assertTrue("Counter for 'Corporate' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void CorporateIntegrationTest.testFindCorporate() {
        Corporate obj = dod.getRandomCorporate();
        Assert.assertNotNull("Data on demand for 'Corporate' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Corporate' failed to provide an identifier", id);
        obj = Corporate.findCorporate(id);
        Assert.assertNotNull("Find method for 'Corporate' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Corporate' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void CorporateIntegrationTest.testFindAllCorporates() {
        Assert.assertNotNull("Data on demand for 'Corporate' failed to initialize correctly", dod.getRandomCorporate());
        long count = Corporate.countCorporates();
        Assert.assertTrue("Too expensive to perform a find all test for 'Corporate', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Corporate> result = Corporate.findAllCorporates();
        Assert.assertNotNull("Find all method for 'Corporate' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Corporate' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void CorporateIntegrationTest.testFindCorporateEntries() {
        Assert.assertNotNull("Data on demand for 'Corporate' failed to initialize correctly", dod.getRandomCorporate());
        long count = Corporate.countCorporates();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Corporate> result = Corporate.findCorporateEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Corporate' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Corporate' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void CorporateIntegrationTest.testFlush() {
        Corporate obj = dod.getRandomCorporate();
        Assert.assertNotNull("Data on demand for 'Corporate' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Corporate' failed to provide an identifier", id);
        obj = Corporate.findCorporate(id);
        Assert.assertNotNull("Find method for 'Corporate' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyCorporate(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Corporate' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void CorporateIntegrationTest.testMergeUpdate() {
        Corporate obj = dod.getRandomCorporate();
        Assert.assertNotNull("Data on demand for 'Corporate' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Corporate' failed to provide an identifier", id);
        obj = Corporate.findCorporate(id);
        boolean modified =  dod.modifyCorporate(obj);
        Integer currentVersion = obj.getVersion();
        Corporate merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Corporate' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void CorporateIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Corporate' failed to initialize correctly", dod.getRandomCorporate());
        Corporate obj = dod.getNewTransientCorporate(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Corporate' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Corporate' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Corporate' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void CorporateIntegrationTest.testRemove() {
        Corporate obj = dod.getRandomCorporate();
        Assert.assertNotNull("Data on demand for 'Corporate' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Corporate' failed to provide an identifier", id);
        obj = Corporate.findCorporate(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Corporate' with identifier '" + id + "'", Corporate.findCorporate(id));
    }
    
}
