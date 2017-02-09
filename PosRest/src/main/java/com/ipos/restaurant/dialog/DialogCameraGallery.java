package com.ipos.restaurant.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.ipos.restaurant.R;

public abstract class DialogCameraGallery extends Dialog implements
        View.OnClickListener {

    
    public DialogCameraGallery(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(true);
    }

    public DialogCameraGallery(Context context, int theme) {
        super(context, theme);
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(true);
    }

    
    public DialogCameraGallery(Context context) {
        super(context, R.style.style_dialog2);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_camera_gallary);
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        this.setCancelable(true);
        Button line1 = (Button) findViewById(R.id.dialogx__line1);
        Button line2 = (Button) findViewById(R.id.dialogx__line2);
        Button line4 = (Button) findViewById(R.id.dialogx__line4);
        line1.setOnClickListener(this);
        line2.setOnClickListener(this);
        line4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.dialogx__line1:
            onOpenCamera();
            dismiss();
            break;

        case R.id.dialogx__line2:
            onOpenGallary();
            dismiss();
            break;

        case R.id.dialogx__line4:
           dismiss();
           break;

        default:
            break;
        }
    }

    
    public abstract void onOpenGallary();
    
    public abstract void onOpenCamera();
    
}
