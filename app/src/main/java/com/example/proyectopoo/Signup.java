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
    //Metodo para registrarse
    public void SignUp(View view){
        //los datos se almacenas en cadenas de texto
        String username = etUsername.getText().toString();
        String fullname = etFullname.getText().toString();
        String password = etPassword.getText().toString();
        String rePassword = etRepassword.getText().toString();

        //se verifica si todos los campos tienen un valor
        if(!username.isEmpty() && !password.isEmpty() && !fullname.isEmpty() && !rePassword.isEmpty() && (btClient.isChecked() || btStore.isChecked())){
            if(password.equals(rePassword)){
                //se verifica que el usuario no este en la tabla
                boolean checkUser = DBUsers.CheckUser(username);
                    if(checkUser == true){
                        //se verifica que tipo de usuario se registra
                        String typeofuser = "";
                        if(btClient.isChecked()){
                            typeofuser = "cliente";
                        }else if(btStore.isChecked()){
                            typeofuser = "tienda";
                        }
                        //se verifica si la base de datos guardo correctamente los datos
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
            Toast.makeText(getApplicationContext(),"Rellene todos los campos",Toast.LENGTH_SHORT).show();
        }
    }
    public void Back(View view){
        Intent back = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(back);
        finish();
    }
}