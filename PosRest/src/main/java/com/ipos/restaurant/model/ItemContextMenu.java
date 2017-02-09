package com.ipos.restaurant.model;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

 
public class ItemContextMenu {
    private CharSequence mText;
    private Drawable mImage;
    private int mActionTag;
    private Object mObj;

    // new obj Item
    public ItemContextMenu(Resources res, CharSequence text, int imageResourceId, Object obj, int actionTag) {
        this.mText = text;
        if (imageResourceId != -1) {
            mImage = res.getDrawable(imageResourceId);
        } else {
            mImage = null;
        }
        this.mActionTag = actionTag;
        this.mObj = obj;
    }

    public ItemContextMenu() {

    }

    public void setText(CharSequence text) {
        this.mText = text;
    }

    public CharSequence getText() {
        return mText;
    }

    public void setImage(Drawable image) {
        this.mImage = image;
    }

    public Drawable getImage() {
        return mImage;
    }

    public void setActionTag(int tag) {
        this.mActionTag = tag;
    }

    public int getActionTag() {
        return mActionTag;
    }

    public Object getObj() {
        return mObj;
    }

    public void setObj(Object obj) {
        this.mObj = obj;
    }
}
