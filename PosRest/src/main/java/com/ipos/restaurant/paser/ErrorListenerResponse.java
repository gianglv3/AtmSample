package com.ipos.restaurant.paser;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

public class ErrorListenerResponse implements ErrorListener {

	private boolean refresh = false;
	private int c_id = 0;

	public int getTabid() {
		return c_id;
	}

	public void setTabid(int c_id) {
		this.c_id = c_id;
	}

	public void setRefresh(boolean refe) {
		refresh = refe;
	}

	public boolean getRefresh() {
		return refresh;
	}

	@Override
	public void onErrorResponse(VolleyError error) {

	}

}
