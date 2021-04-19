package com.example.proyectopoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class loginStore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_store);
    }
    public void Back(View view){
        Intent back = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(back);
        finish();
    }
}