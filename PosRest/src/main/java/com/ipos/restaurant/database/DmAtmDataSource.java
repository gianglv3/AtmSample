package com.ipos.restaurant.database;//package com.ipos.restaurant.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ipos.restaurant.ApplicationFoodBook;
import com.ipos.restaurant.Constants;
import com.ipos.restaurant.database.constants.DmAtmContants;
import com.ipos.restaurant.model.DmAtm;
import com.ipos.restaurant.util.DistanceUtil;
import com.ipos.restaurant.util.Utilities;

import java.util.ArrayList;

/**
 * Created by toanvk2 on 25/03/2015.
 */
public class DmAtmDataSource {
    // Database fields
    private static String TAG = DmAtmDataSource.class.getSimpleName();
    private SQLiteDatabase databaseRead;
    private SQLiteDatabase databaseWrite;
    private FoodBookSQLiteHelper dbHelper;
    private static DmAtmDataSource mInstance;

    public static DmAtmDataSource getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new DmAtmDataSource(context);
        }
        return mInstance;
    }

    private DmAtmDataSource(Context context) {
        dbHelper = FoodBookSQLiteHelper.getInstance(context);
        databaseRead = dbHelper.getMyReadableDatabase();
        databaseWrite = dbHelper.getMyWritableDatabase();
    }

    /**
     * dong con tro
     *
     * @param cursor
     */
    private void closeCursor(Cursor cursor) {
        if (cursor != null)
            cursor.close();
    }

    /**
     * setContentValuesFromMediaModel
     *
     * @param topSong
     * @return contentValues
     */
    private ContentValues setValues(DmAtm topSong) {
        ContentValues values = new ContentValues();
        values.put(DmAtmContants.NAME, topSong.getName());
        values.put(DmAtmContants.NAME_SEARCH, Utilities.convert(topSong.getName().toLowerCase()));
        values.put(DmAtmContants.ADDRESS, topSong.getAddres());
        values.put(DmAtmContants.LAT, topSong.getLatitude());
        values.put(DmAtmContants.LNG, topSong.getLongtitude());

        return values;
    }

    public void insertPos(DmAtm mediaModel) {
        try {
            ContentValues values = setValues(mediaModel);
            deletePosfromID(mediaModel.getName());
          //  Log.d(TAG,"Value "+values);
            databaseWrite.insert(DmAtmContants.TABLE, null, values);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * insertListMediaModelToDB
     *
     * @param listMedia
     */
    public void insertListPos(ArrayList<DmAtm> listMedia) {
        if (listMedia == null || listMedia.isEmpty())
            return;
        try {
            databaseWrite.beginTransaction();
            try {
                for (DmAtm item : listMedia) {
                    ContentValues values = setValues(item);
                    databaseWrite.insert(DmAtmContants.TABLE, null, values);
                }
                databaseWrite.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                databaseWrite.endTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * update Media
     *
     * @param mediaModel
     */
    public void updateMedia(DmAtm mediaModel) {
        try {
            ContentValues values = setValues(mediaModel);
            String whereClause = DmAtmContants.NAME + " = " + mediaModel.getName();
            databaseWrite.update(DmAtmContants.TABLE, values, whereClause, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * get MediaModel from cursor
     *
     * @param cur
     * @return MediaModel
     */
    private DmAtm getPosFromCursor(Cursor cur) {
        DmAtm allModel = new DmAtm();
        allModel.setId(cur.getInt(0));
        allModel.setName(cur.getString(1));
        allModel.setAddres(cur.getString(2));
        allModel.setLatitude(cur.getDouble(3));
        allModel.setLongtitude(cur.getDouble(4));

        return allModel;
    }

    /**
     * get MediaModel from songId
     *
     * @param id
     * @return MediaModel
     */
    public DmAtm getMediaFromSongId(long id) {
        DmAtm mediaModel = null;
        Cursor cursor = null;
        try {
            if (databaseRead == null)
                return null;
            String query = DmAtmContants.SELECT_MEDIA_BY_ID_STATEMENT
                    + id;
            cursor = databaseRead.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mediaModel = getPosFromCursor(cursor);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCursor(cursor);
        }
        return mediaModel;
    }

    /**
     * get all media from db
     *
     * @return listMediaModel
     */
    public ArrayList<DmAtm> getAllPosFromDb() {
        ArrayList<DmAtm> listMedia = null;
        Cursor cursor = null;
        try {
            if (databaseRead == null) {
                return listMedia;
            }
            cursor = databaseRead.rawQuery(DmAtmContants.SELECT_ALL_STATEMENT, null);
            if (cursor != null && cursor.getCount() > 0) {
                listMedia = new ArrayList<DmAtm>();
                if (cursor.moveToFirst()) {
                    do {
                        DmAtm item = getPosFromCursor(cursor);
                        // set phone number
                        DistanceUtil.caluDistane(item);
                        listMedia.add(item);
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCursor(cursor);
        }
        return listMedia;
    }
    public ArrayList<DmAtm> getAllPosFromDb(String name) {
        ArrayList<DmAtm> listMedia = null;
        Cursor cursor = null;
        try {
            if (databaseRead == null) {
                return listMedia;
            }
            String query=DmAtmContants.SELECT_ALL_QUERY_BYNAME;
            query=query.replace("#name",name.toLowerCase());
            cursor = databaseRead.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                listMedia = new ArrayList<>();
                if (cursor.moveToFirst()) {
                    do {
                        DmAtm item = getPosFromCursor(cursor);
                        // set phone number
                        DistanceUtil.caluDistane(item);
                        listMedia.add(item);
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCursor(cursor);
        }
        return listMedia;
    }

    public ArrayList<DmAtm> getAllPosFromDb(String name,double lat,double lng) {
        ArrayList<DmAtm> listMedia = null;
        Cursor cursor = null;
        try {
            if (databaseRead == null) {
                return listMedia;
            }
            long radius=0;

            while (true) {
                String query = DmAtmContants.SELECT_ALL_QUERY;
                query = query.replace("#name", name.toLowerCase());


                radius=getRadius(radius);

                double biglat = lat + (radius / 1000) * Constants.PARAM_LAT;
                double smalllat = lat - (radius / 1000) * Constants.PARAM_LAT;

                double biglng = lng + (radius / 1000) * Constants.PARAM_LNG;
                double smalllng = lng - (radius / 1000) * Constants.PARAM_LNG;


                query = query.replace("#latbig", "" + biglat);
                query = query.replace("#latsmall", "" + smalllat);
                query = query.replace("#lngbig", "" + biglng);
                query = query.replace("#lngsmall", "" + smalllng);

                cursor = databaseRead.rawQuery(query, null);

              //  Log.d(TAG,"Query "+query+"/ "+radius);

                if (cursor != null && cursor.getCount() > 0) {
                    listMedia = new ArrayList<>();
                    if (cursor.moveToFirst()) {
                        do {
                            DmAtm item = getPosFromCursor(cursor);
                            // set phone number
                            DistanceUtil.caluDistane(item);
                            if (item.getDistance()!=-1&&item.getDistance()<=radius) {
                                listMedia.add(item);
                            }
                        } while (cursor.moveToNext());
                    }
                }

                if (radius==Constants.RADIUS_20k||(listMedia!=null&&listMedia.size()>0)) {
                    break;
                }
            }
            if (listMedia!=null&&listMedia.size()>0) {
                ApplicationFoodBook.getInstance().getmLocationBussiness().setRadius(radius);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCursor(cursor);
        }

        return listMedia;
    }

    /**
     * delete Media by songId
     *
     * @param id
     */
    public void deletePosfromID(String id) {
        try {
            String whereClause = DmAtmContants.NAME + " = '" + id+"'";
            databaseWrite.delete(DmAtmContants.TABLE, whereClause, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * xoa het media
     */
    public void deleteAllTable() {
        try {
            databaseWrite.execSQL(DmAtmContants.DELETE_ALL_STATEMENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * xoa bang media
     */
    public void dropTable() {
        try {
            databaseWrite.execSQL(DmAtmContants.DROP_STATEMENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private long getRadius(long radius){
        if (radius<Constants.RADIUS_1k) {
            return  Constants.RADIUS_1k;
        } else if (radius<Constants.RADIUS_2k) {
            return Constants.RADIUS_2k;
        } else if (radius<Constants.RADIUS_4k) {
            return Constants.RADIUS_4k;
        } else if (radius<Constants.RADIUS_6k) {
            return Constants.RADIUS_6k;
        } else if (radius<Constants.RADIUS_8k) {
            return Constants.RADIUS_8k;
        } else if (radius<Constants.RADIUS_10k) {
            return Constants.RADIUS_10k;
        } else if (radius<Constants.RADIUS_12k) {
            return Constants.RADIUS_12k;
        } else if (radius<Constants.RADIUS_20k) {
            return Constants.RADIUS_20k;
        }
        return Constants.RADIUS_20k;

    }
}
