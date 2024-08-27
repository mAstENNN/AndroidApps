package com.example.project2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context mContext;
    private int mResource;
    public MovieAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Movie> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource,parent,false);

        ImageView imageView = convertView.findViewById(R.id.image);
        TextView title = convertView.findViewById(R.id.title);
        TextView actors = convertView.findViewById(R.id.actors);

        imageView.setImageResource(getItem(position).getThumbnail());
        title.setText(getItem(position).getTitle());
        actors.setText(getItem(position).getActors());

        return convertView;
    }
}
