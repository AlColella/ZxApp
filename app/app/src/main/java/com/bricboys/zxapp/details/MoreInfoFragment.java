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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bricboys.zxapp.ConnectionManager;
import com.bricboys.zxapp.ParameterClass;
import com.bricboys.zxapp.R;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


public class MoreInfoFragment extends Fragment implements LoaderManager.LoaderCallbacks<MoreInfo> {

    private String mParametro;
    private String url;
    private View rootView;
    private TapeAdapter mAdapter;
    private ProgressBar pbar;

    public MoreInfoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Bundle param = getArguments();
        mParametro = param.getString("parm");
        //Log.e("Fragment", "Parametro: " + mParametro);

        rootView = inflater.inflate(R.layout.more_info, container, false);

        boolean isConnected = ConnectionManager.verifyConnection(getContext());
        if(isConnected) {
            //getLoaderManager().initLoader(1, null, ImagesFragment.class);
            getLoaderManager().initLoader(1,null,this);
        }

        return rootView;
    }

    @NonNull
    @Override
    public Loader<MoreInfo> onCreateLoader(int id, @Nullable Bundle args) {
        return new MoreInfoLoader(getContext(),mParametro);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<MoreInfo> loader, MoreInfo data) {
        if(data != null) {
            MoreInfo more = data;
            formataTela(more);
        }
    }


    public void formataTela(MoreInfo more){

        if(!more.getControls().isEmpty()) {
            TextView controls = rootView.findViewById((R.id.controls));
            controls.setText("Controls: " + more.getControls());
        }

        if(!more.getNumberOfPlayers().isEmpty()) {
            TextView players = rootView.findViewById(R.id.players);
            players.setText("Nr of players: " + more.getNumberOfPlayers());
        }

        if(!more.getOtherSystems().isEmpty()) {
            TextView others = rootView.findViewById(R.id.other);
            others.setText("Other systems: " + more.getOtherSystems());
        }

        if(!more.getOriginalPrice().isEmpty()) {
            TextView price = rootView.findViewById(R.id.price);
            price.setText("Original price: " + more.getOriginalPrice());
        }

        if(!more.getAdvertisement().isEmpty()) {
            Ion.getDefault(getContext()).getHttpClient().getSSLSocketMiddleware().setSpdyEnabled(false);
            url = more.getAdvertisement();
            if (!url.isEmpty()) {
                String newUrl = "";
                if (url.substring(0, 14).equals("/pub/sinclair/")) {
                    newUrl = url.replaceAll("/pub/sinclair/", ParameterClass.pubSinclair);
                } else {
                    newUrl = url.replaceAll("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
                }
                ImageView advert = rootView.findViewById(R.id.advertisement);
                advert.setVisibility(View.VISIBLE);
                Ion.with(advert).load(newUrl).cancel();
                //Ion.with(inlay).load(newUrl);
                Ion.with(this).load(newUrl).intoImageView(advert);
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<MoreInfo> loader) {

    }
}
