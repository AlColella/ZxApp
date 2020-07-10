package com.bricboys.zxapp.details;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bricboys.zxapp.ConnectionManager;
import com.bricboys.zxapp.ParameterClass;
import com.bricboys.zxapp.R;
import com.koushikdutta.ion.Ion;

public class DetailsActivityBackup extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Details> {

    private String mParametro;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        this.setTitle("Details");

        Intent intent = getIntent();
        mParametro = intent.getStringExtra("itemId");

        TextView idText = findViewById(R.id.id_text);
        idText.setText(mParametro);

        boolean isConnected = ConnectionManager.verifyConnection(DetailsActivityBackup.this);
        if(isConnected) {
            getLoaderManager().initLoader(1, null, DetailsActivityBackup.this);
        }

    }

    @Override
    public Loader<Details> onCreateLoader(int id, Bundle args) {
       // return new DetailsLoader(this,mParametro);
        return null;
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

        Ion.getDefault(this).getHttpClient().getSSLSocketMiddleware().setSpdyEnabled(false);
        url = detail.getmInlay();
        if (!url.isEmpty()) {
            String newUrl = "";
            if (url.substring(0, 14).equals("/pub/sinclair/")) {
                newUrl = url.replaceAll("/pub/sinclair/", ParameterClass.pubSinclair);
            } else {
                newUrl = url.replaceAll("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
            }
            ImageView inlay = findViewById(R.id.inlay);
            inlay.setVisibility(View.VISIBLE);
            Ion.with(inlay).load(newUrl).cancel();
            //Ion.with(inlay).load(newUrl);
            Ion.with(this).load(newUrl).intoImageView(inlay);
        } else {
            ImageView inlay = findViewById(R.id.inlay);
            TextView inlayText = findViewById(R.id.inlay_text);
            inlayText.setVisibility(View.GONE);
            inlay.setVisibility(View.GONE);
            View view_id = findViewById(R.id.view_id);
            view_id.setVisibility(View.GONE);
        }
        url = detail.getmLoadingScreen();
        if(!url.isEmpty()) {
            String newUrl = "https://zxinfo.dk/media" + url;
            ImageView loading = findViewById(R.id.loading);
            loading.setVisibility(View.VISIBLE);
            Ion.with(loading).load(newUrl).cancel();
            //Ion.with(inlay).load(newUrl);
            Ion.with(this).load(newUrl).intoImageView(loading);
        } else {
            ImageView loading = findViewById(R.id.loading);
            loading.setVisibility(View.GONE);
            TextView loadText = findViewById(R.id.loadText);
            loadText.setVisibility(View.GONE);
            View view_id = findViewById(R.id.view_loading);
            view_id.setVisibility(View.GONE);
        }
        url = detail.getmRunningScreen();
        //Log.e("Running ZX81: ", url);
        if(!url.isEmpty()) {
            String newUrl;
            if (url.substring(0, 14).equals("/pub/sinclair/")) {
                newUrl = url.replaceAll("/pub/sinclair/", ParameterClass.pubSinclair);
            } else if (url.substring(0, 14).equals("/zxdb/sinclair/")) {
                newUrl = url.replaceAll("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
            } else {
                newUrl = "https://zxinfo.dk/media" + url;
            }
            //Log.e("Running: ", newUrl);
            ImageView running = findViewById(R.id.running);
            running.setVisibility(View.VISIBLE);
            Ion.with(running).load(newUrl).cancel();
            //Ion.with(inlay).load(newUrl);
            Ion.with(this).load(newUrl).intoImageView(running);
        } else {
            ImageView running = findViewById(R.id.running);
            running.setVisibility(View.GONE);
            TextView runText = findViewById(R.id.runningText);
            runText.setVisibility(View.GONE);
            View view_id = findViewById(R.id.view_running);
            view_id.setVisibility(View.GONE);
        }
        //url = detail.
        url = detail.getmYouTube();
        //Log.e("Running ZX81: ", url);
        if(!url.isEmpty()) {
            String newUrl = getYoutubeThumbnailUrlFromVideoUrl(url);
            ImageView video = findViewById(R.id.video);
            video.setVisibility(View.VISIBLE);
            Ion.with(this).load(newUrl).intoImageView(video);
            //Log.e("Youtube: ", newUrl);
            video.setOnClickListener (new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openYoutube(url);
                }
            });

        } else {
            ImageView video = findViewById(R.id.video);
            video.setVisibility(View.GONE);
            TextView videoText = findViewById(R.id.videoText);
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
