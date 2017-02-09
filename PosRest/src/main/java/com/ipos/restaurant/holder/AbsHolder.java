package com.ipos.restaurant.holder;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ipos.restaurant.ApplicationFoodBook;
import com.ipos.restaurant.R;
import com.ipos.restaurant.bussiness.FontBussiness;
import com.ipos.restaurant.bussiness.ImageLoaderBussiness;

public abstract class AbsHolder {

	protected Context mContext;
	protected ImageLoaderBussiness mImageloader;
	protected FontBussiness mFontBussiness;
	protected Resources mResources;
	private View mConvertView;
	
	public AbsHolder(Context mContext) {
		this.mContext =mContext;
		this.mResources=mContext.getResources();
		ApplicationFoodBook app =(ApplicationFoodBook) mContext.getApplicationContext();
		mImageloader =app.getImageLoader();
		mFontBussiness =app.getFontBussiness();
	}
	
	protected int getItemLayout() {
		return R.layout.adapter_place;
	}

	public View getConvertView() {
		return mConvertView;
	}

	public void setConvertView(View mConvertView) {
		this.mConvertView = mConvertView;
	}
	
	public abstract void iniHolder(int position, View convertView, ViewGroup parent,LayoutInflater inflater);
	
	public abstract void setElement(Object obj);
	
}
