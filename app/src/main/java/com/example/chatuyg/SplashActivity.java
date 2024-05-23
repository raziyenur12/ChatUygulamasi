package com.example.chatuyg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    Button login,signUp;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        login=findViewById(R.id.splashscreen_loginbutton);
        signUp=findViewById(R.id.splashscreen_signupbutton);
        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            Toast.makeText(SplashActivity.this,"Yönlendiriliyorsunuz",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
finish();
        }
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this,RegisterActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            }
        });


    }
}