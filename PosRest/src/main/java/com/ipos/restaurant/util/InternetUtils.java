package com.ipos.restaurant.util;

import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class InternetUtils {
    public static boolean isReachable(Context context) {
        // First, check we have any sort of connectivity
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = connMgr.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {
            // Some sort of connection is open, check if server is reachable
            try {
                URL url = new URL("http://www.google.com");
                HttpURLConnection urlc = (HttpURLConnection) url
                        .openConnection();
                urlc.setRequestProperty("User-Agent", "Android Application");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(2 * 1000); // Thirty seconds timeout in
                                                  // milliseconds
                urlc.connect();
                if (urlc.getResponseCode() == 200) { // Good response
                    // Toast.makeText(context, "High connection",
                    // Toast.LENGTH_LONG).show();
                    return true;
                } else { // Anything else is unwanted
                    ToastUtil.makeText(context, "Low connection", Toast.LENGTH_LONG);
                            
                    return false;
                }
            } catch (Exception e) {
                ToastUtil.makeText(context, "Connection Error", Toast.LENGTH_LONG)
                        ;
                Log.i("InternetUtils.isReachable()",
                        "DatDH----> " + e.getMessage());
                return false;
            }
        } else {
            ToastUtil.makeText(context, "No connection", Toast.LENGTH_LONG);
            return false;
        }
    }
    
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager 
              = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
