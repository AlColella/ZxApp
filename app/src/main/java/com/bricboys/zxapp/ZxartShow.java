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

public class ZxartShow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zxart_show);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.zxart_show_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String param = intent.getStringExtra("item");
        String title = intent.getStringExtra("title");

        PhotoView coverView = findViewById(R.id.zxartShow);
        if(param != null && !param.isEmpty()) {
            Picasso.get().load(param).into(coverView);
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
