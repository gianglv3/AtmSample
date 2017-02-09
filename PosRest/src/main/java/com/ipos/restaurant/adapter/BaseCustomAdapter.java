package com.ipos.restaurant.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ipos.restaurant.ApplicationFoodBook;
import com.ipos.restaurant.bussiness.ImageLoaderBussiness;
import com.ipos.restaurant.util.SharedPref;

public class BaseCustomAdapter extends BaseAdapter {

	protected SharedPref config;
	protected boolean isGetWIDTH = false;

	protected int finalWidth = 0;
	protected int finalHeight = 0;
	protected Context mContext;
	protected int currentSelect = -1;
	protected ImageLoaderBussiness mImageLoader;

	public BaseCustomAdapter(Context context) {
		config = new SharedPref(context);
		this.mContext = context;
		ApplicationFoodBook app = (ApplicationFoodBook) context.getApplicationContext();
		mImageLoader =app.getImageLoader();

	}

	@Override
	public int getCount() {

		return 0;
	}

	@Override
	public Object getItem(int position) {

		return null;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		return null;
	}

}
