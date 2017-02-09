package com.ipos.restaurant.holder;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ipos.restaurant.ApplicationFoodBook;
import com.ipos.restaurant.R;
import com.ipos.restaurant.bussiness.FontBussiness;
import com.ipos.restaurant.bussiness.ImageLoaderBussiness;



public abstract class AbsRecyleHolder extends RecyclerView.ViewHolder {

	protected Context mContext;
	protected ImageLoaderBussiness mImageloader;
	protected FontBussiness mFontBussiness;
	protected Resources mResources;
	private View mConvertView;

	public AbsRecyleHolder(Context mContext,View view) {
		super(view);
		mConvertView=view;
		this.mContext =mContext;
		this.mResources=mContext.getResources();
		ApplicationFoodBook app =(ApplicationFoodBook) mContext.getApplicationContext();
		mImageloader =app.getImageLoader();
		mFontBussiness =app.getFontBussiness();
	}



	public static int getItemLayout(int mType) {
		return R.layout.adapter_place;
	}

	public View getConvertView() {
		return mConvertView;
	}


	public abstract void setElement(Object obj);


}
