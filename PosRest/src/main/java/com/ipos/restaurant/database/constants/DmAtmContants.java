package com.ipos.restaurant.database.constants;

/**
 */
public class DmAtmContants {
    public static final String TABLE = "ATM";
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String ADDRESS = "ADDRESS";
    public static final String LAT = "LAT";
    public static final String LNG = "LNG";

    public static final String NAME_SEARCH = "NAME_SEARCH";





    public static final String CREATE_STATEMENT = "CREATE TABLE IF NOT EXISTS " + TABLE +
            " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + NAME + " TEXT,"
            + ADDRESS + " TEXT,"
            + LAT + " TEXT,"
            + LNG + " TEXT,"

            + NAME_SEARCH + " TEXT)";

    public static final String DROP_STATEMENT = "DROP TABLE IF EXISTS " + TABLE;
    public static final String SELECT_ALL_STATEMENT = "SELECT * FROM " + TABLE;
    public static final String SELECT_MEDIA_BY_ID_STATEMENT = "SELECT * FROM "
            + TABLE + " WHERE " + NAME + " = ";
    public static final String DELETE_ALL_STATEMENT = "DELETE FROM " + TABLE;
    public static final String WHERE_ID = NAME + " = ?";
    public static final String SELECT_ALL_QUERY_BYNAME = "SELECT * FROM " + TABLE
            +" WHERE "+NAME_SEARCH+" like '%#name%' ";
    public static final String SELECT_ALL_QUERY = "SELECT * FROM " + TABLE
            +" WHERE "+NAME_SEARCH+" like '%#name%' "
            +"AND ("+LAT+" <= #latbig AND "+LAT+" >= #latsmall )"
            +"AND ("+LNG+" <= #lngbig AND "+LNG+" >= #lngsmall )";
}
