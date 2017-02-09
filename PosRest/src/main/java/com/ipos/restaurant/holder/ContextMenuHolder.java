package com.ipos.restaurant.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipos.restaurant.R;
import com.ipos.restaurant.model.ItemContextMenu;

 
public class ContextMenuHolder extends AbsHolder {
    private TextView mTvwContent;
    private ImageView mImgDetail;
    private ContextMenuHolder mHolder;

    public ContextMenuHolder(Context context) {
    	super(context);
        mHolder = this;
    }
 
  
    private void setViewHolder(ItemContextMenu item) {
//        mHolder.mTvwSinger.setVisibility(View.GONE);
        mTvwContent.setText(item.getText());
        if (item.getImage() != null) {
            mImgDetail.setVisibility(View.VISIBLE);
            mImgDetail.setImageDrawable(item.getImage());
        } else {
            mImgDetail.setVisibility(View.GONE);
        }
    }

	@Override
	public void iniHolder(int position, View convertView, ViewGroup parent,
			LayoutInflater inflater) {
		  View mRowView = inflater.inflate(R.layout.holder_context_menu, parent, false);
	        mHolder.mTvwContent = (TextView) mRowView.findViewById(R.id.item_context_menu_text);
	        mHolder.mImgDetail = (ImageView) mRowView.findViewById(R.id.item_context_menu_icon);
	        mRowView.setTag(mHolder);
	        setConvertView(mRowView);
	}

	@Override
	public void setElement(Object obj) {
		   ItemContextMenu item = (ItemContextMenu) obj;
	        setViewHolder(item);
	}
}