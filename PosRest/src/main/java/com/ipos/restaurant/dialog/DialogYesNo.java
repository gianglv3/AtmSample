package com.ipos.restaurant.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ipos.restaurant.R;

public abstract class DialogYesNo extends Dialog implements
		View.OnClickListener {

	TextView tvHeader;
	TextView tvContent;
	TextView btnCancel;

	TextView btnOk;

	public DialogYesNo(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init();
	}

	public DialogYesNo(Context context, int theme) {
		super(context, theme);
		init();
	}

	public DialogYesNo(Context context) {
		super(context, R.style.style_dialog2);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.dialog_alert);
		init();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

 

	public void setOneButton() {

			btnCancel.setVisibility(View.GONE);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((ViewGroup.MarginLayoutParams)(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)));

        params.gravity=Gravity.CENTER;
		btnOk.setLayoutParams(params);
        btnOk.setGravity(Gravity.CENTER);
        int padding=getContext().getResources().getDimensionPixelSize(R.dimen.action_bar_height_40);
        btnOk.setPadding(padding,0,padding,0);

	}

	private void init() {
		tvHeader = (TextView) findViewById(R.id.lbl_dialog_header);
		tvContent = (TextView) findViewById(R.id.lbl_dialog_text);

		if (getHeader()==null) {
			
		} else {
			tvHeader.setText(getHeader());
		}
		tvContent.setText(getMessage());

		btnCancel = (TextView) findViewById(R.id.btn_cancel);
 
		btnOk = (TextView) findViewById(R.id.btn_ok);

		btnCancel.setOnClickListener(this);
 
		btnOk.setOnClickListener(this);
		getWindow().getAttributes().windowAnimations = R.style.PopupAnimation;
		setCanceledOnTouchOutside(true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			setActionYes();
			break;

	 
		case R.id.btn_cancel:
			setActionNo();
			break;

		default:
			break;
		}
	}
 
	public abstract String getHeader();

	public abstract String getMessage();

	public abstract void setActionYes();

	public abstract void setActionNo();

	public void setNameButtonYes(int resID) {
		btnOk.setText(resID);
	}

	public void setNameButtonNo(int resID) {
		btnCancel.setText(resID);
	}

}
