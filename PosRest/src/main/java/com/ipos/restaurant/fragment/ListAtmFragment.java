package com.ipos.restaurant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.ipos.restaurant.ApplicationFoodBook;
import com.ipos.restaurant.adapter.AtmAdapter;
import com.ipos.restaurant.bussiness.LocationBussiness;
import com.ipos.restaurant.database.DmAtmDataSource;
import com.ipos.restaurant.model.DmAtm;
import com.ipos.restaurant.util.SortHelper;
import com.ipos.restaurant.util.Utilities;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by GiangLV on 2/8/2017.
 */

public class ListAtmFragment extends  LoadMoreFragment {

    private AtmAdapter mAdapter;
    private ArrayList<DmAtm> datas= new ArrayList<>();
    private DmAtmDataSource mDmAtmDataSource= DmAtmDataSource.getInstance(ApplicationFoodBook.getInstance());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNoAddMore=true;
        isRefresh=false;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        searchAtmByName("");
        initAdapter();
        showListView(true);
    }

    private void initAdapter() {
        mAdapter = new AtmAdapter(mActivity,datas);
        mListView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utilities.hideKeyboard(null,mActivity);
                DmAtm us=datas.get(position);
                Utilities.gotodriectMap(mActivity,""+us.getLatitude(),""+us.getLongtitude(),us.getName());
            }
        });
    }


    public static ListAtmFragment newInstance() {
        ListAtmFragment fragment = new ListAtmFragment();
        return fragment;
    }


    public void searchAtmByName(String query) {

        if (mAdapter==null) {
            return;
        }
        LocationBussiness mLocationBussiness = ApplicationFoodBook.getInstance().getmLocationBussiness();

        datas.clear();
        if (TextUtils.isEmpty(query)) {
            ArrayList<DmAtm> temp = mDmAtmDataSource.getAllPosFromDb();
            if (temp != null) {
                datas.addAll(temp);
            }
            Collections.sort(datas, SortHelper.sorDistance);
        } else {


            if (mLocationBussiness.getLocationCurrent() != null) {
                double lat = mLocationBussiness.getLocationCurrent().getLatitude();
                double lng = mLocationBussiness.getLocationCurrent().getLongitude();
                ArrayList<DmAtm> temp = mDmAtmDataSource.getAllPosFromDb(query, lat, lng);
                if (temp != null) {
                    datas.addAll(temp);

                }

                Collections.sort(datas, SortHelper.sorDistance);
            } else {
                ArrayList<DmAtm> temp = mDmAtmDataSource.getAllPosFromDb(query);
                if (temp != null) {
                    datas.addAll(temp);
                }

            }
        }
        Log.d(TAG,"Query Search "+query+" / "+datas.size());
        mAdapter.notifyDataSetChanged();
    }
    public ArrayList<DmAtm> getDatas() {
        return datas;
    }
}
