package com.hbetz.shopbudget;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Hunter on 11/30/2016.
 */

public class ItemPresenter implements Serializable {

    public static final String PREFERENCES = "Preferences";
    public static final String LIST = "List";
    public static final String CART = "Cart";
    public static final String TOTAL = "Total";

    private Context context;
    private MainActivity view;
    private double totalPrice;
    private ArrayList<Item> items;
    private ArrayList<Item> cartItems;

    private SharedPreferences sharedpreferences;

    public ItemPresenter(Context context, MainActivity v) {
        this.context = context;
        this.view = v;
    }

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

    public SharedPreferences getSharedpreferences() {
        return sharedpreferences;
    }

    public void setSharedpreferences(SharedPreferences sharedpreferences) {
        this.sharedpreferences = sharedpreferences;
    }

    public void initialize() {
        loadData();
    }

    /**
     * After a user adds a new item, if they provided both a price and quantity, add it to their shopping cart.
     * Otherwise, add it to their shopping list
     */
    public void addItemToList() {

        EditText itemNameTxt = (EditText) this.view.findViewById(R.id.editItemName);
        EditText itemPriceTxt = (EditText) this.view.findViewById(R.id.editItemPrice);
        EditText itemQtyTxt = (EditText) this.view.findViewById(R.id.editItemQty);

        if (itemNameTxt.getText().toString().equals("")) {
            return;
        }
        Item item = new Item();
        //item.setQuantity(0.00);
        //item.setPrice(0.00);
        item.setEditable(false);
        item.setName(itemNameTxt.getText().toString());
        item.setId(item.getName() + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));

        if (!itemPriceTxt.getText().toString().matches("") && !itemQtyTxt.getText().toString().matches("")) {
            //Price and quantity are set, user has bought the item
            Log.d("TAG", "Price and Qty not empy");
            item.setPrice(Double.parseDouble(itemPriceTxt.getText().toString()));
            item.setQuantity(Double.parseDouble(itemQtyTxt.getText().toString()));
            this.cartItems.add(item);
            this.totalPrice += (item.getPrice() * item.getQuantity());
            this.totalPrice = Utilities.round(this.totalPrice, 2);
            appendCartRow(item);
            this.view.updateTotal(this.totalPrice);
        } else {
            String pri = "";
            String qty = "";
            if (!itemPriceTxt.getText().toString().equals("")) {
                //Only price is set
                pri = itemPriceTxt.getText().toString();
                item.setPrice(Double.parseDouble(pri));
                //item.setQuantity(0.00);
            } else if (!itemQtyTxt.getText().toString().equals("")) {
                //Only quantity set
                qty = itemQtyTxt.getText().toString();
                item.setQuantity(Integer.parseInt(qty));
                //item.setPrice(0.00);
            } else {
                //neither is set
                //item.setQuantity(0.00);
                //item.setPrice(0.00);
            }
            items.add(item);
            appendListRow(item, pri, qty);
        }

