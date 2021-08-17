package com.example.proyectopoo;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;

public class Client extends User implements Serializable {

    private String id,email,fullname,type;
    private FirebaseFirestore firebaseFirestore;

    public Client(String email,String fullname,String type,String id){
        this.email = email;
        this.fullname = fullname;
        this.type = type;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addProdutcCart(Product product, Context context){
        firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = firebaseFirestore
                .collection("Users")
                .document(id)
                .collection("ShoppingCart")
                .document();

        HashMap<String,Object> productInfo = new HashMap<>();
        productInfo.put("name",product.getName());
        productInfo.put("description",product.getDescription());
        productInfo.put("storeName",product.getStoreName());
        productInfo.put("totalPrice",product.getPrice());
        productInfo.put("amountOfProducts",product.getStock());

        documentReference
                .set(productInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText( context, "Producto agregado al carrito", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText( context, "Error al agregar al carrito", Toast.LENGTH_SHORT).show();
                    }
                });
        firebaseFirestore.terminate();
    }

    public void deletProductCart(Product product, Context context){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = firebaseFirestore
                .collection("Users")
                .document(id)
                .collection("ShoppingCart")
                .document(product.getId());

        documentReference
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context,"El producto se eliminó exitosamente",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Error en la eliminación del producto",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
