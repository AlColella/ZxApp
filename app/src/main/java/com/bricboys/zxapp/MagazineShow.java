package com.bricboys.zxapp;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MagazineShow extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magazine_show);
        setTitle("Full screen");
        context = this;

        Intent intent = getIntent();
        String param = intent.getStringExtra("item");
        //Log.e("item", param);

        if(!param.isEmpty()) {
            String url = param;
            String newUrl = null;
            if (url.substring(0,14).equals("/pub/sinclair/")) {
                newUrl = url.replaceAll("/pub/sinclair/", ParameterClass.pubSinclair);
            } else {
                newUrl = url.replaceAll("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
            }
            ImageView coverView = findViewById(R.id.magazineShow);
           // Picasso.with(context).cancelRequest(coverView);
            Picasso.get().load(newUrl).into(coverView);
        }

        Button btnClose = findViewById(R.id.btnIvClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
