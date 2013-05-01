package com.chridam.blackout.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.chridam.blackout.csvreader.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Windows
 * Date: 5/1/13
 * Time: 6:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "loader.db";
    private static final String CSV_FILE = "locations.csv";
    private static final int DATABASE_VERSION = 1;
    private static Context ctx;

    public LocationDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        LocationsTable.onCreate(database);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        LocationsTable.onUpgrade(database, oldVersion, newVersion);
    }

    public static List<String[]> importCSVData(String filename)
    {
        String next[];
        List<String[]> list = new ArrayList<String[]>();

        try {
            final AssetManager assetMgr =  ctx.getResources().getAssets();
            CSVReader reader = new CSVReader(new InputStreamReader(assetMgr.open(filename)));
            for(;;) {
                next = reader.readNext();
                if(next != null) {
                    list.add(next);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insertList()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        List<String[]> source =  importCSVData(CSV_FILE);
        try{
            db.beginTransaction();
            for (String[] unit: source){

                ContentValues cv = new ContentValues();
                cv.put(LocationsTable.COLUMN_NAME, unit[0]);
                cv.put(LocationsTable.COLUMN_AREACODE, unit[1]);
                cv.put(LocationsTable.COLUMN_REGION, unit[2]);
                db.insert(LocationsTable.TABLE_LOCATION, null, cv);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
        } finally {
            db.endTransaction();
        }
        db.close();
    }


}

