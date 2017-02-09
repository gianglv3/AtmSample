/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ipos.restaurant.paser;

import com.google.gson.annotations.SerializedName;
import com.ipos.restaurant.model.InfoModel;

import java.io.Serializable;


/**
 * 
 * @author gianglv3
 */
public class RestInfoModel extends AbsResultData implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@SerializedName("data")
	private InfoModel data;

	public InfoModel getData() {
		return data;
	}

	public void setData(InfoModel data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RestInfoModel [data=" + data + "] error " + getError();
	}

}
