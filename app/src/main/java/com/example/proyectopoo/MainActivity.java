package com.example.proyectopoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
    }
    public void SignUpA(View view){
        //Se instancia un Inten para inciar una nueva Activity
        Intent singUp = new Intent(getApplicationContext(),Signup.class);
        startActivity(singUp);
        finish();
    }
    public void LogInA(View view){
        //Se instancia un Inten para inciar una nueva Activity
        Intent logIn = new Intent(getApplicationContext(),Login.class);
        startActivity(logIn);
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser!= null){
            firebaseAuth.signOut();
        }
    }
}