package com.bricboys.zxapp.details;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class MoreInfoLoader extends AsyncTaskLoader {

    String parametro;

    public MoreInfoLoader(Context context, String parametro) {
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
        MoreInfo generals = MoreInfoQuery.extractInfo(parametro);
        return generals;
    }
}
