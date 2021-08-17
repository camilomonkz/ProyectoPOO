package com.example.proyectopoo;

public class Product2 {
    private String name, description, id;
    private float price;
    private int amount;

    public Product2(String name, String description, String id, float price, int amount) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.price = price;
        this.amount = amount;
    }

    public Product2(String name, String description, String id, float price) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
