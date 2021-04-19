package com.example.proyectopoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void SignUpA(View view){
        Intent singUp = new Intent(getApplicationContext(),Signup.class);
        startActivity(singUp);
        finish();
    }
    public void LogInA(View view){
        Intent logIn = new Intent(getApplicationContext(),Login.class);
        startActivity(logIn);
        finish();
    }

}