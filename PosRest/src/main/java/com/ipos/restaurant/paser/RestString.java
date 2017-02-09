package com.ipos.restaurant.paser;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class RestString extends AbsResultData implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@SerializedName("data")
	private String data;

	public String getData(Context mContext) {
//		if (isValidLogin()){
//			DmMember.logout(mContext);
//		}
		return data;
	}
	

	public String getData( ) {
//		if (isValidLogin()){
//			DmMember.logout(ApplicationFoodBook.getInstance());
//		}
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isSuccess() {
		//if (data!=null&&"1".equals(data)) {
		if (data!=null) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "RestString {data=" + data + "} error { " + getError() + "}";
	}

}