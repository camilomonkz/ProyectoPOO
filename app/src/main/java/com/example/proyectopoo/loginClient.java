package com.example.proyectopoo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class loginClient extends AppCompatActivity {
    //[INICIO declaracion de variables]
    private TextView tvFullnameUser, tvTypeofuserInfo;
    private RecyclerView recyclerViewProducts;
    private Adapter adapter;
    private SearchView searchView;
    //[FIN declaracion de variables]
    //[INICIO declaracion de variables firebase]
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;  
    //[FIN declaracion de variables]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_client);
        //[INICIO inicializacion de variables firebase]
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //[INICIO inicializacion de variables firebase]

        //se inicializa el usuario
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //se captura el id del usuario

        searchView = findViewById(R.id.search);

        recyclerViewProducts = findViewById(R.id.rv);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();

        CollectionReference collectionReference = firebaseFirestore.collection("AllProducts");

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Product> listProducts = new ArrayList<>();
                if(task.getResult() != null){
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        listProducts.add(documentSnapshot.toObject(Product.class));
                    }
                    adapter = new Adapter(listProducts);
                    recyclerViewProducts.setAdapter(adapter);
                }

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    public void Back(View view){
        firebaseAuth.signOut();
        Intent back = new Intent(getApplicationContext(),Login.class);
        startActivity(back);
        finish();
    }

}