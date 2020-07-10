package com.bricboys.zxapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView mMagazineTextView;
    private TextView mGeneralTextView;
    private TextView mWallpaperTextView;
    private TextView mZxArtTextView;
    private static int REQUEST_CODE=1;
    //private TextView mPublisherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, REQUEST_CODE);

        mZxArtTextView = findViewById(R.id.zxartTextView);
        mMagazineTextView = findViewById(R.id.magazineTextView);
        mGeneralTextView = findViewById(R.id.generalTextView);
        mWallpaperTextView = findViewById(R.id.wallpaperTextView);
       // mPublisherTextView = findViewById(R.id.publisherTextView);

        mZxArtTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent zxartIntent = new Intent(MainActivity.this, ZxartActivity.class);
                startActivity(zxartIntent);
            }
        });

        mMagazineTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent magazineIntent = new Intent(MainActivity.this, MagazineActivity.class);
                startActivity(magazineIntent);
            }
        });

        mGeneralTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent generalIntent = new Intent(MainActivity.this, GeneralActivity.class);
                startActivity(generalIntent);
            }
        });

        mWallpaperTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent wallpaperIntent = new Intent(MainActivity.this, WallpaperSearch.class);
                wallpaperIntent.putExtra("total", "1");
                wallpaperIntent.putExtra("mode", "full");
                startActivity(wallpaperIntent);
            }
        });

       // mPublisherTextView.setOnClickListener(new View.OnClickListener() {
       //     @Override
        //    public void onClick(View v) {
       //         Intent publisherIntent = new Intent(MainActivity.this, PublisherActivity.class);
        //        startActivity(publisherIntent);
       //     }
      //  });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showAboutDialog();
        return super.onOptionsItemSelected(item);
    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.about_dialog_msg)
               .setTitle("ZX App");
        builder.setNeutralButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
