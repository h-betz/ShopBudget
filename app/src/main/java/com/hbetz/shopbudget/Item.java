package com.hbetz.shopbudget;

import java.io.Serializable;

/**
 * Created by Hunter on 11/23/2016.
 */

public class Item implements Serializable {

    private double price;
    private boolean editable;
    private double quantity;
    private String name;
    private String id;

    public Item() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
