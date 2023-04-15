package com.example.datingapp.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.datingapp.R;

public class ChooseLoginOrRegistration extends AppCompatActivity {

    private Button buttonLogin;
    private Button buttonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login_or_registration);

        buttonLogin=(Button) findViewById(R.id.login);
        buttonRegister=(Button) findViewById(R.id.register);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChooseLoginOrRegistration.this,LoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChooseLoginOrRegistration.this,RegistrationActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}