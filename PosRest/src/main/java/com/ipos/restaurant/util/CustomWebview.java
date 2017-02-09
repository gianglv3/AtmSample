package com.ipos.restaurant.util;

import android.content.Context;
import android.util.AttributeSet;

import com.ipos.restaurant.customview.ObservableWebView;


public class CustomWebview extends ObservableWebView {
	public CustomWebview(Context context) {
		super(context);
	}

	public CustomWebview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public boolean canScrollHor(int direction) {
		final int offset = computeHorizontalScrollOffset();
		final int range = computeHorizontalScrollRange()
				- computeHorizontalScrollExtent();
		if (range == 0)
			return false;
		if (direction < 0) {
			return offset > 0;
		} else {
			return offset < range - 1;
		}
	}
}