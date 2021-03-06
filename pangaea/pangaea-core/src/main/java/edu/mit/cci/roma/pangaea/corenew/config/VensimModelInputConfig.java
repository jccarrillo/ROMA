package edu.mit.cci.roma.pangaea.corenew.config;

import java.util.Map;

import org.simpleframework.xml.Element;

import edu.mit.cci.roma.pangaea.corenew.PangaeaPropsUtils;
import edu.mit.cci.roma.pangaea.corenew.inputs.InputValue;
import edu.mit.cci.roma.pangaea.corenew.inputs.StringInputValue;
import edu.mit.cci.roma.pangaea.corenew.processors.inputs.InputProcessor;

public class VensimModelInputConfig extends BaseVensimVariableInfo {

	@Element(required=false)
	private VensimModelInputProcessorConfig processorConfig;
	
	private InputProcessor processor;
	
	public Map<String, InputValue> processInputValues(Map<String, InputValue> inputValues) {
		Map<String, InputValue> ret = inputValues;
		InputProcessor processor = getProcessor();
		if (processor == null) {
			// do nothing as we don't need to process the value
			String varName = getVensimContextVariable() == null ? getName() : getVensimContextVariable(); 
			if (getDefaultVal() != null && !inputValues.containsKey(varName) ) {
				inputValues.put(varName, new StringInputValue(varName, getDefaultVal()));
			}
		}
		else {
			ret = processor.processInputValues(inputValues);
		}
		
		if (getVensimContextVariable() != null && ret.containsKey(getName())) {
			// replace parameter for getName() with getVensimContextVariable as this is the variable that we need to set
			ret.put(getVensimContextVariable(), ret.get(getName()));
			ret.remove(getName());
		}
		return ret;
		
	}
	
	
	private InputProcessor getProcessor() {
		if (processorConfig != null && processor == null) {
			processor = PangaeaPropsUtils.getInputProcessorForName(processorConfig.getName());
			processor.init(getVensimContextVariable() == null ? getName() : getVensimContextVariable(), getName(), processorConfig.getConfiguration());
		}
		return processor;
	}
	

}
