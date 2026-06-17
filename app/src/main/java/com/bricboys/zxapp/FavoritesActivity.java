package com.bricboys.zxapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bricboys.zxapp.adapter.FavoritesAdapter;
import com.bricboys.zxapp.details.DetailsActivity;
import com.bricboys.zxapp.viewmodel.FavoriteViewModel;

public class FavoritesActivity extends AppCompatActivity {

    private FavoritesAdapter mAdapter;
    private FavoriteViewModel mViewModel;
    private TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.favorites_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mEmptyView = findViewById(R.id.empty_view);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        mAdapter = new FavoritesAdapter(item -> {
            if ("MAGAZINE".equals(item.type)) {
                Intent showIntent = new Intent(this, MagazineShow.class);
                showIntent.putExtra("item", item.imageUrl);
                showIntent.putExtra("title", item.title);
                startActivity(showIntent);
            } else if ("ART".equals(item.type)) {
                Intent showIntent = new Intent(this, ZxartShow.class);
                showIntent.putExtra("item", item.imageUrl);
                showIntent.putExtra("title", item.title);
                startActivity(showIntent);
            } else {
                Intent detail = new Intent(this, DetailsActivity.class);
                detail.putExtra("itemId", item.itemId);
                detail.putExtra("name", item.title);
                startActivity(detail);
            }
        });
        recyclerView.setAdapter(mAdapter);

        mViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        mViewModel.getAllFavorites().observe(this, list -> {
            mAdapter.setItems(list);
            mEmptyView.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
