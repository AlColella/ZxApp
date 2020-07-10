package com.bricboys.zxapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ZxartShow extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zxart_show);
        setTitle("Full screen");
        context = this;

        Intent intent = getIntent();
        String param = intent.getStringExtra("item");
        //Log.e("item", param);

        if(!param.isEmpty()) {
            String url = param;
            String newUrl = url;

            ImageView coverView = findViewById(R.id.zxartShow);
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
