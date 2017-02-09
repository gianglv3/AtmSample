package com.ipos.restaurant.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ipos.restaurant.R;
import com.ipos.restaurant.model.DmAtm;
import com.ipos.restaurant.util.DistanceUtil;


public class AtmHolder extends AbsHolder {

	private SimpleDraweeView image;
	private TextView lblName;
	private TextView lblAdd;



	private DmAtm mPos;


	public AtmHolder(Context mContext) {
		super(mContext);
	 
	}

	protected int getItemLayout() {
	 
			return R.layout.adapter_pos;

	 

	}

	@Override
	public void iniHolder(int position, View convertView, ViewGroup parent,
			LayoutInflater inflater) {
		convertView = inflater.inflate(getItemLayout(), null);
		 
			image = (SimpleDraweeView) convertView.findViewById(R.id.image);
			lblName = (TextView) convertView.findViewById(R.id.name);
			lblAdd = (TextView) convertView.findViewById(R.id.add);

		 
			setConvertView(convertView);

		convertView.setTag(this);
	}

	@Override
	public void setElement(Object obj) {
		//
		setData((DmAtm) obj);
	}

	private void setData(DmAtm t) {
		this.mPos = t;

		DistanceUtil.caluDistane(mPos);

		if (TextUtils.isEmpty(t.getShowDistance())) {
			lblName.setText(t.getName());
		} else{
			lblName.setText(t.getName()+" ~ "+t.getShowDistance());
		}
		lblAdd.setText(t.getAddres());

		 
	}

}
