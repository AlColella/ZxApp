package com.bricboys.zxapp.details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bricboys.zxapp.ParameterClass;
import com.bricboys.zxapp.R;

import org.w3c.dom.Text;

import java.util.List;

public class TapeAdapter extends ArrayAdapter<Tapes> {
    public TapeAdapter(@NonNull Context context, int resource, List<Tapes> tape) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.tape_item, parent, false);
        }

        Tapes currentTape = getItem(position);

        if (!currentTape.getmUrl().isEmpty()) {

            TextView type = listItemView.findViewById(R.id.type);
            type.setText("Type: " + currentTape.getmType());

            TextView format = listItemView.findViewById(R.id.format);
            format.setText("Format: " + currentTape.getmFormat());

            if (!currentTape.getmSize().isEmpty()) {
                TextView size = listItemView.findViewById(R.id.size);
                size.setText("Size: " + currentTape.getmSize() + " bytes");
            } else {
                TextView size = listItemView.findViewById(R.id.size);
                size.setVisibility(View.GONE);
            }

            //<editor-fold desc="Description">
           /* String Url = currentTape.getmUrl();
            String newUrl = "";
            if (Url.substring(0, 14).equals("/pub/sinclair/")) {
                newUrl = Url.replaceAll("/pub/sinclair/", ParameterClass.pubSinclair);
            } else {
                newUrl = Url.replaceAll("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
            } */
            //</editor-fold>

            //TextView url = listItemView.findViewById(R.id.url);
            //url.setText("URL: " + newUrl);

            TextView publisher = listItemView.findViewById(R.id.publisher);
            publisher.setText("Publisher: " + currentTape.getmPublisher());

            TextView origin = listItemView.findViewById(R.id.origin);
            origin.setText("Origin: " + currentTape.getmOrigin());

            TextView encoding = listItemView.findViewById(R.id.encoding);
            encoding.setText("Encoding: " + currentTape.getmEncoding());

        }

        return listItemView;
    }
}
