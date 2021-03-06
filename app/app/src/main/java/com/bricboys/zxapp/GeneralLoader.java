package com.bricboys.zxapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class GeneralLoader extends AsyncTaskLoader {

    private String parametro;

    public GeneralLoader(Context context, String parametro) {
        super(context);
        this.parametro = parametro;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        List<General> generals = GeneralQuery.extractGeneral(parametro);
        return generals;
    }
}
