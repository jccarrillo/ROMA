// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.mit.cci.roma.web;

import edu.mit.cci.roma.impl.DefaultVariable;

import java.lang.String;
import java.util.List;

privileged aspect ExcelSimulationForm_Roo_JavaBean {
    
    public byte[] ExcelSimulationForm.getFiledata() {
        return this.filedata;
    }
    
    public void ExcelSimulationForm.setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }
    
    public List<String> ExcelSimulationForm.getInputRanges() {
        return this.inputRanges;
    }
    
    public void ExcelSimulationForm.setInputRanges(List<String> inputRanges) {
        this.inputRanges = inputRanges;
    }
    
    public List<String> ExcelSimulationForm.getOutputRanges() {
        return this.outputRanges;
    }
    
    public void ExcelSimulationForm.setOutputRanges(List<String> outputRanges) {
        this.outputRanges = outputRanges;
    }
    
    public List<String> ExcelSimulationForm.getInputWorksheetNames() {
        return this.inputWorksheetNames;
    }
    
    public void ExcelSimulationForm.setInputWorksheetNames(List<String> inputWorksheetNames) {
        this.inputWorksheetNames = inputWorksheetNames;
    }
    
    public List<String> ExcelSimulationForm.getOutputWorksheetNames() {
        return this.outputWorksheetNames;
    }
    
    public void ExcelSimulationForm.setOutputWorksheetNames(List<String> outputWorksheetNames) {
        this.outputWorksheetNames = outputWorksheetNames;
    }
    
    public List<DefaultVariable> ExcelSimulationForm.getInputVars() {
        return this.inputVars;
    }
    
    public void ExcelSimulationForm.setInputVars(List<DefaultVariable> inputVars) {
        this.inputVars = inputVars;
    }
    
    public List<DefaultVariable> ExcelSimulationForm.getOutputVars() {
        return this.outputVars;
    }
    
    public void ExcelSimulationForm.setOutputVars(List<DefaultVariable> outputVars) {
        this.outputVars = outputVars;
    }
    
    public String ExcelSimulationForm.getSimulationName() {
        return this.simulationName;
    }
    
    public void ExcelSimulationForm.setSimulationName(String simulationName) {
        this.simulationName = simulationName;
    }
    
    public String ExcelSimulationForm.getSimulationDescription() {
        return this.simulationDescription;
    }
    
    public void ExcelSimulationForm.setSimulationDescription(String simulationDescription) {
        this.simulationDescription = simulationDescription;
    }
    
}