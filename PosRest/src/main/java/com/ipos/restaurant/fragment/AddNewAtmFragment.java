package com.ipos.restaurant.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ipos.restaurant.R;
import com.ipos.restaurant.database.DmAtmDataSource;
import com.ipos.restaurant.model.DmAtm;
import com.ipos.restaurant.util.ToastUtil;

import static com.ipos.restaurant.R.id.lat;

/**
 * Created by SupeMrOne on 2/7/2017.
 */
public class AddNewAtmFragment extends  BaseFragment {


    private EditText mName;
    private EditText mAddress;
    private EditText mLat;
    private EditText mLng;

    private DmAtmDataSource mDmAtmDataSource;

    private View mAdd;

    @Override
    protected int getItemLayout() {
        return R.layout.fragment_add_atm;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mDmAtmDataSource =DmAtmDataSource.getInstance(mActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= super.onCreateView(inflater, container, savedInstanceState);
        mName= (EditText) v.findViewById(R.id.name);
        mAddress= (EditText) v.findViewById(R.id.address);
        mLat= (EditText) v.findViewById(lat);
        mLng= (EditText) v.findViewById(R.id.lng);
        mAdd = v.findViewById(R.id.add_atm);
        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initDataView();
    }

    private  void initDataView(){

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAtm();
            }
        });
    }



    private  void addAtm() {


        String name=mName.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {

            ToastUtil.makeText(mActivity,R.string.ten_khong_duoc_rong);
            return;
        }

        String add=mAddress.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {

            ToastUtil.makeText(mActivity,R.string.diachi_ko_duocrong);
            return;
        }

        String lat =mLat.getText().toString().trim();

        String lng=mLng.getText().toString().trim();


        if (TextUtils.isEmpty(lat)||TextUtils.isEmpty(lng)) {
            ToastUtil.makeText(mActivity,R.string.nhap_kinhvi);
            return;
        }

        DmAtm atm = new DmAtm();

        atm.setName(name);

        atm.setAddres(add);
        try {
            double latitude = Double.parseDouble(lat);
            atm.setLatitude(latitude);
        }catch (Exception e) {
            e.printStackTrace();
        }

        try {
            double longtitude=Double.parseDouble(lng);
            atm.setLongtitude(longtitude);
        }catch (Exception e) {
            e.printStackTrace();
        }

        mDmAtmDataSource.insertPos(atm);


        ToastUtil.makeText(mActivity,R.string.themoi_atm_succ);

        mName.setText("");
        mAddress.setText("");
        mLng.setText("");
        mLat.setText("");
    }

    public  static AddNewAtmFragment newInstance() {
        AddNewAtmFragment fragment = new AddNewAtmFragment();
        return fragment;
    }

    public void showKeyBoard() {
        if (mAdd!=null) {
            mAdd.setVisibility(View.GONE);
        }
    }

    public void hidenKeyboard() {
        if (mAdd!=null) {
            mAdd.setVisibility(View.VISIBLE);
        }
    }


}
