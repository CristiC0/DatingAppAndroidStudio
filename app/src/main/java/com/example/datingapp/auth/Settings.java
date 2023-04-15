package com.example.datingapp.auth;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.datingapp.DatabaseHelper;
import com.example.datingapp.MainActivity;
import com.example.datingapp.R;

public class Settings extends AppCompatActivity {

    private Button submit;
    private EditText username;
    private EditText password;
    private RadioGroup gender;
    private RadioButton selectedRadio;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Settings.this,MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        gender=(RadioGroup) findViewById(R.id.gender);

        User currentUser=CurrentUser.getInstance().getUser();
        username.setText(currentUser.getUsername());
        password.setText(currentUser.getPassword());

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedRadio=(RadioButton) findViewById(checkedId);
            }
        });

        if(currentUser.getGender().equals("Male")) {
            ((RadioButton) findViewById(R.id.m)).toggle();
        } else ((RadioButton)findViewById(R.id.f)).toggle();

        submit=(Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              DatabaseHelper dh=new DatabaseHelper(Settings.this);
              User newUser=new User(username.getText().toString(),password.getText().toString(),selectedRadio.getText().toString());
              dh.updateUser(newUser);
              CurrentUser.getInstance().setUser(newUser);
                Intent intent=new Intent(Settings.this,MainActivity.class);
                startActivity(intent);
                finish();
                return;
        }});
    };
}