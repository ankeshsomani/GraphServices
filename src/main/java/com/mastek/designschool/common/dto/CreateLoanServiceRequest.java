package com.mastek.designschool.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateLoanServiceRequest {
	
	private String title;
	
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String postcode;
	private String addressLine1;
	private String addressLine2;
	private String niNumber;	
	private String town;
	private String county;
	private String email;
	private String pin;

	private Long homeNumber;
	private Long mobileNumber;
	private String status;
	private String nameOfEmployer;
	private Integer monthsAtEmployment;
	public String getNiNumber() {
		return niNumber;
	}
	public void setNiNumber(String niNumber) {
		this.niNumber = niNumber;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getHomeNumber() {
		return homeNumber;
	}
	public void setHomeNumber(Long homeNumber) {
		this.homeNumber = homeNumber;
	}
	public Long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNameOfEmployer() {
		return nameOfEmployer;
	}
	public void setNameOfEmployer(String nameOfEmployer) {
		this.nameOfEmployer = nameOfEmployer;
	}
	public Integer getMonthsAtEmployment() {
		return monthsAtEmployment;
	}
	public void setMonthsAtEmployment(Integer monthsAtEmployment) {
		this.monthsAtEmployment = monthsAtEmployment;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankIbanCode() {
		return bankIbanCode;
	}
	public void setBankIbanCode(String bankIbanCode) {
		this.bankIbanCode = bankIbanCode;
	}
	public Long getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(Long bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public Integer getMonthsWithBank() {
		return monthsWithBank;
	}
	public void setMonthsWithBank(Integer monthsWithBank) {
		this.monthsWithBank = monthsWithBank;
	}
	public Integer getLoanRequested() {
		return loanRequested;
	}
	public void setLoanRequested(Integer loanRequested) {
		this.loanRequested = loanRequested;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	private String bankName;
	private String bankIbanCode;
	private Long bankAccountNumber;
	private Integer monthsWithBank;
	private Integer loanRequested;
}
