package edu.mit.cci.roma.pangaea.servlet;

import javax.xml.bind.annotation.XmlElement;

import edu.mit.cci.roma.pangaea.core.SimulationResults;


/**
 *
 */

public class Wrapper {

	@XmlElement(name="simulationdata")
	public SimulationResults result;

	public Wrapper() {

	}

	public Wrapper(SimulationResults result) {
		this.result = result;
	}

}