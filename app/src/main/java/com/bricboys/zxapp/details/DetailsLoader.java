package com.bricboys.zxapp.details;

import androidx.loader.content.AsyncTaskLoader;
import android.content.Context;

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
