package com.bricboys.zxapp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ZxartAdapter extends ArrayAdapter<ZxArt> {

    //private String pubSinclair = "https://archive.org/download/World_of_Spectrum_June_2017_Mirror/World%20of%20Spectrum%20June%202017%20Mirror.zip/World%20of%20Spectrum%20June%202017%20Mirror/sinclair/";
   // private String zxdbSinclair = "https://spectrumcomputing.co.uk/zxdb/sinclair/";
    private Context context;

    public ZxartAdapter(@NonNull Context context, int resource, List<ZxArt> zxarts) {
        super(context, resource, zxarts);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.zxart_item, parent, false);
        }

        ZxArt currentZxart = getItem(position);

        TextView titView = listItemView.findViewById(R.id.zxart_title);
        titView.setText(currentZxart.getmTitle());

        TextView ratingView = listItemView.findViewById(R.id.zxart_rating);
        ratingView.setText("Rating: " + currentZxart.getmRating());

        TextView yearView = listItemView.findViewById(R.id.zxart_year);
        yearView.setText("Year: " + currentZxart.getmYear());

        if(!currentZxart.getmPictureImage().isEmpty()) {
            String url = currentZxart.getmPictureImage();
            String newUrl = url;

            ImageView coverView = listItemView.findViewById(R.id.zxart_image);

            Picasso.get().cancelRequest(coverView);
            Picasso.get().load(newUrl).into(coverView);
        } else {
            ImageView coverView = listItemView.findViewById(R.id.zxart_image);
            Picasso.get().cancelRequest(coverView);
            Picasso.get().load((String)null).error(R.drawable.no_photo).into(coverView);
        }
        return listItemView;
    }
}
