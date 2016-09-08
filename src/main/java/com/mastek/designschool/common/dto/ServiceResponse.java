package com.mastek.designschool.common.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The Class ServiceResponse.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceResponse {
	/** The success. */
	@JsonProperty("success")
	private boolean success = false;

	/** The error message. */
	@JsonProperty("errorMessage")
	private String errorMessage;

	/** The response objects. */
	@JsonProperty("result")
	private Map<String, Object> result;

	/**
	 * Instantiates a new service response.
	 */
	private ServiceResponse() {
		this.setResult(new HashMap<String, Object>());
	}

	/**
	 * Instantiates a new service response.
	 * 
	 * @param success
	 *            the success
	 * @param errorMessage
	 *            the error message
	 */
	public ServiceResponse(boolean success, String errorMessage) {
		this();
		this.setSuccess(success);
		this.setErrorMessage(errorMessage);
	}

	/**
	 * Instantiates a new service response.
	 * 
	 * @param responseStatus
	 *            the response status
	 * @param errorMessage
	 *            the error message
	 * @param key
	 *            the key
	 * @param object
	 *            the object
	 */
	public ServiceResponse(boolean responseStatus, String errorMessage,
			String key, Object object) {
		this(responseStatus, errorMessage);
		this.getResult().put(key, object);
	}

	/**
	 * Gets the result.
	 * 
	 * @return the result
	 */
	public Map<String, Object> getResult() {
		return result;
	}

	/**
	 * Sets the result.
	 * 
	 * @param result
	 *            the result
	 */
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	/**
	 * Gets the error message.
	 * 
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the error message.
	 * 
	 * @param errorMessage
	 *            the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Adds the.
	 * 
	 * @param key
	 *            the key
	 * @param object
	 *            the object
	 */
	public void add(String key, Object object) {
		this.getResult().put(key, object);
	}

	/**
	 * Gets the.
	 * 
	 * @param key
	 *            the key
	 * @return the object
	 */
	public Object get(String key) {
		return this.getResult().get(key);
	}

	/**
	 * Checks if is success.
	 * 
	 * @return true, if is success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Sets the success.
	 * 
	 * @param success
	 *            the new success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * Gets the success.
	 * 
	 * @return the success
	 */
	public boolean getSuccess() {
		return success;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[responseStatus="
				+ this.success + ", errorMessage=" + this.errorMessage
				+ ", responseObjects=" + this.result + "]";
	}
}