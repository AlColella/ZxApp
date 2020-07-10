package com.bricboys.zxapp.details;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.bricboys.zxapp.R;


public class DetailsActivity extends AppCompatActivity {

    private String mParametro;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        Intent intent = getIntent();
        mParametro = intent.getStringExtra("itemId");
        mName = intent.getStringExtra("name");

        //Log.e("Details", "name: " + mName);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_detalhes);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        DetalheFragmentPageAdapter adapter = new DetalheFragmentPageAdapter(getSupportFragmentManager(), getApplicationContext(),
                mParametro, mName );

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

}
