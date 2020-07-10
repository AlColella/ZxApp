package com.bricboys.zxapp.details;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class TapeLoader extends AsyncTaskLoader {

    String parametro;

    public TapeLoader(Context context, String parametro) {
        super(context);
        this.parametro = parametro;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public Object loadInBackground() {
        //Log.e("TapeLoader", "Cheguei aqui: " + parametro);
        List<Tapes> generals = TapeQuery.extractTapes(parametro);
        return generals;
    }
}
