package edu.mit.cci.roma.pangaea.corenew;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import edu.mit.cci.roma.pangaea.core.PangaeaConnection;
import edu.mit.cci.roma.pangaea.core.PangaeaException;
import edu.mit.cci.roma.pangaea.core.VensimException;
import edu.mit.cci.roma.pangaea.core.VensimHelper;
import edu.mit.cci.roma.pangaea.corenew.config.VensimModelInputConfig;
import edu.mit.cci.roma.pangaea.corenew.config.VensimModelOutputConfig;

public class VensimModelRunner {

    private static Logger log = Logger.getLogger(PangaeaConnection.class);
    private final static int VENSIM_CONTEXT_CREATION_MAX_FAILURE_COUNT = 10;
    
    
	private VensimModelDefinition definition; 
    private VensimHelper vensim;
    private VensimHelper baselineVensim;
	
	public VensimModelRunner(VensimModelDefinition definition) throws PangaeaException {
		this.definition = definition;
		
        String libName = PangaeaPropsUtils.getVensimLibName();
        String modelPath = definition.getPath();
        
		for (int i = 0; i < VENSIM_CONTEXT_CREATION_MAX_FAILURE_COUNT && vensim == null; i++) {
			try {
				log.info("creating new vensim helper\n\tdll lib: " + libName + "\n\tmodel path: " + modelPath);
				VensimHelper[] vensimHelpers = VensimHelper.getMultipleVensimHelpers(libName, modelPath, 2);
				baselineVensim = vensimHelpers[0];
				vensim = vensimHelpers[1];
			} catch (Throwable e) {
				log.error("An exception was thrown when initializing Vensim, try: " + i, e);
			}
		}
		if (vensim == null) {
			throw new PangaeaException("Can't initialize vensim");
		}
	}
	
	public VensimModelResults runTheModel(Map<String, String> inputs) throws PangaeaException {
		
		try {
			// set default values in baseline
			Map<String, String> defaultValueInputs = new HashMap<String, String>();
			for (VensimModelInputConfig inputConfig: definition.getInputs()) {
				if (inputConfig.getDefaultVal() != null) {
					defaultValueInputs.put(inputConfig.getName(), inputConfig.getDefaultVal());
					inputConfig.processInputValues(defaultValueInputs);
				}
			}
			for (Map.Entry<String, String> entry: defaultValueInputs.entrySet()) {
				baselineVensim.setVariable(entry.getKey(), entry.getValue());
			}
			baselineVensim.run();
			// process inputs
			for (VensimModelInputConfig inputConfig: definition.getInputs()) {
				inputs = inputConfig.processInputValues(inputs);
			}
			// set all not modified inputs to default values
			for (Map.Entry<String, String> defaultValEntry: defaultValueInputs.entrySet()) {
				if (! inputs.containsKey(defaultValEntry.getKey())) {
					inputs.put(defaultValEntry.getKey(), defaultValEntry.getValue());
				}
			}
			for (Map.Entry<String, String> entry: inputs.entrySet()) {
				vensim.setVariable(entry.getKey(), entry.getValue());
			}
			vensim.run();
			
			VensimModelResults vensimModelResults = new VensimModelResults(definition, vensim);
			for (VensimModelOutputConfig output: definition.getOutputs()) {
				vensimModelResults.addOutput(output, output.computeOutputValues(vensim, baselineVensim));
			}
			return vensimModelResults;
		}
		catch (VensimException e) {
			throw new PangaeaException("Exception thrown when trying to run the model", e);
		}
		
		
	}
	
	public VensimHelper getVensim() {
		return vensim;
	}

	public void stop() throws VensimException {
		if (baselineVensim != null) {
			baselineVensim.end();
			baselineVensim = null;
		}
		
		if (vensim != null) {
			vensim.end();
			vensim = null;
		}
	}
	

}