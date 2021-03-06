package edu.mit.cci.roma.client.comm;

import edu.mit.cci.roma.client.Scenario;
import edu.mit.cci.roma.client.Simulation;
import edu.mit.cci.roma.client.Tuple;
import edu.mit.cci.roma.client.TupleStatus;
import edu.mit.cci.roma.client.Variable;
import edu.mit.cci.roma.impl.DefaultVariable;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: jintrone
 * Date: 3/21/11
 * Time: 1:13 AM
 */


/**
 * Note that these tests depend upon specific db configuration;
 *
 * @TODO create necessary DB configuration scripts to isolate test
 */
public class TestLiveService {

    @Test
    public void testPangaea() throws IOException, MetaDataNotFoundException, ScenarioNotFoundException, ModelNotFoundException {

        ClientRepository repo = ClientRepository.instance("localhost:8080/roma-server");
        Simulation sim = repo.getSimulation(1L);

        Assert.assertNotNull(sim);
        Map<String, Object> inputs = new HashMap<String, Object>();
        //developed
        inputs.put("Developed start year", "2012");  //start year
        inputs.put("Developed target year", "2050");  //target year
        inputs.put("Pct change in Developed FF emissions", "200");   //target

        //developing a
        inputs.put("Developing A start year", "2012");
        inputs.put("Developing A target year", "2050");
        inputs.put("Pct change in Developing A FF emissions", "200");

        //developing b
        inputs.put("Developing B start year", "2012");
        inputs.put("Developing B target year", "2050");
        inputs.put("Pct change in Developing B FF emissions", "200");


        inputs.put("Target Sequestration", "0.50");  //sequestration (afforestation)
        inputs.put("Global land use emissions change", "0.50");  //deforestation
        Scenario scenario = repo.runModelWithInputNames(sim, inputs, 546L, true);
        Assert.assertNotNull(scenario);

    }

    @Test
    public void testCompositeModel() throws IOException, MetaDataNotFoundException, ScenarioNotFoundException, ModelNotFoundException {

        ClientRepository repo = ClientRepository.instance("localhost:8080/roma-server");
        Simulation sim = repo.getSimulation(10L);

        Assert.assertNotNull(sim);
        Map<String, Object> inputs = new HashMap<String, Object>();
        //developed
        inputs.put("Developed start year", "2012");  //start year
        inputs.put("Developed target year", "2050");  //target year
        inputs.put("Pct change in Developed FF emissions", "200");   //target

        //developing a
        inputs.put("Developing A start year", "2012");
        inputs.put("Developing A target year", "2050");
        inputs.put("Pct change in Developing A FF emissions", "200");

        //developing b
        inputs.put("Developing B start year", "2012");
        inputs.put("Developing B target year", "2050");
        inputs.put("Pct change in Developing B FF emissions", "200");


        inputs.put("Target Sequestration", "0.50");  //sequestration (afforestation)
        inputs.put("Global land use emissions change", "0.50");  //deforestation
        Scenario scenario = repo.runModelWithInputNames(sim, inputs, 546L, true);
        Assert.assertNotNull(scenario);

    }

   // @Test
    public void testErrorScenario() throws IOException, MetaDataNotFoundException, ScenarioNotFoundException, ModelNotFoundException {

        ClientRepository repo = ClientRepository.instance("localhost");
        Scenario scenario = repo.getScenario(25L);
        Variable var = null;
        for (Variable v:scenario.getOutputSet()) {
            if (v.getMetaData().getId() == 12L) {
                var = v;
                break;
            }
        }

        assert var != null;
        for (Tuple t:var.getValue()) {
            Assert.assertTrue(t.getStatus(1).equals(TupleStatus.OUT_OF_RANGE));
        }


    }

    @Test
    public void testDOEModel() throws IOException, MetaDataNotFoundException, ScenarioNotFoundException, ModelNotFoundException {
        ClientRepository repo = ClientRepository.instance("cognosis.mit.edu");
        Simulation sim = repo.getSimulation(15L);
        Assert.assertNotNull(sim);
        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put("Coal-based_electricity_in_2050","48");
        inputs.put("Natural_gas-based_electricity_in_2050","99");
        inputs.put("Nuclear_electricity_in_2050","16");
        inputs.put("Renewable_electricity_in_2050","14");
        inputs.put("Share_of_fossil_CCS_in_2050","2");
        inputs.put("Industry_energy_effeciency_increase_by_2050","43");
        inputs.put("Low_carbon_fuel_mix_in_2050","50");
        inputs.put("Biomass_feedstock_in_2050","50");
        inputs.put("New_building_improvements_by_2030","75");
        inputs.put("Residential_building_retrofits_by_2050","100");
        inputs.put("Retrofit_improvements_in_2050","12");
        inputs.put("Appliance_and_equipment_efficicency_increase_by_2050","52");
        inputs.put("Electrification_share_of_heating_and_cooking_in_2050","91");
        inputs.put("LDV_fleet_MPG_in_2050","36");
        inputs.put("Non-LDV_efficiency_improvements_by_2050","0");
        inputs.put("Biofuels_production_in_2050","50");
        inputs.put("LDV_miles_travelled_in_2050","9600");
        Scenario scenario = repo.runModelWithInputNames(sim, inputs, 546L, true);
        Assert.assertNotNull(scenario);

        System.err.println("----------INPUTS-----------");
        for (Variable v:scenario.getInputSet()) {
          System.err.println(v.getMetaData().getName()+":"+createValueString(v.getValue()));
        }


        System.err.println("----------OUTPUTS-----------");
        for (Variable v:scenario.getOutputSet()) {
            System.err.println(v.getMetaData().getName()+":"+createValueString(v.getValue()));

        };



    }

