package com.chridam.blackout;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import com.chridam.blackout.contentprovider.LocationsContentProvider;
import com.chridam.blackout.database.LocationsTable;

public class LocationScheduleDetailActivity extends Activity {
    private TextView mNameText;
    private TextView mRegionText;
    private TextView mAreaCodeText;

    private Uri locationUri;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_detail);

        mNameText = (TextView) findViewById(R.id.name);
        mRegionText = (TextView)findViewById(R.id.region);
        mAreaCodeText = (TextView)findViewById(R.id.areacode);

        Bundle extras = getIntent().getExtras();

        // check from the saved instance
        locationUri = (savedInstanceState == null) ? null : (Uri)savedInstanceState
                .getParcelable(LocationsContentProvider.CONTENT_ITEM_TYPE);

        // or passed from the other activity
        if (extras != null){
            locationUri = extras.getParcelable(LocationsContentProvider.CONTENT_ITEM_TYPE);
            fillData(locationUri);
        }
    }

    private void fillData(Uri uri) {
        String[] projection = {LocationsTable.COLUMN_NAME,
                LocationsTable.COLUMN_AREACODE, LocationsTable.COLUMN_REGION };
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();

            mNameText.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(LocationsTable.COLUMN_NAME)));
            mAreaCodeText.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(LocationsTable.COLUMN_AREACODE)));
            mRegionText.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(LocationsTable.COLUMN_REGION)));

            // Always close the cursor
            cursor.close();
        }
    }


}