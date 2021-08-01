package com.example.proyectopoo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class loginStore extends AppCompatActivity {
    private ImageButton singUpProductActivity;
    private RecyclerView recyclerViewProducts;
    private Adapter adapter;
    private FirebaseFirestore firebaseFirestore;
    private SearchView searchView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_store);

        searchView = findViewById(R.id.search);

        Bundle takeObject = getIntent().getExtras();

        Store store = null;

        if(takeObject != null){
            store = (Store) takeObject.getSerializable("Store");
        }
        singUpProductActivity = (ImageButton)findViewById(R.id.signUpProductActivity);

        Store finalStore = store;

        recyclerViewProducts = findViewById(R.id.rv);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore
                .collection("AllProducts");

        FirestoreRecyclerOptions<Product> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query,Product.class).build();


        adapter = new Adapter(firestoreRecyclerOptions);
        adapter.notifyDataSetChanged();

        recyclerViewProducts.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscar(newText);
                return true;
            }
        });

        singUpProductActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ProductViewStrore.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("Store", finalStore);
                intent.putExtras(bundle);

                startActivity(intent);
                finish();
            }
        });

    }

    private void buscar(String newText) {

        Query busqueda = firebaseFirestore
                .collection("AllProducts")
                .whereEqualTo("name",newText);

        FirestoreRecyclerOptions<Product> result = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(busqueda,Product.class).build();

        adapter = new Adapter(result);

        recyclerViewProducts.setAdapter(adapter);
    }

    public void Back(View view){
        Intent back = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(back);
        finish();
    }
}