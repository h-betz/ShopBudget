package com.hbetz.shopbudget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button addBtn;
    private TableLayout listTable;
    private TableLayout cartTable;
    private ItemPresenter ip;

    private TextView totalValue;
    private TextView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View main = findViewById(R.id.totalTxt);
        View root = main.getRootView();
        root.setBackgroundColor(getResources().getColor(R.color.cork));

        this.totalValue = (TextView) findViewById(R.id.totalValue);
        this.edit = (TextView) findViewById(R.id.edit);

        if (this.ip == null) {
            this.ip = new ItemPresenter(this, this);
        }

        this.ip.initialize();

        if (Double.compare(this.ip.getTotalPrice(), 0.00) > 0) {
            this.totalValue.setText("$" + Double.toString(this.ip.getTotalPrice()));
        }

        this.addBtn = (Button) findViewById(R.id.addBtn);
        this.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip.addItemToList();
            }
        });

        this.listTable = (TableLayout) findViewById(R.id.list_table);
        this.cartTable = (TableLayout) findViewById(R.id.cart_table);
        loadTableData();

        this.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit.getText().toString().equals("Delete")) {
                    ip.enableEdit();
                    edit.setText("Done");
                } else {
                    ip.delete(listTable, cartTable);
                    edit.setText("Delete");
                }
            }
        });
    }

    /**
     * Adds row to list table
     *
     * @param row
     */
    public void appendListRow(TableRow row) {
        this.listTable.addView(row);
    }

    /**
     * Adds row to cart table
     *
     * @param row
     */
    public void appendCartRow(TableRow row) {
        this.cartTable.addView(row);
    }

    /**
     * Removes specified item from the specified table
     *
     * @param item
     * @param table
     */
    public void removeItem(Item item, String table) {
        if (table.equals("list")) {
            for (int i = 0; i < listTable.getChildCount(); i++) {
                TableRow row = (TableRow) listTable.getChildAt(i);
                ViewHolder holder = (ViewHolder) row.getTag();
                if (item.getId().equals(holder.item.getId())) {
                    listTable.removeView(row);
                    return;
                }
            }
        } else {
            for (int i = 0; i < cartTable.getChildCount(); i++) {
                TableRow row = (TableRow) cartTable.getChildAt(i);
                CartHolder holder = (CartHolder) row.getTag();
                if (item.getId().equals(holder.item.getId())) {
                    cartTable.removeView(row);
                }
            }
        }
    }

    /**
     * Remove items in deletedItems list from the tables
     *
     * @param deletedItems
     */
    public void removeRow(ArrayList<Item> deletedItems, String table) {
        if (table.equals("list")) {
            for (Item item : deletedItems) {
                for (int i = 0; i < listTable.getChildCount(); i++) {
                    TableRow row = (TableRow) listTable.getChildAt(i);
                    ViewHolder holder = (ViewHolder) row.getTag();
                    if (item.getId().equals(holder.item.getId())) {
                        listTable.removeView(row);
                    }
                }
            }
        } else {
            for (Item item : deletedItems) {
                for (int i = 0; i < cartTable.getChildCount(); i++) {
                    TableRow row = (TableRow) cartTable.getChildAt(i);
                    CartHolder holder = (CartHolder) row.getTag();
                    if (item.getId().equals(holder.item.getId())) {
                        cartTable.removeView(row);
                    }
                }
            }
        }
    }

    public void loadTableData() {
        this.ip.loadTableData();
    }

    /**
     * Update checkbox visibility of list items
     * @param listView
     */
    public void updateListUI(String listView) {
        if (listView.equals("lv")) {
            for (int i = 0; i < this.listTable.getChildCount(); i++) {
                TableRow row = (TableRow) this.listTable.getChildAt(i);
                if (row.findViewById(R.id.checkBox).getVisibility() == View.GONE) {
                    row.findViewById(R.id.plusSign).setVisibility(View.GONE);
                    row.findViewById(R.id.checkBox).setVisibility(View.VISIBLE);
                } else {
                    row.findViewById(R.id.checkBox).setVisibility(View.GONE);
                    row.findViewById(R.id.plusSign).setVisibility(View.VISIBLE);
                }
            }
        } else {
            for (int i = 0; i < this.cartTable.getChildCount(); i++) {
                TableRow row = (TableRow) this.cartTable.getChildAt(i);
                if (row.findViewById(R.id.deleteBox).getVisibility() == View.GONE) {
                    row.findViewById(R.id.deleteBox).setVisibility(View.VISIBLE);
                } else {
                    row.findViewById(R.id.deleteBox).setVisibility(View.GONE);
                }
            }
        }
    }

    public void updateTotal(Double total) {
        this.totalValue.setText("$" + Double.toString(total));
    }

    public void clearEntry() {
        EditText itemNameTxt = (EditText) findViewById(R.id.editItemName);
        EditText itemPriceTxt = (EditText) findViewById(R.id.editItemPrice);
        EditText itemQtyTxt = (EditText) findViewById(R.id.editItemQty);

        itemNameTxt.setText("");
        itemPriceTxt.setText("");
        itemQtyTxt.setText("");
        itemNameTxt.setHint("Item name");
        itemPriceTxt.setHint("Price");
        itemQtyTxt.setHint("Quantity");
    }

    public void handleChanges(View v) {
        this.ip.handleChanges(v);
    }

}
