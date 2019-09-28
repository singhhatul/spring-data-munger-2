package com.stackroute.datamunger.query.parser;

/*There are total 4 DataMungerTest file:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 4 methods
 * a)getBaseQuery()  b)getFileName()  c)getOrderByClause()  d)getGroupByFields()
 * 
 * Once you implement the above 4 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 2 methods
 * a)getFields() b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getRestrictions()  b)getLogicalOperators()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

import java.util.ArrayList;

import java.util.List;

public class QueryParser {

	private QueryParameter queryParameter = new QueryParameter();

	/*
	 * This method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString) {

		queryParameter.setFileName(getFileName(queryString));
		queryParameter.setBaseQuery(getBaseQuery(queryString));
		queryParameter.setOrderByFileds(getOrderByFields(queryString));
		queryParameter.setGroupByfields(getGroupByfields(queryString));
		queryParameter.setFields(getFields(queryString));
		queryParameter.setLogicalOperators(getLogicalOperators(queryString));
		queryParameter.setAggregateFunctions(getAggregateFunctions(queryString));
		queryParameter.setRestrictions(getRestrictions(queryString));

		return queryParameter;
	}

	/*
	 * Extract the name of the file from the query. File name can be found after the
	 * "from" clause.
	 */
	public String getFileName(String queryString){
		queryString=queryString.toLowerCase();

		String[] splittedQuery=queryString.split("from");
		queryString=splittedQuery[1];

		String[] splittedFileName=queryString.split(" ");
		queryString=splittedFileName[1];
		return queryString;
	}


	/*
	 * 
	 * Extract the baseQuery from the query.This method is used to extract the
	 * baseQuery from the query string. BaseQuery contains from the beginning of the
	 * query till the where clause
	 */
	public String getBaseQuery(String queryString) {
		queryString=queryString.toLowerCase();

			String[] base = queryString.split(" where");
			queryString = base[0];

			return queryString;

	}
	/*
	 * extract the order by fields from the query string. Please note that we will
	 * need to extract the field(s) after "order by" clause in the query, if at all
	 * the order by clause exists. For eg: select city,winner,team1,team2 from
	 * data/ipl.csv order by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one order by fields.
	 */
	public List<String> getOrderByFields(String queryString)
	{
		queryString=queryString.toLowerCase();

		if(queryString.contains("order by")){
		String[] base=queryString.split("order by ");
		queryString=base[1];

		ArrayList<String> orderByResult= new ArrayList<String>();

		orderByResult.add(queryString);

		return orderByResult;
		}

		return null;
	}
	/*
	 * Extract the group by fields from the query string. Please note that we will
	 * need to extract the field(s) after "group by" clause in the query, if at all
	 * the group by clause exists. For eg: select city,max(win_by_runs) from
	 * data/ipl.csv group by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one group by fields.
	 */
	public List<String> getGroupByfields(String queryString) {
		queryString = queryString.toLowerCase();
		if (queryString.contains("group by")) {
			String[] base = queryString.split("group by ");
			queryString = base[1];
			String[] baseQuery = queryString.split(" ");
			queryString = baseQuery[0];
			ArrayList<String> groupByResult = new ArrayList<>();
			groupByResult.add(queryString);
			return groupByResult;
		} else {
			ArrayList<String> groupByResult = new ArrayList<>();
			groupByResult.add(queryString);
			return groupByResult;
		}
	}


	/*
	 * Extract the selected fields from the query string. Please note that we will
	 * need to extract the field(s) after "select" clause followed by a space from
	 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
	 * query mentioned above, we need to extract "city" and "win_by_runs". Please
	 * note that we might have a field containing name "from_date" or "from_hrs".
	 * Hence, consider this while parsing.
	 */
	public List<String> getFields(String queryString) {
		ArrayList<String> field = new ArrayList<String>();
		queryString=queryString.toLowerCase();
		String[] selectFields=queryString.split(" from");
		queryString=selectFields[0];
		String[] fields=queryString.split("select ");
		queryString=fields[1];
		String[] finalFields=queryString.split(",");

		for(int i=0;i<finalFields.length;i++){
			field.add(finalFields[i]);
		}
		return field;



	}
	/*
	 * Extract the conditions from the query string(if exists). for each condition,
	 * we need to capture the following: 1. Name of field 2. condition 3. value
	 * 
	 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
	 * where season >= 2008 or toss_decision != bat
	 * 
	 * here, for the first condition, "season>=2008" we need to capture: 1. Name of
	 * field: season 2. condition: >= 3. value: 2008
	 * 
	 * the query might contain multiple conditions separated by OR/AND operators.
	 * Please consider this while parsing the conditions.
	 * 
	 */

	public ArrayList<Restriction> getRestrictions(String queryString) {
		String[] conditions = null;
		ArrayList<Restriction> restrictionList = null;
		if (queryString.contains("where")) {
			String conditionQuery = queryString.split("where|group by|order by")[1].trim();
			conditions = conditionQuery.split(" and | or ");
			restrictionList = new ArrayList<Restriction>();

			for (int i = 0; i < conditions.length; i++) {
				if (conditions[i].contains("'")) {
					String fieldName = conditions[i].split(" ")[0];
					String[] restriction = conditions[i].split("'");
					Restriction r = new Restriction(fieldName.trim(), restriction[1].trim(), restriction[0].trim().split(" ")[1]);
					restrictionList.add(r);
				} else {
					String[] restriction = conditions[i].split(" ");
					Restriction r = new Restriction(restriction[0].trim(), restriction[2].trim(), restriction[1].trim());
					restrictionList.add(r);
				}
			}
		}

		return restrictionList;
	}

	/*
	 * Extract the logical operators(AND/OR) from the query, if at all it is
	 * present. For eg: select city,winner,team1,team2,player_of_match from
	 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
	 * bangalore
	 * 
	 * The query mentioned above in the example should return a List of Strings
	 * containing [or,and]
	 */
	public List<String> getLogicalOperators(String queryString) {
		List<String> logicalString = null;
		String[] query = queryString.split(" ");
		String getResult = "";
		String[] arrayString;
		if (queryString.contains("where ")) {
			logicalString = new ArrayList<String>();
			for (int i = 0; i < query.length; i++) {
				if (query[i].matches("and|or|not")) {
					getResult += query[i] + " ";
				}
			}
			arrayString = getResult.toString().split(" ");
			for (int i = 0; i < arrayString.length; i++) {
				logicalString.add(arrayString[i]);
			}
		}
		return logicalString;
	}
	/*
	 * Extract the aggregate functions from the query. The presence of the aggregate
	 * functions can determined if we have either "min" or "max" or "sum" or "count"
	 * or "avg" followed by opening braces"(" after "select" clause in the query
	 * string. in case it is present, then we will have to extract the same. For
	 * each aggregate functions, we need to know the following: 1. type of aggregate
	 * function(min/max/count/sum/avg) 2. field on which the aggregate function is
	 * being applied.
	 * 
	 * Please note that more than one aggregate function can be present in a query.
	 * 
	 * 
	 */

	public ArrayList<AggregateFunction> getAggregateFunctions(String queryString) {
		queryString = queryString.toLowerCase();
		queryString = queryString.split("from")[0].trim();
		String selectString = queryString.split("select")[1].trim();
		String[] aggregateArray = selectString.split(",");
		ArrayList<String> aggregrateList = new ArrayList<String>();
		ArrayList<AggregateFunction> list = new ArrayList<AggregateFunction>();
		for (int i = 0; i < aggregateArray.length; i++) {
			if (aggregateArray[i].contains("(")) {
				aggregrateList.add(aggregateArray[i].trim());
			}
		}
		int listSize = aggregrateList.size();
		if (listSize == 0) {
			return null;
		} else {
			for (int i = 0; i < listSize; i++) {
				String[] functionArray = aggregrateList.get(i).split("\\(|\\)");
				AggregateFunction af = new AggregateFunction(functionArray[1], functionArray[0]);
				list.add(af);
			}
			return list;
		}
	}


}