package com.example.datingapp.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.datingapp.DatabaseHelper;
import com.example.datingapp.MainActivity;
import com.example.datingapp.R;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private EditText username;
    private EditText password;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ChooseLoginOrRegistration.class);
                startActivity(intent);
                finish();
                return;
            }
        });


        login=(Button) findViewById(R.id.login);
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sUsername=username.getText().toString();
                final String sPassword=password.getText().toString();

                DatabaseHelper hp=new DatabaseHelper(LoginActivity.this);
                boolean existsUser=hp.existsUser(new User(sUsername,sPassword));
                if(existsUser) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                } else{
                    Toast.makeText(LoginActivity.this, "No such user in DB", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}