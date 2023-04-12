package com.example.datingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ListAdapter extends android.widget.ArrayAdapter<Match> {
    public ListAdapter(Context context, int resource, List<Match> matches) {
        super(context, resource,matches);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Match match=getItem(position);
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item,parent,false);
        }
        TextView name=(TextView) convertView.findViewById(R.id.name);
        ImageView image=(ImageView) convertView.findViewById(R.id.image);

        name.setText(match.getName());
        Glide.with(getContext()).load(match.getImage()).into(image);

        return convertView;
    }
}
