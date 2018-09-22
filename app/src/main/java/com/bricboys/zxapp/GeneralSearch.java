package com.bricboys.zxapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class GeneralSearch extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<General>>{

    private String mParametro;
    private ProgressBar pbar;
    private GeneralAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_search);
        setTitle("General list");

        Intent intent = getIntent();
        mParametro = intent.getStringExtra("parametro");

        pbar = findViewById(R.id.indeterminateBar);
        pbar.setVisibility(View.VISIBLE);

        boolean isConnected = ConnectionManager.verifyConnection(GeneralSearch.this);

        if (isConnected) {
            mAdapter = new GeneralAdapter(this, 0, new ArrayList<General>());
            ListView listView = findViewById(R.id.list_general);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    openYoutube(mAdapter.getItem(position).getmYoutubeLink());
                }
            });
            getLoaderManager().initLoader(1, null, GeneralSearch.this);
        } else {
            ListView listView = findViewById(R.id.list_general);
            TextView emptyView = findViewById(R.id.empty_list_item);
            emptyView.setText(R.string.noInternet);
            listView.setEmptyView(emptyView);
            pbar.setVisibility(View.GONE);
        }

        //String parm = parametro.replaceAll(" ", "%20");
        //Log.e("Parametro: ", parm);
    }

    private void openYoutube(String youtubeUrl) {
        if(!youtubeUrl.isEmpty()) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl)));
        }
    }

    @Override
    public Loader<ArrayList<General>> onCreateLoader(int id, Bundle args) {
        return new GeneralLoader(this, mParametro);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<General>> loader, ArrayList<General> data) {
        mAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        } else {
            ListView listView = findViewById(R.id.list_general);
            listView.setEmptyView(findViewById(R.id.empty_list_item));
        }
        pbar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<General>> loader) {
        mAdapter.clear();
    }

}
