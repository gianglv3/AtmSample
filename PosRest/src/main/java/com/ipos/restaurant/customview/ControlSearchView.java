package com.ipos.restaurant.customview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.ipos.restaurant.R;

public class ControlSearchView {

	private View mRoot;

	private Context mContext;

	private EditText mEdtSearch;

	private ImageView mIclose;
	
	
	

	public ControlSearchView(View v) {
		this.mRoot = v;
		mContext = mRoot.getContext();
		mEdtSearch = (EditText)mRoot. findViewById(R.id.search);
		
		mIclose = (ImageView)mRoot. findViewById(R.id.clear);
		mIclose.setVisibility(View.GONE);
		
		mIclose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mIclose.setVisibility(View.VISIBLE);
				mEdtSearch.setText("");
			}
		});
		mEdtSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length()>0) {
					mIclose.setVisibility(View.VISIBLE);
				} else {
					mIclose.setVisibility(View.GONE);
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});

	}
	
	
	public String getValues() {
		return mEdtSearch.getText().toString().trim();
	}
	
	public void requestForcus() {
		mEdtSearch.requestFocus();
	}
	
	 
	public void setHintTextView(int resid) {
		mEdtSearch.setHint(resid);
	}
	

	public void setHintTextView(String resid) {
		mEdtSearch.setHint(resid);
	}

	public void setVisibility(int visibility) {
		mRoot.setVisibility(visibility);
	}
	
	public void setVisibileIconSearch() {
		mEdtSearch.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		int left =mContext.getResources().getDimensionPixelOffset(R.dimen.standard_margin_double);
		int nomar=mContext.getResources().getDimensionPixelOffset(R.dimen.standard_margin_half);
		mEdtSearch.setPadding(left,nomar,nomar,nomar);
	}

	public View getView() {
		return mRoot;
	}

	public EditText getmEdtSearch() {
		return mEdtSearch;
	}

	public void setmEdtSearch(EditText mEdtSearch) {
		this.mEdtSearch = mEdtSearch;
	}
	
	
}