    public static String createValueString(List<Tuple> values) {
        StringBuilder builder = new StringBuilder();
        for (Tuple t:values) {
            builder.append("[");
            for (String v:t.getValues()) {
                builder.append(v).append(";");
            }
            builder.append("]");
        }
        return builder.toString();
    }

    //    @Test
//    public void testRepositoryRetrieval_Simulations() throws IOException {
//        ClientRepository repo = ClientRepository.instance("localhost", 8080);
//        Collection<Simulation> sims = repo.getAllSimulations();
//        log.info("Retrieved " + sims.size() + " simulations:");
//        for (Simulation sim : sims) {
//            log.info(sim.getName());
//            StringBuffer buf = new StringBuffer();
//            buf.append("-- Inputs\n");
//            for (MetaData md : sim.getInputs()) {
//                buf.append("---- ").append(md.getId()).append(":").append(md.getName()).append(" - ").append(md.getDescription());
//                buf.append("\n");
//            }
//            log.info(buf.toString());
//            buf = new StringBuffer();
//            buf.append("-- Outputs\n");
//            for (MetaData md : sim.getOutputs()) {
//                buf.append("---- ").append(md.getId()).append(":").append(md.getName()).append(" - ").append(md.getDescription());
//                buf.append(" : isIndex - ").append(md.getIndex());
//                buf.append(" : indexingmd - "+(md.getIndexingMetaData()!=null?md.getIndexingMetaData().getId():"<none>"));
//
//                buf.append("\n");
//            }
//            buf.append("\n");
//            log.info(buf);
//        }
//
//    }
//
//    @Test
//    public void testRepositoryRetrieval_Scenario() throws IOException {
//        ClientRepository repo = ClientRepository.instance("localhost", 8080);
//
//        Scenario s = repo.getScenario(2703l);
//        Assert.assertNotNull(s);
//        log.info("Scenario: " + s.getName());
//        log.info(getScenarioString(s));
//
//    }
//
//
//    @Test
//    public void testCompositeModelRun() throws IOException, ScenarioNotFoundException, ModelNotFoundException, MetaDataNotFoundException {
//        ClientRepository repo = ClientRepository.instance("localhost", 8080);
//        Scenario scenario = TestHelper.runCompositeOne(repo);
//        log.info("Scenario: "+scenario.getName()+" id:"+scenario.getId());
//        log.info(getScenarioString(scenario));
//        Assert.assertEquals(EntityState.TEMPORARY,scenario.getState());
//
//    }
//       @Test
//     public void testCompositeModelRun2() throws IOException, ScenarioNotFoundException, ModelNotFoundException, MetaDataNotFoundException {
//        ClientRepository repo = ClientRepository.instance("localhost:8080/roma-server");
//        Scenario scenario = TestHelper.runCompositeTwo(repo);
//        log.info("Scenario: "+scenario.getName()+" id:"+scenario.getId());
//        log.info(getScenarioString(scenario));
//        Assert.assertEquals(EntityState.TEMPORARY,scenario.getState());
//
//    }
//
//    @Test
//    public void testTypeField() throws IOException {
//        ClientRepository repo = ClientRepository.instance("localhost", 8080);
//         Collection<Simulation> sims = repo.getAllSimulations();
//        log.info("Retrieved " + sims.size() + " simulations:");
//        for (Simulation s:repo.getAllSimulations()) {
//            log.info("Retrieved: "+s.getName()+" - "+s.getId());
//        }
//        Simulation sim = repo.getSimulation(920L);
//        Assert.assertNotNull(sim);
//        Assert.assertEquals("test test",sim.getType());
//    }



//    @Test
//    public void testModelUpdate() throws IOException, ScenarioNotFoundException, ModelNotFoundException {
//        ClientRepository repo = ClientRepository.instance("localhost", 8080);
//        Simulation s = repo.getSimulation(621L);
//        String name = s.getName();
//        s.setName("fooey");
//        repo.updateSimulation(s);
//
//        s = repo.getSimulation(621L);
//        Assert.assertEquals("fooey",s.getName());
//
//        s.setName(name);
//        repo.updateSimulation(s);
//
//        s = repo.getSimulation(621L);
//        Assert.assertEquals(name,s.getName());
//
//    }


//    private static String getScenarioString(Scenario s) {
//        List<DefaultVariable> inputs = s.getInputSet();
//        StringBuffer buf = new StringBuffer();
//        buf.append("Inputs\n");
//        for (DefaultVariable v : inputs) {
//            buf.append(v.getMetaData().getName()).append(":").append(v.getMetaData().getId()).append(":");
//            buf.append(v.getValue().toString());
//            buf.append("\n");
//        }
//        buf.append("\n");
//        List<DefaultVariable> outputs = s.getOutputSet();
//        buf.append("Outputs\n");
//        for (DefaultVariable v : outputs) {
//            buf.append(v.getMetaData().getName()).append(":").append(v.getMetaData().getId()).append(":");
//            buf.append(v.getValue().toString()).append(":");
//            buf.append("\n");
//        }
//        return buf.toString();
//    }

}
