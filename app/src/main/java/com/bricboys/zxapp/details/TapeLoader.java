package com.bricboys.zxapp.details;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

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
