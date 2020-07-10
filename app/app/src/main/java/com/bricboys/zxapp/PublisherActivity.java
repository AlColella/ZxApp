package com.bricboys.zxapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PublisherActivity extends AppCompatActivity {

    private Button mButton;
    private EditText mPublisherEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);

        this.setTitle("Publisher");

        mPublisherEdit = findViewById(R.id.publisher_edit);
        mButton = findViewById(R.id.buttonSearch);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pubIntent = new Intent(PublisherActivity.this, PublisherSearch.class);
                pubIntent.putExtra("parametro",mPublisherEdit.getText().toString());
                startActivity(pubIntent);
            }
        });
    }
}
