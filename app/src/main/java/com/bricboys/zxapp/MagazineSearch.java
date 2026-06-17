package com.bricboys.zxapp;

import android.content.Intent;
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

import com.bricboys.zxapp.adapter.MagazineAdapter;
import com.bricboys.zxapp.viewmodel.FavoriteViewModel;
import com.bricboys.zxapp.viewmodel.MagazineViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

public class MagazineSearch extends AppCompatActivity {

    private String mParametro;
    private MagazineAdapter mAdapter;
    private MagazineViewModel mViewModel;
    private FavoriteViewModel mFavoriteViewModel;
    private SwipeRefreshLayout mSwipeRefresh;
    private ShimmerFrameLayout mShimmer;
    private TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magazine_search);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.magazine_search_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Magazine List");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        mParametro = intent.getStringExtra("parametro");

        mSwipeRefresh = findViewById(R.id.swipe_refresh);
        mShimmer = findViewById(R.id.shimmer_view_container);
        mEmptyView = findViewById(R.id.empty_list_item);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        mFavoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        
        mAdapter = new MagazineAdapter(
            item -> {
                Intent showIntent = new Intent(MagazineSearch.this, MagazineShow.class);
                showIntent.putExtra("item", item.getMagazineImage());
                showIntent.putExtra("title", item.getMagazineTitle());
                startActivity(showIntent);
            },
            item -> {
                String url = item.getMagazineImage();
                String fullUrl = url;
                if (url != null) {
                    if (url.startsWith("/pub/sinclair/")) {
                        fullUrl = url.replace("/pub/sinclair/", ParameterClass.pubSinclair);
                    } else if (url.startsWith("/zxdb/sinclair/")) {
                        fullUrl = url.replace("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
                    }
                }
                mFavoriteViewModel.toggleFavorite(item.getMagazineImage(), item.getMagazineTitle(), "MAGAZINE", fullUrl);
            }
        );
        recyclerView.setAdapter(mAdapter);

        mFavoriteViewModel.getAllFavoriteIds().observe(this, ids -> {
            mAdapter.setFavoriteIds(ids);
        });

        mViewModel = new ViewModelProvider(this).get(MagazineViewModel.class);

        mViewModel.getMagazines().observe(this, list -> {
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

        mSwipeRefresh.setOnRefreshListener(() -> mViewModel.loadMagazines(mParametro));

        if (mViewModel.getMagazines().getValue() == null) {
            mViewModel.loadMagazines(mParametro);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
