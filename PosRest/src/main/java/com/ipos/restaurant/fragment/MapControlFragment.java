package com.ipos.restaurant.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ipos.restaurant.ApplicationFoodBook;
import com.ipos.restaurant.R;
import com.ipos.restaurant.activities.MainAtmActivity;
import com.ipos.restaurant.bussiness.LocationBussiness;
import com.ipos.restaurant.bussiness.MapControlBussiness;
import com.ipos.restaurant.database.DmAtmDataSource;
import com.ipos.restaurant.model.DmAtm;

import java.util.ArrayList;

/**
 * Created by GiangLV on 2/8/2017.
 */

public class MapControlFragment extends  BaseFragment {


    private MapControlBussiness mapControlBussiness;
    private DmAtmDataSource mDmAtmDataSource;
    private  MapSuportFragment mapSuportFragment;

    private TextView mRadius;

    @Override
    protected int getItemLayout() {
        return R.layout.fragment_map;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mDmAtmDataSource=DmAtmDataSource.getInstance(mActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= super.onCreateView(inflater, container, savedInstanceState);
        mRadius= (TextView) v.findViewById(R.id.radius);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initMap();

    }

    private  void initMap() {
          mapSuportFragment = new MapSuportFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.map, mapSuportFragment ).commit();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                mapControlBussiness = new MapControlBussiness((MainAtmActivity) mActivity,mapSuportFragment,MapControlFragment.this);
                mapControlBussiness.setInit();
                showAllData();
            }
        },1000);



    }


    public  void initMapBussiness() {
        if (mapSuportFragment!=null) {
            mapControlBussiness = new MapControlBussiness((MainAtmActivity) mActivity,mapSuportFragment,MapControlFragment.this);
            mapControlBussiness.setInit();
            showAllData();
        }
    }
    private void showAllData(){
        ArrayList<DmAtm> temp =mDmAtmDataSource.getAllPosFromDb();

        if (temp!=null) {
            mapControlBussiness.setData(temp);
        }
    }
    public static MapControlFragment newInstance() {
        MapControlFragment fragment = new MapControlFragment();

        return fragment;
    }

    public void searchAtmByName(String query) {

        LocationBussiness mLocationBussiness = ApplicationFoodBook.getInstance().getmLocationBussiness();

        if (TextUtils.isEmpty(query)) {
            ArrayList<DmAtm> temp = mDmAtmDataSource.getAllPosFromDb();
            if (temp != null) {
                mapControlBussiness.setData(temp);
                mapControlBussiness.goMyLocation();
                mapControlBussiness.goZoomByDistance((int)mLocationBussiness.getRadius());

            }
        } else {
            if (mLocationBussiness.getLocationCurrent() != null) {
                double lat = mLocationBussiness.getLocationCurrent().getLatitude();
                double lng = mLocationBussiness.getLocationCurrent().getLongitude();
                ArrayList<DmAtm> temp = mDmAtmDataSource.getAllPosFromDb(query, lat, lng);
                if (temp != null) {
                    mapControlBussiness.setData(temp);
                    mapControlBussiness.goMyLocation();
                    mapControlBussiness.goZoomByDistance((int)mLocationBussiness.getRadius());

                }
            } else{
                ArrayList<DmAtm> temp = mDmAtmDataSource.getAllPosFromDb(query);
                if (temp != null) {
                    mapControlBussiness.setData(temp);
                    mapControlBussiness.goMyLocation();
                    mapControlBussiness.goZoomByDistance((int)mLocationBussiness.getRadius());

                }
            }
        }
    }

    public void setData(ArrayList<DmAtm> datas) {
        LocationBussiness mLocationBussiness = ApplicationFoodBook.getInstance().getmLocationBussiness();

        mapControlBussiness.setData(datas);
//        mapControlBussiness.goMyLocation();
        mapControlBussiness.goZoomByDistance((int)mLocationBussiness.getRadius());


    }

    public void setRadius(float distance) {

        String text="r=";
        if (distance < 1000) {//1km
            text=text+ String.format("%3.0fm", distance);
        } else {

            text = text + String.format("%.2fkm", distance / 1000);
        }

        mRadius.setText(text);

    }
}
