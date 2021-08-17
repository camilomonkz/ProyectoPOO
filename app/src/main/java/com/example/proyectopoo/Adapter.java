package com.example.proyectopoo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {
    private List<Product> arrayProducts;
    private List<Product> arrayAllProducts;
    private String type,activity;
    private Object object;
    private Client client;

    public Adapter(List<Product> arrayProducts,String type,Object object,String activity) {
        this.arrayProducts = arrayProducts;
        this.type = type;
        this.object = object;
        this.activity = activity;
        arrayAllProducts = new ArrayList<>(arrayProducts);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_product_view, viewGroup, false);
        return new ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product currentProduct = arrayProducts.get(position);
        holder.productName.setText(currentProduct.getName());
        holder.productDescription.setText(currentProduct.getDescription());
        holder.productPrice.setText("Precio: $" + String.valueOf(currentProduct.getPrice()));
        holder.productStock.setText("Unds: " + String.valueOf(currentProduct.getStock()));

    }

    @Override
    public int getItemCount() {
        return arrayProducts.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDescription, productPrice, productStock;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
                productName = itemView.findViewById(R.id.productNameEPV);
                productDescription = itemView.findViewById(R.id.productDescriptionEPV);
                productPrice = itemView.findViewById(R.id.productPriceEPV);
                productStock = itemView.findViewById(R.id.productStockEPV);
        }


    }


    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> filterlist = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filterlist.addAll(arrayAllProducts);
            }
            else{
                String stringQuery = constraint.toString().toLowerCase().trim();
                for(Product product :arrayProducts){
                    if(product.getName().toLowerCase().contains(stringQuery)){
                        filterlist.add(product);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterlist;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayProducts.clear();
            arrayProducts.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

}
