package com.example.proyectopoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class AddProduct extends AppCompatActivity {

    private EditText etNameProduct, etDescriptionProduct, etPriceProduct, etStockProduct;
    private Button signUpProductBtn, verBtn,backBtn;
    private TextView provisional;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        etNameProduct = (EditText) findViewById(R.id.productName);
        etDescriptionProduct = (EditText) findViewById(R.id.productDescription);
        etPriceProduct = (EditText) findViewById(R.id.productPrice);
        etStockProduct = (EditText) findViewById(R.id.productStock);
        signUpProductBtn = (Button) findViewById(R.id.signUpProduct);
        provisional = (TextView) findViewById(R.id.textViewprovisional);
        verBtn = (Button) findViewById(R.id.button10);
        backBtn = (Button) findViewById(R.id.backBtnAddP);

        Bundle takeObject = getIntent().getExtras();

        Store store = null;

        if(takeObject != null){
            store = (Store) takeObject.getSerializable("Store");
        }


        Store finalStore = store;


        signUpProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = etNameProduct.getText().toString();
                String productDescription = etDescriptionProduct.getText().toString();
                String productPrice = etPriceProduct.getText().toString();
                String productStock = etStockProduct.getText().toString();

                String userID = finalStore.getId();

                if(!productName.isEmpty()
                        && !productDescription.isEmpty()
                        && !productPrice.isEmpty()
                        && !productStock.isEmpty()){

                    Product product = new Product(productName,
                            productDescription,
                            Float.parseFloat(productPrice),
                            Integer.parseInt(productStock));

                    finalStore.setProduct(product, userID, getApplicationContext());
                } else {
                    if(productName.isEmpty()){
                        etNameProduct.setError("Nombre del producto no especificado");
                    }else if (productDescription.isEmpty()){
                        etDescriptionProduct.setError("Descripción del producto no especificado");
                    }else if (productPrice.isEmpty()){
                        etPriceProduct.setError("Precio del producto no especificado");
                    }else if (productStock.isEmpty()) {
                        etStockProduct.setError("Existencias del producto no especificado");
                    }

                }
            }
        });

        verBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ProductViewStrore.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("Store",finalStore);
                intent.putExtras(bundle);

                startActivity(intent);
                finish();
            }
        });
    }


}