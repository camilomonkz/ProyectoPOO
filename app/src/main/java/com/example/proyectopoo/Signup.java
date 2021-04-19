package com.example.proyectopoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class Signup extends AppCompatActivity {

    private EditText etUsername, etFullname, etPassword, etRepassword;
    private RadioButton btClient, btStore;
    private DBAdmin DBUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = (EditText)findViewById(R.id.usernameup);
        etFullname = (EditText)findViewById(R.id.fullnamup);
        etPassword = (EditText)findViewById(R.id.passwordup);
        etRepassword = (EditText)findViewById(R.id.repassword);
        btClient = (RadioButton)findViewById(R.id.client);
        btStore = (RadioButton)findViewById(R.id.store);
        DBUsers = new DBAdmin(this);
    }

    public void SignUp(View view){
        String username = etUsername.getText().toString();
        String fullname = etFullname.getText().toString();
        String password = etPassword.getText().toString();
        String rePassword = etRepassword.getText().toString();

        if(!username.isEmpty() && !password.isEmpty() && !fullname.isEmpty() && !rePassword.isEmpty() && (btClient.isChecked() || btStore.isChecked())){
            if(password.equals(rePassword)){
                boolean checkUser = DBUsers.CheckUser(username);
                    if(checkUser == true){
                        String typeofuser = "";
                        if(btClient.isChecked()){
                            typeofuser = "cliente";
                        }else if(btStore.isChecked()){
                            typeofuser = "tienda";
                        }
                        boolean singup = DBUsers.SingUp(username,password,fullname,typeofuser);
                        if(singup == true){
                            Toast.makeText(getApplicationContext(),"¡Registro exitoso!",Toast.LENGTH_SHORT).show();
                            Intent login = new Intent(getApplicationContext(),Login.class);
                            startActivity(login);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"El registro fallo",Toast.LENGTH_SHORT).show();
                        }
;                }else {
                        Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show();
                    }
            }else{
                Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Rellene todos los campos",Toast.LENGTH_LONG).show();
        }
    }
    public void Back(View view){
        Intent back = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(back);
        finish();
    }
}