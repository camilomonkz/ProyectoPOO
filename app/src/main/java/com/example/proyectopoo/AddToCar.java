package com.example.proyectopoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddToCar extends AppCompatActivity {

    private TextView ProductName,ProductDescription,PriceAll,ProductAmount;
    private Button ButtonAgregar,ButtonBack,ButtonAdd,ButtonLess;
    private int price,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_car);

        Product product = null;
        Client client = null;
        Bundle takeObject = getIntent().getExtras();

        if(takeObject != null) {
            client = (Client) takeObject.getSerializable("Client");
            product = (Product) takeObject.getSerializable("Product");
        }

        Product finalproduct = product;
        Client finalClient = client;

        //Unir las variables de la clase con la interfaz
        ProductName = (TextView) findViewById(R.id.productNameAddCar);
        ProductDescription = (TextView) findViewById(R.id.productDescriptionAddCar);
        PriceAll = (TextView) findViewById(R.id.priceAllAddCar);
        ProductAmount = (TextView) findViewById(R.id.productAmountAddCar);
        ButtonAdd = (Button) findViewById(R.id.buttonAddAddCar);
        ButtonLess = (Button) findViewById(R.id.buttonLessAddCar);
        amount = 1;
        price = (int)(amount *  finalproduct.getPrice());


        //Agregar nombre y descripcion a la interfaz
        ProductName.setText(finalproduct.getName(),TextView.BufferType.EDITABLE);
        ProductDescription.setText(finalproduct.getDescription(),TextView.BufferType.EDITABLE);
        ProductAmount.setText(String.valueOf(amount),TextView.BufferType.EDITABLE);
        PriceAll.setText("Total: $"+String.valueOf(price),TextView.BufferType.EDITABLE);


        ButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount < finalproduct.getStock()){
                    amount++;
                    price = (int) (amount * finalproduct.getPrice());
                    ProductAmount.setText(String.valueOf(amount), TextView.BufferType.EDITABLE);
                    PriceAll.setText("Precio: $" + String.valueOf(price), TextView.BufferType.EDITABLE);
                }else{
                    Toast.makeText(getApplicationContext(),String.valueOf(amount)+" es el número máximo de productos",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ButtonLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount > 0 ) {
                    amount--;
                    price = (int) (amount * finalproduct.getPrice());
                    ProductAmount.setText(String.valueOf(amount), TextView.BufferType.EDITABLE);
                    PriceAll.setText("Precio: $" + String.valueOf(price), TextView.BufferType.EDITABLE);
                }else{
                    Toast.makeText(getApplicationContext(),String.valueOf(amount)+" es el número mínimo de productos",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ButtonAgregar = (Button)findViewById(R.id.buttonAgregarAddCar);

        ButtonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product productCart = new Product(
                        finalproduct.getName(),
                        finalproduct.getDescription(),
                        price,
                        amount,
                        finalproduct.getStoreName(),
                        finalproduct.getId()
                );
                finalClient.addProdutcCart(productCart,getApplicationContext());
                /*
                Intent intent = new Intent(getApplicationContext(),loginClient.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Client",finalClient);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();*/
            }
        });

        ButtonBack = (Button) findViewById(R.id.buttonBackAddCar);

        ButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),loginClient.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
