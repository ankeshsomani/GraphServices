package com.mastek.designschool.common.service;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.mastek.designschool.common.constants.GraphServiceConstants;
import com.mastek.designschool.common.constants.Labels;
import com.mastek.designschool.common.constants.RelationshipTypes;
import com.mastek.designschool.common.dto.CreateLoanServiceRequest;
import com.mastek.designschool.common.dto.CustomerSuspicionIndex;
import com.mastek.designschool.common.dto.ServiceResponse;
import com.mastek.designschool.common.dto.SuspicionIndexResponse;

@Component
public class GraphService {

	public ServiceResponse createLoan(CreateLoanServiceRequest createLoanServiceRequest) {
		ServiceResponse serviceResponse = null;
		// CREATE NODES
		Connection con = null;
		try {
			con = getDBConnection();
			
			
			
			HashMap<String, Object> custProperties = ServiceUtils
					.constructCustomerNodeProperties(createLoanServiceRequest);
			if (!ServiceUtils.findNode(con, Labels.CUSTOMER, custProperties))
				ServiceUtils.createNode(con, Labels.CUSTOMER, custProperties);

			HashMap<String, Object> addressProperties = ServiceUtils
					.constructAddressProperties(createLoanServiceRequest);
			if (!ServiceUtils.findNode(con, Labels.ADDRESS, addressProperties))
				ServiceUtils.createNode(con, Labels.ADDRESS, addressProperties);

			HashMap<String, Object> emailProperties = ServiceUtils.constructEmailProperties(createLoanServiceRequest);
			if (!ServiceUtils.findNode(con, Labels.EMAILADDR, emailProperties))
				ServiceUtils.createNode(con, Labels.EMAILADDR, emailProperties);
			
			HashMap<String, Object> pinProperties = ServiceUtils.constructPinProperties(createLoanServiceRequest);
			if (!ServiceUtils.findNode(con, Labels.PIN, pinProperties))
				ServiceUtils.createNode(con, Labels.PIN, pinProperties);
			
			HashMap<String, Object> employerProperties = ServiceUtils.constructEmployerProperties(createLoanServiceRequest);
			if (!ServiceUtils.findNode(con, Labels.EMPLOYER, employerProperties))
				ServiceUtils.createNode(con, Labels.EMPLOYER, employerProperties);
			
			HashMap<String, Object> bankProperties = ServiceUtils.constructBankProperties(createLoanServiceRequest);
			if (!ServiceUtils.findNode(con, Labels.BANK_NAME, bankProperties))
				ServiceUtils.createNode(con, Labels.BANK_NAME, bankProperties);
			
			HashMap<String, Object> bankAccProperties = ServiceUtils.constructBankAccProperties(createLoanServiceRequest);
			if (!ServiceUtils.findNode(con, Labels.BANK_ACCOUNT_NO, bankAccProperties))
				ServiceUtils.createNode(con, Labels.BANK_ACCOUNT_NO, bankAccProperties);
			
			HashMap<String, Object> phoneProperties = ServiceUtils.constructPhoneProperties(createLoanServiceRequest);
			if (!ServiceUtils.findNode(con, Labels.PHONENUMBER, phoneProperties))
				ServiceUtils.createNode(con, Labels.PHONENUMBER, phoneProperties);

			// CREATE RELATIONSHIPS
 
			ServiceUtils.createRelationship(con, Labels.CUSTOMER, Labels.ADDRESS, custProperties, addressProperties,
					RelationshipTypes.STAYS_AT);
			ServiceUtils.createRelationship(con, Labels.CUSTOMER, Labels.EMAILADDR, custProperties, emailProperties,
					RelationshipTypes.HAS_EMAIL_ADDR);
			ServiceUtils.createRelationship(con, Labels.CUSTOMER, Labels.PHONENUMBER, custProperties, phoneProperties,
					RelationshipTypes.HAS_PHONE_NO);
			ServiceUtils.createRelationship(con, Labels.CUSTOMER, Labels.PIN, custProperties, pinProperties,
					RelationshipTypes.HAS_PIN);
			ServiceUtils.createRelationship(con, Labels.CUSTOMER, Labels.EMPLOYER, custProperties, employerProperties,
					RelationshipTypes.WORKS_FOR);
			ServiceUtils.createRelationship(con, Labels.CUSTOMER, Labels.BANK_ACCOUNT_NO, custProperties, bankAccProperties,
					RelationshipTypes.HAS_ACCOUNTNO);
			ServiceUtils.createRelationship(con, Labels.CUSTOMER, Labels.BANK_NAME, custProperties, bankProperties,
					RelationshipTypes.HAS_ACCOUNT_IN);
			
			loadSuspicionMatrix(con);

		}

		catch (SQLException sql) {
			sql.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Completed");
		serviceResponse = new ServiceResponse(true, "success");

		return serviceResponse;
	}

	private void loadSuspicionMatrix(Connection con) {
		
		
	}

	private Connection getDBConnection() throws SQLException {
		Connection con = null;

		Driver driver = new org.neo4j.jdbc.Driver();
		DriverManager.registerDriver(driver);
		con = DriverManager.getConnection(GraphServiceConstants.DATABASE_URL, GraphServiceConstants.USERNAME,
				GraphServiceConstants.PASSWORD);
		if (con == null) {
			System.out.println("NULL CONNECTION");
		}
		return con;
	}

	public ServiceResponse getAllCustomers() {
		ServiceResponse serviceResponse = null;
		Connection con =null;
		try {
			con = getDBConnection();
		}
		catch (SQLException sql) {
			sql.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return serviceResponse;
	}

	public SuspicionIndexResponse getSuspicionIndex() {
		
	/*	CustomerSuspicionIndex cust1=new CustomerSuspicionIndex();
		cust1.setCustomerName("Anil Sharma");
		cust1.setOverallSuspicionIndex(19.0);
		ArrayList<String> rules= new ArrayList<String>();
		rules.add("Same Address");
		rules.add("Same Email");
		cust1.setRules(rules);
	
		
		CustomerSuspicionIndex cust2=new CustomerSuspicionIndex();
		cust2.setCustomerName("Anmit Sharma");
		cust2.setOverallSuspicionIndex(59.0);
		ArrayList<String> rules2= new ArrayList<String>();
		rules2.add("Same Bank");
		rules2.add("Same Email");
		cust1.setRules(rules);
		ArrayList<CustomerSuspicionIndex> suspList=new ArrayList<CustomerSuspicionIndex>();
		suspList.add(cust1);
		suspList.add(cust2);*/
		SuspicionIndexResponse response=new SuspicionIndexResponse();
		Connection con=null;
		HashMap<String, CustomerSuspicionIndex> suspicionList=new HashMap<String, CustomerSuspicionIndex>();
		ArrayList<CustomerSuspicionIndex> suspList=new ArrayList<CustomerSuspicionIndex>();
		try {
			con = getDBConnection();
			
		SuspicionRulesUtil.runRules(suspicionList, con);
		Iterator<Entry<String, CustomerSuspicionIndex>> iterator = suspicionList.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, CustomerSuspicionIndex> temp=iterator.next();
			CustomerSuspicionIndex suspicionIndex = temp.getValue();
			suspList.add(suspicionIndex);
		}
		response.setCustomerSuspicionIndexList(suspList);
		}
		catch(SQLException sqe){
			sqe.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return response;
		
	}

	// CUSTOMER SUSPICION INDEX
	//GET ALL CUSTOMERS
	//A CUSTOMER MAY SATISFY MORE THAN ONE RULE
	//FIRST RULE 10% SUSPICION
	//SECOND RULE 30% SUSPICION 
	//OVERALL SUSPICION 30%
	//BUT WE WILL DISPLAY BOTH THE CATEGORIES WHERE WE SUSPECTED CUSTOMERS
	//STORE CUSTOMERS WITH INDEX AND AN SUSPICION INDEX OBJECT
	//LOAD ALL RULES
	//RUN EVERY RULE
		//WHEN EVERY RULE RUNS MODIFY THE CUSTOMER OBJECT STORED IN HASHMAP USING INDEX
	
	
	
	// CHECK IF CUSTOMER SHARES HIS ADDRESS WITH SOME OTHER CUSTOMER
	// CHECK IF CUSTOMER SHARES HIS EMAIL WITH SOME OTHER CUSTOMER
	// CHECK IF CUSTOMER SHARES HIS PHONENUMBER WITH SOME OTHER CUSTOMER
	// CHECK IF CUSTOMER SHARES HIS ADDRESS AND EMAIL BOTH WITH SOME OTHER
	// CUSTOMER

}
