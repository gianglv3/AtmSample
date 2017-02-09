package com.ipos.restaurant.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ipos.restaurant.R;
import com.ipos.restaurant.customview.ControlLoadMore;
import com.ipos.restaurant.util.Log;
import com.ipos.restaurant.util.SharedPref;

public class LoadMoreFragment extends ListFragment implements OnScrollListener,
		SwipeRefreshLayout.OnRefreshListener {
	
	protected String TAG = getClass().getName();

	protected int currentFirstVisibleItem;
	protected int currentVisibleItemCount;
	protected int currentTotalItemCount;
	protected ControlLoadMore controlLoadMore;

	protected boolean isHaveLoadMore=true;
	protected SwipeRefreshLayout mSwipeRefreshLayout;

 
	protected ListView mListView;

	protected ProgressBar loading;

	protected TextView txtError;

	protected View framelayout;

	protected boolean isLoadingMore = false;
	protected boolean isNoAddMore = false;
	protected boolean isLoading = false;

	protected int maxErrorReTry = 3;
	protected int countError = 0;
 
	protected int currentPage=1;
	protected Handler mHandel;
	protected SharedPref pref;

	protected boolean isMaxHeRefresh = false;

	protected Activity mActivity;

	protected boolean isRefresh = true;

	public boolean isMaxHeRefresh() {
		return isMaxHeRefresh;
	}

	public void setMaxHeRefresh(boolean isMaxHeRefresh) {
		this.isMaxHeRefresh = isMaxHeRefresh;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		controlLoadMore = new ControlLoadMore(inflater.inflate(
				R.layout.include_list_item_loading, null));

		controlLoadMore.setOnClick(new OnClickListener() {

			@Override
			public void onClick(View v) {

				onLoadMore();

			}
		});

		View layout = inflater.inflate(getItemLayout(), container, false);
		mListView = (ListView) layout.findViewById(android.R.id.list);
		
		txtError = (TextView) layout.findViewById(R.id.error);

		framelayout = (View) layout.findViewById(R.id.layout_root);

		mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_refesh);
		if (mSwipeRefreshLayout!=null) {
			mSwipeRefreshLayout.setOnRefreshListener(this);
			mSwipeRefreshLayout.setColorSchemeResources(R.color.red);
			if (!isRefresh) {
				mSwipeRefreshLayout.setEnabled(false);

			}
		}

		loading = (ProgressBar) layout.findViewById(R.id.loading);
		mHandel = new Handler();

		return layout;
	}

	protected int getItemLayout() {
		return R.layout.list_cate;
	}



	protected void onloadRetry() {
		Log.i(TAG,"LOAD RETRY");
		
		showListView(false);
		showOnRetryLoad(false);
	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
		mActivity = activity;
		pref = new SharedPref(mActivity);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		getListView().setOnScrollListener(this);
		super.onActivityCreated(savedInstanceState);


	}

	protected void addLoadMore() {
		getListView().addFooterView(controlLoadMore.getView());
	}


	protected void showOnRetryLoad(boolean kt) {
		if (kt) {
			framelayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					onloadRetry();

				}
			});
		} else {
			framelayout.setOnClickListener(null);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		this.currentFirstVisibleItem = firstVisibleItem;
		this.currentVisibleItemCount = visibleItemCount;
		this.currentTotalItemCount = totalItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		boolean needToLoad = (currentFirstVisibleItem + currentVisibleItemCount >= currentTotalItemCount - 2)
				&& (currentVisibleItemCount != currentTotalItemCount);

		if (needToLoad) {

			Log.i("LoadMoreFragment.onScrollStateChanged()",
					"GiangLV----> Load more");
			onLoadMore();
		}

	}

	protected void onLoadMore() {
		if (isNoAddMore)
			return;
		controlLoadMore.showLoading(true);

	}


	public void showErrorNetWork(boolean isShow) {

		if (isShow) {
			showOnRetryLoad(true);
			framelayout.setClickable(true);
			txtError.setVisibility(View.VISIBLE);
			txtError.setText(R.string.error_network);
			loading.setVisibility(View.GONE);
			mSwipeRefreshLayout.setVisibility(View.GONE);
		} else {
			framelayout.setClickable(false);
			txtError.setVisibility(View.GONE);
			showListView(isShow);
		}
	}

	public void showLoadNoresult(boolean isShow) {

		if (isShow) {
			showOnRetryLoad(true);
			framelayout.setClickable(true);
			txtError.setVisibility(View.VISIBLE);
			txtError.setText(R.string.load_no_result);
			loading.setVisibility(View.GONE);
			mListView.setVisibility(View.GONE);
		} else {
			framelayout.setClickable(false);
			txtError.setVisibility(View.GONE);
			showListView(isShow);
		}
	}

	public void showListView(boolean isShow) {

		txtError.setVisibility(View.GONE);
		if (isShow) {
			loading.setVisibility(View.GONE);
			mSwipeRefreshLayout.setVisibility(View.VISIBLE);
		} else {
			loading.setVisibility(View.VISIBLE);
			mSwipeRefreshLayout.setVisibility(View.GONE);
		}
	}

	public void showloading() {
		txtError.setVisibility(View.GONE);
		loading.setVisibility(View.VISIBLE);
	}
 


	@Override
	public void onRefresh() {
		Log.i(TAG,"LOAD REFRESH");
	}
}
