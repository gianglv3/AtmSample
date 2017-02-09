package com.ipos.restaurant.bussiness;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

public class ImageLoaderBussiness {

	 
	
	@SuppressWarnings("unused")
	private static final String TAG = "ImageLoaderBussiness";

	public ImageLoaderBussiness(Context mContext) {


		Fresco.initialize(mContext);
	}
	

	public void setImageFresco(String url,SimpleDraweeView image) {
		try {
		//	Log.i(TAG, "Image Bussiness "+url);
			if (url==null|| !url.contains("http")) {
				image.setImageURI(null);
				return;
			}

			Uri uri = Uri.parse(url);

			image.setImageURI(uri);

		} catch (Exception ex) {
			ex.printStackTrace();
			image.setImageURI(null);
		}
	}
}

 