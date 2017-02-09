/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ipos.restaurant.paser;

import com.google.gson.annotations.SerializedName;
import com.ipos.restaurant.model.ErrorMessage;

import java.io.Serializable;


/**
 * 
 * @author gianglv3
 */
public class AbsResultData implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	private int VALID_TOKEN=1400;
	
	
	@SerializedName("error")
	private ErrorMessage error;

	@SerializedName("is_next")
	private int isNextData=0;

	public void setError(ErrorMessage error) {
		this.error = error;
	}

	public ErrorMessage getError() {
		//ErrorMessage error = new ErrorMessage();
		return error;
	}


	public int getIsNextData() {
		return isNextData;
	}

	public void setIsNextData(int isNextData) {
		this.isNextData = isNextData;
	}


	public boolean isNext() {
		if (isNextData==1) return  true;
		return  false;
	}

	public boolean isValidLogin() {
		if (getError()!=null&&getError().getCode()==VALID_TOKEN) {
			return true;
		}
		return false;
	}
}
