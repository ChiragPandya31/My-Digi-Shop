package com.example.e_commerce.model;

import com.hishd.tinycart.model.Item;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product  implements Item, Serializable  {
    private String name,image,status;
    private double price, dicount;
    private int stock,id;
    private int quantity;


    public String getName() {
        return name;
    }

    public Product(String name, String image, String status, double price, double dicount, int stock, int id) {
        this.name = name;
        this.image = image;
        this.status = status;
        this.price = price;
        this.dicount = dicount;
        this.stock = stock;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDicount() {
        return dicount;
    }

    public void setDicount(double dicount) {
        this.dicount = dicount;
    }

    public int getStock() {
        return stock;
    }


    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public BigDecimal getItemPrice() {
        return new BigDecimal(price);
    }

    @Override
    public String getItemName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
