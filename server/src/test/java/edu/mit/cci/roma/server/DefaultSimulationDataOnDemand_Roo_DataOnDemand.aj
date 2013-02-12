// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.mit.cci.roma.server;

import edu.mit.cci.roma.impl.DefaultSimulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect DefaultSimulationDataOnDemand_Roo_DataOnDemand {
    
    declare @type: DefaultSimulationDataOnDemand: @Component;
    
    private Random DefaultSimulationDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<DefaultServerSimulation> DefaultSimulationDataOnDemand.data;
    
    public DefaultSimulation DefaultSimulationDataOnDemand.getSpecificDefaultSimulation(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        DefaultSimulation obj = data.get(index);
        return DefaultServerSimulation.findDefaultServerSimulation(obj.getId());
    }
    
    public DefaultServerSimulation DefaultSimulationDataOnDemand.getRandomDefaultSimulation() {
        init();
        DefaultSimulation obj = data.get(rnd.nextInt(data.size()));
        return DefaultServerSimulation.findDefaultServerSimulation(obj.getId());
    }

    public boolean DefaultSimulationDataOnDemand.modifyDefaultSimulation(DefaultSimulation obj) {
        return false;
    }
    
    public void DefaultSimulationDataOnDemand.init() {
        data = DefaultServerSimulation.findDefaultServerSimulationEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'DefaultSimulation' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<DefaultServerSimulation>();
        for (int i = 0; i < 10; i++) {
            DefaultServerSimulation obj = getNewTransientDefaultSimulation(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}