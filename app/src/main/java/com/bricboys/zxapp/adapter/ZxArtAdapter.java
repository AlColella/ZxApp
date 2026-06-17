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

import com.bricboys.zxapp.R;
import com.bricboys.zxapp.ZxArt;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ZxArtAdapter extends RecyclerView.Adapter<ZxArtAdapter.ViewHolder> {
    private List<ZxArt> items = new ArrayList<>();
    private Set<String> favoriteIds = new HashSet<>();
    private OnItemClickListener listener;
    private OnFavoriteClickListener favoriteListener;

    public interface OnItemClickListener {
        void onItemClick(ZxArt item);
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClick(ZxArt item);
    }

    public ZxArtAdapter(OnItemClickListener listener, OnFavoriteClickListener favoriteListener) {
        this.listener = listener;
        this.favoriteListener = favoriteListener;
    }

    public void setItems(List<ZxArt> newItems) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zxart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ZxArt item = items.get(position);
        boolean isFavorite = favoriteIds.contains(item.getmPictureImage());
        holder.bind(item, isFavorite, listener, favoriteListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, rating, year, author;
        ImageButton btnFavorite;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.zxart_image);
            title = itemView.findViewById(R.id.zxart_title);
            rating = itemView.findViewById(R.id.zxart_rating);
            year = itemView.findViewById(R.id.zxart_year);
            author = itemView.findViewById(R.id.zxart_author);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }

        void bind(ZxArt item, boolean isFavorite, OnItemClickListener listener, OnFavoriteClickListener favListener) {
            title.setText(item.getmTitle());
            rating.setText("Rating: " + item.getmRating());
            year.setText("Year: " + item.getmYear());
            author.setText(item.getmAuthorId() != null ? "Author: " + item.getmAuthorId() : "");

            if (item.getmPictureImage() != null && !item.getmPictureImage().isEmpty()) {
                Picasso.get().load(item.getmPictureImage()).placeholder(R.drawable.no_photo).into(image);
            } else {
                image.setImageResource(R.drawable.no_photo);
            }

            if (isFavorite) {
                btnFavorite.setImageResource(android.R.drawable.btn_star_big_on);
                btnFavorite.setColorFilter(Color.parseColor("#FFD700")); // Gold
            } else {
                btnFavorite.setImageResource(android.R.drawable.btn_star_big_off);
                btnFavorite.setColorFilter(Color.GRAY);
            }

            itemView.setOnClickListener(v -> listener.onItemClick(item));
            btnFavorite.setOnClickListener(v -> favListener.onFavoriteClick(item));
        }
    }
}
