package com.example.proyectopoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class PorfileForm extends AppCompatActivity {

    private EditText etStoreName, etLocation, etPhoneNumber;
    private Button aceptBtn;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porfile_form);

        Bundle takeObject = getIntent().getExtras();

        Store store = null;

        if(takeObject != null){
            store = (Store) takeObject.getSerializable("Store");
        }

        Store finalStore = store;

        firebaseFirestore = FirebaseFirestore.getInstance();

        etStoreName = (EditText) findViewById(R.id.etStoreNameP);
        etLocation = (EditText) findViewById(R.id.etLocationP);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneP);

        aceptBtn = (Button) findViewById(R.id.aceptBtnP);

        aceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storeName  = etStoreName.getText().toString().trim();
                String location = etLocation.getText().toString().trim();
                String phoneNumber = etPhoneNumber.getText().toString().trim();

                if(!storeName.isEmpty() && !location.isEmpty() && !phoneNumber.isEmpty()){
                    DocumentReference documentReference = firebaseFirestore
                            .collection("Users")
                            .document(finalStore.getId());
                    HashMap<String,Object> userInfo = new HashMap<>();
                    userInfo.put("storeName",storeName);
                    userInfo.put("phoneNumber",phoneNumber);
                    userInfo.put("location",location);

                    documentReference
                            .update(userInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(),"Perfil actualizado correctamente",Toast.LENGTH_SHORT).show();

                                    Store actualStore = new Store(finalStore.getEmail(),
                                            finalStore.getFullname(),
                                            finalStore.getType(),
                                            finalStore.getId(),
                                            storeName,
                                            phoneNumber,
                                            location);
                                    Intent intent = new Intent(getApplicationContext(),loginStore.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("Store",actualStore);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Error en la actualización del perfil",Toast.LENGTH_SHORT).show();
                            }
                            });

                }else{

                }
            }
        });


    }
}