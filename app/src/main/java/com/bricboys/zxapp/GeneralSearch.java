package com.bricboys.zxapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bricboys.zxapp.adapter.GeneralAdapter;
import com.bricboys.zxapp.details.DetailsActivity;
import com.bricboys.zxapp.viewmodel.FavoriteViewModel;
import com.bricboys.zxapp.viewmodel.GeneralViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

public class GeneralSearch extends AppCompatActivity {

    private String mParametro;
    private GeneralAdapter mAdapter;
    private GeneralViewModel mViewModel;
    private FavoriteViewModel mFavoriteViewModel;
    private SwipeRefreshLayout mSwipeRefresh;
    private ShimmerFrameLayout mShimmer;
    private TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_search);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.general_search_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        mParametro = intent.getStringExtra("parametro");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("General Search: " + mParametro);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        // Force Toolbar colors via code to ensure they are light
        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getNavigationIcon() != null) {
            toolbar.getNavigationIcon().setTint(Color.WHITE);
        }

        mSwipeRefresh = findViewById(R.id.swipe_refresh);
        mShimmer = findViewById(R.id.shimmer_view_container);
        mEmptyView = findViewById(R.id.empty_list_item);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        mFavoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        
        mAdapter = new GeneralAdapter(
            item -> {
                Intent detail = new Intent(GeneralSearch.this, DetailsActivity.class);
                detail.putExtra("itemId", item.getmId());
                detail.putExtra("name", item.getmTitle());
                startActivity(detail);
            },
            item -> {
                String imageUrl = item.getmImageId() != null ? "https://zxinfo.dk/media" + item.getmImageId() : "";
                mFavoriteViewModel.toggleFavorite(item.getmId(), item.getmTitle(), "GENERAL", imageUrl);
            }
        );
        recyclerView.setAdapter(mAdapter);

        mFavoriteViewModel.getAllFavoriteIds().observe(this, ids -> {
            mAdapter.setFavoriteIds(ids);
        });

        mViewModel = new ViewModelProvider(this).get(GeneralViewModel.class);

        mViewModel.getGenerals().observe(this, list -> {
            mAdapter.setItems(list);
            mEmptyView.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
        });

        mViewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                mShimmer.startShimmer();
                mShimmer.setVisibility(View.VISIBLE);
            } else {
                mShimmer.stopShimmer();
                mShimmer.setVisibility(View.GONE);
                mSwipeRefresh.setRefreshing(false);
            }
        });

        mViewModel.getError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        mSwipeRefresh.setOnRefreshListener(() -> mViewModel.search(mParametro));

        if (mViewModel.getGenerals().getValue() == null) {
            mViewModel.search(mParametro);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
