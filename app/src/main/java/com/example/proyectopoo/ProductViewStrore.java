package com.example.proyectopoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ProductViewStrore extends AppCompatActivity {

    private FloatingActionButton addProductBtn, backBtn;
    private RecyclerView recyclerViewProducts;
    private StandProduct standProduct;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view_strore);

        Bundle takeObject = getIntent().getExtras();

        Store store = null;

        if(takeObject != null){
            store = (Store) takeObject.getSerializable("Store");
        }


        Store finalStore = store;

        recyclerViewProducts = findViewById(R.id.recyclerStore);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore
                .collection("Users")
                .document(finalStore.getId())
                .collection("Products");

        FirestoreRecyclerOptions<Product> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query,Product.class).build();


        standProduct = new StandProduct(firestoreRecyclerOptions,finalStore);
        standProduct.notifyDataSetChanged();

        recyclerViewProducts.setAdapter(standProduct);

        addProductBtn = (FloatingActionButton) findViewById(R.id.addProductButton);
        backBtn = (FloatingActionButton) findViewById(R.id.backBtnMyProducts);

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddProduct.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("Store",finalStore);
                intent.putExtras(bundle);

                startActivity(intent);
                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),loginStore.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("Store",finalStore);
                intent.putExtras(bundle);

                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        standProduct.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        standProduct.stopListening();
    }
}