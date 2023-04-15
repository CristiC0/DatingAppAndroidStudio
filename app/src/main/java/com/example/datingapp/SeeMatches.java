package com.example.datingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.datingapp.Match.MatchesAdapter;

public class SeeMatches extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter matchesAdapter;
    private  RecyclerView.LayoutManager layoutManager;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_matches);

        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SeeMatches.this,MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });


        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(SeeMatches.this);
        recyclerView.setLayoutManager(layoutManager);
        matchesAdapter=new MatchesAdapter(SeeMatches.this);
        recyclerView.setAdapter(matchesAdapter);


    }
}