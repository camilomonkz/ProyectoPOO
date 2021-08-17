package com.example.proyectopoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends AppCompatActivity {
    private TextView Total;
    private Integer precio;
    private RecyclerView recyclerViewCar;
    private AdapterCart adapterCar;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private ImageButton backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        Client client = null;
        Bundle takeObject = getIntent().getExtras();

        if(takeObject != null){
            client = (Client) takeObject.getSerializable("Client");
        }

        Client finalClient = client;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerViewCar = findViewById(R.id.RecyclerViewCar);
        Total = (TextView) findViewById(R.id.total);
        precio = 0;

        //[INICIO inicializacion de variables ]

        Toast.makeText(getApplicationContext(),"Se inicio el carrito",Toast.LENGTH_SHORT).show();


        recyclerViewCar.setLayoutManager(new LinearLayoutManager(this));

        CollectionReference collectionReference = firebaseFirestore.collection("Users")
                                                                    .document(finalClient.getId())
                                                                    .collection("ShoppingCart");


        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Product> listProducts = new ArrayList<>();
                if(task.getResult() != null){
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        Product product = new Product(
                                String.valueOf(documentSnapshot.get("name")),
                                String.valueOf(documentSnapshot.get("description")),
                                Float.parseFloat(String.valueOf(documentSnapshot.get("totalPrice"))),
                                Integer.parseInt(String.valueOf(documentSnapshot.get("amountOfProducts"))),
                                String.valueOf(documentSnapshot.get("storeName")),
                                documentSnapshot.getId());
                        listProducts.add(product);
                        precio += product.getPrice();
                    }
                    Total.setText(String.valueOf(precio));
                    adapterCar = new AdapterCart(listProducts,finalClient,getApplicationContext());
                    recyclerViewCar.setAdapter(adapterCar);
                }

            }
        });

        backBtn = (ImageButton) findViewById(R.id.backBtnSC);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),loginClient.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Client",finalClient);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

    }
}