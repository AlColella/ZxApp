package com.bricboys.zxapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class MagazineShow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magazine_show);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.magazine_show_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String param = intent.getStringExtra("item");
        String title = intent.getStringExtra("title");

        PhotoView coverView = findViewById(R.id.magazineShow);
        if(param != null && !param.isEmpty()) {
            String url = param;
            String newUrl;
            if (url.startsWith("/pub/sinclair/")) {
                newUrl = url.replace("/pub/sinclair/", ParameterClass.pubSinclair);
            } else if (url.startsWith("/zxdb/sinclair/")) {
                newUrl = url.replace("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
            } else {
                newUrl = url;
            }
            Picasso.get().load(newUrl).into(coverView);
        }

        findViewById(R.id.btnIvClose).setOnClickListener(v -> finish());

        findViewById(R.id.btnShare).setOnClickListener(v -> {
            ImageUtils.shareImage(this, coverView, title);
        });

        findViewById(R.id.btnSetWallpaper).setOnClickListener(v -> {
            ImageUtils.setWallpaper(this, coverView);
        });
    }
}
