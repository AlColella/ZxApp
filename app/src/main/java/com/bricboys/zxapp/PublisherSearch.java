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

import com.bricboys.zxapp.details.DetailsActivity;

import java.util.ArrayList;

public class PublisherSearch extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Publisher>> {

    private String mParametro;
    private ProgressBar pbar;
    private PublisherAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publisher_search);
        setTitle("Titles by Publisher");

        Intent intent = getIntent();
        mParametro = intent.getStringExtra("parametro");

        pbar = findViewById(R.id.indeterminateBar);
        pbar.setVisibility(View.VISIBLE);

        boolean isConnected = ConnectionManager.verifyConnection(PublisherSearch.this);

        if (isConnected) {
            mAdapter = new PublisherAdapter(this, 0, new ArrayList<Publisher>());
            ListView listView = findViewById(R.id.list_general);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //openYoutube(mAdapter.getItem(position).getmYoutubeLink());
                    String itemId = mAdapter.getItem(position).getmId().toString();
                    String type = mAdapter.getItem(position).getmType().toString();
                    if(type.contains("Game")) {
                        Intent detail = new Intent(PublisherSearch.this, DetailsActivity.class);
                        detail.putExtra("itemId", itemId);
                        startActivity(detail);
                    }
                }
            });
            getLoaderManager().initLoader(1, null, PublisherSearch.this);
        } else {
            ListView listView = findViewById(R.id.list_general);
            TextView emptyView = findViewById(R.id.empty_list_item);
            emptyView.setText(R.string.noInternet);
            listView.setEmptyView(emptyView);
            pbar.setVisibility(View.GONE);
        }

    }

    @Override
    public Loader<ArrayList<Publisher>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Publisher>> loader, ArrayList<Publisher> data) {

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Publisher>> loader) {

    }
}
