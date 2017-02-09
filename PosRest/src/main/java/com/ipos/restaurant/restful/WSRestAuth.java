package com.ipos.restaurant.restful;

import android.content.Context;

import com.ipos.restaurant.util.SharedPref;

/**
 * Created by GiangLV on 12/4/2015.
 */
public class WSRestAuth extends  AbsRestful {

    public WSRestAuth(Context context) {
        this.context = context;
        config = new SharedPref(context);
    }


}
