package com.ipos.restaurant.bussiness;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.ipos.restaurant.ApplicationFoodBook;

/**
 * Created by GiangLV on 5/26/2016.
 */
public class PermissionBussiness {

    private static final String TAG = PermissionBussiness.class.getSimpleName();

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    public static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 2;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_SD = 3;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 4;
    private static final int MY_PERMISSIONS_REQUEST_READ_SMS = 5;
    private Activity mActivity;



    public  PermissionBussiness(Activity mActivity) {
        this.mActivity=mActivity;

    }

    private void checkContactPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.d(TAG, " SHOW POP REQUEST checkContactPermission");
            } else {

                // No explanation needed, we can request the permission.
                Log.d(TAG, " Canrequest REQUEST checkContactPermission");
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else{
            checkLocationPermission();
        }
    }

    private void checkLocationPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.d(TAG, " SHOW POP REQUEST checkLocationPermission");
            } else {

                // No explanation needed, we can request the permission.
                Log.d(TAG, " Canrequest REQUEST checkLocationPermission");
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            checkWriteSdPermission();
        }

    }


    private void checkWriteSdPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.d(TAG, " SHOW POP REQUEST checkWriteSdPermission");
            } else {

                // No explanation needed, we can request the permission.
                Log.d(TAG, " Canrequest REQUEST checkWriteSdPermission");
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_SD);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else{
            checkCallPhonePermission();
        }
    }

    private void checkCallPhonePermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.CALL_PHONE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.d(TAG, " SHOW POP REQUEST checkCallPhonePermission");
            } else {

                // No explanation needed, we can request the permission.
                Log.d(TAG, " Canrequest REQUEST checkCallPhonePermission");
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            checkReceiveSMSPermission();
        }
    }

    private void checkReceiveSMSPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.RECEIVE_SMS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.d(TAG, " SHOW POP REQUEST checkReceiveSMSPermission");
            } else {

                // No explanation needed, we can request the permission.
                Log.d(TAG, " Canrequest REQUEST checkReceiveSMSPermission");
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.RECEIVE_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG,"Permission result "+grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                checkLocationPermission();

                return;
            case MY_PERMISSIONS_REQUEST_COARSE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    ApplicationFoodBook.getInstance().getmLocationBussiness().init();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                checkWriteSdPermission();
                return;
            case MY_PERMISSIONS_REQUEST_WRITE_SD:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                checkCallPhonePermission();

                return;
            case MY_PERMISSIONS_REQUEST_CALL_PHONE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                checkReceiveSMSPermission();
                return;

            case MY_PERMISSIONS_REQUEST_READ_SMS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

                return;

        }


    }


    public void run() {
        checkContactPermission();
    }
}
