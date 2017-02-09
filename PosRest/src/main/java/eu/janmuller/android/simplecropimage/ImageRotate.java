/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.janmuller.android.simplecropimage;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ipos.restaurant.R;
import com.ipos.restaurant.util.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * The activity can crop specific region of interest from an image.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ImageRotate extends MonitoredActivity {

    final int IMAGE_MAX_SIZE = 1024;

    private static final String TAG                    = "CropImage";
    public static final  String IMAGE_PATH             = "image-path";
    public static final  String SCALE                  = "scale";
    public static final  String ORIENTATION_IN_DEGREES = "orientation_in_degrees";
    public static final  String ASPECT_X               = "aspectX";
    public static final  String ASPECT_Y               = "aspectY";
    public static final  String OUTPUT_X               = "outputX";
    public static final  String OUTPUT_Y               = "outputY";
    public static final  String SCALE_UP_IF_NEEDED     = "scaleUpIfNeeded";
    public static final  String CIRCLE_CROP            = "circleCrop";
    public static final  String RETURN_DATA            = "return-data";
    public static final  String RETURN_DATA_AS_BITMAP  = "data";
    public static final  String ACTION_INLINE_DATA     = "inline-data";

    // These are various options can be specified in the intent.
    private       Bitmap.CompressFormat mOutputFormat    = Bitmap.CompressFormat.JPEG;
    private       Uri                   mSaveUri         = null;
    private       boolean               mCircleCrop      = false;
    private final Handler               mHandler         = new Handler();

    private int             mOutputX;
    private int             mOutputY;
    private boolean         mScale;
    private ImageView   mImageView;
    private ContentResolver mContentResolver;
    private Bitmap          mBitmap;
    private String          mImagePath;

    boolean       mWaitingToPick; // Whether we are wait the user to pick a face.
    boolean       mSaving;  // Whether the "save" button is already clicked.
    HighlightView mCrop;

    // These options specifiy the output image size and whether we should
    // scale the output to fit it (or just crop it).
    private boolean mScaleUp = true;

    private final BitmapManager.ThreadSet mDecodingThreads =
            new BitmapManager.ThreadSet();

    @Override
    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);
        mContentResolver = getContentResolver();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.v15_image_rotate);

        mImageView = (ImageView) findViewById(R.id.image);

        showStorageToast(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {

            if (extras.getString(CIRCLE_CROP) != null) {

        	if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            		mImageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        	}

                mCircleCrop = true;
            }

            mImagePath = extras.getString(IMAGE_PATH);

            mSaveUri = getImageUri(mImagePath);
            mBitmap = getBitmap(mImagePath);
            
            if (mBitmap == null) {

                Log.d(TAG, "finish!!!");
                finish();
                return;
            }
            
            mBitmap.getWidth();
            mBitmap.getHeight();
            
            
            int rotate = getCameraPhotoOrientation(this, mSaveUri, mImagePath);
            Log.i("CropImage.onCreate()", "rotate img "+rotate);
            mBitmap = Util.rotateImage(mBitmap, rotate);
     
            if (extras.containsKey(ASPECT_X) && extras.get(ASPECT_X) instanceof Integer) {

                extras.getInt(ASPECT_X);
            } 
 
            if (extras.containsKey(ASPECT_Y) && extras.get(ASPECT_Y) instanceof Integer) {

                extras.getInt(ASPECT_Y);
            }
 
            mOutputX = extras.getInt(OUTPUT_X);
            mOutputY = extras.getInt(OUTPUT_Y);
            mScale = extras.getBoolean(SCALE, true);
            mScaleUp = extras.getBoolean(SCALE_UP_IF_NEEDED, true);
        }
 
        // Make UI fullscreen.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.discard).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        setResult(RESULT_CANCELED);
                        finish();
                    }
                });

        findViewById(R.id.save).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        try {
                            onSaveClicked();
                        } catch (Exception e) {
                            finish();
                        }
                    }
                });
        findViewById(R.id.rotateLeft).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        mBitmap = Util.rotateImage(mBitmap, -90);
                        new RotateBitmap(mBitmap);
                        mImageView.setImageBitmap(mBitmap);
                        
                    }
                });

        findViewById(R.id.rotateRight).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        mBitmap = Util.rotateImage(mBitmap, 90);
                        new RotateBitmap(mBitmap);
                        mImageView.setImageBitmap(mBitmap);
                     
                    }
                });
    
    }
    
    public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath){
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
            }

 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    
    
    private Uri getImageUri(String path) {

        return Uri.fromFile(new File(path));
    }

    private Bitmap getBitmap(String path) {

        Uri uri = getImageUri(path);
        InputStream in = null;
        try {
            in = mContentResolver.openInputStream(uri);

            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(in, null, o);
            in.close();

            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = mContentResolver.openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();

            return b;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "file " + path + " not found");
        } catch (IOException e) {
            Log.e(TAG, "file " + path + " not found");
        }
        return null;
    }

 
    private void onSaveClicked() throws Exception {
        // TODO this code needs to change to use the decode/crop/encode single
        // step api so that we don't require that the whole (possibly large)
        // bitmap doesn't have to be read into memory
        if (mSaving) return;

        if (mCrop == null) {

            return;
        }

        mSaving = true;

        Rect r = mCrop.getCropRect();

        int width = r.width();
        int height = r.height();

        // If we are circle cropping, we want alpha channel, which is the
        // third param here.
        Bitmap croppedImage;
        try {

            croppedImage = Bitmap.createBitmap(width, height,
                    mCircleCrop ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        } catch (Exception e) {
            throw e;
        }
        if (croppedImage == null) {

            return;
        }

        {
            Canvas canvas = new Canvas(croppedImage);
            Rect dstRect = new Rect(0, 0, width, height);
            canvas.drawBitmap(mBitmap, r, dstRect, null);
        }

        if (mCircleCrop) {

            // OK, so what's all this about?
            // Bitmaps are inherently rectangular but we want to return
            // something that's basically a circle.  So we fill in the
            // area around the circle with alpha.  Note the all important
            // PortDuff.Mode.CLEAR.
            Canvas c = new Canvas(croppedImage);
            Path p = new Path();
            p.addCircle(width / 2F, height / 2F, width / 2F,
                    Path.Direction.CW);
            c.clipPath(p, Region.Op.DIFFERENCE);
            c.drawColor(0x00000000, PorterDuff.Mode.CLEAR);
        }

		/* If the output is required to a specific size then scale or fill */
        if (mOutputX != 0 && mOutputY != 0) {

            if (mScale) {

                /* Scale the image to the required dimensions */
                Bitmap old = croppedImage;
                croppedImage = Util.transform(new Matrix(),
                        croppedImage, mOutputX, mOutputY, mScaleUp);
                if (old != croppedImage) {

                    old.recycle();
                }
            } else {

				/* Don't scale the image crop it to the size requested.
                 * Create an new image with the cropped image in the center and
				 * the extra space filled.
				 */

                // Don't scale the image but instead fill it so it's the
                // required dimension
                Bitmap b = Bitmap.createBitmap(mOutputX, mOutputY,
                        Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(b);

                Rect srcRect = mCrop.getCropRect();
                Rect dstRect = new Rect(0, 0, mOutputX, mOutputY);

                int dx = (srcRect.width() - dstRect.width()) / 2;
                int dy = (srcRect.height() - dstRect.height()) / 2;

				/* If the srcRect is too big, use the center part of it. */
                srcRect.inset(Math.max(0, dx), Math.max(0, dy));

				/* If the dstRect is too big, use the center part of it. */
                dstRect.inset(Math.max(0, -dx), Math.max(0, -dy));

				/* Draw the cropped bitmap in the center */
                canvas.drawBitmap(mBitmap, srcRect, dstRect, null);

				/* Set the cropped bitmap as the new bitmap */
                croppedImage.recycle();
                croppedImage = b;
            }
        }

        // Return the cropped image directly or save it to the specified URI.
        Bundle myExtras = getIntent().getExtras();
        if (myExtras != null && (myExtras.getParcelable("data") != null
                || myExtras.getBoolean(RETURN_DATA))) {

            Bundle extras = new Bundle();
            extras.putParcelable(RETURN_DATA_AS_BITMAP, croppedImage);
            setResult(RESULT_OK,
                    (new Intent()).setAction(ACTION_INLINE_DATA).putExtras(extras));
            finish();
        } else {
            final Bitmap b = croppedImage;
            Util.startBackgroundJob(this, null, getString(R.string.saving_image),
                    new Runnable() {
                        public void run() {

                            saveOutput(b);
                        }
                    }, mHandler);
        }
    }

    public  void saveOutput(Bitmap croppedImage) {

        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = mContentResolver.openOutputStream(mSaveUri);
                if (outputStream != null) {
                    croppedImage.compress(mOutputFormat, 90, outputStream);
                }
            } catch (IOException ex) {

                Log.e(TAG, "Cannot open file: " + mSaveUri, ex);
                setResult(RESULT_CANCELED);
                finish();
                return;
            } finally {

                Util.closeSilently(outputStream);
            }

            Bundle extras = new Bundle();
            Intent intent = new Intent(mSaveUri.toString());
            intent.putExtras(extras);
            intent.putExtra(IMAGE_PATH, mImagePath);
            intent.putExtra(ORIENTATION_IN_DEGREES, Util.getOrientationInDegree(this));
            setResult(RESULT_OK, intent);
        } else {

            Log.e(TAG, "not defined image url");
        }
        croppedImage.recycle();
        finish();
    }

    @Override
    protected void onPause() {

        super.onPause();
        BitmapManager.instance().cancelThreadDecoding(mDecodingThreads);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        if (mBitmap != null) {

            mBitmap.recycle();
        }
    }

 
    public static final int NO_STORAGE_ERROR  = -1;
    public static final int CANNOT_STAT_ERROR = -2;

    public static void showStorageToast(Activity activity) {

        showStorageToast(activity, calculatePicturesRemaining(activity));
    }

    public static void showStorageToast(Activity activity, int remaining) {

        String noStorageText = null;

        if (remaining == NO_STORAGE_ERROR) {

            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_CHECKING)) {

                noStorageText = activity.getString(R.string.preparing_card);
            } else {

                noStorageText = activity.getString(R.string.no_storage_card);
            }
        } else if (remaining < 1) {

            noStorageText = activity.getString(R.string.not_enough_space);
        }

        if (noStorageText != null) {

            ToastUtil.makeText(activity, noStorageText, 5000);
        }
    }

    public static int calculatePicturesRemaining(Activity activity) {

        try {
            /*if (!ImageManager.hasStorage()) {
                return NO_STORAGE_ERROR;
            } else {*/
        	String storageDirectory = "";
        	String state = Environment.getExternalStorageState();
        	if (Environment.MEDIA_MOUNTED.equals(state)) {
        		storageDirectory = Environment.getExternalStorageDirectory().toString();
        	}
        	else {
        		storageDirectory = activity.getFilesDir().toString();
        	}
            StatFs stat = new StatFs(storageDirectory);
            float remaining = ((float) stat.getAvailableBlocks()
                    * (float) stat.getBlockSize()) / 400000F;
            return (int) remaining;
            //}
        } catch (Exception ex) {
            // if we can't stat the filesystem then we don't know how many
            // pictures are remaining.  it might be zero but just leave it
            // blank since we really don't know.
            return CANNOT_STAT_ERROR;
        }
    }


}


