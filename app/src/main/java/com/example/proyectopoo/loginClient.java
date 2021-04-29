package com.example.proyectopoo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class loginClient extends AppCompatActivity {
    //[INICIO declaracion de variables]
    private TextView tvFullnameUser, tvTypeofuserInfo;
    //[FIN declaracion de variables]
    //[INICIO declaracion de variables firebase]
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    //[FIN declaracion de variables]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_client);
        //[INICIO inicializacion de variables]
        tvFullnameUser = (TextView)findViewById(R.id.userfullname);
        tvTypeofuserInfo = (TextView)findViewById(R.id.typeofuserinfo);
        //[FIN inicializacion de variables]
        //[INICIO inicializacion de variables firebase]
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //[INICIO inicializacion de variables firebase]

        //se inicializa el usuario
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //se captura el id del usuario
        String userID = user.getUid();
        //se accede a la lista
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(userID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                //se recuperan los datos de la lista y se muestran en pantalla
                tvFullnameUser.setText(documentSnapshot.getString("nombre completo"));
                tvTypeofuserInfo.setText(documentSnapshot.getString("tipo de cliente"));
            }
        });


    }
    public void Back(View view){
        firebaseAuth.signOut();
        Intent back = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(back);
        finish();
    }

}