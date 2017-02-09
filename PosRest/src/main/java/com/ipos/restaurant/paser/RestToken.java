package com.ipos.restaurant.paser;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RestToken extends AbsResultData implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@SerializedName("Token")
	private String Token = "0";

	public String getToken() {
		return Token;
	}

	public void setData(String Token) {
		this.Token = Token;
	}

	public boolean isSuccess() {
		if ("0".equals(Token)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "RestToken {data=" + Token + "} error { " + getError() + "}";
	}

}