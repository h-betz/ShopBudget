package com.hbetz.shopbudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Hunter on 12/20/2016.
 */

public class CartRow {

    Context context;

    public CartRow(Context context) {
        this.context = context;
    }

    public TableRow constructView(Item item) {
        CartHolder holder = new CartHolder();
        holder.item = item;
        TableRow row = (TableRow) LayoutInflater.from(this.context).inflate(R.layout.completed_item_row, null);

        holder.box = (CheckBox) row.findViewById(R.id.deleteBox);
        holder.nameTxt = (TextView) row.findViewById(R.id.itemNameCompleted);
        holder.priceTxt = (TextView) row.findViewById(R.id.itemPriceCompleted);
        holder.qtyText = (TextView) row.findViewById(R.id.itemQtyCompleted);

        holder.nameTxt.setText(item.getName());
        if (Double.compare(item.getPrice(), 0.00) > 0 || Double.compare(item.getPrice(), 0.00) == 0) {
            //Price is set
            holder.priceTxt.setText("$ " + Double.toString(item.getPrice()));
        }
        if (Double.compare(item.getQuantity(), 0.00) > 0 || Double.compare(item.getQuantity(), 0.00) == 0) {
            //Quantity is set
            holder.qtyText.setText(Double.toString(item.getQuantity()));
        }
        if (item.isEditable()) {
            holder.box.setVisibility(View.VISIBLE);
        } else {
            holder.box.setVisibility(View.GONE);
        }
        row.setTag(holder);
        return row;
    }

}
