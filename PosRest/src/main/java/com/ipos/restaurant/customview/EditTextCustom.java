package com.ipos.restaurant.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.ipos.restaurant.R;

/**
 * Created by GianglV on 8/3/2015.
 */
public class EditTextCustom extends EditText {
    public EditTextCustom(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    public EditTextCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public EditTextCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextCustom(Context context) {
        super(context);
        init();
    }

    private void init() {
        int padding =getContext().getResources().getDimensionPixelSize(R.dimen.standard_margin);
        setPadding(padding,padding,padding,padding);

    }
}
