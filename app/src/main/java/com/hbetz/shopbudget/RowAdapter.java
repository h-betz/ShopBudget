package com.hbetz.shopbudget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hunter on 11/26/2016.
 */

public class RowAdapter extends ArrayAdapter<Item> {

    Context context;
    CartHolder holder;

    public RowAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public RowAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        holder = new CartHolder();
        Log.d("TAG", "HERE");
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.completed_item_row, parent, false);
        }

        holder.box = (CheckBox) v.findViewById(R.id.deleteBox);
        holder.nameTxt = (TextView) v.findViewById(R.id.itemNameCompleted);
        holder.priceTxt = (TextView) v.findViewById(R.id.itemPriceCompleted);
        holder.qtyText = (TextView) v.findViewById(R.id.itemQtyCompleted);

        v.setTag(holder);

        Item item = getItem(position);

        holder = (CartHolder) v.getTag();

        if (item != null) {
            holder.item = item;
            holder.nameTxt.setText(item.getName());
            if (Double.compare(item.getPrice(), 0.00) > 0) {
                //Price is set
                holder.priceTxt.setText(Double.toString(item.getPrice()));
            }
            if (item.getQuantity() > 0) {
                //Quantity is set
                holder.qtyText.setText(Integer.toString(item.getQuantity()));
            }
            if (item.isEditable()) {
                holder.box.setVisibility(View.VISIBLE);
            } else {
                holder.box.setVisibility(View.GONE);
            }
        }

        return v;
    }

}
