package com.mastek.designschool.common.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.RelationshipType;

import com.mastek.designschool.common.constants.GraphServiceConstants;
import com.mastek.designschool.common.dto.CreateLoanServiceRequest;

public class ServiceUtils {
	public static boolean findNode(Connection con, Label label, HashMap<String, Object> prop) throws SQLException {
		
		StringBuffer query = new StringBuffer();
		boolean returnedVal = false;
		query.append("MATCH (node:");
		query.append(label);
		query.append(retrieveNodeProperties(prop));
		query.append(") RETURN node");
		System.out.println(query);
		try (PreparedStatement stmt = con.prepareStatement(query.toString())) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				returnedVal = true;
				HashMap<String, Object> node = (HashMap) rs.getObject("node");
				System.out.println(node.get("firstName"));
			}
		}
		return returnedVal;
	}

	public static void createRelationship(Connection con, Label label1, Label label2, HashMap prop1, HashMap prop2,
			RelationshipType rel) throws SQLException {
		StringBuffer query = new StringBuffer();
		query.append("MATCH (node1:");
		query.append(label1);
		query.append(retrieveNodeProperties(prop1));
		query.append("),(");
		query.append("node2:");
		query.append(label2);
		query.append(retrieveNodeProperties(prop2));
		query.append(") CREATE (node1)-[r:");
		query.append(rel);
		query.append("]->(node2) RETURN r");
		System.out.println(query);

		try (PreparedStatement stmt = con.prepareStatement(query.toString())) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> relationship = (HashMap) rs.getObject("r");
				System.out.println(relationship.get("name"));
			}

		}

	}
	public static HashMap<String, Object> constructPhoneProperties(CreateLoanServiceRequest createLoanServiceRequest) {
		HashMap<String,Object> properties=new HashMap<String,Object>();
		properties.put(GraphServiceConstants.PHONENUMBER, createLoanServiceRequest.getMobileNumber());
		properties.put(GraphServiceConstants.NAME, createLoanServiceRequest.getMobileNumber());
		return properties;
	}



	public static HashMap<String, Object> constructBankProperties(CreateLoanServiceRequest createLoanServiceRequest) {
		HashMap<String,Object> properties=new HashMap<String,Object>();
		//properties.put(GraphServiceConstants.BANK_IBAN_CODE, createLoanServiceRequest.getBankIbanCode());
		properties.put(GraphServiceConstants.BANK_NAME, createLoanServiceRequest.getBankName().toUpperCase());
		//properties.put(GraphServiceConstants.BANK_ACCOUNT_NO, createLoanServiceRequest.getBankAccountNumber());
		//properties.put(GraphServiceConstants.MONTHS_WITH_BANK, createLoanServiceRequest.getMonthsWithBank());
		properties.put(GraphServiceConstants.NAME, createLoanServiceRequest.getBankName().toUpperCase());
		return properties;
	}

	public static HashMap<String, Object> constructBankAccProperties(
			CreateLoanServiceRequest createLoanServiceRequest) {
		HashMap<String,Object> properties=new HashMap<String,Object>();		
		properties.put(GraphServiceConstants.BANK_ACCOUNT_NO, createLoanServiceRequest.getBankAccountNumber());		
		properties.put(GraphServiceConstants.NAME, createLoanServiceRequest.getBankAccountNumber());
		return properties;
	}

	public static HashMap<String, Object> constructEmployerProperties(CreateLoanServiceRequest createLoanServiceRequest) {
		HashMap<String,Object> properties=new HashMap<String,Object>();
		properties.put(GraphServiceConstants.EMPLOYER_NAME, createLoanServiceRequest.getNameOfEmployer().toUpperCase());
	//	properties.put(GraphServiceConstants.MONTHS_AT_EMPLOYMENT, createLoanServiceRequest.getMonthsAtEmployment());
		properties.put(GraphServiceConstants.NAME, createLoanServiceRequest.getNameOfEmployer().toUpperCase());
		return properties;
	}



	public  static HashMap<String, Object> constructEmailProperties(CreateLoanServiceRequest createLoanServiceRequest) {
		HashMap<String,Object> properties=new HashMap<String,Object>();
		properties.put(GraphServiceConstants.EMAILADDR, createLoanServiceRequest.getEmail().toUpperCase());
		properties.put(GraphServiceConstants.NAME, createLoanServiceRequest.getEmail().toUpperCase());
		return properties;
	}
	public static HashMap<String, Object> constructAddressProperties(CreateLoanServiceRequest createLoanServiceRequest) {
		HashMap<String,Object> properties=new HashMap<String,Object>();
		properties.put(GraphServiceConstants.ADDRESSLINE1, createLoanServiceRequest.getAddressLine1().toUpperCase());
		properties.put(GraphServiceConstants.ADDRESSLINE2, createLoanServiceRequest.getAddressLine2().toUpperCase());
		properties.put(GraphServiceConstants.TOWN, createLoanServiceRequest.getTown().toUpperCase());
		properties.put(GraphServiceConstants.COUNTY, createLoanServiceRequest.getCounty().toUpperCase());
		properties.put(GraphServiceConstants.POSTCODE, createLoanServiceRequest.getPostcode().toUpperCase());
		properties.put(GraphServiceConstants.NAME, createLoanServiceRequest.getHomeNumber()+createLoanServiceRequest.getPostcode().toUpperCase());
		return properties;
	}

