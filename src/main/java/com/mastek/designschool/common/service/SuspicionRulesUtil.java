package com.mastek.designschool.common.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.mastek.designschool.common.dto.CustomerSuspicionIndex;
import com.mastek.designschool.common.dto.FraudRing;
import com.mastek.designschool.common.dto.SuspicionRules;
import com.mastek.designschool.common.dto.SuspicionTable;

public class SuspicionRulesUtil {
	public static HashMap<String, CustomerSuspicionIndex> runRules(
		HashMap<String, CustomerSuspicionIndex> suspicionList, Connection con) throws SQLException {
		//Get connected customers and connect type using below function
		List<FraudRing> fraudList = runRule1(con);
		
		
		for (FraudRing ring : fraudList) {
			String type = ring.getConnectType();
			HashSet<String> customers = ring.getConnectedCustomers();
			//For each connected customer
			for (String customer : customers) {
				if (suspicionList.get(customer) != null) {
					modifySuspicionIndex(type, suspicionList.get(customer), customers, customer);
				} else {
					CustomerSuspicionIndex suspIndex = new CustomerSuspicionIndex();
					suspIndex.setCustomerName(customer);
					//Set rules and shared customers in object
					modifySuspicionIndex(type, suspIndex, customers, customer);
					suspicionList.put(customer, suspIndex);
				}

			}
		}
		calculateSuspicionIndex(suspicionList);
		return suspicionList;
	}

	public static HashMap<String, CustomerSuspicionIndex> calculateSuspicionIndex(
			HashMap<String, CustomerSuspicionIndex> suspicionList) {
		Iterator<Entry<String, CustomerSuspicionIndex>> iterator = suspicionList.entrySet().iterator();
		double suspIndex = 0;

		while (iterator.hasNext()) {
			suspIndex = 0;
			Entry<String, CustomerSuspicionIndex> temp=iterator.next();
			CustomerSuspicionIndex suspicionIndex = temp.getValue();
		//	CustomerSuspicionIndex suspicionIndex = (CustomerSuspicionIndex) iterator.next();
			List<String> rules = suspicionIndex.getRules();
			int count = 0;
			int pos = 0;
			int[] sharedCount = { 0, 0, 0, 0, 0, 0, 0 };
			ArrayList<String> types = new ArrayList<String>();
			for (String rule : rules) {
				String type = findSuspicionType(rule);
				types.add(type);
				pos = getPosition(type);
				List<String> customers = suspicionIndex.getSharedCustomers().get(type);
				sharedCount[pos] = customers.size();
				count++;
			}
			if (count <= 2) {
				// Use matrix and multipliers to calculate suspicion index
				if (count == 2) {
					suspIndex = SuspicionTable.suspicionMatrix[getPosition(types.get(0))][getPosition(types.get(1))];
				} else if (count == 1) {
					int pos2 = getPosition(types.get(0));
					suspIndex = SuspicionTable.suspicionMatrix[pos2][pos2];
				} else {
					suspIndex = 0;
				}
			} else {
				for (int a = 0; a < types.size(); a++) {
					int pos2 = getPosition(types.get(a));
					//HashMap<String, List<String>> temp1=suspicionIndex.getSharedCustomers();
					//int count1=(temp1.get(types.get(a))).size();
					double multiplier=getMultiplier(types.get(a),sharedCount[pos2]);
					suspIndex = suspIndex + (SuspicionTable.suspicionMatrix[pos2][pos2]) * (multiplier);
				}
			}
			suspicionIndex.setOverallSuspicionIndex(suspIndex);
		}
		return suspicionList;
	}

	private static  double getMultiplier(String type, int count) {
		double multiplier=1;
		if(type.equals("ADDRESS")){
			multiplier=SuspicionTable.ADDRESS_MULTIPLIER[count-1];
		}
		else if(type.equals("EMAILADDR")){
			multiplier=SuspicionTable.EMAIL_ADD_MULTIPLIER[count-1];
		}
		else if(type.equals("PHONENUMBER")){
			multiplier=SuspicionTable.PHONE_NO_MULTIPLIER[count-1];
		}
		
		return multiplier;
	}

