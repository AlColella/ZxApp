package com.bricboys.zxapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GeneralActivity extends AppCompatActivity {

    private Button mButton;
    private EditText mGeneralEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        this.setTitle("General Search");

        mGeneralEdit = findViewById(R.id.general_edit);

        mButton = findViewById(R.id.buttonSearch);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent magazineSearch = new Intent(GeneralActivity.this, GeneralSearch.class);
                magazineSearch.putExtra("parametro", mGeneralEdit.getText().toString());
                startActivity(magazineSearch);
            }
        });

    }
}
