package com.ipos.restaurant;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.ipos.restaurant.bussiness.FontBussiness;
import com.ipos.restaurant.bussiness.ImageLoaderBussiness;
import com.ipos.restaurant.bussiness.LocationBussiness;
import com.ipos.restaurant.restful.AbsRestful;
import com.ipos.restaurant.util.Log;
import io.fabric.sdk.android.Fabric;


public class ApplicationFoodBook extends Application {
	
	private RequestQueue mRequestQueue;

	private static ApplicationFoodBook sInstance;


	private ImageLoaderBussiness mImageLoader;

	private LocationBussiness mLocationBussiness;

	private FontBussiness mFontBussiness;


	private static boolean mIsVisible = false;




	@Override
	public void onCreate() {
		super.onCreate();
		Fabric.with(this, new Crashlytics());

		sInstance = this;

		initBussiness();


	}

	private void initBussiness() {

		initLoginMember();


		FoodBookTracker.init(this);
		FacebookSdk.sdkInitialize(getApplicationContext());
		mImageLoader = new ImageLoaderBussiness(this);


		mLocationBussiness = new LocationBussiness(this);
		mLocationBussiness.init();
		mFontBussiness = new FontBussiness(this);


	}

	public void initLoginMember(){


	}

	public static void setIsVisible(boolean visible) {
		mIsVisible = visible;
	}

	public static boolean getIsVisible() {
		return mIsVisible;
	}

	public  LocationBussiness getmLocationBussiness () {
		return  mLocationBussiness;
	}



	public static synchronized ApplicationFoodBook getInstance() {
		return sInstance;
	}

	public RequestQueue getRequestQueue() {
		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());

				VolleyLog.DEBUG = Constants.IS_LOG;


		}

		return mRequestQueue;
	}

	public static final String TAG = "VolleyPatternsFooD";

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		req.setRetryPolicy(AbsRestful.reTryPolicy());
		Log.i("App.addToRequestQueue() tag " + tag,
				"url restful " + req.getUrl());


		getRequestQueue().add(req);
	}

	/**
	 * Adds the specified request to the global queue using the Default TAG.
	 * 
	 * @param req
	 */
	public <T> void addToRequestQueue(Request<T> req) {
		// set the default tag if tag is empty
		req.setTag(TAG);
		req.setRetryPolicy(AbsRestful.reTryPolicy());
		Log.i("App.addToRequestQueue()", "url restful " + req.getUrl());
		getRequestQueue().add(req);
	}

	/**
	 * Cancels all pending requests by the specified TAG, it is important to
	 * specify a TAG so that the pending/ongoing requests can be cancelled.
	 * 
	 * @param tag
	 */
	public void cancelPendingRequests(Object tag) {
		Log.i("App.cancelPendingRequests()", "destroy request " + tag);
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
	public ImageLoaderBussiness getImageLoader() {
		return mImageLoader;
	}

	public FontBussiness getFontBussiness() {

		return mFontBussiness;
	}





}
