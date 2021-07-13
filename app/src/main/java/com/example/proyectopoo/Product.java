package com.example.proyectopoo;

import java.io.Serializable;
import java.util.HashMap;

public class Product implements Serializable {
    private String name, description;
    private float price;
    private int stock;

    public Product(){
    }

    public Product(String name, String description,float price, int stock){
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }


    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public float getPrice(){
        return price;
    }

    public int getStock(){

        return stock;
    }

    public void setName(String name){

        this.name = name;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setPrice(float price){
        this.price = price;
    }
    public void setStock(int stock){
        this.stock = stock;
    }
}
