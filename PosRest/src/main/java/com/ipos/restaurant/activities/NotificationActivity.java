package com.ipos.restaurant.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class NotificationActivity extends ActionBarActivity {



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//
//		DmPush item = (DmPush) getIntent().getSerializableExtra(Constants.KEY_DATA);
//		processNotification(item);
	}
//
//	private void processNotification(DmPush item) {
//		if (item==null) {
//			finish();
//			return;
//		}
//
//		Intent i = new Intent(this, SplashActivity.class);
//		switch (item.getPush_type()) {
//
//			case DmPush.PUSH_UPDATE:
//				if (item.hasUrl()) {
//					i = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
//					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				} else {
//					i = Utilities.genIntentGoMarket(this);
//				}
//				break;
//
//			case DmPush.PUSH_TYPE_RATE_ORDER:
////				i = new Intent(this, RateOrderActivity.class);
////				i.putExtra(Constants.KEY_DATA,item);
//				break;
//			case DmPush.PUSH_TYPE_UPDATE_STATUS_ORDER_ONLINE_WITH_URL:
//			case DmPush.PUSH_TYPE_OPEN_URL:
//				i = new Intent(this, WebViewActivity.class);
//				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				i.putExtra(Constants.KEY_DATA,item.getUrl());
//				break;
//
//			default:
//				break;
//		}
//
//		FoodBookTracker.sendEvent(GA.NOTIFICATION,GA.NOTIFICATION_CLICK,item.getTitle(),0);
//		startActivity(i);
//		finish();;
//	}



}
