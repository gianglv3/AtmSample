package com.ipos.restaurant;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class FoodBookTracker {

	
	public static GoogleAnalytics analytics;
	public static Tracker tracker;
	

	public static void init(Context mContext) {
		 	analytics = GoogleAnalytics.getInstance(mContext);
		    analytics.setLocalDispatchPeriod(1800);

		    tracker = analytics.newTracker(mContext.getString(R.string.ga_trackingId)); // Replace with actual tracker/property Id
		    tracker.enableExceptionReporting(false);
		    tracker.enableAdvertisingIdCollection(true);
		    tracker.enableAutoActivityTracking(true);

	}

    public static void sendEvent(String category,String action,String label,long value) {
     //   EasyTracker.getTracker().sendEvent(category, action, label, value);
    	if (tracker==null) return;
    	// Builder parameters can overwrite the screen name set on the tracker.
    	tracker.send(new HitBuilders.EventBuilder()
    	       .setCategory(category)
    	       .setAction(action)
    	       .setLabel(label)
    	       .build());
    }
    

    
}
