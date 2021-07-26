package com.example.proyectopoo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


public class StandProduct extends FirestoreRecyclerAdapter< Product, StandProduct.ViewHolder> {

    Store store;

    public StandProduct(@NonNull FirestoreRecyclerOptions<Product> options, Store store) {
        super(options);
        this.store = store;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Product product) {

        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());

        final String productID = documentSnapshot.getId();

        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText(String.valueOf(product.getPrice()));
        holder.productStock.setText(String.valueOf(product.getStock()));

        Product finalProduct = new Product(product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                productID);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),EditProduct.class);
                Bundle bundel = new Bundle();

                bundel.putSerializable("Store",store);
                bundel.putSerializable("Product",finalProduct);

                intent.putExtras(bundel);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_product_view,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView productName, productDescription, productPrice, productStock;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            productName = itemView.findViewById(R.id.productNameEPV);
            productDescription = itemView.findViewById(R.id.productDescriptionEPV);
            productPrice = itemView.findViewById(R.id.productPriceEPV);
            productStock = itemView.findViewById(R.id.productStockEPV);
        }

    }
    interface RecyclerItemClick {
        void itemClick();
    }
}
