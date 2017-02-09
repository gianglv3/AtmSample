package com.ipos.restaurant.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.ipos.restaurant.ApplicationFoodBook;
import com.ipos.restaurant.util.Log;


/**
 * Created by GiangLV on 12/10/2016.
 */

public class GpsBroadcast extends BroadcastReceiver {
    private String TAG="GpsBroadcast";

    @Override
    public void onReceive(Context context, Intent intent) {
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);


        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {

        }
        else
        {
            Log.d(TAG+".onReceive","Location off 1.1 ");
            ApplicationFoodBook.getInstance().getmLocationBussiness().setNull();

        }


    }
}
