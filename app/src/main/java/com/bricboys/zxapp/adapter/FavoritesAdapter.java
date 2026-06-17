package com.bricboys.zxapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bricboys.zxapp.R;
import com.bricboys.zxapp.data.Favorite;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private List<Favorite> items = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Favorite item);
    }

    public FavoritesAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<Favorite> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favorite item = items.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, type;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.favorite_image);
            title = itemView.findViewById(R.id.favorite_title);
            type = itemView.findViewById(R.id.favorite_type);
        }

        void bind(Favorite item, OnItemClickListener listener) {
            title.setText(item.title);
            type.setText(item.type);

            if (item.imageUrl != null && !item.imageUrl.isEmpty()) {
                Picasso.get().load(item.imageUrl).placeholder(R.drawable.no_photo).into(image);
            } else {
                image.setImageResource(R.drawable.no_photo);
            }

            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}
