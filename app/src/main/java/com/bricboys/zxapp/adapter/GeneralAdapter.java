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

import com.bricboys.zxapp.General;
import com.bricboys.zxapp.ParameterClass;
import com.bricboys.zxapp.R;
import com.bricboys.zxapp.ScreenManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.ViewHolder> {
    private List<General> items = new ArrayList<>();
    private Set<String> favoriteIds = new HashSet<>();
    private OnItemClickListener listener;
    private OnFavoriteClickListener favoriteListener;

    public interface OnItemClickListener {
        void onItemClick(General item);
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClick(General item);
    }

    public GeneralAdapter(OnItemClickListener listener, OnFavoriteClickListener favoriteListener) {
        this.listener = listener;
        this.favoriteListener = favoriteListener;
    }

    public void setItems(List<General> newItems) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.general_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        General item = items.get(position);
        boolean isFavorite = favoriteIds.contains(item.getmId());
        holder.bind(item, isFavorite, listener, favoriteListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, type, release, publisher, available, machine, author, score, typeScreen;
        ImageButton btnFavorite;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.loadingScreen);
            title = itemView.findViewById(R.id.title_name);
            type = itemView.findViewById(R.id.type);
            release = itemView.findViewById(R.id.release);
            publisher = itemView.findViewById(R.id.publisher);
            available = itemView.findViewById(R.id.available);
            machine = itemView.findViewById(R.id.machine);
            author = itemView.findViewById(R.id.author_text);
            score = itemView.findViewById(R.id.score_text);
            typeScreen = itemView.findViewById(R.id.textScreen);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }

        void bind(General item, boolean isFavorite, OnItemClickListener listener, OnFavoriteClickListener favListener) {
             title.setText(item.getmTitle());
             type.setText(item.getmType());
             release.setText("Release: " + item.getmRelease());
             publisher.setText("Publisher: " + item.getmPublisher());
             available.setText("Available: " + item.getmAvailability());
             machine.setText("Machine: " + item.getmMachine());
             author.setText("Author: " + item.getmAuthor());
             score.setText("Score: " + item.getmScore());
             typeScreen.setText(item.getTypeImage());

             String url = item.getmImageId();
             String typeImg = item.getTypeImage();

             // Verify if image should be displayed based on screen type and current screen setting
             boolean shouldShowImage = false;
             if (url != null && !url.isEmpty() && typeImg != null) {
                 String screenSetting = ScreenManager.verifyScreen();

                 // For Book type or Hardware thumbnail, always show
                 if (type.getText().toString().equals("Book") || typeImg.equals("Hardware thumbnail")) {
                     shouldShowImage = true;
                 }
                 // For Loading screens, show if screen setting is "loading" or not set to "running"
                 else if (typeImg.equals("Loading screen")) {
                     if (screenSetting == null || screenSetting.equals("loading") || !screenSetting.equals("running")) {
                         shouldShowImage = true;
                     }
                 }
                 // For Running screens, show only if screen setting is "running"
                 else if (typeImg.equals("Running screen")) {
                     if (screenSetting != null && screenSetting.equals("running")) {
                         shouldShowImage = true;
                     }
                 }
             }

             if (shouldShowImage) {
                 String fullUrl;
                 // Handle different URL formats (pub/sinclair vs zxdb/sinclair paths)
                 if (url.substring(0, Math.min(14, url.length())).equals("/pub/sinclair/")) {
                     fullUrl = url.replaceAll("/pub/sinclair/", ParameterClass.pubSinclair);
                 } else if (url.substring(0, Math.min(14, url.length())).equals("/zxdb/sinclair/")) {
                     fullUrl = url.replaceAll("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
                 } else {
                     fullUrl = "https://zxinfo.dk/media" + url;
                 }
                 Picasso.get().load(fullUrl).placeholder(R.drawable.no_photo).into(image);
                 image.setVisibility(View.VISIBLE);
                 typeScreen.setVisibility(View.VISIBLE);
             } else {
                 image.setImageResource(R.drawable.no_photo);
                 image.setVisibility(View.GONE);
                 typeScreen.setVisibility(View.GONE);
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
