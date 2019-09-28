package com.stackroute.datamunger.query.parser;

import java.util.List;

/* 
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */

public class QueryParameter {
	private String queryString;
	private String fileName;
	private String baseQuery;
	private String queryType;
	private List<Restriction> restrictions;
	private List<String> logicalOperators;
	private List<String> fields;
	private List<AggregateFunction> aggregateFunctions;
	private List<String> groupByfields;
	private List<String> orderByFileds;

	public String getFileName() {
		return this.fileName;
	}


	public String getBaseQuery() {
		return this.baseQuery;
	}

	public List<Restriction> getRestrictions() {
		return this.restrictions;
	}

	public List<String> getLogicalOperators() {
		return this.logicalOperators;
	}

	public List<String> getFields() {
		return this.fields;
	}

	public List<AggregateFunction> getAggregateFunctions() {
		return this.aggregateFunctions;
	}

	public List<String> getGroupByFields() {
		return this.groupByfields;
	}

	public List<String> getOrderByFields() {
		return this.orderByFileds;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setBaseQuery(String baseQuery) {
		this.baseQuery = baseQuery;
	}

	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	public void setLogicalOperators(List<String> logicalOperators) {
		this.logicalOperators = logicalOperators;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public void setAggregateFunctions(List<AggregateFunction> aggregateFunctions) {
		this.aggregateFunctions = aggregateFunctions;
	}

	public List<String> getGroupByfields() {
		return groupByfields;
	}

	public void setGroupByfields(List<String> groupByfields) {
		this.groupByfields = groupByfields;
	}

	public List<String> getOrderByFileds() {
		return orderByFileds;
	}

	public void setOrderByFileds(List<String> orderByFileds) {
		this.orderByFileds = orderByFileds;
	}

	public String getQueryString() {
		return this.queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getQueryType() {
		return this.queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
}