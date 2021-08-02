package com.example.proyectopoo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.firebase.firestore.DocumentSnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable  {
    List<Product> arrayProducts;
    List<Product> arrayAllProducts;

    public Adapter(List<Product> arrayProducts) {
        this.arrayProducts = arrayProducts;
        arrayAllProducts = new ArrayList<>(arrayProducts);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_product_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product currentProduct = arrayProducts.get(position);
        holder.productName.setText(currentProduct.getName());
        holder.productDescription.setText(currentProduct.getDescription());
        holder.productPrice.setText(String.valueOf(currentProduct.getPrice()));
        holder.productStock.setText(String.valueOf(currentProduct.getStock()));

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
