package com.ipos.restaurant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ipos.restaurant.holder.AtmHolder;
import com.ipos.restaurant.model.DmAtm;

import java.util.ArrayList;

public class AtmAdapter extends BaseCustomAdapter {

	private ArrayList<DmAtm> data;
	private LayoutInflater inflater;


	public AtmAdapter(Context context, ArrayList<DmAtm> data) {
		super(context);
		this.data = data;
 
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AtmHolder mHolder;

		if (convertView == null) {

			mHolder = new AtmHolder(mContext);
			 mHolder.iniHolder(position, convertView, parent, inflater);;
		 
		 
	  convertView=mHolder.getConvertView();

		} else {
			mHolder = (AtmHolder) convertView.getTag();
		}

		DmAtm t = data.get(position);

 
		mHolder.setElement(t);

		return convertView;
	}

 
}
