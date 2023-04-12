package com.example.datingapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MatchesViewHolders extends RecyclerView.ViewHolder  {
//    public TextView match;
    public TextView matchName;
    public ImageView matchImage;

    public MatchesViewHolders(@NonNull View itemView) {
        super(itemView);

//        match=(TextView) itemView.findViewById(R.id.matchId);
        matchImage=(ImageView) itemView.findViewById(R.id.matchImage);
        matchName=(TextView)  itemView.findViewById(R.id.matchName);
    }
}
