package com.example.proyectopoo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditProduct extends AppCompatActivity {

    private EditText etproductName,etproductDescription,etproductPrice,etproductStock;
    private Button editProductBtn,deleteProductBtn,backBtn;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        Bundle takeObject = getIntent().getExtras();

        Store store = null;
        Product product = null;

        if(takeObject != null){
            store = (Store) takeObject.getSerializable("Store");
            product = (Product) takeObject.getSerializable("Product");
        }

        Store finalStore = store;
        Product finalProduct = product;

        etproductName = (EditText)findViewById(R.id.productNameEditP);
        etproductDescription = (EditText)findViewById(R.id.productDescriptionEditP);
        etproductPrice = (EditText)findViewById(R.id.productPriceEditP);
        etproductStock = (EditText)findViewById(R.id.productStockEditP);

        etproductName.setText(finalProduct.getName(),TextView.BufferType.EDITABLE);
        etproductDescription.setText(finalProduct.getDescription(),TextView.BufferType.EDITABLE);
        etproductPrice.setText(String.valueOf(finalProduct.getPrice()),TextView.BufferType.EDITABLE);
        etproductStock.setText(String.valueOf(finalProduct.getStock()),TextView.BufferType.EDITABLE);

        editProductBtn = (Button) findViewById(R.id.editBtnEdidtP);

        Log.d("id",finalProduct.getId());

        editProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = etproductName.getText().toString();
                String productDescription = etproductDescription.getText().toString();
                String productPrice = etproductPrice.getText().toString();
                String productStock = etproductStock.getText().toString();

                if(!productName.isEmpty()
                        && !productDescription.isEmpty()
                        && !productPrice.isEmpty()
                        && !productStock.isEmpty()){

                    Product productEdit = new Product(productName,
                            productDescription,
                            Float.parseFloat(productPrice),
                            Integer.parseInt(productStock),
                            finalStore.getStoreName(),
                            finalProduct.getId());


                    finalStore.editProductInfo(productEdit,finalStore.getId(),EditProduct.this);

                    Intent intent = new Intent(getApplicationContext(),ProductViewStrore.class);
                    Bundle bundle = new Bundle();

                    bundle.putSerializable("Store",finalStore);
                    intent.putExtras(bundle);

                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(),"maraca",Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteProductBtn = (Button)findViewById(R.id.deleteBtnEditP);

        deleteProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDelete = new AlertDialog.Builder(EditProduct.this );

                alertDelete.setMessage("Desea eliminar el producto "+finalProduct.getName())
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finalStore.deleteProduct(finalProduct,getApplicationContext());

                                Intent intent = new Intent(getApplicationContext(),ProductViewStrore.class);
                                Bundle bundle = new Bundle();

                                bundle.putSerializable("Store",finalStore);
                                intent.putExtras(bundle);

                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDelete.setTitle("Eliminaci??n de producto");

                AlertDialog dialog = alertDelete.create();

                dialog.show();

            }
        });


        backBtn = (Button)findViewById(R.id.backBtnEditP);

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