        this.view.clearEntry();
        saveData();

    }

    /**
     * Adds item to shopping list
     *
     * @param item
     * @param price
     * @param quantity
     */
    public void appendListRow(Item item, String price, String quantity) {
        ListRow lr = new ListRow(this.context);
        TableRow row = lr.constructView(item, price, quantity);
        this.view.appendListRow(row);
    }

    /**
     * Adds item to shopping cart
     *
     * @param item
     */
    public void appendCartRow(Item item) {
        CartRow cr  = new CartRow(this.context);
        TableRow row = cr.constructView(item);
        this.view.appendCartRow(row);
    }

    /**
     * Populates tables with item data
     */
    public void loadTableData() {
        for (Item item : this.items) {
            ListRow lr = new ListRow(this.context);
            String price = "";
            String quantity = "";
            if (Double.toString(item.getPrice()).equals("0.0")) {
                price = "";
            }
            if (Double.toString(item.getQuantity()).equals("0.0")) {
                quantity = "";
            }
            TableRow row = lr.constructView(item, price, quantity);
            this.view.appendListRow(row);
        }
        for (Item item : this.cartItems) {
            CartRow cr = new CartRow(this.context);
            TableRow row = cr.constructView(item);
            this.view.appendCartRow(row);
        }
    }

    /**
     * Handle user changes to item on their shopping list. Check for incorrect input, update item
     * add item to shopping cart, show error, etc.
     *
     * @param v
     */
    public void handleChanges(View v) {
        ViewHolder holder = (ViewHolder) v.getTag();
        EditText priceEdit = holder.priceEdit;
        EditText qtyEdit = holder.qtyEdit;
        Item item = holder.item;
        Log.d("Tag", "Inside handle changes");
        //Price and quantity are not empty
        if (!priceEdit.getText().toString().equals("") && !qtyEdit.getText().toString().equals("")) {
            Log.d("tag", "both have text");
            double oldPrice = item.getPrice();
            double oldQty = item.getQuantity();
            double newPrice = Double.parseDouble(priceEdit.getText().toString());
            double newQty = Double.parseDouble(qtyEdit.getText().toString());

            //Compare previous price with new price
            if (Double.compare(oldPrice, newPrice) < 0) {
                //Price has increased
                item.setPrice(newPrice);
            } else if (Double.compare(oldPrice, newPrice) > 0) {
                //Price has decreased
                item.setPrice(newPrice);
            }

            //Compare previous quantity with new one
            if (oldQty < newQty) {
                //Quantity has increased
                item.setQuantity(newQty);
            } else if (oldQty > newQty) {
                //Quantity has decreased
                item.setQuantity(newQty);
            }
            updateTotal(oldPrice, oldQty, item);
            updateCart(item);
        } else if (!priceEdit.getText().toString().equals("")) {
            //Only price has text
            double newPrice = Double.parseDouble(priceEdit.getText().toString());
            if (item.getQuantity() > 0) {
                //User reset quantity
                item.setQuantity(0);
            }
            item.setPrice(newPrice);
            updateList(item);
        } else if (!qtyEdit.getText().toString().equals("")) {
            //Only quantity has text
            int newQty = Integer.parseInt(qtyEdit.getText().toString());
            if (Double.compare(item.getPrice(), 0.00) > 0) {
                //User has removed the price
                item.setPrice(0.00);
            }
            item.setQuantity(newQty);
            updateList(item);
        } else {
            //Neither has text
            double oldPrice = item.getPrice();
            double oldQty = item.getQuantity();
            if (oldQty > 0) {
                item.setQuantity(0);
            }
            if (Double.compare(oldPrice, 0.00) > 0) {
                item.setPrice(0.00);
            }
            updateList(item);
        }
        saveData();
    }

    /**
     * Update the shopping cart total to reflect price or quantity changes
     *
     * @param oldPrice
     * @param oldQty
     * @param item
     */
    private void updateTotal(double oldPrice, double oldQty, Item item) {
        double oldTotal  = oldPrice * oldQty;
        oldTotal = Utilities.round(oldTotal, 2);
        this.totalPrice -= oldTotal;
        double itemTotal = item.getPrice() * item.getQuantity();
        itemTotal = Utilities.round(itemTotal, 2);
        this.totalPrice += itemTotal;
        this.totalPrice = Utilities.round(this.totalPrice, 2);
        if (this.totalPrice < 0) {
            this.totalPrice = 0.00;
        }
        this.view.updateTotal(this.totalPrice);
    }

    /**
     * Update item in the list
     *
     * @param item
     */
    private void updateList(Item item) {
        int index = 0;
        for (Item i : this.items) {
            if (i.getId().equals(item.getId())) {
                this.items.set(index, item);
                //this.view.updateListUI("lv");
                return;
            }
            index++;
        }
    }

    /**
     * Update shopping cart and shopping list according to reflect the changes. Update the UI to reflect
     * these changes as well
     *
     * @param item
     */
    private void updateCart(Item item) {
        this.items.remove(item);
        this.view.removeItem(item, "list");
        this.cartItems.add(item);
        appendCartRow(item);
    }

    /**
     * Enable checkbox so user can delete whatever items they wish
     */
    public void enableEdit() {
        if (this.items.size() > 0) {
            this.view.updateListUI("lv");
        }
        if (this.cartItems.size() > 0) {
            this.view.updateListUI("cart");
        }
    }

    /**
     * For the items deleted, subtract their cost from the total
     *
     * @param item
     */
    private void removeFromTotal(Item item) {
        double amount = item.getPrice() * item.getQuantity();
        amount = Utilities.round(amount, 2);
        this.totalPrice -= amount;
        this.totalPrice = Utilities.round(this.totalPrice, 2);
        if (Double.compare(this.totalPrice, 0) < 0) {
            this.totalPrice = 0.00;
        }
    }

    /**
     * Removes selected items from list table or cart table
     */
    public void delete(TableLayout listTable, TableLayout cartTable) {
        int i;
        ArrayList<Item> deleteItems = new ArrayList<>();
        TableRow row;
        for (i = 0; i < listTable.getChildCount(); i++) {
            row = (TableRow) listTable.getChildAt(i);
            ViewHolder holder = (ViewHolder) row.getTag();
            if (holder.box.isChecked()) {
                deleteItems.add(holder.item);
            }
            holder.box.setChecked(false);
        }
        for (Item item : deleteItems) {
            this.items.remove(item);
        }
        this.view.removeRow(deleteItems, "list");
        this.view.updateListUI("lv");
        deleteItems = new ArrayList<>();
        for (i = 0; i < cartTable.getChildCount(); i++) {
            row = (TableRow) cartTable.getChildAt(i);
            CartHolder holder = (CartHolder) row.getTag();
            if (holder.box.isChecked()) {
                deleteItems.add(holder.item);
            }
            holder.box.setChecked(false);
        }
        for (Item item : deleteItems) {
            this.cartItems.remove(item);
            removeFromTotal(item);
        }
        this.view.updateTotal(this.totalPrice);
        this.view.removeRow(deleteItems, "cart");
        this.view.updateListUI("cart");
        saveData();
    }

    /**
     * Save data to shared preferences
     */
    private void saveData() {
        SharedPreferences.Editor editor = this.sharedpreferences.edit();
        try {
            editor.putString(LIST, ObjectSerializer.serialize(this.getItems()));
            editor.putString(CART, ObjectSerializer.serialize(this.getCartItems()));
            editor.putLong(TOTAL, Double.doubleToRawLongBits(this.getTotalPrice()));
            editor.commit();
        } catch (IOException e) {

        }
    }

    /**
     * Load data from shared preferences
     */
    private void loadData() {
        this.sharedpreferences = this.view.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        try {
            this.items = (ArrayList<Item>) ObjectSerializer.deserialize(sharedpreferences.getString(LIST, ObjectSerializer.serialize(new ArrayList<Item>())));
            this.cartItems = (ArrayList<Item>) ObjectSerializer.deserialize(sharedpreferences.getString(CART, ObjectSerializer.serialize(new ArrayList<Item>())));
            this.totalPrice = Utilities.round(Double.longBitsToDouble(sharedpreferences.getLong(TOTAL, Double.doubleToLongBits(0.00))), 2);
            if (Double.compare(this.totalPrice, 0) < 0) {
                this.totalPrice = 0.00;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
