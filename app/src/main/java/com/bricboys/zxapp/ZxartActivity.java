package com.bricboys.zxapp;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxart);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.zxart_root_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Configura a Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_zxart);
        setSupportActionBar(toolbar);

        // 2. Define o título e a seta de voltar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Zx Art");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

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

    // 3. FORMA MODERNA: Faz a seta de voltar funcionar sem usar onBackPressed()
    @Override
    public boolean onSupportNavigateUp() {
        // Esta é a forma recomendada para fechar a activity atual
        // quando se clica no botão "Voltar" da Toolbar
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}