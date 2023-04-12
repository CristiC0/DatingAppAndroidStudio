package com.example.datingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListAdapter listAdapter;
    List<Match> cards;
    Button goToMatches;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentUser.getInstance().setUser(null);
                Intent intent=new Intent(MainActivity.this,ChooseLoginOrRegistration.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        goToMatches=(Button) findViewById(R.id.goToMatches);

        goToMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SeeMatches.class);
                startActivity(intent);
                return;
            }
        });

        DatabaseHelper databaseHelper=new DatabaseHelper(MainActivity.this);

        String searchedGenre=CurrentUser.getInstance().getUser().getGender().equals("Male")?
                "Female":"Male";
        cards=databaseHelper.getListOfMatches(searchedGenre);
        databaseHelper.close();

        listAdapter=new ListAdapter(this,R.layout.item,cards);
        SwipeFlingAdapterView flingContainer=(SwipeFlingAdapterView)findViewById(R.id.frame);

        flingContainer.setAdapter(listAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                cards.remove(0);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                databaseHelper.createSwipe( new Swipe(CurrentUser.getInstance().getUser().getId(),((Match)dataObject).getId()));
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                cards.add(new Match("No matches left! ","https://st3.depositphotos.com/5266903/15718/v/450/depositphotos_157186616-stock-illustration-unknown-person-flat-vector-icon.jpg",null) );
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });


        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
            }
        });


    }

}
