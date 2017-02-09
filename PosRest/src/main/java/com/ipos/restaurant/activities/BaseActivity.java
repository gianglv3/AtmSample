package com.ipos.restaurant.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.ipos.restaurant.ApplicationFoodBook;
import com.ipos.restaurant.Constants;
import com.ipos.restaurant.R;
import com.ipos.restaurant.bussiness.ChangeAvatarBussiness;
import com.ipos.restaurant.bussiness.FontBussiness;
import com.ipos.restaurant.bussiness.ShareFacebookBussiness;
import com.ipos.restaurant.util.Log;
import com.ipos.restaurant.util.SharedPref;

public class BaseActivity extends ActionBarActivity {
	protected String TAG=getClass().getSimpleName();



	protected SharedPref config;
	protected Handler mHandler;
 
	protected FontBussiness mFontBussiness;
 

	protected ShareFacebookBussiness mFacebookBussiness;
	private ChangeAvatarBussiness mChangeAvatar;
	protected boolean isKeyBoard = false;
	protected int keyboardHeight;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
	protected boolean isKeyBoardVisible;
	private  LogOutBroadCast mLogout;
	public static long timeStopApplication=0;
	public static long timeStartApplication=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		overridePendingTransition(0,0);
		Log.i(Constants.keylog, "onCreate " + getClass().getName());
		  mFragmentManager = getSupportFragmentManager();
		config = new SharedPref(this);
		mHandler = new Handler();
		mChangeAvatar = new ChangeAvatarBussiness(this);
		mFontBussiness =ApplicationFoodBook.getInstance().getFontBussiness();
		mFacebookBussiness =new ShareFacebookBussiness(this, savedInstanceState);
		registerLogoutBroadCast();

	}

	private void registerLogoutBroadCast() {
		mLogout = new LogOutBroadCast();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constants.ACTION_LOGOUT);
		registerReceiver(mLogout,intentFilter);
	}

	private void unRegisterLogoutBroadcast() {
		unregisterReceiver(mLogout);
	}
	  /**
     * execute replacing fragment transaction
     *
     * @param fragment         Fragment to be display
     * @param isAddToBackStack true if could navigate
     */
    protected void executeFragmentTransaction(Fragment fragment, int fragmentParentId,
                                              boolean isAddToBackStack, boolean isAnimations) {
		try {
			mFragmentTransaction = mFragmentManager.beginTransaction();
//        if (isAnimations) {
//            mFragmentTransaction.setCustomAnimations(R.anim.slide_in_from_right,
//                    R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
//        }
			// Add the fragment to the 'fragment_container' FrameLayout
			mFragmentTransaction.replace(fragmentParentId, fragment);
//        mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        if (isAddToBackStack) {
//            mFragmentTransaction.addToBackStack(null);
//        }
			mFragmentTransaction.commit();
		}catch (Exception e){
			e.printStackTrace();
		}
    }

	 



	public void shareAction(Bundle item) {
		// Log.i("BaseActivity.shareAction()", "URL " + item.getUrl());
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT,
				item.getString(Constants.KEY_SUBJECT));
		intent.putExtra(Intent.EXTRA_TEXT,
				item.getString(Constants.KEY_URL));
		intent.putExtra(Intent.EXTRA_TITLE,
				item.getString(Constants.KEY_TITLE));
		startActivity(intent);

	}

	@Override
	protected void onPause() {
		super.onPause();
		ApplicationFoodBook.setIsVisible(false);
	}


	public void actionShare() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
 
		startActivity(intent);
	}
	
	public void actionSendReport() {

			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("message/rfc822");
			intent.putExtra(Intent.EXTRA_SUBJECT,
					getString(R.string.app_name));
			intent.putExtra(Intent.EXTRA_EMAIL,
					new String[] { Constants.MAIL_FB });
//			intent.putExtra(android.content.Intent.EXTRA_TEXT,
//					getString(R.string.send_feedback_content) + " "
//							+ getString(R.string.app_name));

			startActivity(Intent.createChooser(intent, "Send Mail"));
		
	}

	@Override
	public void onStart() {
		mFacebookBussiness.onStart();
		super.onStart();

		Log.i(Constants.keylog, "onStart " + getClass().getName());
	}

	@Override
	protected void onStop() {
		mFacebookBussiness.onStop();
		super.onStop();
		timeStopApplication=System.currentTimeMillis();
		Log.i(Constants.keylog, "onStop " + getClass().getName());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mFacebookBussiness.onActivityResult(requestCode, resultCode, data);
		mChangeAvatar.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mFacebookBussiness.onSaveInstanceState(outState);
	}


	public ChangeAvatarBussiness getChangeAvatarUtil() {
		return mChangeAvatar;
	}

	// ===init keyboard show====//

	/**
	 * change height of emoticons keyboard according to height of actual
	 * keyboard
	 * 
	 * @param height
	 *            minimum height by which we can make sure actual keyboard is
	 *            open or not
	 */
	private void changeKeyboardHeight(int height) {

		if (height > 100) {
			keyboardHeight = height;
			config.putInt(Constants.HEIGHT_KEY, keyboardHeight);
		 
		}

	}

	/*
	 * * Checking keyboard height and keyboard visibility
	 */
	int previousHeightDiffrence = 0;

	protected void checkKeyboardHeight(final View parentLayout) {

		parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {

						Rect r = new Rect();
						parentLayout.getWindowVisibleDisplayFrame(r);

						int screenHeight = getUsableScreenHeight(parentLayout);
						int heightDifference = screenHeight - (r.bottom);

						previousHeightDiffrence = heightDifference;
						//	Log.i("Keyboard", " Keyboard "+previousHeightDiffrence+"/ "+screenHeight+"/ "+r.bottom+"/ "+r.top);
						if (heightDifference > 144) {

							isKeyBoardVisible = true;
							changeKeyboardHeight(heightDifference);

							if (!isKeyBoard) {
								isKeyBoard = true;
								Log.i("Keyboard", " Keyboard show");
								showKeyboard();
							}
						} else {

							if (isKeyBoard) {
								isKeyBoard = false;
								Log.i("KEyboard.", "Keyboard Off");
								hidenKeyboard();

							}

							isKeyBoardVisible = false;

						}

					}
				});

	}

	private int getUsableScreenHeight(View rootView) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			DisplayMetrics metrics = new DisplayMetrics();

			WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
			windowManager.getDefaultDisplay().getMetrics(metrics);

			return metrics.heightPixels;

		} else {
			return rootView.getRootView().getHeight();
		}
	}
	
	protected void showKeyboard() {
		
	}

	protected void hidenKeyboard() {
		
	}

	public void shareFaceBook(String message,String link) {
		mFacebookBussiness.shareFacebook(message, link);
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unRegisterLogoutBroadcast();
	}

	@Override
	protected void onResume() {
		super.onResume();
		timeStartApplication=System.currentTimeMillis();
		ApplicationFoodBook.setIsVisible(true);
		Log.i(Constants.keylog, "onResume " + getClass().getName());
	}

	@Override
	public void finish() {

		super.finish();
		overridePendingTransition(0,0);
	}

	private void logOut() {

	}
	private void exit() {

		finish();
	}


	private class LogOutBroadCast extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action =intent.getAction();
			if (action==null) return;
			if (action.equals(Constants.ACTION_LOGOUT)) {
				logOut();

			} else if (action.equals(Constants.ACTION_EXIT)) {
				exit();
			}
		}
	}
}
