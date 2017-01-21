package com.hbetz.shopbudget;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hunter on 12/9/2016.
 */

public class Cart implements Serializable {

    private double totalPrice;
    private ArrayList<Item> items;
    private ArrayList<Item> cartItems;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<Item> cartItems) {
        this.cartItems = cartItems;
    }
}
