package com.example.chatuyg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    Button signup,login;
    TextView emailTv,passwordTv,nameTv;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signup= findViewById(R.id.registeractivity_signup);
        login = findViewById(R.id.registeractivity_login);
        emailTv = findViewById(R.id.registeractivity_emailTv);
       passwordTv = findViewById(R.id.registeractivity_passwordTv);
nameTv=findViewById(R.id.registeractivity_nameTv);

      mAuth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });

        signup.setOnClickListener(view -> {
            String mail=emailTv.getText().toString();
            String password=passwordTv.getText().toString();

            if(password.length()<6){
                Toast.makeText(RegisterActivity.this,"Şifre en az 6 karakter olmalıdır.",Toast.LENGTH_SHORT).show();
                return;
            }

        mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Kayıt başarılı",Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Kayıt başarısız",Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}