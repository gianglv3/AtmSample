package com.ipos.restaurant.customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ipos.restaurant.ApplicationFoodBook;
import com.ipos.restaurant.R;
import com.ipos.restaurant.bussiness.FontBussiness;
import com.ipos.restaurant.util.Log;

public class TitleViewPager extends HorizontalScrollView implements
		OnPageChangeListener, OnClickListener {

	private ViewPager mViewPager;
	private PagerAdapter mAdapter;
	private TextView mFirst;
	private TextView mLast;
	private OnPageChangeListener mPageChangeListener;
	private LinearLayout tabsContainer;
	private Context mContext;
	private Resources mResources;

	private int width = 0;
	private int widthItem = 200;
	private int widthFirst = 0;
	private int widthLast = 0;
	private boolean isOntouchOutSize = false;
	private boolean isStartOutTouch = false;

	private int colorText = Color.WHITE;
	private int colorTextActive = Color.WHITE;

	private int currentText = 0;
	private int prevText = 0;
	private float textSize = 15f;
	private float textActiveSize = 17f;
	private FontBussiness mFontBussiness;
	private int minHeight;

	public TitleViewPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		mResources = mContext.getResources();
		init();
	}

	public TitleViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		mResources = mContext.getResources();
		init();
	}

	public TitleViewPager(Context context) {
		super(context);
		this.mContext = context;
		mResources = mContext.getResources();
		init();
	}

	private void init() {
		mFontBussiness = ApplicationFoodBook.getInstance().getFontBussiness();
		minHeight = (int) (TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, textActiveSize, mContext.getResources()
						.getDisplayMetrics()));

		Log.i("TAG","MIN HEIGHT "+minHeight);
		widthItem = mResources
				.getDimensionPixelSize(R.dimen.width_text_title_slide_home);
		mFirst = new TextView(mContext);
		mLast = new TextView(mContext);
		mFirst.setWidth(widthItem);
		mLast.setWidth(widthItem);

		tabsContainer = new LinearLayout(mContext);
		tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
		tabsContainer.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		addView(tabsContainer);
	}

	public void setColorText(int colorText) {

		this.colorText = colorText;

	}

	public void setColorTextActive(int colorTextActive) {

		this.colorTextActive = colorTextActive;

	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public void setViewPager(ViewPager mPager) {
		this.mViewPager = mPager;

		mAdapter = mViewPager.getAdapter();

		tabsContainer.removeAllViews();
		tabsContainer.setGravity(Gravity.CENTER_VERTICAL);

		if (mAdapter == null)
			return;

		tabsContainer.addView(mFirst);
		currentText=0;
		prevText=0;

		scrollTo(0,0);
	ViewGroup.LayoutParams params =	new ViewGroup.LayoutParams(
		        ViewGroup.LayoutParams.WRAP_CONTENT,
		        ViewGroup.LayoutParams.MATCH_PARENT);

		for (int i = 0; i < mAdapter.getCount(); i++) {
			TextView mLight = new TextView(getContext());
//			float textWidth = mLight.getPaint().measureText(mAdapter.getPageTitle(i).toString());
//			Log.i("TAG","Text Width "+textWidth);
			mLight.setWidth(widthItem);
			mLight.setLayoutParams(params);
			mLight.setSingleLine();
			mLight.setEllipsize(TextUtils.TruncateAt.END);

			mLight.setTypeface(mFontBussiness.getOpenSanRegular());
			mLight.setMinHeight(minHeight);
			mLight.setPadding(2, 0, 2, 0);
			mLight.setGravity(Gravity.CENTER);
			mLight.setTextSize(textSize);
			mLight.setTag(i);
			mLight.setOnClickListener(this);
			mLight.setTextColor(colorText);
			mLight.setText(mAdapter.getPageTitle(i));
			tabsContainer.addView(mLight);

		}
		tabsContainer.addView(mLast);

		currentText = mViewPager.getCurrentItem();

		setTextActive();

		mViewPager.setOnPageChangeListener(this);

		ViewTreeObserver observer = getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {

				width = getWidth();

				int paddingRight = getPaddingRight();
				widthLast = getWidth() / 2 - paddingRight - widthItem / 2;
				mLast.setWidth(widthLast);
				widthFirst = width / 2 - widthItem / 2;
				mFirst.setWidth(widthFirst);
				Log.i(VIEW_LOG_TAG, "TITLE PAGER INIT " + width + "/ "
						+ widthItem+"/ "+tabsContainer.getHeight());
				getViewTreeObserver().removeGlobalOnLayoutListener(this);

			}

		});

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

		if (mPageChangeListener != null) {
			mPageChangeListener.onPageScrollStateChanged(arg0);
		}

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

		if (mPageChangeListener != null) {
			mPageChangeListener.onPageScrolled(position, positionOffset,
					positionOffsetPixels);

		}

	}

	@Override
	public void onPageSelected(final int position) {
		Log.i(VIEW_LOG_TAG, "Scroll position " + position + "/ " + getWidth());
		if (mPageChangeListener != null) {
			mPageChangeListener.onPageSelected(position);
		}

		postDelayed(new Runnable() {

			@Override
			public void run() {

				prevText = currentText;
				currentText = position;
				setTextActive();
				smoothScrollTo(position * widthItem, 0);

			}
		}, 100);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {

		super.onScrollChanged(l, t, oldl, oldt);
		removeCallbacks(mRunCheckCurrentItem);
	
		if (!isOntouchOutSize)
			postDelayed(mRunCheckCurrentItem, 30);

	}
	

	private Runnable mRunCheckCurrentItem = new Runnable() {

		@Override
		public void run() {

			checkCurrentItem();
		}
	};

	private void checkCurrentItem() {
		if (!isStartOutTouch)
			return;

		isStartOutTouch = false;

		final int position = (getScrollX() + widthItem / 2) / (widthItem);
		Log.i(VIEW_LOG_TAG, "FINISH SCROLL   " + getScrollX() + "/ " + position);
		prevText = currentText;
		currentText = position;
		setTextActive();
		// disable roi de scroll
		smoothScrollTo(position * widthItem, 0);
		postDelayed(new Runnable() {

			@Override
			public void run() {
				mViewPager.setCurrentItem(position);

			}
		}, 50);

	}

	private void setTextActive() {
		if (tabsContainer.getChildCount()<=2) return;;
		Log.i(VIEW_LOG_TAG, "Last / Current " + prevText + "/ " + currentText);
		TextView prev = (TextView) tabsContainer.getChildAt(prevText + 1);
		TextView current = (TextView) tabsContainer.getChildAt(currentText + 1);
		prev.setTextColor(colorText);
		prev.setTextSize(textSize);
		prev.setTypeface(mFontBussiness.getOpenSanRegular());
		current.setTextColor(colorTextActive);
		current.setTextSize(textActiveSize);
		current.setTypeface(mFontBussiness.getOpenSanSemiBold());
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		isOntouchOutSize = true;
		isStartOutTouch = true;
		if (ev.getAction() == MotionEvent.ACTION_UP) {

			isOntouchOutSize = false;
			removeCallbacks(mRunCheckCurrentItem);
			;
			postDelayed(mRunCheckCurrentItem, 50);

		}
		return super.onTouchEvent(ev);
	}

	public void setOnPageChangeListener(
			OnPageChangeListener onPageChangeListener) {

		this.mPageChangeListener = onPageChangeListener;

	}

	@Override
	public void onClick(View v) {

		try {

			int position = (Integer) v.getTag();
			mViewPager.setCurrentItem(position);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
