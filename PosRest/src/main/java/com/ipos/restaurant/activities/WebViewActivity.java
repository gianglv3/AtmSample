package com.ipos.restaurant.activities;

import android.os.Bundle;

import com.ipos.restaurant.R;
import com.ipos.restaurant.Constants;
import com.ipos.restaurant.fragment.WebViewFragment;

public class WebViewActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		String url =getIntent().getStringExtra(Constants.KEY_DATA);
		WebViewFragment mWebViewFragment = WebViewFragment.newInstance(url);
		executeFragmentTransaction(mWebViewFragment,R.id.content,false,false);
	}
}
