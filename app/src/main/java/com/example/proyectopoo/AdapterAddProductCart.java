package com.example.proyectopoo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterAddProductCart extends RecyclerView.Adapter<AdapterAddProductCart.ViewHolder> implements Filterable {
    private List<Product> productList;
    private List<Product> allProducts;
    private  Client client;
    private Context context;
    private int amount = 1;

    public AdapterAddProductCart(List<Product> productList, Client client, Context context) {
        this.productList = productList;
        this.client = client;
        this.context = context;
        allProducts = new ArrayList<>(productList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_product_shoppingcart_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product currentProduct = productList.get(position);
        holder.productName.setText(currentProduct.getName());
        holder.productDescription.setText(currentProduct.getDescription());
        holder.amountText.setText(String.valueOf(amount));
        holder.totalPrice.setText("Total: $"+String.valueOf(amount * currentProduct.getPrice()));

        holder.addAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount < currentProduct.getStock()) {
                    amount++;
                    holder.amountText.setText(String.valueOf(amount));
                    holder.totalPrice.setText("Total: $"+String.valueOf(amount * currentProduct.getPrice()));
                }else{
                    Toast.makeText(context,String.valueOf(amount)+" es el número máximo de productos",Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.lessAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount > 0) {
                    amount--;
                    holder.amountText.setText(String.valueOf(amount));
                    holder.totalPrice.setText(String.valueOf("Total: $"+amount * currentProduct.getPrice()));
                }else{
                    Toast.makeText(context,String.valueOf(amount)+" es el número mínimo de productos",Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount != 0) {
                    client.addProdutcCart(currentProduct, context);
                }else{
                    Toast.makeText(context,"No se pueden agregar 0 productos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    @Override
    public Filter getFilter() {
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button addAmount, lessAmount, addProduct;
        TextView productName,productDescription,totalPrice,amountText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameAddCar);
            productDescription = itemView.findViewById(R.id.productDescriptionAddCar);
            totalPrice = itemView.findViewById(R.id.priceAllAddCar);
            addAmount = itemView.findViewById(R.id.buttonAddAddCar);
            lessAmount = itemView.findViewById(R.id.buttonLessAddCar);
            addProduct = itemView.findViewById(R.id.addProductAddCart);
            amountText = itemView.findViewById(R.id.productAmountAddCar);
        }
    }
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> filterlist = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filterlist.addAll(productList);
            }
            else{
                String stringQuery = constraint.toString().toLowerCase().trim();
                for(Product product :productList){
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
            productList.clear();
            productList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
