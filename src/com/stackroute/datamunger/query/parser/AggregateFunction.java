package com.stackroute.datamunger.query.parser;

/* This class is used for storing name of field, aggregate function for 
 * each aggregate function
 * generate getter and setter for this class,
 * Also override toString method
 * */

public class AggregateFunction {
		private String fieldParameter,functionParameter;




	// Write logic for constructor
	public AggregateFunction(String field, String function) {
		this.fieldParameter=field;
		this.functionParameter=function;
	}

	public String getFieldParameter() {
		return fieldParameter;
	}

	public void setFieldParameter(String fieldParameter) {
		this.fieldParameter = fieldParameter;
	}

	public String getFunctionParameter() {
		return functionParameter;
	}

	public void setFunctionParameter(String functionParameter) {
		this.functionParameter = functionParameter;
	}

	@Override
	public String toString() {
		return "AggregateFunction{" +
				"fieldParameter='" + fieldParameter + '\'' +
				", functionParameter='" + functionParameter + '\'' +
				'}';
	}
}