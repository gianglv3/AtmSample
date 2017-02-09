package com.ipos.restaurant.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipos.restaurant.R;
import com.ipos.restaurant.bussiness.PermissionBussiness;
import com.ipos.restaurant.database.DmAtmDataSource;
import com.ipos.restaurant.fragment.AddNewAtmFragment;
import com.ipos.restaurant.fragment.ListAtmFragment;
import com.ipos.restaurant.fragment.MapControlFragment;
import com.ipos.restaurant.model.DmAtm;
import com.ipos.restaurant.util.Log;
import com.ipos.restaurant.util.Utilities;

import java.util.Random;
import java.util.TimerTask;

/**
 * Created by GiangLV on 12/15/2016.
 */

public class MainAtmActivity extends  BaseActivity   {
    private static final String IMPORTDATA = "IMPORT";


    private PermissionBussiness mPermissionBussiness;
    private Fragment mFragment;
    private Toolbar toolbar;

    private AddNewAtmFragment mAddNewAtmFragment;

    private MapControlFragment mMapControlFragment;

    private ListAtmFragment mListAtmFragment;
    private EditText edtSearch;
    private ImageView icClear;
    private View mCustomerSearch;

    private View mMapView;
    private View mContent;

    private DmAtmDataSource mDmAtmDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        findView();
        setupToolbar();
        initStart();

        displayMap();


