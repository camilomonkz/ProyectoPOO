package com.example.proyectopoo;

import java.io.Serializable;

public class Product implements Serializable {
    private String name, description, id, storeName;
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

    public Product(String name, String description,float price, int stock,String storeName,String id){
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.id = id;
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int getPrice(){
        return (int) price;
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
