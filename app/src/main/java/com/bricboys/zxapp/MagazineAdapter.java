package com.bricboys.zxapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.List;

public class MagazineAdapter extends ArrayAdapter<Magazine> {

    private String pubSinclair = "https://archive.org/download/World_of_Spectrum_June_2017_Mirror/World%20of%20Spectrum%20June%202017%20Mirror.zip/World%20of%20Spectrum%20June%202017%20Mirror/sinclair/";
    private String zxdbSinclair = "https://spectrumcomputing.co.uk/zxdb/sinclair/";
    private Context context;

    public MagazineAdapter(@NonNull Context context, int resource, List<Magazine> magazines) {
        super(context, resource, magazines);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.magazine_item, parent, false);
        }

        Magazine currentMagazine = getItem(position);

        TextView titView = listItemView.findViewById(R.id.magazine_name);
        titView.setText(currentMagazine.getMagazineTitle());

        TextView issueView = listItemView.findViewById(R.id.magazine_issue);
        issueView.setText("Issue: " + currentMagazine.getMagazineIssue());

        TextView yearView = listItemView.findViewById(R.id.magazine_year);
        yearView.setText("Year: " + currentMagazine.getMagazineYear());

        if(!currentMagazine.getMagazineImage().isEmpty()) {
            String url = currentMagazine.getMagazineImage();
            String newUrl = null;
            if (url.substring(0,14).equals("/pub/sinclair/")) {
                newUrl = url.replaceAll("/pub/sinclair/", pubSinclair);
            } else {
                newUrl = url.replaceAll("/zxdb/sinclair/", zxdbSinclair);
            }


            ImageView coverView = listItemView.findViewById(R.id.magazine_image);
            //ImageView coverView = (ImageView) convertView;
            /*if(coverView == null) {
                convertView = new ImageView(context);
            }*/
            Picasso.get().cancelRequest(coverView);
            Picasso.get().load(newUrl).into(coverView);
        } else {
            ImageView coverView = listItemView.findViewById(R.id.magazine_image);
            Picasso.get().cancelRequest(coverView);
            Picasso.get().load((String)null).error(R.drawable.no_photo).into(coverView);
        }
        return listItemView;
    }
}
