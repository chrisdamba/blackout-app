package com.chridam.blackout;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import com.chridam.blackout.contentprovider.MyLocationsContentProvider;
import com.chridam.blackout.database.LocationsTable;

/*
 * LocationListViewActivity displays the loadshedding locations info
 * in a list
 *
 */

public class LocationListViewActivity extends ListActivity implements
    LoaderManager.LoaderCallbacks<Cursor> {
    // private Cursor cursor;
    private SimpleCursorAdapter adapter;
    private LoaderManager loaderManager;


    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_info);
        loaderManager = getLoaderManager();
                //this.getListView().setDividerHeight(2);
        fillData();

        // Initiate the Cursor Loader
        //loaderManager.initLoader(0, null, this);
    }

    private void fillData() {
        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] columns = new String[] {
                LocationsTable.COLUMN_AREACODE,
                LocationsTable.COLUMN_NAME,
                LocationsTable.COLUMN_REGION
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.code,
                R.id.name,
                R.id.region,
        };

        // Initiate the Cursor Loader
        loaderManager.initLoader(0, null, this);

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information

        adapter = new SimpleCursorAdapter(
                this, // context
                R.layout.location_info, // list item id
                null, // cursor
                columns, // field from cursor
                to, // items in the list
                0);
        setListAdapter(adapter);
    }

    // Creates a new loader after the initLoader () call
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                LocationsTable.COLUMN_ID,
                LocationsTable.COLUMN_NAME,
                LocationsTable.COLUMN_AREACODE,
                LocationsTable.COLUMN_REGION
        };
        CursorLoader cursorLoader = new CursorLoader(this,
                MyLocationsContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Replace the result Cursor displayed by the Adapter Cursor with
        // the new result set.
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Data is not available anymore, delete reference
        // by removing the existing result Cursor from the List Adapter
        adapter.swapCursor(null);
    }

    @Override
    protected void onResume(){
        super.onResume();
        loaderManager.restartLoader(0, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loaderManager.destroyLoader(0);
        /*if (openHelper != null) {
            openHelper.close();
        }
        if (cdh != null) {
            cdh.close();
        }*/
    }

    @Override
    protected void onPause(){
        super.onPause();
        loaderManager.destroyLoader(0);
    }
}