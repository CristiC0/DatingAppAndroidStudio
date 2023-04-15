package com.example.datingapp.Match;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datingapp.R;

public class MatchesViewHolders extends RecyclerView.ViewHolder  {
    public TextView matchName;
    public ImageView matchImage;
    public ImageButton delete;
    public LinearLayout match;

    public MatchesViewHolders(@NonNull View itemView) {
        super(itemView);

        matchImage=(ImageView) itemView.findViewById(R.id.matchImage);
        matchName=(TextView)  itemView.findViewById(R.id.matchName);
        delete=(ImageButton) itemView.findViewById(R.id.delete);
        match=(LinearLayout) itemView.findViewById(R.id.match);
    }
}
