package com.example.proyectopoo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class FragmentAddCart extends Fragment {

    private Button backBtn , aceptBtn,addBtn, lessBtn;


    public FragmentAddCart() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Client client = null;
        Product product = null;

        if (getArguments() != null) {
            client = (Client) getArguments().getSerializable("Client");
            product = (Product) getArguments().getSerializable("Product");
        }
        Client finalClient = client;
        Product finalProduct = product;
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_add_cart, container, false);

        //Toast.makeText(getContext(), finalClient.getFullname(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), finalProduct.getName(), Toast.LENGTH_SHORT).show();

        backBtn = root.findViewById(R.id.buttonBackAddCar);
        aceptBtn = root.findViewById(R.id.buttonAgregarAddCar);
        addBtn = root.findViewById(R.id.buttonAddAddCar);
        lessBtn = root.findViewById(R.id.buttonLessAddCar);




        return root;
    }
}