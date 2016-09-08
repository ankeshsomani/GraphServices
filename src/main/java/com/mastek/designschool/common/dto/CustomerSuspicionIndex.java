package com.mastek.designschool.common.dto;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerSuspicionIndex {
	
	private Double overallSuspicionIndex;
	@JsonIgnore
	private List<String> rules;
	private String customerId;
	private String customerName;

	@JsonIgnore
	private HashMap<String,List<String>> sharedCustomers;
	
	
	private String  suspicionReason;
	
	public String getSuspicionReason() {
		return suspicionReason;
	}
	public void setSuspicionReason(String suspicionReason) {
		this.suspicionReason = suspicionReason;
	}
	public Double getOverallSuspicionIndex() {
		return overallSuspicionIndex;
	}
	public void setOverallSuspicionIndex(Double overallSuspicionIndex) {
		this.overallSuspicionIndex = overallSuspicionIndex;
	}
	public List<String> getRules() {
		return rules;
	}
	public void setRules(List<String> rules) {
		this.rules = rules;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public HashMap<String, List<String>> getSharedCustomers() {
		return sharedCustomers;
	}
	public void setSharedCustomers(HashMap<String, List<String>> sharedCustomers) {
		this.sharedCustomers = sharedCustomers;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	
	
}
