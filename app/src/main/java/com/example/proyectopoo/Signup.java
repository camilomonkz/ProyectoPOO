package com.example.proyectopoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Signup extends AppCompatActivity {
    //[INICIO declaracion de variables]
    private EditText etEmail, etFullname, etPassword, etRepassword;
    private RadioButton btClient, btStore;
    //[FIN declaracion de variables]
    //[INICIO declaracion de variable firebase]
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    //[FIN declaracion de variable firebase]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //[INICIO inicializacion de variables]
        etEmail = (EditText) findViewById(R.id.emailup);
        etFullname = (EditText) findViewById(R.id.fullnamup);
        etPassword = (EditText) findViewById(R.id.passwordup);
        etRepassword = (EditText) findViewById(R.id.repassword);
        btClient = (RadioButton) findViewById(R.id.client);
        btStore = (RadioButton) findViewById(R.id.store);
        //[FIN inicializacion de variables]
        //[INICIO inicializacion de variable firebase]
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //[INICIO inicializacion de variable firebase]
    }

    //Metodo para registrarse
    public void SignUp(View view) {
        //los datos se almacenas en cadenas de texto
        String email = etEmail.getText().toString().trim();
        String fullname = etFullname.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String rePassword = etRepassword.getText().toString().trim();

        final String[] typeOfUser = {""};
        //se verifica que todos los campos tengan valor diferente a vacio
        if(!email.isEmpty() && !fullname.isEmpty() && !password.isEmpty() && !rePassword.isEmpty() && (btStore.isChecked() || btClient.isChecked())){

            //se verifica que la contraseña tenga más de 6 caracteres y que coincidan las contraseñas
            if((password.length()> 6) && rePassword.equals(password)) {

                firebaseAuth
                        .createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //inicializa el usuario
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //toma el id de usuario
                            String userID = user.getUid();
                            //crea una lista para guardar los datos de usuaro (nombre, tipo de cliente)
                            //se guarda en un documento que relaciona el id con los datos
                            DocumentReference documentReference = firebaseFirestore
                                    .collection("Users")
                                    .document(userID);
                            //Guardar los datos en un map
                            Map<String,Object> dataUser = new HashMap<>();

                            if(btClient.isChecked()){
                                typeOfUser[0] = "cliente";
                            }else if(btStore.isChecked()){
                                typeOfUser[0] = "tienda";
                            }

                            dataUser.put("fullname",fullname);
                            dataUser.put("typeOfUser",typeOfUser[0]);
                            //se envia el map con los datos de usuario para que se guarden
                            documentReference.set(dataUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid){
                                    Toast.makeText(getApplicationContext(), "¡Registro exitoso!", Toast.LENGTH_SHORT).show();
                                    Intent logIn = new Intent(getApplicationContext(), Login.class);
                                    startActivity(logIn);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error en el registro", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else{
                            Toast.makeText(getApplicationContext(), "Error en el registro", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }else{
                if(password.length() <= 6){
                    etPassword.setError("La contraseña debe tener más de 6 caracteres");
                }else if(!password.equals(rePassword)){
                    etPassword.setError("La contraseñas no coinciden");
                }
            }
        }else{
            if(email.isEmpty()){
                etEmail.setError("Correo de electronico obligatorio");
            }else if(fullname.isEmpty()){
                etFullname.setError("Nombre obligatorio");
            }else if(password.isEmpty()){
                etPassword.setError("Contraseña Obligatoria");
            }else if(rePassword.isEmpty()){
                etRepassword.setError("Repita la contraseña");
            }else if(!btClient.isChecked() && !btStore.isChecked()){
                Toast.makeText(getApplicationContext(),"Seleccione el tipo de usuario",Toast.LENGTH_SHORT).show();
            }
        }

    }
    public void Back(View view){
        Intent back = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(back);
        finish();
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser!= null){
            firebaseAuth.signOut();
        }
    }
}