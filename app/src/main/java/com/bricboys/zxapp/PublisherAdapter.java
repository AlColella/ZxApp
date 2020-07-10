package com.bricboys.zxapp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class PublisherAdapter extends ArrayAdapter<Publisher> {
    public PublisherAdapter(@NonNull Context context, int resource, List<Publisher> publisher) {
        super(context, resource, publisher);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
