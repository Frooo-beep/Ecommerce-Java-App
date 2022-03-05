package com.frostdev.ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.frostdev.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText username, email, password;
    private FirebaseAuth auth;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        //getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null){
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }

        username = findViewById(R.id.username_regist);
        email = findViewById(R.id.email_regist);
        password = findViewById(R.id.pw_regist);

        preferences = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);

        boolean isFirstTime = preferences.getBoolean("firstTime", true);

        if (isFirstTime){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();

            Intent intent = new Intent(RegisterActivity.this, OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void signup(View view){

        String userName = username.getText().toString();
        String userEmail = email.getText().toString();
        String userPassw = password.getText().toString();

        if (TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Enter your name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Entter your email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPassw)){
            Toast.makeText(this, "Set your password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassw.length() < 8){
            Toast.makeText(this, "At least there are 8 character in your password", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail, userPassw)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener< AuthResult >() {
                    @Override
                    public void onComplete(@NonNull Task< AuthResult > task) {

                        if (task.isSuccessful()){
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            Toast.makeText(RegisterActivity.this, "Registration success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signin(View view){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}