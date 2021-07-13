package com.example.proyectopoo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Store extends User implements Serializable {
    private String email, fullname, type, id;

    public Store(String email, String fullname, String type) {
        this.email = email;
        this.fullname = fullname;
        this.type = type;
    }

    public Store(String email, String fullname, String type, String id) {
        this.email = email;
        this.fullname = fullname;
        this.type = type;
        this.id = id;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setProduct(Object product, String id, Context context) {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = firebaseFirestore
                .collection("Users")
                .document(id)
                .collection("Products")
                .document();

        Product finalproduct = (Product) product;

        getInfo(new FirestoreCallback() {
            @Override
            public void onCallback(List<String> list) {
                if(list.size() > 0){
                    Toast.makeText(context,"El producto ya existe",Toast.LENGTH_SHORT).show();
                }else{
                    HashMap<String, Object> productInfo = new HashMap<>();

                    productInfo.put("name",finalproduct.getName());
                    productInfo.put("description",finalproduct.getDescription());
                    productInfo.put("price",finalproduct.getPrice());
                    productInfo.put("stock",finalproduct.getStock());

                    documentReference
                            .set(productInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(context,"Registro de producto exitoso",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context,"Error en el registro del producto   ",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        },id,finalproduct.getName());

    }
    private void getInfo(FirestoreCallback firestoreCallback, String id, String productName){

        ArrayList<String> productList = new ArrayList<>();

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        CollectionReference collectionReference = firebaseFirestore
                .collection("Users")
                .document(id)
                .collection("Products");
        collectionReference
                .whereEqualTo("name",productName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot element: task.getResult()){
                    String product = element.getId();
                    productList.add(product);
                }
                firestoreCallback.onCallback(productList);
            }
        });
    }
}
interface FirestoreCallback{
    void onCallback(List<String> list);
}