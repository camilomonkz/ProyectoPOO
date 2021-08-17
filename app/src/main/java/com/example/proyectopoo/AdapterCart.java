package com.example.proyectopoo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {
    private List<Product> arrayProducts;
    private Client client;
    private Context context;

    public AdapterCart(List<Product> arrayProducts, Client client, Context context) {
        this.arrayProducts = arrayProducts;
        this.client = client;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_shopping_view, viewGroup, false);
        return new AdapterCart.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product currentProduct = arrayProducts.get(position);
        holder.productName.setText(currentProduct.getName());
        holder.productDescription.setText(currentProduct.getDescription());
        holder.productPrice.setText("Precio: $" + String.valueOf(currentProduct.getPrice()* currentProduct.getStock()));
        holder.productStock.setText("Unds: " + String.valueOf(currentProduct.getStock()));
        holder.productStoreName.setText(currentProduct.getStoreName());

        if(arrayProducts.size() != 0){
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    client.deletProductCart(currentProduct,context);
                    arrayProducts.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDescription, productPrice, productStock,productStoreName;
        FloatingActionButton deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameS);
            productDescription = itemView.findViewById(R.id.productDescriptionS);
            productPrice = itemView.findViewById(R.id.totalPriceS);
            productStock = itemView.findViewById(R.id.productNumberS);
            productStoreName = itemView.findViewById(R.id.productStoreNameS);
            deleteBtn = itemView.findViewById(R.id.deleteBtnCart);
        }
    }
}