/*	public static HashMap<String, Object> constructPostcodeProperties(CreateLoanServiceRequest createLoanServiceRequest) {
		HashMap<String,Object> properties=new HashMap<String,Object>();
		properties.put(GraphServiceConstants.POSTCODE, createLoanServiceRequest.getPostcode());
		properties.put(GraphServiceConstants.NAME, createLoanServiceRequest.getPostcode());
		return properties;
	}*/

	public static HashMap<String, Object> constructCustomerNodeProperties(CreateLoanServiceRequest createLoanServiceRequest) {
		HashMap<String,Object> properties=new HashMap<String,Object>();
		properties.put(GraphServiceConstants.TITLE, createLoanServiceRequest.getTitle());
		properties.put(GraphServiceConstants.FIRST_NAME, createLoanServiceRequest.getFirstName().toUpperCase());
		properties.put(GraphServiceConstants.LAST_NAME, createLoanServiceRequest.getLastName().toUpperCase());
		//properties.put(GraphServiceConstants.DOB, createLoanServiceRequest.getDateOfBirth());
		//properties.put(GraphServiceConstants.LIVING_STATUS, createLoanServiceRequest.getStatus());
		//properties.put(GraphServiceConstants.LOAN_REQUESTED, createLoanServiceRequest.getLoanRequested());
		properties.put(GraphServiceConstants.NAME, createLoanServiceRequest.getFirstName().toUpperCase()+" "+createLoanServiceRequest.getLastName().toUpperCase());
		//properties.put(GraphServiceConstants.NI_NUMBER, createLoanServiceRequest.getNiNumber());
		return properties;
	}
	public static HashMap<String, Object> constructPinProperties(CreateLoanServiceRequest createLoanServiceRequest) {
		HashMap<String,Object> properties=new HashMap<String,Object>();
		properties.put(GraphServiceConstants.PIN, createLoanServiceRequest.getNiNumber().toUpperCase());	
		properties.put(GraphServiceConstants.NAME, createLoanServiceRequest.getNiNumber().toUpperCase());
		return properties;
	}
	

	public static void createNode(Connection con, Label label, HashMap<String, Object> properties)
			throws SQLException {
		StringBuffer query = new StringBuffer();
		query.append("CREATE (node:" + label);
		Iterator<String> iterator = properties.keySet().iterator();
		query.append(retrieveNodeProperties(properties));
		query.append(") RETURN node");
		System.out.println(query);
		try (PreparedStatement stmt = con.prepareStatement(query.toString())) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> node = (HashMap) rs.getObject("node");

				System.out.println(node.get("emailAddress"));
			}

		}

	}

	public static String retrieveNodeProperties(HashMap prop1) {
		Iterator<String> iterator = prop1.keySet().iterator();
		StringBuffer query = new StringBuffer();
		query.append("{");
		int count = 0;
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			if (count > 0) {
				query.append(",");
			}
			query.append(key);
			query.append(":");
			query.append('"');
			query.append(prop1.get(key));
			query.append('"');

			count++;
		}
		query.append("}");
		return query.toString();
	}

	

	

}
