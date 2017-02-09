package com.ipos.restaurant.bussiness;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.maps.android.SphericalUtil;
import com.ipos.restaurant.ApplicationFoodBook;
import com.ipos.restaurant.R;
import com.ipos.restaurant.activities.MainAtmActivity;
import com.ipos.restaurant.fragment.MapControlFragment;
import com.ipos.restaurant.fragment.MapSuportFragment;
import com.ipos.restaurant.model.DmAtm;
import com.ipos.restaurant.util.DistanceUtil;
import com.ipos.restaurant.util.Log;
import com.ipos.restaurant.util.ToastUtil;
import com.ipos.restaurant.util.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sama2
 *
 */
public class MapControlBussiness implements OnMarkerClickListener,
		OnInfoWindowClickListener, OnMarkerDragListener,
		OnMyLocationButtonClickListener, OnCameraChangeListener {

	private MainAtmActivity mActivity;
	private ImageLoaderBussiness mImageLoader;
	private MapSuportFragment mSupportMapFragment;
	private GoogleMap mGoogleMap;
	private Map<Marker, DmAtm> mHashMap = new HashMap<>();
	private ArrayList<DmAtm> data = new ArrayList<>();
	private int deviceWidth;
	private int circleWidth;
	private DmAtm mItem;
	private List<Marker> mMarkerRainbow = new ArrayList<Marker>();
	private MapControlFragment controlFragment;

	public MapControlBussiness(MainAtmActivity mainActivity,MapSuportFragment mapSuportFragment,MapControlFragment mapControlFragment) {
		this.mActivity = mainActivity;
		this.controlFragment=mapControlFragment;
		this.mSupportMapFragment=mapSuportFragment;
		ApplicationFoodBook app = (ApplicationFoodBook) mainActivity
				.getApplication();
		mImageLoader = app.getImageLoader();
		DisplayMetrics displaymetrics = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);

		deviceWidth = displaymetrics.widthPixels;
		deviceWidth = deviceWidth / 2;//ban kinh
		circleWidth = deviceWidth;
	}

	public void setWidthCircle(int dive) {
		circleWidth = dive;
	}

	public void setInit() {
		setUpMapIfNeeded();
	}

	public void setData(ArrayList<DmAtm> dt) {

		data.clear();
		data.addAll(dt);
		onResetMap();
	}

	public void addData(ArrayList<DmAtm> dt) {


		data.addAll(dt);
		onResetMap();
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mGoogleMap == null) {
			// Try to obtain the map from the SupportMapFragment.

			mGoogleMap = mSupportMapFragment.getMap();

			// Check if we were successful in obtaining the map.
			if (mGoogleMap != null) {
				try {
					setUpMap();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void setUpMap() {


		mGoogleMap.setOnCameraChangeListener(this);

		// Hide the zoom controls as the button panel will cover it.
		mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
		//mGoogleMap.getUiSettings().setZoomGesturesEnabled(false);
		// mMap.getUiSettings().setCompassEnabled(true);
		// Add lots of markers to the map.
		addMarkersToMap();

		// Setting an info window adapter allows us to change the both the
		// contents and look of the
		// info window.
		mGoogleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

		// Set listeners for marker events. See the bottom of this class for
		// their behavior.
		mGoogleMap.setOnMarkerClickListener(this);
		mGoogleMap.setOnInfoWindowClickListener(this);
		mGoogleMap.setOnMarkerDragListener(this);


		if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.

		} else{
			mGoogleMap.setMyLocationEnabled(true);
		}


		mGoogleMap.setOnMyLocationButtonClickListener(this);

		mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
		mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);

		// VisibleRegion vr = mGoogleMap.getProjection().getVisibleRegion();
		// double left = vr.latLngBounds.southwest.longitude;
		// double top = vr.latLngBounds.northeast.latitude;
		// double right = vr.latLngBounds.northeast.longitude;
		// double bottom = vr.latLngBounds.southwest.latitude;
		//
		// Location MiddleLeftCornerLocation = null;//(center's
		// latitude,vr.latLngBounds.southwest.longitude)
		// Location center=new Location("center");
		// center.setLatitude( vr.latLngBounds.getCenter().latitude);
		// center.setLongitude( vr.latLngBounds.getCenter().longitude);
		// float dis = center.distanceTo(MiddleLeftCornerLocation);//calculate
		// distane be

		// 05-26 21:13:06.836: D/Zoom(9709): Zoom: 14.163085/lat/lng:
		// (20.916532925094366,105.80302264541388)/ 1.072682E7
		Location myLocation = LocationBussiness.getMyLocation(mActivity);
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
				myLocation.getLatitude(), myLocation.getLongitude()), 13));

	}

	public void goMyLocation() {
        try {
            Location myLocation = mGoogleMap.getMyLocation();

		if (myLocation == null||mGoogleMap==null) {
			//myLocation=	LocationHelper.getMyLocation(mActivity);
			//ToastUtil.makeText(mActivity, R.string.mylocation_not_data);
			return;
		}
		CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(
				myLocation.getLatitude(), myLocation.getLongitude()));
	//	CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

		mGoogleMap.moveCamera(center);

//		mGoogleMap.animateCamera(zoom);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public void goZoomByDistance(int distance) {
		if (distance==0) {
			distance=1000;
		}
		animateToMeters(distance);
	}

	private void addMarkersToMap() {
		if (mGoogleMap == null)
			return;
		// Uses a colored icon

		initItem();
		initDataList();

		// Pan to see all markers in view.
		// Cannot zoom to bounds until the map has a size.

//		final View mapView = mSupportMapFragment.getView();
//		if (mapView.getViewTreeObserver().isAlive()) {
//			mapView.getViewTreeObserver().addOnGlobalLayoutListener(
//					new OnGlobalLayoutListener() {
//						@SuppressWarnings("deprecation")
//						// We use the new method when supported
//						@SuppressLint("NewApi")
//						// We check which build version we are using.
//						@Override
//						public void onGlobalLayout() {
//
//							if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//								mapView.getViewTreeObserver()
//										.removeGlobalOnLayoutListener(this);
//							} else {
//								mapView.getViewTreeObserver()
//										.removeOnGlobalLayoutListener(this);
//							}
//
//							onForcusPoint();
//							onForcusData();
//						}
//					});
//		}
		goMyLocation();
	}

	private void initDataList() {
		if (data == null)
			return;
		for (int i = 0; i < data.size(); i++) {
			DmAtm it = data.get(i);
			MarkerOptions makerOption = new MarkerOptions();
			makerOption.title(it.getName());
			LatLng position = new LatLng(it.getLatitude(),
					it.getLongtitude());
			makerOption.position(position);

			//mImageLoader.setImageFresco(it.getImageMaster(),(SimpleDraweeView) marker.findViewById(R.id.image));
			makerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.annotation));

			makerOption.flat(false);
			Marker maker = mGoogleMap.addMarker(makerOption);
			mHashMap.put(maker, it);
			mMarkerRainbow.add(maker);
		}
	}

	private void initItem() {

		if (mItem != null) {
			MarkerOptions makerOption = new MarkerOptions();
			makerOption.title(mItem.getName());
			LatLng position = new LatLng(mItem.getLatitude(),
					mItem.getLongtitude());
			makerOption.position(position);

//			mImageLoader.setImageFresco(mItem.getImageMaster(),(SimpleDraweeView) marker.findViewById(R.id.image));
			//makerOption.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(mActivity, marker)));
			
			makerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.annotation));

			makerOption.flat(true);
			Marker maker = mGoogleMap.addMarker(makerOption);
			mHashMap.put(maker, mItem);
			mMarkerRainbow.add(maker);

		}

	}

	private void onForcusData() {
		if (data == null)
			return;

		Builder bounds = new LatLngBounds.Builder();

		for (int i = 0; i < data.size(); i++) {
			DmAtm it = data.get(i);
			LatLng postition = new LatLng(it.getLatitude(),
					it.getLongtitude());

			bounds.include(postition);
		}
//		Location myLocation =ApplicationFoodBook.getInstance().getmLocationBussiness().getLocationCurrent();
//		if (myLocation!=null) {
//			LatLng postition = new LatLng(myLocation.getLatitude(),
//					myLocation.getLongitude());
//			bounds.include(postition);
//		}
		try {
			Log.i("MapMarkerActivity.onForcusPoint()", "for cus ");
			mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(
					bounds.build(), 100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onForcusPoint() {
		if (mItem == null)
			return;
		Builder bounds = new LatLngBounds.Builder();

		LatLng postition = new LatLng(mItem.getLatitude(),
				mItem.getLongtitude());
		LatLng postition2 = new LatLng(mItem.getLatitude() + 0.01,
				mItem.getLongtitude() + 0.01);
		LatLng postition3 = new LatLng(mItem.getLatitude() - 0.01,
				mItem.getLongtitude() - 0.01);
		bounds.include(postition).include(postition2).include(postition3);

		try {
			Log.i("MapMarkerActivity.onForcusPoint()", "for cus ");
			mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(
					bounds.build(), 100));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkReady() {
		if (mGoogleMap == null) {
			ToastUtil.makeText(mActivity, R.string.map_not_ready,
					Toast.LENGTH_SHORT);

			return false;
		}
		return true;
	}

	/** Called when the Clear button is clicked. */
	public void onClearMap() {
		if (!checkReady()) {
			return;
		}
		mGoogleMap.clear();
	}

	/** Called when the Reset button is clicked. */
	public void onResetMap() {
		if (!checkReady()) {
			return;
		}
		// Clear the map because we don't want duplicates of the markers.
		mGoogleMap.clear();
		addMarkersToMap();
	}

	@Override
	public boolean onMyLocationButtonClick() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onMarkerDrag(Marker marker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMarkerDragEnd(Marker marker) {


	}

	@Override
	public void onMarkerDragStart(Marker marker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInfoWindowClick(Marker marker) {

		DmAtm us = mHashMap.get(marker);
		if (us!=null) {
			Utilities.gotodriectMap(mActivity,""+us.getLatitude(),""+us.getLongtitude(),us.getName());
		}
	}

	@Override
	public boolean onMarkerClick(Marker marker) {


		return false;
	}

	// ====orther claass==//

	/** Demonstrates customizing the info window and/or its contents. */
	class CustomInfoWindowAdapter implements InfoWindowAdapter {

		// These a both viewgroups containing an ImageView with id "badge" and
		// two TextViews with id
		// "title" and "snippet".
		private final View mWindow;
		private final View mContents;

		@SuppressLint("InflateParams")
		CustomInfoWindowAdapter() {
			mWindow = mActivity.getLayoutInflater().inflate(
					R.layout.custom_info_window, null);
			mContents = mActivity.getLayoutInflater().inflate(
					R.layout.custom_info_contents, null);

		}

		@Override
		public View getInfoWindow(Marker marker) {
			return null;
//			render(marker, mWindow);
//			return mWindow;
		}

		@Override
		public View getInfoContents(Marker marker) {

			render(marker, mContents);
			return mContents;
		}

		private void render(Marker marker, View view) {

			DmAtm us = mHashMap.get(marker);

			if (us == null)
				return;

			//mImageLoader.getCache(us.getImagePath());

			SimpleDraweeView img = (SimpleDraweeView) view.findViewById(R.id.badge);
			//mImageLoader.setImageFresco(us.getImagePathThumb(), img);
			 	TextView titleUi = ((TextView) view.findViewById(R.id.title));
			TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));

			DistanceUtil.caluDistane(us);
			if (TextUtils.isEmpty(us.getShowDistance())) {
				titleUi.setText(us.getName());
			} else {
				titleUi.setText(us.getName() + " ~ " + us.getShowDistance());
			}
			snippetUi.setText(us.getAddres());
		}
	}

	@Override
	public void onCameraChange(CameraPosition position) {

		LatLngBounds bounds = mGoogleMap.getProjection().getVisibleRegion().latLngBounds;
		VisibleRegion vr = mGoogleMap.getProjection().getVisibleRegion();
		double left = vr.latLngBounds.southwest.longitude;
		double top = vr.latLngBounds.northeast.latitude;
		double right = vr.latLngBounds.northeast.longitude;
		double bottom = vr.latLngBounds.southwest.latitude;

		Location MiddleLeftCornerLocation = new Location("Left");// (center's
																	// latitude,vr.latLngBounds.southwest.longitude)

		Location center = new Location("center");
		center.setLatitude(vr.latLngBounds.getCenter().latitude);
		center.setLongitude(vr.latLngBounds.getCenter().longitude);
		MiddleLeftCornerLocation.setLatitude(center.getLatitude());
		MiddleLeftCornerLocation.setLongitude(right);

		float dis = center.distanceTo(MiddleLeftCornerLocation);// calculate
																// distane
		Log.d("Zoom", "Zoom: " + position.zoom + "/" + position.target + "/ "
				+ dis);
		float percent=(float)circleWidth/(float)deviceWidth;
//		mActivity.setZoomSlide((int) (dis*percent));

		controlFragment.setRadius((dis*percent));

		//Log.d("MAP","Percent "+(dis*percent));

	}

	private void animateToMeters(int meters) {
		// int mapHeightInDP =200;
		// Resources r = mActivity.getResources();
		// Log.i("Zoom ", "map size "+mapHeightInDP+"/ "+meters);
		// int mapSideInPixels = (int)
		// TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mapHeightInDP,
		// r.getDisplayMetrics());
		float percent=(float)circleWidth/(float)deviceWidth;
		float metertem=(float)meters/percent;

		Log.i("ZOom", "Zoom map " + meters + "/" + metertem + "/ " + circleWidth + "/ " + deviceWidth + "/ " + percent);
		VisibleRegion vr = mGoogleMap.getProjection().getVisibleRegion();

		Location center = new Location("center");
		center.setLatitude(vr.latLngBounds.getCenter().latitude);
		center.setLongitude(vr.latLngBounds.getCenter().longitude);
		double lat =center.getLatitude();
		double log=center.getLongitude();
		LatLng point = new LatLng(lat,log);
		LatLngBounds latLngBounds = calculateBounds(point, metertem);
		if (latLngBounds != null) {
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(
					latLngBounds, 0);
			if (mGoogleMap != null)
				mGoogleMap.animateCamera(cameraUpdate,100, null);

		}

	}

	private LatLngBounds calculateBounds(LatLng center, double radius) {
		return new LatLngBounds.Builder()
				.include(SphericalUtil.computeOffset(center, radius, 0))
				.include(SphericalUtil.computeOffset(center, radius, 90))
				.include(SphericalUtil.computeOffset(center, radius, 180))
				.include(SphericalUtil.computeOffset(center, radius, 270))
				.build();
	}

}
