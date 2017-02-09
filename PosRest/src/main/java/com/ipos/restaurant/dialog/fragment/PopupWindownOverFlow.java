package com.ipos.restaurant.dialog.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ipos.restaurant.R;
import com.ipos.restaurant.abs.OnItemClickDialogContext;
import com.ipos.restaurant.adapter.ContextMenuAdapter;
import com.ipos.restaurant.model.ItemContextMenu;

/**
 * Created by toanvk on 9/29/2014.
 */
public class PopupWindownOverFlow extends PopupWindow {
	private View mParentView;
	private View popupView;
	private Context context;
	private ArrayList<ItemContextMenu> listItem;
	private ContextMenuAdapter menuAdapter = null;
	private OnItemClickDialogContext clickHandler;
	private ListView listView;
	private Drawable bg;

	public PopupWindownOverFlow() {
		super();
	}

	public PopupWindownOverFlow(View contentView) {
		super(contentView);
	}

	public static PopupWindownOverFlow newInstance(Context context,
			View parentView, OnItemClickDialogContext callBack,
			ArrayList<ItemContextMenu> listItem) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.popup_overflow_menu, null);
		PopupWindownOverFlow popup = new PopupWindownOverFlow(view);
		popup.context = context;
		popup.mParentView = parentView;
		popup.listItem = listItem;
		popup.clickHandler = callBack;
		// int widthScreen = ConvertHelper.getWidthScreen(context);
		// popup.setWidth((widthScreen / 2) - 10);
		// popup.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
		// popup.setFocusable(true);
		// popup.setOutsideTouchable(true);
		// popup.bg =
		// context.getResources().getDrawable(R.drawable.bg_action_bar);
		// popup.setBackgroundDrawable(popup.bg);
		return popup;
	}

	@Override
	public void dismiss() {
		this.bg = null;
		super.dismiss();
	}

	public void setListItem(ArrayList<ItemContextMenu> listItem) {
		this.listItem = listItem;
	}

	public void showPopup() {
		LayoutInflater inflater = LayoutInflater.from(context);
		popupView = initControl(inflater);
		setContentView(popupView);
		setAnimationStyle(R.style.AnimationOverFlow);
		mParentView.postDelayed(new Runnable() {
			@Override
			public void run() {
				int[] locationParent = new int[2];
				mParentView.getLocationOnScreen(locationParent);
				showAtLocation(mParentView, Gravity.TOP | Gravity.RIGHT, 0,
						locationParent[1]);
			}
		}, 100);

	}

	public void showPopupAtView() {
		LayoutInflater inflater = LayoutInflater.from(context);
		popupView = initControl(inflater);
		setContentView(popupView);
		// setAnimationStyle(R.style.PopupAnimation);
		mParentView.postDelayed(new Runnable() {
			@Override
			public void run() {
				int[] locationParent = new int[2];
				mParentView.getLocationOnScreen(locationParent);
				showAsDropDown(mParentView);
			}
		}, 100);

	}

	/**
	 * init controller
	 *
	 * @param: n/a
	 * @return: n/a
	 * @throws: n/a
	 */
	private View initControl(LayoutInflater inflater) {
		// View view = inflater.inflate(R.layout.popup_overflow_menu, null);
		View view = getContentView();
		menuAdapter = new ContextMenuAdapter(context);
		menuAdapter.setListItem(listItem);
		listView = (ListView) view.findViewById(R.id.menu_listview);
		// add footer and header
		listView.setAdapter(menuAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				dismiss();

				ItemContextMenu item = (ItemContextMenu) parent
						.getItemAtPosition(position);
				if (clickHandler!=null) {
					clickHandler.OnItemClick(position, item
							.getActionTag());
				 
				}
			}
		});
		return view;
	}

}