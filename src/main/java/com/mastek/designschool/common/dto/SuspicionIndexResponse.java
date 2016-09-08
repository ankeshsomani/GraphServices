package com.mastek.designschool.common.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SuspicionIndexResponse {
	
	@JsonProperty(value="suspectedList")
	private List<CustomerSuspicionIndex> customerSuspicionIndexList;

	public List<CustomerSuspicionIndex> getCustomerSuspicionIndexList() {
		return customerSuspicionIndexList;
	}

	public void setCustomerSuspicionIndexList(List<CustomerSuspicionIndex> customerSuspicionIndexList1) {
		customerSuspicionIndexList = customerSuspicionIndexList1;
	}


	
}
