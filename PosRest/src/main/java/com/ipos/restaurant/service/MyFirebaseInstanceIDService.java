/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ipos.restaurant.service;


import android.content.Context;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.ipos.restaurant.Constants;
import com.ipos.restaurant.util.Log;
import com.ipos.restaurant.util.SharedPref;

import java.sql.Timestamp;

import static com.ipos.restaurant.util.Utilities.getAppVersion;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    private static final String PROPERTY_REG_ID = "FB_registration_id";
    private static final String PROPERTY_APP_VERSION = "FB_appVersion";
    private static final String PROPERTY_ON_SERVER_EXPIRATION_TIME = "FB_onServerExpirationTimeMs";
    public static final long REGISTRATION_EXPIRY_TIME_MS = 1000 * 3600 * 5;//4 ngay la expire ngay
    private SharedPref pref;

    @Override
    public void onCreate() {
        super.onCreate();
        pref = new SharedPref(this);

    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.

        pref.putString(Constants.KEY_PUSH_TOKEN, refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        updatePushOnserver(this);
    }
    public static void updatePushOnserver(final Context mContext) {

    }

    public  static void checkUpdateOnfoodbook(Context mContext) {

        Log.d(TAG,"checkUpdateOnfoodbook ");

        if (getRegistrationId(mContext).equals("")) {
            updatePushOnserver(mContext);
        }

    }
    /**
     * Stores the registration id, app versionCode, and expiration time in the
     * application's {@code SharedPreferences}.
     *
     * @param context
     *            application's context.
     * @param regId
     *            registration id
     */
    private static void setRegistrationId(Context context, String regId) {
        final SharedPref prefs = new SharedPref(context);
        int appVersion = getAppVersion(context);
        Log.v(TAG, "Saving regId on app version " + appVersion);
        prefs.putString(PROPERTY_REG_ID, regId);
        prefs.putInt(PROPERTY_APP_VERSION, appVersion);
        long expirationTime = System.currentTimeMillis()
                + REGISTRATION_EXPIRY_TIME_MS;
        Log.v(TAG, "Setting registration expiry time to "
                + new Timestamp(expirationTime));
        prefs.putLong(PROPERTY_ON_SERVER_EXPIRATION_TIME, expirationTime);
    }

    /**
     * Gets the current registration id for application on GCM service.
     * <p>
     * If result is empty, the registration has failed.
     *
     * @return registration id, or empty string if the registration is not
     *         complete.
     */
    private static String getRegistrationId(Context context) {
        final SharedPref prefs = new SharedPref(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.length() == 0) {
            Log.v(TAG, "Registration not found.");
            return "";
        }
        // check if app was updated; if so, it must clear registration id to
        // avoid a race condition if GCM sends a message
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
                Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion || isRegistrationExpired(context)) {
            Log.v(TAG, "App version changed or registration expired. "
                    + currentVersion + "/ " + registeredVersion);
            return "";
        }
        Log.d(TAG,"Register Push "+registrationId);
        return registrationId;
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */

    /**
     * Checks if the registration has expired.
     *
     * <p>
     * To avoid the scenario where the device sends the registration to the
     * server but the server loses it, the app developer may choose to
     * re-register after REGISTRATION_EXPIRY_TIME_MS.
     *
     * @return true if the registration has expired.
     */
    private static boolean isRegistrationExpired(Context mContext) {
        final SharedPref prefs = new SharedPref(mContext);
        // checks if the information is not stale
        long expirationTime = prefs.getLong(PROPERTY_ON_SERVER_EXPIRATION_TIME,
                -1);
        Log.i(TAG, "Registration----> "
                + expirationTime + "/ " + System.currentTimeMillis());
        return System.currentTimeMillis() > expirationTime;
    }

    public  static  String getPushId(Context mContext) {
        final SharedPref prefs = new SharedPref(mContext);
        String registrationId = prefs.getString(Constants.KEY_PUSH_TOKEN, "");
        return  registrationId;
    }
}
