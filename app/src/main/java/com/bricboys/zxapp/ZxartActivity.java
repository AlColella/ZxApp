package com.bricboys.zxapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ZxartActivity extends AppCompatActivity {

    private Button mButtonSearch;
    private EditText mZxartEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxart);
        this.setTitle("Zx Art Search");

        mZxartEdit = findViewById(R.id.zxart_edit);

        mButtonSearch = findViewById(R.id.buttonSearch);
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zxartSearch = new Intent(ZxartActivity.this, ZxartSearch.class);
                zxartSearch.putExtra("parametro", mZxartEdit.getText().toString());
                startActivity(zxartSearch);
            }
        });

    }
}
