package com.ipos.restaurant.customview;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ipos.restaurant.R;

public class ControlLoadMore {

	View root;

	TextView tvTitle;

	ProgressBar loading;

	public ControlLoadMore(View r) {
		this.root = r;
		loading = (ProgressBar) root.findViewById(R.id.loading);
		tvTitle = (TextView) root.findViewById(R.id.error);

	}

	public void setOnClick(OnClickListener onclick) {
		root.setOnClickListener(onclick);
	}

	public void setBackGroundWhite() {
		root.setBackgroundColor(Color.WHITE);
	}

	// true thi showloading
	// false show load fail
	public void showLoading(boolean show) {
		root.setVisibility(View.VISIBLE);
		if (show) {
			loading.setVisibility(View.VISIBLE);
			tvTitle.setVisibility(View.GONE);
		} else {
			loading.setVisibility(View.GONE);
			tvTitle.setVisibility(View.VISIBLE);
			tvTitle.setText(R.string.error_network);
		}
	}

	public View getView() {
		return root;
	}

	public void loadingNoresult() {

		showLoading(false);
		tvTitle.setText(R.string.load_more_no_result);
		tvTitle.setVisibility(View.INVISIBLE);

	}

	
	
	
	public void visiableLoadMore() {
		root.setVisibility(View.INVISIBLE);
	}

//	public  void changeHeight(boolean b) {
//
//		if (b) {
////			r
////			root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 10));
////			root.requestLayout();
//		} else{
//			root.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//			root.requestLayout();
//		}
//	}

	// public void goneLoadMore() {
	//
	// tvTitle.setVisibility(View.GONE);
	// loading.setVisibility(View.GONE);
	// root.setPadding(0,0,0,0);
	// root.setVisibility(View.GONE);
	//
	// }
	public void setHiden(boolean isHide) {

		if (isHide)
			root.setVisibility(View.GONE);
		else
			root.setVisibility(View.VISIBLE);
	}

}
