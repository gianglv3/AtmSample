package com.ipos.restaurant.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ipos.restaurant.R;
import com.ipos.restaurant.util.Log;


/**
 * Created by GiangLV on 10/2/2015.
 */
public class BaseRecycleFragment extends  BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    protected RecyclerView mRecyclerView;
    protected boolean isLoading = false;


    protected boolean isHaveLoadMore=true;
    protected int pastVisiblesItems, visibleItemCount, totalItemCount;

    protected LinearLayoutManager mLayoutManager;

    protected int currentPage=1;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    protected ProgressBar loading;

    protected TextView txtError;

    protected View framelayout;

    @Override
    protected int getItemLayout() {
        return R.layout.fragment_home_recycle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.listMovieHits);
        mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);



        txtError = (TextView) v.findViewById(R.id.error);

        loading = (ProgressBar) v.findViewById(R.id.loading);
        framelayout =  v.findViewById(R.id.layout_root);

        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refesh);
        if (mSwipeRefreshLayout!=null) {
            mSwipeRefreshLayout.setOnRefreshListener(this);
            mSwipeRefreshLayout.setColorSchemeResources(R.color.red);

        }

        return  v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    protected  void initLoadMore() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    Log.d(TAG,"VisibleItemCount "+visibleItemCount+"/ "+totalItemCount+"/ past "+pastVisiblesItems+" / "+isLoading+"/ "+isHaveLoadMore);


                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loadMore();
                        }

                }
            }
        });
    }

    protected void loadMore() {


    }


    @Override
    public void onRefresh() {

    }

    protected void onloadRetry() {
        Log.i(TAG, "LOAD RETRY");

        showListView(false);
        showOnRetryLoad(false);
    }



    protected void showOnRetryLoad(boolean kt) {
        if (kt) {
            framelayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    onloadRetry();

                }
            });
        } else {
            framelayout.setOnClickListener(null);
        }
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

    public void showErrorNetWork(boolean isShow,String message) {

        if (isShow) {
            showOnRetryLoad(true);
            framelayout.setClickable(true);
            txtError.setVisibility(View.VISIBLE);
            txtError.setText(message);
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
            mRecyclerView.setVisibility(View.GONE);
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

}
