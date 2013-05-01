package com.chridam.blackout;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.chridam.blackout.contentprovider.LocationsContentProvider;
import com.chridam.blackout.database.LocationsTable;

public class LocationsListActivity extends ListActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    // private Cursor cursor;
    private SimpleCursorAdapter adapter;


    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locations_list);
        this.getListView().setDividerHeight(2);
        fillData();
    }

    // Opens the second activity if an entry is clicked
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, LocationScheduleDetailActivity.class);
        Uri locationUri = Uri.parse(LocationsContentProvider.CONTENT_URI + "/" + id);
        i.putExtra(LocationsContentProvider.CONTENT_ITEM_TYPE, locationUri);

        startActivity(i);
    }



    private void fillData() {

        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] { LocationsTable.COLUMN_NAME };
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.label };

        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(this, R.layout.location_row, null, from,
                to, 0);

        setListAdapter(adapter);
    }

    // Creates a new loader after the initLoader () call
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { LocationsTable.COLUMN_ID, LocationsTable.COLUMN_NAME };
        CursorLoader cursorLoader = new CursorLoader(this,
                LocationsContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // data is not available anymore, delete reference
        adapter.swapCursor(null);
    }
}
