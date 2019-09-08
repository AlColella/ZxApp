package com.bricboys.zxapp.details;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class DetailsLoader extends AsyncTaskLoader {

    String parametro;

    public DetailsLoader(Context context, String parametro) {
        super(context);
        this.parametro = parametro;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        Details detail = DetailsQuery.extractDetails(parametro);
        return detail;
    }
}
