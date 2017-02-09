package com.ipos.restaurant.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ipos.restaurant.ApplicationFoodBook;
import com.ipos.restaurant.R;
import com.ipos.restaurant.activities.BaseActivity;
import com.ipos.restaurant.bussiness.FontBussiness;
import com.ipos.restaurant.bussiness.ImageLoaderBussiness;
import com.ipos.restaurant.util.SharedPref;

public class BaseFragment extends Fragment{
	protected   final String TAG = getClass().getSimpleName();
	protected BaseActivity mActivity;
	protected SharedPref pref;
	protected ImageLoaderBussiness mImageLoader;
	protected FontBussiness mFontBussiness;
	protected Handler mHandler;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity=(BaseActivity) activity;
		pref = new SharedPref(mActivity);
		ApplicationFoodBook app = (ApplicationFoodBook) mActivity.getApplication();
		mImageLoader=app.getImageLoader();
		mFontBussiness = app.getFontBussiness();
		mHandler = new Handler();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(getItemLayout(), null);
	 
		
		return v;
	}
	
	protected int getItemLayout() {
		 
		return R.layout.fragment_home;
	}
}
