package com.example.proyectopoo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;

public class ProgressBarLogin extends Activity {

    /**
     Este Activity es una pantalla de carga para determinar el tipo de usuario (cliente o tienda)
     El usuario ya inicio seción
     */


    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar_login);
        //Inicializacion de las variables de firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        //se captura el id del usuario
        String userID = user.getUid();
        //se accede al documento donde se encuentran los datos del usuario
        DocumentReference documentReference = firebaseFirestore
                .collection("Users")
                .document(userID);


        //Esto hace que el codigo se ejecute en un tiempo determinado
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, FirebaseFirestoreException error) {
                                //Se captura el tipo de usuario, y se determina si es un cliente o una tienda
                                if(value.getString("typeOfUser").equals("tienda")){
                                    //se crea un objeto tienda con los datos de usuario
                                    Store store = new Store(user.getEmail(),
                                            value.getString("fullname"),
                                            value.getString("typeOfUser"),
                                            userID);
                                    //Se crea un Intent para iniciar un nuevo Activity y se envia el objeto tienda
                                    Intent intent = new Intent(getApplicationContext(),loginStore.class);
                                    Bundle bundle = new Bundle();

                                    bundle.putSerializable("Store",store);

                                    intent.putExtras(bundle);

                                    startActivity(intent);
                                    finish();
                                }else{
                                    //se crea un objeto cliente con los datos de usuario
                                    Client client = new Client(user.getEmail(),
                                            value.getString("fullname"),
                                            value.getString("typeOfUser"),
                                            userID);
                                    //Se crea un Intent para iniciar un nuevo Activity y se envia el objeto cliente
                                    Intent intent = new Intent(getApplicationContext(),loginClient.class);
                                    Bundle bundle = new Bundle();

                                    bundle.putSerializable("Client",client);

                                    intent.putExtras(bundle);

                                    startActivity(intent);
                                    finish();
                                }
                                Toast.makeText(getApplicationContext(),"Inicio de sesión exitoso",Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        }, 1500);

    }
}