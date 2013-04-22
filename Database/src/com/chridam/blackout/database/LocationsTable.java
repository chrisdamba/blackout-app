package com.chridam.blackout.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LocationsTable {
    // Database table
    public static final String TABLE_LOCATIONS = "location";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AREACODE = "areacode";
    public static final String COLUMN_REGION = "region";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_LOCATIONS
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_AREACODE + " text not null,"
            + COLUMN_REGION
            + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        //database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
       /* Log.w(LocationsTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        onCreate(database);*/
    }
}
