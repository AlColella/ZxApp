package com.bricboys.zxapp.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MoreInfoFragment extends Fragment implements LoaderManager.LoaderCallbacks<MoreInfo> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return null;
    }

    @NonNull
    @Override
    public Loader<MoreInfo> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<MoreInfo> loader, MoreInfo data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<MoreInfo> loader) {

    }
}
