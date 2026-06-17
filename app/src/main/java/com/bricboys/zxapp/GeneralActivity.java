package com.bricboys.zxapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GeneralActivity extends AppCompatActivity {

    private Button mButton;
    private RadioButton loading;
    private RadioButton running;

    private EditText mGeneralEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.general_root_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mGeneralEdit = findViewById(R.id.general_edit);
        loading = findViewById(R.id.radio_loading);
        running = findViewById(R.id.radio_running);
        loading.setActivated(true);
        loading.setChecked(true);
        running.setActivated(false);
        ScreenManager.screenView = "loading";
        loading.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loading.setActivated(true);
                running.setActivated(false);
                ScreenManager.screenView = "loading";
            }
        });

        running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.setActivated(false);
                running.setActivated(true);
                ScreenManager.screenView = "running";
            }
        });

        mButton = findViewById(R.id.buttonSearch);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent magazineSearch = new Intent(GeneralActivity.this, GeneralSearch.class);
                magazineSearch.putExtra("parametro", mGeneralEdit.getText().toString());
                startActivity(magazineSearch);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("General Search");
        }

        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
