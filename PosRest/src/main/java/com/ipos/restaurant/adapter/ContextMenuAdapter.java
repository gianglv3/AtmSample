package com.ipos.restaurant.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ipos.restaurant.holder.ContextMenuHolder;
import com.ipos.restaurant.model.ItemContextMenu;

 
public class ContextMenuAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<ItemContextMenu> mItems = new ArrayList<ItemContextMenu>();
    private Context mContext;

    public ContextMenuAdapter(Context context) {
        mContext = context;
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // set list item
    public void setListItem(ArrayList<ItemContextMenu> list) {
        mItems = list;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        ItemContextMenu item = (ItemContextMenu) getItem(position);
        return item.getActionTag();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemContextMenu item = (ItemContextMenu) getItem(position);
 
        ContextMenuHolder holder = null;
        if (convertView == null) {
            holder = new ContextMenuHolder(mContext);
            holder.iniHolder(position, convertView, parent, layoutInflater);
            convertView=holder.getConvertView();
            
        } else {
            holder = (ContextMenuHolder) convertView.getTag();
        }
        holder.setElement(item);
        return holder.getConvertView();
    }
}
