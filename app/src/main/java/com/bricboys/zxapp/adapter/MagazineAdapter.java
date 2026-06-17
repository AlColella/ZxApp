package com.bricboys.zxapp.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bricboys.zxapp.Magazine;
import com.bricboys.zxapp.ParameterClass;
import com.bricboys.zxapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MagazineAdapter extends RecyclerView.Adapter<MagazineAdapter.ViewHolder> {
    private List<Magazine> items = new ArrayList<>();
    private Set<String> favoriteIds = new HashSet<>();
    private OnItemClickListener listener;
    private OnFavoriteClickListener favoriteListener;

    public interface OnItemClickListener {
        void onItemClick(Magazine item);
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClick(Magazine item);
    }

    public MagazineAdapter(OnItemClickListener listener, OnFavoriteClickListener favoriteListener) {
        this.listener = listener;
        this.favoriteListener = favoriteListener;
    }

    public void setItems(List<Magazine> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    public void setFavoriteIds(List<String> ids) {
        this.favoriteIds = new HashSet<>(ids);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.magazine_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Magazine item = items.get(position);
        boolean isFavorite = favoriteIds.contains(item.getMagazineImage());
        holder.bind(item, isFavorite, listener, favoriteListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, issue, year;
        ImageButton btnFavorite;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.magazine_image);
            title = itemView.findViewById(R.id.magazine_name);
            issue = itemView.findViewById(R.id.magazine_issue);
            year = itemView.findViewById(R.id.magazine_year);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }

        void bind(Magazine item, boolean isFavorite, OnItemClickListener listener, OnFavoriteClickListener favListener) {
            title.setText(item.getMagazineTitle());
            issue.setText("Issue: " + item.getMagazineIssue());
            year.setText("Year: " + item.getMagazineYear());

            String url = item.getMagazineImage();
            if (url != null && !url.isEmpty()) {
                String newUrl;
                if (url.startsWith("/pub/sinclair/")) {
                    newUrl = url.replace("/pub/sinclair/", ParameterClass.pubSinclair);
                } else if (url.startsWith("/zxdb/sinclair/")) {
                    newUrl = url.replace("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
                } else {
                    newUrl = url;
                }
                Picasso.get().load(newUrl).placeholder(R.drawable.no_photo).into(image);
            } else {
                image.setImageResource(R.drawable.no_photo);
            }

            if (isFavorite) {
                btnFavorite.setImageResource(android.R.drawable.btn_star_big_on);
                btnFavorite.setColorFilter(Color.parseColor("#FFD700"));
            } else {
                btnFavorite.setImageResource(android.R.drawable.btn_star_big_off);
                btnFavorite.setColorFilter(Color.GRAY);
            }

            itemView.setOnClickListener(v -> listener.onItemClick(item));
            btnFavorite.setOnClickListener(v -> favListener.onFavoriteClick(item));
        }
    }
}