	private static void modifySuspicionIndex(String type, CustomerSuspicionIndex customerSuspicionIndex,
			HashSet<String> customers, String customer) {
		List<String> rules = customerSuspicionIndex.getRules();
		if(rules==null){
			rules=new ArrayList<String>();
			customerSuspicionIndex.setRules(rules);
		}
		String mappedRule = findMappedRule(type);
		StringBuilder suspicionReason=new StringBuilder("");
		if(customerSuspicionIndex.getSuspicionReason() !=null){
			suspicionReason.append(customerSuspicionIndex.getSuspicionReason());
			suspicionReason.append(",");
		}
		HashMap<String, List<String>> sharedCust;
		if (!rules.contains(mappedRule)) {
			customerSuspicionIndex.getRules().add(mappedRule);
			suspicionReason.append(type);
			suspicionReason.append("-");
		}
		sharedCust = customerSuspicionIndex.getSharedCustomers();
		if(sharedCust==null){
			sharedCust=new HashMap<String, List<String>>();
			customerSuspicionIndex.setSharedCustomers(sharedCust);
			
		}
		if(sharedCust.get(type)==null){
			sharedCust.put(type, new ArrayList<String>());
		}
		int count=0;
		suspicionReason.append("(");
		for (String temp : customers) {
			//System.out.println("type is :-"+type);
			//System.out.println("temp is :-"+temp);
			if (!temp.equals(customer)) {
				
				sharedCust.get(type).add(temp);
				if(count >0){
					suspicionReason.append(",");
				}
				suspicionReason.append(temp);
				count++;
			}
		}
		suspicionReason.append(")");
		//suspicionReason.append(count);
		customerSuspicionIndex.setSuspicionReason(suspicionReason.toString());

	}
	private static List<FraudRing> runRule1(Connection con) throws SQLException {
		List<FraudRing> fraudList = new ArrayList<FraudRing>();
		String sameAddressQuery = "MATCH (accountHolder:CUSTOMER)-[]->(contactInformation) WITH contactInformation, count(accountHolder) AS RingSize MATCH (contactInformation)<-[]-(accountHolder) WITH collect(accountHolder.name) AS AccountHolders, contactInformation, RingSize WHERE RingSize > 1 RETURN AccountHolders AS FraudRing, labels(contactInformation) AS ContactType, RingSize ORDER BY RingSize DESC";
		try (PreparedStatement stmt = con.prepareStatement(sameAddressQuery.toString())) {
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				FraudRing fraudRing = new FraudRing();
				String type = "";

				Collection<String> contactTypes = (Collection<String>) rs.getObject("ContactType");
				Collection<String> customers = (Collection<String>) rs.getObject("FraudRing");

				for (String s : contactTypes) {
					type = s;
				}
				fraudRing.setConnectType(type);

				fraudRing.setConnectedCustomers(customers);
				fraudList.add(fraudRing);

			}

		}
		return fraudList;

	}

	private static int getPosition(String type) {
		int pos = 0;
		switch (type) {

		case "ADDRESS":
			pos = SuspicionTable.ADDRESS;
			break;
		case "EMAILADDR":
			pos = SuspicionTable.EMAIL_ADD;
			break;
		case "PHONENUMBER":
			pos = SuspicionTable.PHONE_NO;
			break;
		case "PIN":
			pos = SuspicionTable.PIN;
			break;
		case "BANK_ACC_NO":
			pos = SuspicionTable.BANK_ACC_NO;
			break;
		case "BANK_NAME":
			pos = SuspicionTable.BANK_NAME;
			break;
		case "EMPLOYER":
			pos = SuspicionTable.EMPLOYER_NAME;
			break;

		}
		return pos;
	}

	private static String findSuspicionType(String mappedRule) {
		String type = "";
		switch (mappedRule) {

		case SuspicionRules.SHARE_ADDRESS:
			type = "ADDRESS";
			break;
		case SuspicionRules.SHARE_BANK_ACCT_NO:
			type = "BANK_ACC_NO";
			break;
		case SuspicionRules.SHARE_BANK_NAME:
			type = "BANK_NAME";
			break;
		case SuspicionRules.SHARE_EMAILADD:
			type = "EMAILADDR";
			break;
		case SuspicionRules.SHARE_EMPLOYER_NAME:
			type = "EMPLOYER";
			break;
		case SuspicionRules.SHARE_PHONENO:
			type = "PHONENUMBER";
			break;
		case SuspicionRules.SHARE_PIN:
			type = "PIN";
			break;

		}
		return type;
	}

	private static String findMappedRule(String type) {
		String mappedRule = "";
		switch (type) {

		case "ADDRESS":
			mappedRule = SuspicionRules.SHARE_ADDRESS;
			break;
		case "EMAILADDR":
			mappedRule = SuspicionRules.SHARE_EMAILADD;
			break;
		case "PHONENUMBER":
			mappedRule = SuspicionRules.SHARE_PHONENO;
			break;
		case "BANK_NAME":
			mappedRule = SuspicionRules.SHARE_BANK_NAME;
			break;
		case "EMPLOYER":
			mappedRule = SuspicionRules.SHARE_EMPLOYER_NAME;
			break;
		case "BANK_ACCOUNT_NO":
			mappedRule = SuspicionRules.SHARE_BANK_ACCT_NO;
			break;

		}
		return mappedRule;
	}

}
