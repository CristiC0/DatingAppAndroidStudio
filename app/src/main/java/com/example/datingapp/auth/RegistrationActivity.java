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

public class RegistrationActivity extends AppCompatActivity {

    private Button register;
    private EditText username;
    private EditText password;
    private RadioGroup gender;
    private RadioButton rb;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegistrationActivity.this,ChooseLoginOrRegistration.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        register=(Button) findViewById(R.id.register);
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        gender=(RadioGroup) findViewById(R.id.gender);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb.getText()==null){
                    Toast.makeText(RegistrationActivity.this, "Select your gender", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String sGender=rb.getText().toString();
                final String sUsername=username.getText().toString();
                final String sPassword=password.getText().toString();

                DatabaseHelper hp=new DatabaseHelper(RegistrationActivity.this);
                boolean succes=hp.createUser(new User(sUsername,sPassword,sGender));
                hp.existsUser(new User(sUsername,sPassword,sGender));

                if(succes) {
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                } else{
                    Toast.makeText(RegistrationActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb=(RadioButton) findViewById(checkedId);
            }
        });


    }
}