package com.ipos.restaurant.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ipos.restaurant.ApplicationFoodBook;

public class TextViewRobotoBold extends TextView {

	 

	public TextViewRobotoBold(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TextViewRobotoBold(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public TextViewRobotoBold(Context context) {
		super(context);
		init();
	}

	private void init() {

		ApplicationFoodBook app = ApplicationFoodBook.getInstance();
		 
		setTypeface(app.getFontBussiness().getRobotoBold());
	}

	 
}
