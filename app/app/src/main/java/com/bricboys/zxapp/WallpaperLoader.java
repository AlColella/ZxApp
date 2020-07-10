package com.bricboys.zxapp;

import android.content.Context;
import android.content.AsyncTaskLoader;


public class WallpaperLoader extends AsyncTaskLoader {

    String mTotal;
    String mMode;

    public WallpaperLoader(Context context, String mTotal, String mMode) {
        super(context);
        this.mMode = mMode;
        this.mTotal = mTotal;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        //Log.e("TapeLoader", "Cheguei aqui: " + parametro);
        Wallpaper wallpaper = WallpaperQuery.extractInfo(mMode, mTotal);
        return wallpaper;
    }
}
