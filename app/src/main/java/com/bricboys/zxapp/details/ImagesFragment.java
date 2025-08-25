package com.bricboys.zxapp.details;

//import android.support.v4.app.Loader;
import android.content.Intent;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bricboys.zxapp.ConnectionManager;
import com.bricboys.zxapp.ParameterClass;
import com.bricboys.zxapp.R;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class ImagesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Details>{

    private String mParametro;
    private String url;
    private View rootView;

    public ImagesFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        Bundle param = getArguments();
        mParametro = param.getString("parm");
        //Log.e("Fragment", "Parametro: " + mParametro);

        rootView = inflater.inflate(R.layout.detail_layout, container, false);

        final ArrayList<Details> details = new ArrayList<>();


        boolean isConnected = ConnectionManager.verifyConnection(getContext());
        if(isConnected) {
            //getLoaderManager().initLoader(1, null, ImagesFragment.class);
            getLoaderManager().initLoader(1,null,this);
        }

        return rootView;

    }

    @Override
    public Loader<Details> onCreateLoader(int id, Bundle args) {
        return new DetailsLoader(getContext(),mParametro);
    }

    @Override
    public void onLoadFinished(Loader<Details> loader, Details data) {
        Details detail = data;
        formataTela(detail);
        //Log.e("data voltou: ", detail.getmInstructions());
    }

    @Override
    public void onLoaderReset(Loader<Details> loader) {

    }

    private void formataTela(Details detail) {

        TextView id = rootView.findViewById(R.id.id_text);
        id.setText(mParametro);

        Ion.getDefault(getContext()).getHttpClient().getSSLSocketMiddleware();
        url = detail.getmInlay();
        if (!url.isEmpty()) {
            String newUrl = "";
            if (url.substring(0, 14).equals("/pub/sinclair/")) {
                newUrl = url.replaceAll("/pub/sinclair/", ParameterClass.pubSinclair);
            } else {
                newUrl = url.replaceAll("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
            }
            ImageView inlay = rootView.findViewById(R.id.inlay);
            inlay.setVisibility(View.VISIBLE);
            Ion.with(inlay).load(newUrl).cancel();

            Log.e("Inlay url: ", newUrl);
            //Ion.with(inlay).load(newUrl);
            Ion.with(this).load(newUrl).intoImageView(inlay);
        } else {
            ImageView inlay = rootView.findViewById(R.id.inlay);
            TextView inlayText = rootView.findViewById(R.id.inlay_text);
            inlayText.setVisibility(View.GONE);
            inlay.setVisibility(View.GONE);
            View view_id = rootView.findViewById(R.id.view_id);
            view_id.setVisibility(View.GONE);
        }
        url = detail.getmLoadingScreen();
        if(!url.isEmpty()) {
            String newUrl;
            //Log.e("URL no detalhe: ", url);
            //Log.e("Substring: ", url.substring(0,14));
            if (url.substring(0, 14).equals("/pub/sinclair/")) {
                newUrl = url.replaceAll("/pub/sinclair/", ParameterClass.pubSinclair);
            } else if (url.substring(0, 15).equals("/zxdb/sinclair/")) {
                newUrl = url.replaceAll("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
            } else {
                newUrl = "https://zxinfo.dk/media" + url;
            }

            //Log.e("Qual é a URL: ", newUrl);

            ImageView loading = rootView.findViewById(R.id.loading);
            loading.setVisibility(View.VISIBLE);
            Ion.with(loading).load(newUrl).cancel();
            //Ion.with(inlay).load(newUrl);
            Ion.with(this).load(newUrl).intoImageView(loading);
        } else {
            ImageView loading = rootView.findViewById(R.id.loading);
            loading.setVisibility(View.GONE);
            TextView loadText = rootView.findViewById(R.id.loadText);
            loadText.setVisibility(View.GONE);
            View view_id = rootView.findViewById(R.id.view_loading);
            view_id.setVisibility(View.GONE);
        }
        url = detail.getmRunningScreen();
        //Log.e("Running ZX81: ", url);
        if(!url.isEmpty()) {
            String newUrl;
            if (url.substring(0, 14).equals("/pub/sinclair/")) {
                newUrl = url.replaceAll("/pub/sinclair/", ParameterClass.pubSinclair);
            } else if (url.substring(0, 15).equals("/zxdb/sinclair/")) {
                newUrl = url.replaceAll("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
            } else {
                newUrl = "https://zxinfo.dk/media" + url;
            }
            //Log.e("Running: ", newUrl);
            ImageView running = rootView.findViewById(R.id.running);
            running.setVisibility(View.VISIBLE);
            Ion.with(running).load(newUrl).cancel();
            //Ion.with(inlay).load(newUrl);
            Ion.with(this).load(newUrl).intoImageView(running);
        } else {
            ImageView running = rootView.findViewById(R.id.running);
            running.setVisibility(View.GONE);
            TextView runText = rootView.findViewById(R.id.runningText);
            runText.setVisibility(View.GONE);
            View view_id = rootView.findViewById(R.id.view_running);
            view_id.setVisibility(View.GONE);
        }
        //url = detail.
        url = detail.getmYouTube();
        //Log.e("Running ZX81: ", url);
        if(!url.isEmpty()) {
            //id.setText("YouTube Video");
            String newUrl = getYoutubeThumbnailUrlFromVideoUrl(url);
            ImageView video = rootView.findViewById(R.id.video);
            video.setVisibility(View.VISIBLE);
            Ion.with(this).load(newUrl).intoImageView(video);
            //Log.e("Youtube: ", newUrl);
            video.setOnClickListener (new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("YouTube Link", url);
                    openYoutube(url);
                }
            });

        } else {
            ImageView video = rootView.findViewById(R.id.video);
            video.setVisibility(View.GONE);
            TextView videoText = rootView.findViewById(R.id.videoText);
            videoText.setVisibility(View.GONE);
        }

    }

    public static String getYoutubeThumbnailUrlFromVideoUrl(String videoUrl) {
        String imgUrl = "http://img.youtube.com/vi/"+getYoutubeVideoIdFromUrl(videoUrl) + "/0.jpg";
        return imgUrl;
    }

    public static String getYoutubeVideoIdFromUrl(String inUrl) {
        if (inUrl.toLowerCase().contains("youtu.be")) {
            return inUrl.substring(inUrl.lastIndexOf("/") + 1);
        }
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        java.util.regex.Pattern compiledPattern = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = compiledPattern.matcher(inUrl);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    private void openYoutube(String youtubeUrl) {
        if(!youtubeUrl.isEmpty()) {
            startActivity(new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(youtubeUrl)));
        }
    }

}
