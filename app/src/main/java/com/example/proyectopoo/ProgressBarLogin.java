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

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        //se captura el id del usuario
        String userID = user.getUid();
        //se accede a la lista
        DocumentReference documentReference = firebaseFirestore
                .collection("Users")
                .document(userID);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                documentReference.addSnapshotListener( new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, FirebaseFirestoreException error) {
                        if(value.getString("typeOfUser").equals("tienda")){
                            Store store = new Store(user.getEmail(),
                                    value.getString("fullname"),
                                    value.getString("typeOfUser"),
                                    userID);

                            Intent intent = new Intent(getApplicationContext(),loginStore.class);
                            Bundle bundle = new Bundle();

                            bundle.putSerializable("Store",store);

                            intent.putExtras(bundle);

                            startActivity(intent);
                            finish();
                        }else{
                            Client client = new Client(user.getEmail(),
                                    value.getString("fullname"),
                                    value.getString("typeOfUser"),
                                    userID);

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
