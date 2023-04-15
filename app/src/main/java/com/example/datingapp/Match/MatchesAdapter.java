package com.example.datingapp.Match;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datingapp.DatabaseHelper;
import com.example.datingapp.R;
import com.example.datingapp.Swipe;
import com.example.datingapp.auth.CurrentUser;

import java.util.ArrayList;
import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesViewHolders> {
    private List<Match> matchesList;
    private Context context;

    public  MatchesAdapter(Context context){
        this.matchesList=new ArrayList<>();
        DatabaseHelper dh=new DatabaseHelper(context);
        List<Swipe> swipes=dh.getListOfSwipes(CurrentUser.getInstance().getUser().getId());
        for (Swipe s:swipes) {
            Match newMatch=dh.getMatch(s.getTo());
            if(newMatch!=null) this.matchesList.add(newMatch);
        }
        this.context=context;
    }

    @Override
    public MatchesViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match,null,false);
        RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(params);

        MatchesViewHolders rcv=new MatchesViewHolders((layoutView));
        return rcv;
    }

    @Override
    public void onBindViewHolder(MatchesViewHolders holder, int position) {
        holder.matchName.setText(matchesList.get(position).getName());
        Glide.with( holder.matchName.getContext()).load(matchesList.get(position).getImage()).into(holder.matchImage);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dh=new DatabaseHelper(context);
                dh.deleteSwipe(matchesList.get(position).getId());
                ViewGroup.LayoutParams params= holder.match.getLayoutParams();
                params.height=0;
                holder.match.setLayoutParams(params);
            }
        });
    }

    @Override
    public int getItemCount() {
        return matchesList.size();
    }
}
