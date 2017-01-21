package com.hbetz.shopbudget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Hunter on 12/20/2016.
 */

public class ListRow {

    Context context;

    public ListRow(Context context) {
        this.context = context;
    }

    public TableRow constructView(Item item, String price, String quantity) {
        final ViewHolder holder = new ViewHolder();
        holder.item = item;
        TableRow row = (TableRow) LayoutInflater.from(this.context).inflate(R.layout.itemlistrow, null);
        ((TextView)row.findViewById(R.id.itemName)).setText(item.getName());
        holder.nameTxt = (TextView) row.findViewById(R.id.itemName);

        if (!price.equals("")) {
            ((EditText) row.findViewById(R.id.editPrice)).setText(price);
        } else {
            ((EditText) row.findViewById(R.id.editPrice)).setText("");
            ((EditText) row.findViewById(R.id.editPrice)).setHint("Price");
        }
        holder.priceEdit = (EditText) row.findViewById(R.id.editPrice);

        if (!quantity.equals("")) {
            ((EditText)row.findViewById(R.id.editQuantity)).setText(quantity);
        } else {
            ((EditText)row.findViewById(R.id.editQuantity)).setText("");
            ((EditText)row.findViewById(R.id.editQuantity)).setHint("Quantity");
        }
        holder.qtyEdit = (EditText) row.findViewById(R.id.editQuantity);
        holder.box = (CheckBox) row.findViewById(R.id.checkBox);


        if (item.isEditable()) {
            holder.box.setVisibility(View.VISIBLE);
        } else {
            holder.box.setVisibility(View.GONE);
        }

        row.setTag(holder);

        final TableRow tableRow = row;

        holder.add = (TextView) row.findViewById(R.id.plusSign);
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).handleChanges(tableRow);
            }
        });

        return row;
    }

}
