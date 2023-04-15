package com.example.datingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.datingapp.Match.Match;
import com.example.datingapp.auth.ChooseLoginOrRegistration;
import com.example.datingapp.auth.CurrentUser;
import com.example.datingapp.auth.Settings;
import com.example.datingapp.auth.User;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private SeeMatchesAdapter seeMatchesAdapter;
    List<Match> cards;
    Button goToMatches;
    private ImageView back;
    private ImageView settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentUser.getInstance().setUser(null);
                Intent intent=new Intent(MainActivity.this, ChooseLoginOrRegistration.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        settings=(ImageView)findViewById(R.id.setting);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Settings.class);
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
        cards= cards.stream().filter(c-> !databaseHelper.existsSwipe(new Swipe(CurrentUser.getInstance().getUser().getId(),c.getId()))).collect(Collectors.toList());
        Log.d("asd",cards.toString());
        databaseHelper.close();

        seeMatchesAdapter=new SeeMatchesAdapter(this,R.layout.item,cards);
        SwipeFlingAdapterView flingContainer=(SwipeFlingAdapterView)findViewById(R.id.frame);

        flingContainer.setAdapter(seeMatchesAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                cards.remove(0);
                seeMatchesAdapter.notifyDataSetChanged();
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
                seeMatchesAdapter.notifyDataSetChanged();
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
