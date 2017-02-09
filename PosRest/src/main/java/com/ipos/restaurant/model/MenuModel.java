package com.ipos.restaurant.model;

/**
 * Created by GiangLV on 9/21/2015.
 */
public class MenuModel {

    private int mResImage;

    private String mTitle;
    public static  int MENU_HEADER=0;
    public static  int MENU_CONTENT=1;
    private  int type=MENU_CONTENT;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private  int id=-1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getmResImage() {
        return mResImage;
    }

    public void setmResImage(int mResImage) {
        this.mResImage = mResImage;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }


}
