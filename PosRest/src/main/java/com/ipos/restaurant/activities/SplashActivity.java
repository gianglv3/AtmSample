package com.ipos.restaurant.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ipos.restaurant.ApplicationFoodBook;
import com.ipos.restaurant.Constants;
import com.ipos.restaurant.FoodBookTracker;
import com.ipos.restaurant.GA;
import com.ipos.restaurant.R;
import com.ipos.restaurant.util.Log;
import com.ipos.restaurant.util.SharedPref;

import java.util.Date;


public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private TextView mVersion;

    private Handler mHandler;



    @Override
    public void onStart() {
        super.onStart();
        ApplicationFoodBook.setIsVisible(true);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        ApplicationFoodBook.setIsVisible(false);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Date mDate = new Date();

        String time =mDate.getHours()+"h";
        Log.d(TAG,"Start app "+time);
        FoodBookTracker.sendEvent(GA.TIME_START_APP,time,"",0);
        setContentView(R.layout.activity_splash);
        mVersion = (TextView) findViewById(R.id.version);
        PackageInfo pInfo;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            String textVersion = "V" + version;
            mVersion.setText(textVersion);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mHandler = new Handler();
        mHandler.postDelayed(mRunAutoFeature, Constants.SPLASH_TIME);

        // ==============phuc vu ban tin push=============///
        SharedPref pref = new SharedPref(this);
        String json = getIntent().getStringExtra(Constants.KEY_PUTDATA);
        Log.i("SplashActivity.onCreate()", "jso=>n " + json);
        if (json != null && !"".equals(json)) {


            pref.putString(Constants.KEY_PUTDATA, json);

        }

        pref.putLong(Constants.KEY_ON_APP, System.currentTimeMillis());





        //loadFeature();

    }

    private Runnable mRunAutoFeature = new Runnable() {
        @Override
        public void run() {

            Log.i(TAG, "mRunAutoFeature");

            Intent intent = null;


                    intent = new Intent(SplashActivity.this, MainAtmActivity.class);
//                DmPush pus = new DmPush();
//                pus.setPosId("123");
//                pus.setPosName("TESST");
//                intent.putExtra(Constants.KEY_DATA,pus);


            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         //   intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);

            finish();
        }
    };









    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "Ondestroy");

    }





}
