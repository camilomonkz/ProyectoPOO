package com.example.proyectopoo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class StandProduct extends FirestoreRecyclerAdapter< Product, StandProduct.ViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public StandProduct(@NonNull FirestoreRecyclerOptions<Product> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Product model) {
        holder.productName.setText(model.getName());
        holder.productDescription.setText(model.getDescription());
        holder.productPrice.setText(String.valueOf(model.getPrice()));
        holder.productStock.setText(String.valueOf(model.getStock()));
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
}
