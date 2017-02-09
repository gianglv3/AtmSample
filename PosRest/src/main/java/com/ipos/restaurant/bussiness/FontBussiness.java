package com.ipos.restaurant.bussiness;

import android.graphics.Typeface;

import com.ipos.restaurant.ApplicationFoodBook;


public class FontBussiness {

	private Typeface mRobotoLight;
	private Typeface mRobotoBold;
	private Typeface mRobotoRegular;
	private Typeface mRobotoMedium;
	
	private Typeface mOpenSanLight;
	private Typeface mOpenSanBold;
	private Typeface mOpenSanRegular;
	private Typeface mOpenSanSemiBold;
	private ApplicationFoodBook mfoodbook;

	public FontBussiness(ApplicationFoodBook mfoodbook) {
		this.mfoodbook = mfoodbook;
		mRobotoMedium = Typeface.createFromAsset(mfoodbook.getAssets(),
				"fonts/Roboto-Medium.ttf");
		mRobotoLight = Typeface.createFromAsset(mfoodbook.getAssets(),
				"fonts/Roboto-Light.ttf");
		mRobotoRegular = Typeface.createFromAsset(mfoodbook.getAssets(),
				"fonts/Roboto-Regular.ttf");
		mRobotoBold = Typeface.createFromAsset(mfoodbook.getAssets(),
				"fonts/Roboto-Bold.ttf");
		
		
		mOpenSanSemiBold = Typeface.createFromAsset(mfoodbook.getAssets(),
				"fonts/OpenSans-Semibold.ttf");
		mOpenSanLight = Typeface.createFromAsset(mfoodbook.getAssets(),
				"fonts/OpenSans-Light.ttf");
		mOpenSanRegular = Typeface.createFromAsset(mfoodbook.getAssets(),
				"fonts/OpenSans-Regular.ttf");
		mOpenSanBold = Typeface.createFromAsset(mfoodbook.getAssets(),
				"fonts/OpenSans-Bold.ttf");		
	}

	public Typeface getRobotoLight() {
		return mRobotoLight;
	}

	public Typeface getRobotoBold() {
		return mRobotoBold;
	}

	public Typeface getRobotoRegular() {
		return mRobotoRegular;
	}

	public Typeface getRobotoMedium() {
		return mRobotoMedium;
	}

	public Typeface getOpenSanLight() {
		return mOpenSanLight;
	}

	public Typeface getOpenSanBold() {
		return mOpenSanBold;
	}

	public Typeface getOpenSanRegular() {
		return mOpenSanRegular;
	}

	public Typeface getOpenSanSemiBold() {
		return mOpenSanSemiBold;
	}

	public ApplicationFoodBook getfoodbook() {
		return mfoodbook;
	}
	
	
	
	
	
}
