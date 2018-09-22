package com.bricboys.zxapp;

import android.app.Dialog;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MagazineSearch extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Magazine>> {


    //https://api.zxinfo.dk/api/zxinfo/v2/magazines/Sinclair%20User/issuesp
    private String mParametro;
    private ProgressBar pbar;
    private MagazineAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magazine_search);
        setTitle("Magazine List");

        Intent intent = getIntent();
        mParametro = intent.getStringExtra("parametro");
        ParameterClass.parameter = mParametro;

        pbar = findViewById(R.id.indeterminateBar);
        pbar.setVisibility(View.VISIBLE);

        boolean isConnected = ConnectionManager.verifyConnection(MagazineSearch.this);

        if (isConnected) {
            mAdapter = new MagazineAdapter(this, 0, new ArrayList<Magazine>());
            final ListView listView = findViewById(R.id.list);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MagazineSearch.this, MagazineShow.class);
                    intent.putExtra("item", mAdapter.getItem(position).getMagazineImage());
                    startActivity(intent);
                }
            });
            getLoaderManager().initLoader(1, null, MagazineSearch.this);
        } else {
            ListView listView = findViewById(R.id.list);
            TextView emptyView = findViewById(R.id.empty_list_item);
            emptyView.setText(R.string.noInternet);
            listView.setEmptyView(emptyView);
            pbar.setVisibility(View.GONE);
        }
    }

    @Override
    public Loader<ArrayList<Magazine>> onCreateLoader(int id, Bundle args) {
        //return new MagazineLoader(this, mParametro);
        return new MagazineLoader(this,ParameterClass.parameter);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Magazine>> loader, ArrayList<Magazine> data) {
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
    public void onLoaderReset(Loader<ArrayList<Magazine>> loader) {
        mAdapter.clear();
    }
}
