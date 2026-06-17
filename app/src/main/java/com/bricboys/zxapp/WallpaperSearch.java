package com.bricboys.zxapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.koushikdutta.ion.Ion;


public class WallpaperSearch extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Wallpaper> {

    private String mTotal;
    private String mMode;
    private Button refreshBtn;
    private boolean gone = false;

    public WallpaperSearch() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallpaper_activity);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.WallpaperLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        refreshBtn = findViewById(R.id.updateButton);

        LinearLayout ln = findViewById(R.id.WallpaperLayout);
        ln.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!gone) {
                        refreshBtn.setVisibility(View.GONE);
                        gone = true;
                    } else {
                        refreshBtn.setVisibility(View.VISIBLE);
                        gone = false;

                    }


                }
                return true;
            }
        });
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLoaderManager().restartLoader(1, null, WallpaperSearch.this);
            }
        });

        //getSupportActionBar().hide();

        Intent intent = getIntent();
        mTotal = intent.getStringExtra("total");
        mMode = intent.getStringExtra("mode");

        boolean isConnected = ConnectionManager.verifyConnection(this);
        if (isConnected) {
            getLoaderManager().initLoader(1, null, WallpaperSearch.this);

        }

    }

    @NonNull
    @Override
    public Loader<Wallpaper> onCreateLoader(int i, Bundle bundle) {
        return new WallpaperLoader(this, mTotal, mMode);
    }

    @Override
    public void onLoadFinished(Loader<Wallpaper> loader, Wallpaper wallpaper) {
        if (wallpaper != null) {
            formataTela(wallpaper);
        }
    }

    @Override
    public void onLoaderReset(Loader<Wallpaper> loader) {

    }

    public void formataTela(Wallpaper wallpaper) {

        if (!wallpaper.getmRunningScreen().isEmpty()) {
            String url;
            Ion.getDefault(this).getHttpClient().getSSLSocketMiddleware();
            url = wallpaper.getmRunningScreen();
            if (!url.isEmpty()) {
                String newUrl = "";
                if (url.substring(0, 14).equals("/pub/sinclair/")) {
                    newUrl = url.replaceAll("/pub/sinclair/", ParameterClass.pubSinclair);
                } else if (url.substring(0, 15).equals("/zxdb/sinclair/")) {
                    newUrl = url.replaceAll("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
                } else {
                    newUrl = "https://zxinfo.dk/media" + url;
                }
                Log.e("url nova", newUrl);
                ImageView advert = findViewById(R.id.wallpaperRunning);
                advert.setVisibility(View.VISIBLE);
                Ion.with(advert).load(newUrl).cancel();
                //Ion.with(inlay).load(newUrl);
                Ion.with(this).load(newUrl).intoImageView(advert);
            }
        }

        if (!wallpaper.getmLoadingScreen().isEmpty()) {
            String url;
            Ion.getDefault(this).getHttpClient().getSSLSocketMiddleware();
            url = wallpaper.getmLoadingScreen();
            if (!url.isEmpty()) {
                String newUrl = "";
                if (url.substring(0, 14).equals("/pub/sinclair/")) {
                    newUrl = url.replaceAll("/pub/sinclair/", ParameterClass.pubSinclair);
                } else if (url.substring(0, 15).equals("/zxdb/sinclair/")) {
                    newUrl = url.replaceAll("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
                } else {
                    newUrl = "https://zxinfo.dk/media" + url;
                }
                Log.e("url nova", newUrl);
                ImageView advert = findViewById(R.id.wallpaperLoading);
                advert.setVisibility(View.VISIBLE);
                Ion.with(advert).load(newUrl).cancel();
                //Ion.with(inlay).load(newUrl);
                Ion.with(this).load(newUrl).intoImageView(advert);
            }
        }
    }
}