        checkKeyboardHeight(findViewById(R.id.content));
    }

    private void findView(){
        mContent=findViewById(R.id.content);
        mMapView=findViewById(R.id.mapcontent);
    }

    private  void initStart() {

        mPermissionBussiness = new PermissionBussiness(MainAtmActivity.this);
        mPermissionBussiness.run();
        mDmAtmDataSource=DmAtmDataSource.getInstance(this);
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mCustomerSearch=getLayoutInflater().inflate(R.layout.include_search,null);

        getSupportActionBar().setCustomView(mCustomerSearch);

        getSupportActionBar().setDisplayShowCustomEnabled(true);

        edtSearch = (EditText) mCustomerSearch.findViewById(R.id.search);
        icClear = (ImageView)mCustomerSearch. findViewById(R.id.clear);
        icClear.setVisibility(View.GONE);
        icClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                icClear.setVisibility(View.VISIBLE);
                edtSearch.setText("");
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (s.length() > 0) {
                    icClear.setVisibility(View.VISIBLE);
                } else {
                    icClear.setVisibility(View.GONE);
                }


                processSearchMenu(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                    Utilities.hideKeyboard(edtSearch,MainAtmActivity.this);

                    showOnMap();

                return false;
            }
        });

    }

    private void showMapView(boolean b) {
        if (b){
            mMapView.setVisibility(View.VISIBLE);
            mContent.setVisibility(View.GONE);
        } else{
            mMapView.setVisibility(View.GONE);
            mContent.setVisibility(View.VISIBLE);
        }
    }

    private void displayAddNew(){
        if (mAddNewAtmFragment == null) {
            mAddNewAtmFragment = AddNewAtmFragment.newInstance();
        }

        showMapView(false);
        mFragment=mAddNewAtmFragment;
        executeFragmentTransaction(mAddNewAtmFragment, R.id.content, false, false);

        toolbar.setTitle(R.string.add_title_atm);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
    }

    private void displayMap(){
        if (mMapControlFragment == null) {
            mMapControlFragment = MapControlFragment.newInstance();


            executeFragmentTransaction(mMapControlFragment, R.id.mapcontent, false, false);
            toolbar.setTitle(R.string.app_name);
        }
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        showMapView(true);

    }
    private void displayList(){
        if (mListAtmFragment == null) {
            mListAtmFragment = ListAtmFragment.newInstance();
        }
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        showMapView(false);
        mFragment=mListAtmFragment;
        executeFragmentTransaction(mListAtmFragment, R.id.content, false, false);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main,menu);

        return super.onCreateOptionsMenu(menu);
    }



    private void showOnMap() {


        displayMap();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMapControlFragment.setData(mListAtmFragment.getDatas());
            }
        },700);


    }

    private boolean processSearchMenu(String newText) {


        if (!(mFragment instanceof ListAtmFragment)){
            displayList();
        }
            showMapView(false);

            mListAtmFragment.searchAtmByName(newText);
        return  true;


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mFragment!=null) {
            mFragment.onResume();
        }
    }

    @Override
    protected void showKeyboard() {

        if (mAddNewAtmFragment!=null) {
            mAddNewAtmFragment.showKeyBoard();
        }
    }

    @Override
    protected void hidenKeyboard() {
        if (mAddNewAtmFragment!=null) {
            mAddNewAtmFragment.hidenKeyboard();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();

        } else if (item.getItemId() ==R.id.add) {
            displayAddNew();
        } else if (item.getItemId() ==R.id.import_test) {
            importData();
        }
        //Log.d(TAG,"Press menu "+item.getItemId()+"/ "+item.getTitle());
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {


        if (mMapView.getVisibility()==View.GONE) {
            displayMap();
        } else{
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case PermissionBussiness.MY_PERMISSIONS_REQUEST_COARSE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    mHandler.postDelayed(new TimerTask() {
                        @Override
                        public void run() {

                            if (mMapControlFragment!=null) {
                                mMapControlFragment.initMapBussiness();
                                Log.d(TAG,"INIT MAP");
                            }
                        }
                    },1000);

                    Log.d(TAG,"ACcress LOCATION");
                }
                break;
        }
        mPermissionBussiness.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public   void importData() {

        boolean impor =config.getBoolean(IMPORTDATA,false);
        if (impor) return;;
        DmAtm mDmAtm = new DmAtm();

        mDmAtm.setName("Atm Hoàng Quốc Việt");
        mDmAtm.setAddres("106 Hoàng Quốc Việt, Nghĩa Đô, Cầu Giấy, Hà Nội");
        mDmAtm.setLatitude(21.045721);
        mDmAtm.setLongtitude(105.784992);
        mDmAtmDataSource.insertPos(mDmAtm);

        mDmAtm.setName("Atm Hoàng Hoa Thám");
        mDmAtm.setAddres("160 Hoàng Hoa Thám Thụy Khuê, Ba Đình, Hà Nội");
        mDmAtm.setLatitude(21.042178);
        mDmAtm.setLongtitude(105.820261);
        mDmAtmDataSource.insertPos(mDmAtm);

        mDmAtm.setName("Atm Lý Thái Tổ");
        mDmAtm.setAddres("15 Lý Thái Tổ, Hoàn Kiếm, Hà Nội");
        mDmAtm.setLatitude(21.029491);
        mDmAtm.setLongtitude(105.851189);
        mDmAtmDataSource.insertPos(mDmAtm);

        mDmAtm.setName("Atm Hàng Mành");
        mDmAtm.setAddres("24 Hàng Mành, Q. Hoàn Kiếm, Hà Nội");
        mDmAtm.setLatitude(21.031753);
        mDmAtm.setLongtitude(105.848217);
        mDmAtmDataSource.insertPos(mDmAtm);


        mDmAtm.setLatitude(21.146721);
        mDmAtm.setLongtitude(105.694992);
        mDmAtmDataSource.insertPos(mDmAtm);

        for (int i=0;i<15;i++) {
            mDmAtm.setName("Atm Ngách số "+i);
            mDmAtm.setAddres("Ngách số "+i+", Hà Nội");
            Random r=  new Random();

            long value=r.nextInt(5)*2000;
            if (value==0){
                value=1000;
            }
            double random=((double) i)/value;

            if (i%2==0){
                random=random*-1;
            }
            double lat=21.0099127+random;
            if (i%4==0){
                random=random*-1;
            }
            double lgn = 105.8034531 + random;
            //  Log.d(TAG,"LAT LONG "+lat+"/ "+lgn+"/ "+random+"/ "+value);
            mDmAtm.setLatitude(lat);
            mDmAtm.setLongtitude(lgn);
            mDmAtmDataSource.insertPos(mDmAtm);
        }


        for (int i=0;i<25;i++) {
            mDmAtm.setName("Atm Đường số "+i);
            mDmAtm.setAddres("Đường số "+i+", Hà Nội");
            Random r=  new Random();

            long value=r.nextInt(5)*1000;
            if (value==0){
                value=1000;
            }
            double random=((double) i)/value;

            if (i%2==0){
                random=random*-1;
            }
            double lat=21.0099013+random;
            if (i%4==0){
                random=random*-1;
            }
            double lgn = 105.8053272 + random;
            //  Log.d(TAG,"LAT LONG "+lat+"/ "+lgn+"/ "+random+"/ "+value);
            mDmAtm.setLatitude(lat);
            mDmAtm.setLongtitude(lgn);
            mDmAtmDataSource.insertPos(mDmAtm);
        }

        for (int i=0;i<20;i++) {
            mDmAtm.setName("Atm Phố số "+i);
            mDmAtm.setAddres("Phố số "+i+", Hà Nội");
            Random r=  new Random();

            long value=r.nextInt(5)*1000;
            if (value==0){
                value=1000;
            }
            double random=((double) i)/value;

            if (i%2==0){
                random=random*-1;
            }
            double lat=21.0099013+random;
            if (i%4==0){
                random=random*-1;
            }
            double lgn = 105.8053272 + random;
            // Log.d(TAG,"LAT LONG "+lat+"/ "+lgn+"/ "+random+"/ "+value);
            mDmAtm.setLatitude(lat);
            mDmAtm.setLongtitude(lgn);
            mDmAtmDataSource.insertPos(mDmAtm);
        }


        for (int i=0;i<10;i++) {

            mDmAtm.setName("Atm Tổ "+i);
            mDmAtm.setAddres("Tổ "+i+", Hà Nội");


            Random r=  new Random();

            long value=r.nextInt(5)*2000;
            if (value==0){
                value=1000;
            }

            double random=((double) i)/value;

            if (i%2==0){
                random=random*-1;
            }
            double lat=21.0114941+random;
            if (i%4==0){
                random=random*-1;
            }
            double lgn = 105.8079551 + random;

            //Log.d(TAG,"LAT LONG "+lat+"/ "+lgn+"/ "+random+"/ "+value);
            mDmAtm.setLatitude(lat);
            mDmAtm.setLongtitude(lgn);
            mDmAtmDataSource.insertPos(mDmAtm);
        }

        for (int i=0;i<10;i++) {

            mDmAtm.setName("Atm Xã "+i);
            mDmAtm.setAddres("Xã "+i+", Hà Nội");


            Random r=  new Random();

            long value=r.nextInt(5)*10;
            if (value==0){
                value=1000;
            }

            double random=((double) i)/value;

            if (i%2==0){
                random=random*-1;
            }
            double lat=21.0114941+random;
            if (i%4==0){
                random=random*-1;
            }
            double lgn = 105.8079551 + random;

            //Log.d(TAG,"LAT LONG "+lat+"/ "+lgn+"/ "+random+"/ "+value);
            mDmAtm.setLatitude(lat);
            mDmAtm.setLongtitude(lgn);
            mDmAtmDataSource.insertPos(mDmAtm);
        }

        config.putBoolean(IMPORTDATA,true);
    }


}
