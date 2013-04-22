package com.chridam.blackout.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.*;

public class LocationsDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_PATH = "/data/data/com.chridam.blackout/databases/";
    private static final String DATABASE_NAME = "load.db";
    private static final int DATABASE_VERSION = 1;
    private final Context mContext;
    private SQLiteDatabase mDataBase;

    public LocationsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
        createDataBase();
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
       // LocationsTable.onCreate(database);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
       // LocationsTable.onUpgrade(database, oldVersion, newVersion);
    }

    // Creates a empty database on the system and rewrites it with own database
    public void createDataBase() {
        boolean dbExist = checkDataBase();
        if(dbExist){
            // Do nothing - database already exists
        }else{
            // By calling this method and empty database will be created into the default system path
            // of the application so we are able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.v("error", e.toString());
                throw new Error("Error copying database");
            }
        }
    }

    // Check if the database already exist to avoid re-copying the file each time you open the application.
    private boolean checkDataBase(){
       /* mDataBase = null;
        try {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            mDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch(SQLiteException e){
             // Database doesn't exist yet.
        }

        if(mDataBase != null){
            mDataBase.close();
        }
        return mDataBase != null ? true : false;*/
        boolean checkdb = false;
        try {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            Log.v("error", e.toString());
            throw new Error("Database doesn't exist yet.");
        }
        return checkdb;
    }

    // Copies your database from your local assets-folder to the just created empty database in the
    // system folder, from where it can be accessed and handled.
    // This is done by transferring bytestream.
    private void copyDataBase() throws IOException{
        try {
            // Opens local db as the input stream
            InputStream mInput = mContext.getAssets().open(DATABASE_NAME);

            // Path to the just created empty db
            String outFileName = DATABASE_PATH + DATABASE_NAME;

            // Opens the empty db as the output stream
            OutputStream mOutput = new FileOutputStream(outFileName);

            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = mInput.read(buffer))>0){
                mOutput.write(buffer, 0, length);
            }

            // Close the streams
            mOutput.flush();
            mOutput.close();
            mInput.close();
        }
        catch (IOException e) {
                Log.v("error", e.toString());
        }
    }

    public void openDataBase() throws SQLException {
        // Open the database
       /* String mPath = DATABASE_PATH + DATABASE_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READONLY);*/
    }

    @Override
    public synchronized void close() {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }

}
