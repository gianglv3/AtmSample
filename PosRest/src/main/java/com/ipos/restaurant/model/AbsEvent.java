package com.ipos.restaurant.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class AbsEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SerializedName("Is_Active")
	private int Is_Active=0;
 
	
	public boolean isActive() {
		if (Is_Active==1) return true;
		return false;
	}
}
