package com.example.proyectopoo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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

    private RecyclerView recyclerViewProducts;
    private AdapterAddProductCart adapter;
    private SearchView searchView;
    private FirebaseFirestore firebaseFirestore;
    private ImageButton cartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_client);

        Client client = null;

        Bundle takeObject = getIntent().getExtras();

        if(takeObject != null){
            client = (Client)takeObject.getSerializable("Client");
        }

        Client finalClient = client;

        firebaseFirestore = FirebaseFirestore.getInstance();

        searchView = findViewById(R.id.search);

        recyclerViewProducts = findViewById(R.id.rv);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));

        CollectionReference collectionReference = firebaseFirestore.collection("AllProducts");

        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Product> listProducts = new ArrayList<>();
                if(task.getResult() != null){
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        listProducts.add(new Product(
                                String.valueOf(documentSnapshot.get("name")),
                                String.valueOf(documentSnapshot.get("description")),
                                Float.parseFloat(String.valueOf(documentSnapshot.get("price"))),
                                Integer.parseInt(String.valueOf(documentSnapshot.get("stock"))),
                                String.valueOf(documentSnapshot.get("storeName")),
                                documentSnapshot.getId()));
                    }
                    adapter = new AdapterAddProductCart(listProducts,finalClient,getApplicationContext());
                    recyclerViewProducts.setAdapter(adapter);
                    Log.d("Adapter", String.valueOf(adapter));
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

        cartBtn = (ImageButton) findViewById(R.id.ShoppingCartBtn);

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ShoppingCart.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Client",finalClient);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }


    public void Back(View view){
        Intent back = new Intent(getApplicationContext(),Login.class);
        startActivity(back);
        finish();
    }

}