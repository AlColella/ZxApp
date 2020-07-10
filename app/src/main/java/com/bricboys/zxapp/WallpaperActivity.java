package com.bricboys.zxapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class WallpaperActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallpaper_activity);

        Intent wallpaperSearch = new Intent(WallpaperActivity.this, WallpaperSearch.class);
        wallpaperSearch.putExtra("total", "1");
        wallpaperSearch.putExtra("mode", "full");
        startActivity(wallpaperSearch);
    }
}
