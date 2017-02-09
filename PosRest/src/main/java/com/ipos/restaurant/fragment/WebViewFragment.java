package com.ipos.restaurant.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ipos.restaurant.Constants;
import com.ipos.restaurant.R;
import com.ipos.restaurant.abs.OnItemClickDialogContext;
import com.ipos.restaurant.dialog.fragment.PopupWindownOverFlow;
import com.ipos.restaurant.model.ItemContextMenu;
import com.ipos.restaurant.util.Log;

import java.util.ArrayList;

/**
 * Webview
 * 
 * @author gianglv3
 * 
 */
@SuppressLint("SetJavaScriptEnabled")
public class WebViewFragment extends BaseFragment implements OnItemClickDialogContext, SwipeRefreshLayout.OnRefreshListener {

    private WebView mWebView;
    private boolean mIsWebViewAvailable;
    private String mUrl = null;

    private View mActionBarView;
    private View mImgClose;
    private TextView mTitle;
    private View mImgMore;
    private ProgressBar mLoadingBar;
    private  boolean hidenActionbar=false;
    ArrayList<ItemContextMenu> listItem;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataView();;

    }

    /**
     * Creates a new fragment which loads the supplied url as soon as it can
     *
     * @param url the url to load once initialised
     */
    @SuppressLint("ValidFragment")
    public WebViewFragment(String url) {
        super();
        mUrl = url;
    }

    @SuppressLint("ValidFragment")
    public WebViewFragment() {
        super();
        mUrl = Constants.URL_HOME;
    }

    /**
     * Called to instantiate the view. Creates and returns the WebView.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_webview,null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refesh);

        if (mSwipeRefreshLayout!=null) {
            mSwipeRefreshLayout.setOnRefreshListener(this);
            mSwipeRefreshLayout.setColorSchemeResources(R.color.red);

        }
        if (mWebView != null) {
            mWebView.destroy();
        }

        mWebView = (WebView) view.findViewById(R.id.webview);

        mWebView.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                return false;
            }

        });
        mWebView.setWebViewClient(new InnerWebViewClient()); // forces it to open in app
        mWebView.loadUrl(mUrl);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mLoadingBar.setProgress(newProgress);
                String title =view.getTitle();
                if ("".equals(title)) {
                    title=view.getUrl();
                }
                mTitle.setText(title);

                if (newProgress>=95) {
                    mLoadingBar.setVisibility(View.INVISIBLE);
                    mSwipeRefreshLayout.setRefreshing(false);
                } else{
                    mLoadingBar.setVisibility(View.VISIBLE);
                }


            }
        });
        mIsWebViewAvailable = true;
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        setAtionBar(view);

        return view;
    }

    /**
     * Convenience method for loading a url. Will fail if {@link View} is not initialised (but won't throw an {@link Exception})
     *
     * @param url
     */
    public void loadUrl(String url) {
        if (mIsWebViewAvailable) getWebView().loadUrl(mUrl = url);
        else
            Log.w("ImprovedWebViewFragment", "WebView cannot be found. Check the view and fragment have been loaded.");
    }

    /**
     * Called when the fragment is visible to the user and actively running. Resumes the WebView.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    /**
     * Called when the fragment is no longer resumed. Pauses the WebView.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onResume() {
        if (mWebView!=null) {
            mWebView.onResume();
        }
        super.onResume();
    }

    /**
     * Called when the WebView has been detached from the fragment.
     * The WebView is no longer available after this time.
     */
    @Override
    public void onDestroyView() {
        mIsWebViewAvailable = false;
        super.onDestroyView();
    }

    /**
     * Called when the fragment is no longer in use. Destroys the internal state of the WebView.
     */
    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    /**
     * Gets the WebView.
     */
    public WebView getWebView() {
        return mIsWebViewAvailable ? mWebView : null;
    }

    public static WebViewFragment newInstance(String mUrl) {

        WebViewFragment fragment = new WebViewFragment(mUrl);

        return fragment;
    }

    @Override
    public void onRefresh() {
        if (mWebView!=null) {
            mWebView.reload();
        }
    }

    /* To ensure links open within the application */
    private class InnerWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }


    }

    private void initDataView() {
         listItem = new ArrayList<ItemContextMenu>();

        ItemContextMenu shareLink = new ItemContextMenu();
        shareLink.setActionTag(1);
        shareLink.setText(mActivity.getString(R.string.web_pop_sharelink));
        listItem.add(shareLink);

        ItemContextMenu  copylink = new ItemContextMenu();
        copylink.setActionTag(2);
        copylink.setObj(mUrl);
        copylink.setText(mActivity.getString(R.string.web_pop_copylink));
        listItem.add(copylink);


    }

    private void setAtionBar(View view) {

        // action bar
        mActionBarView = view.findViewById(R.id.ab_webview);
        mImgMore = mActionBarView.findViewById(R.id.ab_more_btn);
        mImgClose =  mActionBarView.findViewById(R.id.btn_close);
        mImgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
        mTitle = (TextView) mActionBarView.findViewById(R.id.ab_title);

        mLoadingBar= (ProgressBar) mActionBarView.findViewById(R.id.loading);


        mImgMore.setVisibility(View.GONE);
        mImgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           
            	
            	PopupWindownOverFlow mPopupWindownOverFlow = PopupWindownOverFlow.newInstance(mActivity,mWebView, WebViewFragment.this, listItem);
            	 mPopupWindownOverFlow.showPopup();
            }
        });

        if (hidenActionbar) {
            mActionBarView.setVisibility(View.GONE);
        }
    }

	@Override
	public void OnItemClick(int position, int tag) {
	
		
		
	}


    public void setHidenActionbar(boolean hidenActionbar) {
        this.hidenActionbar = hidenActionbar;
    }
}