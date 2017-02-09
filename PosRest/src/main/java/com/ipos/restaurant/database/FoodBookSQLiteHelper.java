package com.ipos.restaurant.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ipos.restaurant.database.constants.DmAtmContants;
import com.ipos.restaurant.database.constants.FoodbookDatabaseConstant;

/**
 
 */
public class FoodBookSQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG = FoodBookSQLiteHelper.class.getSimpleName();
    private static SQLiteDatabase mWritableDb;
    private static SQLiteDatabase mReadableDb;
    private static FoodBookSQLiteHelper mInstance;

    public FoodBookSQLiteHelper(Context context) {
        super(context, FoodbookDatabaseConstant.DATABASE_NAME, null,
                FoodbookDatabaseConstant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.i(TAG, "Create All table database "+database);
        createAllTable(database);
    }

    private void createAllTable(SQLiteDatabase database) {

        
        database.execSQL(DmAtmContants.CREATE_STATEMENT);
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade oldVersion = " + oldVersion + " newVersion = " + newVersion);

       
    }

    public static FoodBookSQLiteHelper getInstance(Context context) {
        com.ipos.restaurant.util.Log.i(TAG, "INIT  DATABASE" + mInstance);
        if (mInstance == null) {
            mInstance = new FoodBookSQLiteHelper(context);
        }
        return mInstance;
    }

    public void closeDatabaseConnection(SQLiteDatabase database) {
        try {
            if (database != null) {
                database.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "closeDatabaseConnection", e);
        }
    }

    public SQLiteDatabase getMyWritableDatabase() {
        if ((mWritableDb == null) || (!mWritableDb.isOpen())) {
            mWritableDb = mInstance.getWritableDatabase();
        }
        return mWritableDb;
    }


    public SQLiteDatabase getMyReadableDatabase() {
        if ((mReadableDb == null) || (!mReadableDb.isOpen())) {
            mReadableDb = mInstance.getReadableDatabase();
        }
        return mReadableDb;
    }
}