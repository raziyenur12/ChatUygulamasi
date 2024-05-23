package com.example.chatuyg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button signup,login;
    EditText emailTv,passwordTv;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup=findViewById(R.id.loginacitivty_login);
        login=findViewById(R.id.loginactivity_signup);
        emailTv=findViewById(R.id.loginactivity_emailTv);
        passwordTv=findViewById(R.id.loginactivity_passwordTv);

        mAuth=FirebaseAuth.getInstance();

        login.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            finish();
        });

        login.setOnClickListener(view -> {
            String email=emailTv.getText().toString();
            String password=passwordTv.getText().toString();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Giriş başarılı", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this,"Giriş başarısız",Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}