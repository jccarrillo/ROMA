package edu.mit.cci.roma.server;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@Transactional
public class MappedSimulationIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }



    @Autowired
    private MappedSimulationDataOnDemand dod;

    @Test
    public void testCountMappedSimulations() {
        org.junit.Assert.assertNotNull("Data on demand for 'MappedSimulation' failed to initialize correctly", dod.getRandomMappedSimulation());
        long count =  MappedServerSimulation.countMappedSimulations();
        org.junit.Assert.assertTrue("Counter for 'MappedSimulation' incorrectly reported there were no entries", count > 0);
    }

    @Test
    public void testFindMappedSimulation() {
         MappedServerSimulation obj = dod.getRandomMappedSimulation();
        org.junit.Assert.assertNotNull("Data on demand for 'MappedSimulation' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'MappedSimulation' failed to provide an identifier", id);
        obj =  MappedServerSimulation.findMappedSimulation(id);
        org.junit.Assert.assertNotNull("Find method for 'MappedSimulation' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'MappedSimulation' returned the incorrect identifier", id, obj.getId());
    }

    @Test
    public void testFindAllMappedSimulations() {
        org.junit.Assert.assertNotNull("Data on demand for 'MappedSimulation' failed to initialize correctly", dod.getRandomMappedSimulation());
        long count =  MappedServerSimulation.countMappedSimulations();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'MappedSimulation', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List <MappedServerSimulation> result =  MappedServerSimulation.findAllMappedSimulations();
        org.junit.Assert.assertNotNull("Find all method for 'MappedSimulation' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'MappedSimulation' failed to return any data", result.size() > 0);
    }

    @Test
    public void testFindMappedSimulationEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'MappedSimulation' failed to initialize correctly", dod.getRandomMappedSimulation());
        long count =  MappedServerSimulation.countMappedSimulations();
        if (count > 20) count = 20;
        java.util.List <MappedServerSimulation> result =  MappedServerSimulation.findMappedSimulationEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'MappedSimulation' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'MappedSimulation' returned an incorrect number of entries", count, result.size());
    }

    @Test
    public void testFlush() {
         MappedServerSimulation obj = dod.getRandomMappedSimulation();
        org.junit.Assert.assertNotNull("Data on demand for 'MappedSimulation' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'MappedSimulation' failed to provide an identifier", id);
        obj =  MappedServerSimulation.findMappedSimulation(id);
        org.junit.Assert.assertNotNull("Find method for 'MappedSimulation' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyMappedSimulation(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'MappedSimulation' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

    @Test
    public void testMerge() {
         MappedServerSimulation obj = dod.getRandomMappedSimulation();
        org.junit.Assert.assertNotNull("Data on demand for 'MappedSimulation' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'MappedSimulation' failed to provide an identifier", id);
        obj =  MappedServerSimulation.findMappedSimulation(id);
        boolean modified =  dod.modifyMappedSimulation(obj);
        java.lang.Integer currentVersion = obj.getVersion();
         MappedServerSimulation merged = (MappedServerSimulation) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'MappedSimulation' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

    @Test
    public void testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'MappedSimulation' failed to initialize correctly", dod.getRandomMappedSimulation());
         MappedServerSimulation obj = dod.getNewTransientMappedSimulation(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'MappedSimulation' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'MappedSimulation' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'MappedSimulation' identifier to no longer be null", obj.getId());
    }

    @Test
    public void testRemove() {
         MappedServerSimulation obj = dod.getRandomMappedSimulation();
        org.junit.Assert.assertNotNull("Data on demand for 'MappedSimulation' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'MappedSimulation' failed to provide an identifier", id);
        obj =  MappedServerSimulation.findMappedSimulation(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'MappedSimulation' with identifier '" + id + "'",  MappedServerSimulation.findMappedSimulation(id));
    }
}
