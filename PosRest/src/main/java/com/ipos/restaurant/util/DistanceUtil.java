package com.ipos.restaurant.util;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.ipos.restaurant.bussiness.LocationBussiness;
import com.ipos.restaurant.model.DmAtm;

public class DistanceUtil {

	
	private static double distance(LatLng start, LatLng end) {
		Location loca1 = new Location("START");
		Location loca2 = new Location("END");
		loca1.setLatitude(start.latitude);
		loca1.setLongitude(start.longitude);
		loca2.setLatitude(end.latitude);
		loca2.setLongitude(end.longitude);

//	/	Log.i("Utilities.distance()", "Start latlog----> " + start.latitude
//				+ "," + start.longitude);
//		Log.i("Utilities.distance()", "End latlog----> " + end.latitude + ","
//				+ end.longitude);
		return loca1.distanceTo(loca2);
	}



	public static void caluDistane(DmAtm item  ) {
		if (item==null) return;
		if (item.getDistance() == -1) {

			Location mLocation = LocationBussiness.getMyLocation();
			if (mLocation==null) {
				return;
			}
			String lt1 =""+ mLocation.getLatitude();
			String ln1 =""+mLocation.getLongitude();

			LatLng mylocation = new LatLng(Double.parseDouble(lt1),
					Double.parseDouble(ln1));
			LatLng des = new LatLng(item.getLatitude(),
					item.getLongtitude());
			double ds =  distance(mylocation, des);
			Log.i("Utilities.caluDistane()", "distance "+ds+"/ "+item.getId());
			item.setDistance(ds);
		}

	}

	 
}
