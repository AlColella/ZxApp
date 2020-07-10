package com.bricboys.zxapp.details;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.bricboys.zxapp.R;

import java.util.ArrayList;
import java.util.Objects;

public class DownloadFragment  extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Tapes>> {

    private String mParametro;
    private String mNome;///
    private String url;
    private View rootView;
    private TapeAdapter mAdapter;
    private ProgressBar pbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle param = getArguments();
        mParametro = param.getString("parm");
        mNome = param.getString("nome");

        rootView = inflater.inflate(R.layout.tape_search, container, false);

        pbar = rootView.findViewById(R.id.indeterminateBar);
        pbar.setVisibility(View.VISIBLE);

        mAdapter = new TapeAdapter(getContext(), 0, new ArrayList<Tapes>());
        ListView listView = rootView.findViewById(R.id.list_tapes);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mUrl = Objects.requireNonNull(mAdapter.getItem(position)).getmUrl();
                DownloadManager mManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request mRqRequest = new DownloadManager.Request(Uri.parse(mUrl));
                mRqRequest.setTitle(mNome);
                mRqRequest.setDescription("Downloading file");
                mRqRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, mNome + ".zip");
                long idDownLoad=mManager.enqueue(mRqRequest);
            }
        });

        getLoaderManager().initLoader(1, null, DownloadFragment.this);
        //pbar.setVisibility(View.GONE);

        //final ArrayList<Tapes> tapes = new ArrayList<>();

        return rootView;
    }


    @Override
    public Loader<ArrayList<Tapes>> onCreateLoader(int id, @Nullable Bundle args) {
        return new TapeLoader(getContext(),mParametro);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Tapes>> loader, ArrayList<Tapes> data) {
        mAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        } else {
            ListView listView = rootView.findViewById(R.id.list_tapes);
            listView.setEmptyView(rootView.findViewById(R.id.empty_list_item));
        }
        pbar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Tapes>> loader) {
        mAdapter.clear();
    }

}

