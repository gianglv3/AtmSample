package com.ipos.restaurant.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class InfoModel implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@SerializedName("Message")
	protected String message;

	@SerializedName("Code")
	protected int code;

	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return "ErrorMessage{[message = " + message + ", code=" + code + "}";
	}

}
