package com.example.proyectopoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class loginStore extends AppCompatActivity    {
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

        Bundle takeObject = getIntent().getExtras();

        Store store = null;

        if(takeObject != null){
            store = (Store) takeObject.getSerializable("Store");
        }

        Store finalStore = store;

        Log.d("Data2",finalStore.getStoreName());

        if(finalStore.getStoreName().equals("null") || finalStore.getLocation().equals("null")  || finalStore.getPhoneNumber().equals("null")) {

            AlertDialog.Builder alert = new AlertDialog.Builder(loginStore.this);
            alert.setMessage("Debe completar la información del perfil")
                    .setCancelable(false)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), PorfileForm.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Store", finalStore);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    });
            alert.setTitle("Completar perfil");

            AlertDialog dialog = alert.create();

            dialog.show();
        }else{
            searchView = findViewById(R.id.search);
            singUpProductActivity = (ImageButton)findViewById(R.id.ShoppingCartBtn);
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
                            listProducts.add(new Product(
                                    String.valueOf(documentSnapshot.get("name")),
                                    String.valueOf(documentSnapshot.get("description")),
                                    Float.parseFloat(String.valueOf(documentSnapshot.get("price"))),
                                    Integer.parseInt(String.valueOf(documentSnapshot.get("stock"))),
                                    String.valueOf(documentSnapshot.get("storeName")),
                                    documentSnapshot.getId()));
                        }
                        adapter = new Adapter(listProducts,finalStore.getType(),finalStore,"loginStore");
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
    }

    public void Back(View view){
        Intent back = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(back);
        finish();
    }

}