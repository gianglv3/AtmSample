package com.ipos.restaurant.bussiness;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Toast;

import com.ipos.restaurant.InternalStorageContentProvider;
import com.ipos.restaurant.R;
import com.ipos.restaurant.dialog.DialogCameraGallery;
import com.ipos.restaurant.paser.RestString;
import com.ipos.restaurant.util.Log;
import com.ipos.restaurant.util.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import eu.janmuller.android.simplecropimage.CropImage;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ChangeAvatarBussiness {

    private Bitmap avatarUser;
   
    private ProgressDialog progess;
    private Activity mActivity;
    
    private ICropImage icrop;
    
    
    

    public void setIcrop(ICropImage icrop) {
        this.icrop = icrop;
    }

    public ChangeAvatarBussiness(Activity act) {
        this.mActivity = act;
       
    }

    public  final static int IMG_AVATAR_SIZE = 150;

    private void uploadAvatar(Bitmap bitmap) {


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
//
//        WSRestMember rest=  new WSRestMember(mActivity);
//        progess = ProgressDialog.show(mActivity, "",mActivity.getString(R.string.connections_sync_avatar), true,true);
//        progess.setCanceledOnTouchOutside(false);
//        rest.uploadAvatar(encoded, new Listener<RestString>() {
//            @Override
//            public void onResponse(RestString response) {
//                upAvaSucc(response);
//            }
//        }, new ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                upAvaSucc(null);
//            }
//        });
    }

    private  void upAvaSucc(RestString result) {
        progess.dismiss();
        if (result != null) {
            if (result.getData(mActivity)!=null) {
                // thanh cong
                updateUser(result.getData(mActivity));


            } else {
                // fail
                ToastUtil.makeText(mActivity, result.getError().getMessage(), Toast.LENGTH_LONG);
            }

        } else {
            ToastUtil.makeText(mActivity,
                    mActivity.getString(R.string.error_network),
                    Toast.LENGTH_SHORT);
        }
    }

    private class SyncAvatar extends AsyncTask<Bitmap, Void, RestString> {
        @Override
        protected void onPreExecute() {
        try{
                progess = ProgressDialog.show(mActivity, "",mActivity.getString(R.string.connections_sync_avatar), true,true);
                progess.setCanceledOnTouchOutside(false);
            }catch (Exception e) {
                e.printStackTrace();
            }
        
            super.onPreExecute();
        }

        @Override
        protected RestString doInBackground(Bitmap... params) {
//            Bitmap bitmap = params[0];
//            if (bitmap != null) {
//                try {
//                    RestString response = UploadFileUtil.uploadFile(mActivity,
//                            bitmap);
//                   return response;
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
//                return null;
//            } else {
//                return null;
//            }
//            // TODO: upload avatar
            return null;
        }

        @Override
        protected void onPostExecute(RestString result) {
            super.onPostExecute(result);
            progess.dismiss();
            if (result != null) {
            	if (result.getData(mActivity)!=null) {
                    // thanh cong
                   updateUser(result.getData(mActivity));

            	} else {
                    // thanh cong
                    ToastUtil.makeText(mActivity, result.getError().getMessage(), Toast.LENGTH_LONG);
            	}

            } else {
               ToastUtil.makeText(mActivity,
                        mActivity.getString(R.string.error_network),
                        Toast.LENGTH_SHORT);
            }
            
          
        }
        
        public void exe(Bitmap... bitmaps) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, avatarUser);
            } else {
                execute(avatarUser);
            }
        }
    }
    
    private void updateUser(final String url) {
//    	WSRestAuth rest=  new WSRestAuth(mActivity);
//    	 DmMember user  = new DmMember();
//         user.setMemberImage(url);
//         String json =new Gson().toJson(user);
//
//         rest.update(json, new Listener<RestString>() {
//
//				@Override
//				public void onResponse(RestString response) {
//
//					if (response.getData(mActivity)!=null) {
//						DmMember user = ApplicationFoodBook.getInstance().getmDmMember();
//						user.setMemberImage(url);
//						user.saveData(mActivity);
//						ToastUtil.makeText(mActivity, R.string.connections_sync_done, Toast.LENGTH_LONG);
//					  if (icrop!=null) {
//			            	icrop.imageSyn(url);
//			            }
//					} else{
//						ToastUtil.makeText(mActivity, response.getError().getMessage(), Toast.LENGTH_LONG);
//					}
//
//				}
//			}, new ErrorListener(){
//				@Override
//				public void onErrorResponse(VolleyError error) {
//					  ToastUtil.makeText(mActivity,
//		                        mActivity.getString(R.string.error_network),
//		                        Toast.LENGTH_SHORT);
//				}
//			});
    }

    // ---------------------------------------------------------------------------------------------//
    public void showPopupAskCameraGallary() {
        DialogCameraGallery dialog = new DialogCameraGallery(mActivity) {
            @Override
            public void onOpenGallary() {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                mActivity.startActivityForResult(photoPickerIntent,
                        REQUEST_CODE_GALLERY);
            }

            @Override
            public void onOpenCamera() {

                takePicture(mActivity);
            }
        };
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {

                case REQUEST_CODE_GALLERY:

                    try {
                        String state = Environment.getExternalStorageState();
                        File mFileTemp;
                        if (Environment.MEDIA_MOUNTED.equals(state)) {
                            mFileTemp = new File(
                                    Environment.getExternalStorageDirectory(),
                                    InternalStorageContentProvider.TEMP_PHOTO_FILE_NAME);
                        } else {
                            mFileTemp = new File(
                                    mActivity.getFilesDir(),
                                    InternalStorageContentProvider.TEMP_PHOTO_FILE_NAME);
                        }

                        Log.i("ChangeAvatarSupport.onActivityResult()",
                                " request done Gallery");
                        InputStream inputStream = mActivity.getContentResolver()
                                .openInputStream(data.getData());
                        FileOutputStream fileOutputStream = new FileOutputStream(
                                mFileTemp);
                        copyStream(inputStream, fileOutputStream);
                        fileOutputStream.close();
                        inputStream.close();

                        startCropImage(mActivity);

                    } catch (Exception e) {
                    	e.printStackTrace();
                        Log.i("ChangeAvatarSupport.onActivityResult()", "CRASH "
                                + e.getMessage());
                    }

                    break;
                case REQUEST_CODE_TAKE_PICTURE:

                    startCropImage(mActivity);
                    break;

                case REQUEST_CODE_CROP_IMAGE:

                    String path = data.getStringExtra(CropImage.IMAGE_PATH);
                    if (path == null) {

                        return;
                    }
                    File mFileTemp = new File(path);

                    Bitmap bitmap = BitmapFactory.decodeFile(mFileTemp
                            .getPath());
                    bitmap = Bitmap.createScaledBitmap(bitmap, IMG_AVATAR_SIZE,
                            IMG_AVATAR_SIZE, false);

                    this.avatarUser = bitmap;
            
                    if (icrop!=null) {
                    	icrop.cropDone(avatarUser);
                    }
                    
                    Log.i("ChangeAvatarSupport.onActivityResult()",
                            "crop done ");
                    
                    //new SyncAvatar().exe(avatarUser);
                    uploadAvatar(avatarUser);
       
                    break;
                case 999:
                case 998:
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        photo = Bitmap.createScaledBitmap(photo,
                                IMG_AVATAR_SIZE, IMG_AVATAR_SIZE, false);

                        this.avatarUser = photo;
                        
                        if (icrop!=null){
                            icrop.cropDone(avatarUser);
                        }
                        //new SyncAvatar().exe(avatarUser);
                        uploadAvatar(avatarUser);
                    }
                    break;
                }
            }
        } catch (Exception e) {
           ToastUtil.makeText(mActivity, mActivity.getString(R.string.camera_error),
                    Toast.LENGTH_LONG);
            e.printStackTrace();
        }

    }
    
 
    
    /**
     * helper to retrieve the path of an image URI
     */
    @SuppressWarnings("deprecation")
    public String getPath(Uri uri) {
            // just some safety built in 
            if( uri == null ) {
                // TODO perform some logging or show user feedback
                return null;
            }
            // try to retrieve the image from the media store first
            // this will only work for images selected from gallery
            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = mActivity.managedQuery(uri, projection, null, null, null);
            if( cursor != null ){
                int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }
            // this is our fallback here
            return uri.getPath();
    }
    
    
    public int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath){
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

    public static void takePicture(Activity context) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File mFileTemp;
        try {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                mFileTemp = new File(Environment.getExternalStorageDirectory(),
                        InternalStorageContentProvider.TEMP_PHOTO_FILE_NAME);
            } else {
                mFileTemp = new File(context.getFilesDir(),
                        InternalStorageContentProvider.TEMP_PHOTO_FILE_NAME);
            }
            Log.i("UserDetailActivity.startCropImage()", "TAKE IMG "
                    + mFileTemp.getPath());
            Uri mImageCaptureUri = null;

            if (Environment.MEDIA_MOUNTED.equals(state)) {
                mImageCaptureUri = Uri.fromFile(mFileTemp);
            } else {
                /*
                 * The solution is taken from here:
                 * http://stackoverflow.com/questions
                 * /10042695/how-to-get-camera-result-as-a-uri-in-data-folder
                 */
                mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
            }
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                    mImageCaptureUri);
            intent.putExtra("return-data", true);
            context.startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void startCropImage(Activity context) {
        String state = Environment.getExternalStorageState();
        File mFileTemp;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(),
                    InternalStorageContentProvider.TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(context.getFilesDir(),
                    InternalStorageContentProvider.TEMP_PHOTO_FILE_NAME);
        }
        Log.i("UserDetailActivity.startCropImage()",
                "CROP IMG " + mFileTemp.getPath());
        Intent intent = new Intent(context, CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
        intent.putExtra(CropImage.SCALE, true);
        intent.putExtra(CropImage.ASPECT_Y, 2);
        intent.putExtra(CropImage.ASPECT_X, 2);


        context.startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }
    
    
    public static void startCropImageNoXy(Activity context) {
        String state = Environment.getExternalStorageState();
        File mFileTemp;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(),
                    InternalStorageContentProvider.TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(context.getFilesDir(),
                    InternalStorageContentProvider.TEMP_PHOTO_FILE_NAME);
        }
        Log.i("UserDetailActivity.startCropImage()",
                "CROP IMG " + mFileTemp.getPath());
        Intent intent = new Intent(context, CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
        intent.putExtra(CropImage.SCALE, false);

//        intent.putExtra(CropImage.ASPECT_X, 2);
//        intent.putExtra(CropImage.ASPECT_Y, 2);

        context.startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }

    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;

    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }
    
    public interface ICropImage {
        
        void cropDone(Bitmap avatar);
        
        void imageSyn(String url);
    }

	public void synAvatar(String img_value) {
		// TODO Auto-generated method stub
		
	}

}
