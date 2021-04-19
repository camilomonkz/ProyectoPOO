package com.example.proyectopoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private DBAdmin DBUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText)findViewById(R.id.usernamein);
        etPassword = (EditText)findViewById(R.id.passwordin);
        DBUsers = new DBAdmin(this);
    }

    public void Back(View view){
        Intent back = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(back);
        finish();
    }

    //Metodo para iniciar sesión
    public void Login(View view){
        //se guradan los valores en cadenas de texto
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        //se verifica si todos los campos tienen un valor
        if(!username.isEmpty() && !password.isEmpty()){

            //se verifica si existe el nombre de usuario
            boolean checkUser = DBUsers.CheckUser(username);
            if(checkUser == false){

                // si el usuario y contraseña coinciden se guarda el tipo de usuario en checkUserPass
                //si no coinciden checkUserPass guarda una cadena vacia
                String checkUserPass = DBUsers.CheckUserPass(username, password);

                //se verifica el tipo de usuario para enviarlo a su respectiva Activity
                if(checkUserPass.equals("cliente")){
                    Toast.makeText(getApplicationContext(),"¡Inicio de sesión exitoso!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),loginClient.class);
                    startActivity(intent);
                    finish();
                }else if(checkUserPass.equals("tienda")){
                    Toast.makeText(getApplicationContext(),"¡Inicio de sesión exitoso!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),loginStore.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"¡La contraseña es incorrecta!",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"El usuario no existe",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Rellene todos los campos",Toast.LENGTH_SHORT).show();
        }

    }
}