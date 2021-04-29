package com.example.proyectopoo;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {
    //[INICIO declaracion de variables]
    private EditText etEmail, etPassword;
    //[FIN declaracion de variables]
    //[INICIO declaracion de variable firebase]
    private FirebaseAuth firebaseAuth;
    //[FIN declaracion de variable firebase]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //[INICIO inicializacion de variables]
        etEmail = (EditText)findViewById(R.id.emailin);
        etPassword = (EditText)findViewById(R.id.passwordin);
        firebaseAuth = FirebaseAuth.getInstance();
        //[FIN inicializacion de variables]

    }

    public void Back(View view){
        Intent back = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(back);
        finish();
    }

    //Metodo para iniciar sesión
    public void Login(View view){
        //se guradan los valores en cadenas de texto
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        //se verifica que todos los campos tengan valor diferente a vacio
        if(!email.isEmpty() && !password.isEmpty()) {
            //Metodo firebase verifica que el email exista y que coincida con la contraseña
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Intent signIn = new Intent(getApplicationContext(),loginClient.class);
                        startActivity(signIn);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Usuairo o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }else{
            if(email.isEmpty()){
                etEmail.setError("Falta el correo electronico");
            }else if(password.isEmpty()){
                etPassword.setError("Falta la contraseña");
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            firebaseAuth.signOut();
        }
    }
}