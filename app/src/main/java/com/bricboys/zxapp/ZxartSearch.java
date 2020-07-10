package com.bricboys.zxapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ZxartSearch extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<ZxArt>> {

    private String mParametro;
    private ProgressBar pbar;
    private ZxartAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zxart_search);

        Intent intent = getIntent();
        mParametro = intent.getStringExtra("parametro");
        ParameterClass.parameter = mParametro;

        setTitle("ZxArt List: " + mParametro);

        pbar = findViewById(R.id.indeterminateBar);
        pbar.setVisibility(View.VISIBLE);

        boolean isConnected = ConnectionManager.verifyConnection(ZxartSearch.this);

        if (isConnected) {
            mAdapter = new ZxartAdapter(this, 0, new ArrayList<ZxArt>());
            final ListView listView = findViewById(R.id.list);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ZxartSearch.this, ZxartShow.class);
                    intent.putExtra("item", mAdapter.getItem(position).getmPictureImage());
                    startActivity(intent);
                }
            });
            getLoaderManager().initLoader(1, null, ZxartSearch.this);
        } else {
            ListView listView = findViewById(R.id.list);
            TextView emptyView = findViewById(R.id.empty_list_item);
            emptyView.setText(R.string.noInternet);
            listView.setEmptyView(emptyView);
            pbar.setVisibility(View.GONE);
        }
    }



    @Override
    public Loader<ArrayList<ZxArt>> onCreateLoader(int i, Bundle bundle) {
        return new ZxartLoader(this,ParameterClass.parameter);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ZxArt>> loader, ArrayList<ZxArt> data) {
        mAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        } else {
            ListView listView = findViewById(R.id.list);
            listView.setEmptyView(findViewById(R.id.empty_list_item));
        }
        pbar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ZxArt>> loader) {
        mAdapter.clear();
    }
}
