package com.bth.anomalydetection.beans;

import java.util.Map;

/**
 * This class represents an ajax form request response.
 */
public class StatusBean {
	
	public final static int OK_FLAG = 0;
	public final static int ERROR_FLAG = 1;
	
	private String field;
	private String message;
	private int flag;
	private Map<String,String> parameters;
	
	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public StatusBean(int flag) {
		this.flag = flag;
	}
	
	public StatusBean(String field, String message, int flag, Map<String, String> map) {
		this.field = field;
		this.message = message;
		this.flag = flag;
		this.parameters = map;
	}
	
	public StatusBean(String field, String message, int flag) {
		this.field = field;
		this.message = message;
		this.flag = flag;
	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}