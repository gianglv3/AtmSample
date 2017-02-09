package com.ipos.restaurant.paser;

import com.android.volley.Response.Listener;

public class ListenerRefresh<T> implements Listener<T> {

	private boolean refresh = false;

	private int tabID = 0;

	public int getTabid() {
		return tabID;
	}

	public void setTabid(int c_id) {
		this.tabID = c_id;
	}

	public void setRefresh(boolean refe) {
		refresh = refe;
	}

	public boolean getRefresh() {
		return refresh;
	}

	@Override
	public void onResponse(T response) {
		// TODO Auto-generated method stub

	}

}
