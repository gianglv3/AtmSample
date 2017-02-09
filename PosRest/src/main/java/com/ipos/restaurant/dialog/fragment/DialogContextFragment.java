package com.ipos.restaurant.dialog.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ipos.restaurant.R;
import com.ipos.restaurant.abs.OnItemClickDialogContext;
import com.ipos.restaurant.adapter.ContextMenuAdapter;
import com.ipos.restaurant.model.ItemContextMenu;
import com.ipos.restaurant.util.SharedPref;

public class DialogContextFragment extends DialogFragment {

	private ListView mListView;

	private ContextMenuAdapter adapter;
	private ArrayList<ItemContextMenu> datas = new ArrayList<ItemContextMenu>();
	private Activity mActivity;

	private OnDissMiss mDissMiss;

	private TextView mHeader;

	private OnItemClickDialogContext mItemClick;

	public static final int XOA = 1;

	public static final int EDIT = 2;

	private int mPostion = -1;

	private boolean isDel = true;

	private boolean isEdit = true;


	@SuppressLint("ValidFragment")
	public DialogContextFragment(int pos) {
		this.mPostion = pos;
	}


	@SuppressLint("ValidFragment")
	public DialogContextFragment() {

	}


	@SuppressLint("ValidFragment")
	public DialogContextFragment(boolean isDelete, boolean isEdit) {
		this.isDel = isDelete;
		this.isEdit = isEdit;

	}

	private String mTitle = "";

	protected int getItemLayout() {
		return R.layout.dialog_context;
	}

	public OnDissMiss getmDissMiss() {
		return mDissMiss;
	}

	public void setmDissMiss(OnDissMiss mDissMiss) {
		this.mDissMiss = mDissMiss;
	}

	public OnItemClickDialogContext getmItemClick() {
		return mItemClick;
	}

	public void setmItemClick(OnItemClickDialogContext mItemClick) {
		this.mItemClick = mItemClick;
	}

	public ArrayList<ItemContextMenu> getDatas() {
		return datas;
	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
		mActivity = activity;
		new SharedPref(mActivity);
	}

	public void setOnDiss(OnDissMiss mDissMiss) {
		this.mDissMiss = mDissMiss;

	}

	public void setTitle(String text) {
		this.mTitle = text;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogFullscreen);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);

		// request a window without the title
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(getItemLayout(), null);
		;
		mListView = (ListView) v.findViewById(R.id.context_menu_listview);
		mHeader = (TextView) v.findViewById(R.id.context_menu_title);
		mHeader.setText(mTitle);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		adapter = new ContextMenuAdapter(mActivity);
		adapter.setListItem(datas);

		if (datas.size() == 0) {
			ItemContextMenu mDelete = new ItemContextMenu();
			mDelete.setText(getString(R.string.context_menu_xoa));
			mDelete.setActionTag(XOA);
			if (isDel) {
				datas.add(mDelete);
			}
			ItemContextMenu mEdit = new ItemContextMenu();
			mEdit.setActionTag(EDIT);
			mEdit.setText(getString(R.string.context_menu_sua));
			if (isEdit) {
				datas.add(mEdit);
			}
		}
		adapter.notifyDataSetChanged();
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mItemClick != null) {
					if (mPostion != -1) {
						mItemClick.OnItemClick(mPostion, datas.get(position)
								.getActionTag());
					} else {
						mItemClick.OnItemClick(position, datas.get(position)
								.getActionTag());
					}
				}

				dismiss();

			}
		});

	}

	public void setDatas(ArrayList<ItemContextMenu> dt) {

		datas.addAll(dt);
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}

	public interface OnDissMiss {

		void onDissmiss();
	}

	
}
