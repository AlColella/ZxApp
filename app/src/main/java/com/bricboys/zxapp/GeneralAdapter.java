package com.bricboys.zxapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GeneralAdapter extends ArrayAdapter<General> {
    public GeneralAdapter(@NonNull Context context, int resource, List<General> general) {
        super(context, resource, general);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.general_item, parent, false);
        }

        General currentGeneral = getItem(position);

        TextView title = listItemView.findViewById(R.id.title_name);
        title.setText(currentGeneral.getmTitle());

        TextView type = listItemView.findViewById(R.id.type);
        type.setText("Type: " + currentGeneral.getmType());

        TextView release = listItemView.findViewById(R.id.release);
        release.setText("Year of release: " + currentGeneral.getmRelease());

        TextView publisher = listItemView.findViewById(R.id.publisher);
        publisher.setText("Publisher: " + currentGeneral.getmPublisher());

        TextView avail = listItemView.findViewById(R.id.available);
        avail.setText("Availability: " + currentGeneral.getmAvailability());

        TextView machine = listItemView.findViewById(R.id.machine);
        machine.setText("Machine: " + currentGeneral.getmMachine());

        TextView score = listItemView.findViewById(R.id.score_text);
        score.setText("Score: " + currentGeneral.getmScore());

        TextView author = listItemView.findViewById(R.id.author_text);
        author.setText("Author(s): " + currentGeneral.getmAuthor());

//Log.e("image id: ", currentGeneral.getmImageId());
        TextView text = listItemView.findViewById(R.id.textScreen);
       // Log.e("Titulo: ", currentGeneral.getmTitle());
       // Log.e("Id img: ",currentGeneral.getmImageId());
        if(!currentGeneral.getmImageId().isEmpty()) {
            String url = currentGeneral.getmImageId();
            String typeImg = currentGeneral.getTypeImage();
            String newUrl = null;
           // Log.e("type: ", type.getText().toString());
           // Log.e("typeimg: ", typeImg);
            if (type.getText().toString().equals("Type: Book") ||
                    type.getText().toString().equals("Type: Hardware")) {
                text.setText("Image");
                if (url.substring(0, 14).equals("/pub/sinclair/")) {
                    newUrl = url.replaceAll("/pub/sinclair/", ParameterClass.pubSinclair);
                } else {
                    newUrl = url.replaceAll("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
                }

               //Log.e("URL da lista: ", newUrl);
                ImageView imageView = listItemView.findViewById(R.id.loadingScreen);
                Ion.with(imageView).load(newUrl).cancel();
                Ion.with(imageView).load(newUrl);
                imageView.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);
            } else {
                //if(type.getText().toString().equals("Type: Loading screen")) {
                Ion.getDefault(getContext()).getHttpClient().getSSLSocketMiddleware().setSpdyEnabled(false);
                if(typeImg.equals("Loading screen")) {
                    newUrl = "https://zxinfo.dk/media" + url;
                    text.setText("Loading Screen");
                    ImageView imageView = listItemView.findViewById(R.id.loadingScreen);
                    Ion.with(imageView).load(newUrl).cancel();
                    Ion.with(imageView).load(newUrl);
                    imageView.setVisibility(View.VISIBLE);
                    //Picasso.get().cancelRequest(imageView);
                    //Picasso.get().load(newUrl).into(imageView);
                    text.setVisibility(View.VISIBLE);
                } else {
                    if(typeImg.equals("Running screen")) {


                        //newUrl = "https://archive.zx-spectrum.org.uk/WoS" + url;
                        if (url.substring(0, 14).equals("/pub/sinclair/")) {
                            newUrl = url.replaceAll("/pub/sinclair/", ParameterClass.pubSinclair);
                        } else  if (url.substring(0, 14).equals("/zxdb/sinclair/")) {
                            newUrl = url.replaceAll("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
                        } else {
                            newUrl = "https://zxinfo.dk/media" + url;
                        }

                        //Log.e("Running", "url: " + newUrl);
                        text.setText("Running Screen");
                        ImageView imageView = listItemView.findViewById(R.id.loadingScreen);

                        //Picasso.get().cancelRequest(imageView);
                        //Picasso.get().load(newUrl).into(imageView);

                        Ion.with(imageView).load(newUrl).cancel();
                        Ion.with(imageView).load(newUrl);
                        Ion.with(getContext()).load(newUrl).intoImageView(imageView);
                        imageView.setVisibility(View.VISIBLE);
                        text.setVisibility(View.VISIBLE);
                    }
                }
                //} else {
                //    if(type.getText().toString().equals("Type: Running screen")) {
                //        newUrl = "https://archive.zx-spectrum.org.uk/WoS/" + url;
                //        text.setText("Running Screen");
                 //   }
            }
            //Log.e("Livro: ", newUrl);
            /*ImageView imageView = listItemView.findViewById(R.id.loadingScreen);
            imageView.setVisibility(View.VISIBLE);
            Picasso.get().cancelRequest(imageView);
            Picasso.get().load(newUrl).into(imageView);
            text.setVisibility(View.VISIBLE); */
        } else {
            ImageView imageView = listItemView.findViewById(R.id.loadingScreen);
            imageView.setVisibility(View.GONE);
            text.setVisibility(View.GONE);
        }

        if(currentGeneral.getmYoutubeLink().isEmpty()) {
            TextView youtube = listItemView.findViewById(R.id.video_link);
            youtube.setText("Video: No");
        } else {
            TextView youtube = listItemView.findViewById(R.id.video_link);
            youtube.setText("Video: Yes");
        }

        return listItemView;

    }
}
