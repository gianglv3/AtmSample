package com.ipos.restaurant.restful;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.ipos.restaurant.ApplicationFoodBook;
import com.ipos.restaurant.util.SharedPref;


public class AbsRestful {

	protected Context context;
	protected SharedPref config;
	private static  int time_out_retry = 30 * 1000;
	private static int mMaxNumRetries = 0;
	private  static  float mBackoffMultiplier = 1.0f;

	protected static  String GMAP_KEY="AIzaSyB0Mhkwuxhc8-D6_TyDwmR9jmgopq74Odc";
	public AbsRestful() {
		init();
	};

	public AbsRestful(Context context) {
		config = new SharedPref(context);
		init();
	}

	private  void init() {

	}


	protected <T> void addReq(VolleyRequest req, String TAG) {

		ApplicationFoodBook.getInstance().addToRequestQueue(req, TAG);
	}

	public static DefaultRetryPolicy reTryPolicy() {
		return new DefaultRetryPolicy(time_out_retry, mMaxNumRetries,
				mBackoffMultiplier);
	}


 
}

