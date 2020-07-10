package com.bricboys.zxapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class ZxartLoader extends AsyncTaskLoader {
    private String parametro;

    public ZxartLoader(Context context, String parametro) {
        super(context);
        this.parametro = parametro;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        List<ZxArt> zxarts = ZxartQuery.extractZxart(parametro);
        return zxarts;
    }
}
