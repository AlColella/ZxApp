package com.bricboys.zxapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MagazineActivity extends AppCompatActivity {

    private Button mButtonSearch;
    private EditText mMagazineEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);
        this.setTitle("Magazine Search");

        mMagazineEdit = findViewById(R.id.magazine_edit);

        mButtonSearch = findViewById(R.id.buttonSearch);
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent magazineSearch = new Intent(MagazineActivity.this, MagazineSearch.class);
                magazineSearch.putExtra("parametro", mMagazineEdit.getText().toString());
                startActivity(magazineSearch);
            }
        });

    }
}
