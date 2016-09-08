package com.mastek.designschool.common.dto;

import java.util.Collection;
import java.util.HashSet;

public class FraudRing {

	private HashSet<String> connectedCustomers;
	
	public HashSet<String> getConnectedCustomers() {
		return connectedCustomers;
	}
	public void setConnectedCustomers(Collection<String> connectedCustomers1) {
		connectedCustomers=new HashSet<String>();
		for(String temp:connectedCustomers1){
			connectedCustomers.add(temp);
		}
		
	}
	public String getConnectType() {
		return connectType;
	}
	public void setConnectType(String connectType) {
		this.connectType = connectType;
	}
	private String connectType;
}
