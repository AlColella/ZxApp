package com.bricboys.zxapp;

import android.content.Context;
import android.content.AsyncTaskLoader;

import java.util.List;

public class MagazineLoader extends AsyncTaskLoader {
    private String parametro;

    public MagazineLoader(Context context, String parametro) {
        super(context);
        this.parametro = parametro;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        List<Magazine> magazines = MagazineQuery.extractMagazine(parametro);
        return magazines;
    }
}
