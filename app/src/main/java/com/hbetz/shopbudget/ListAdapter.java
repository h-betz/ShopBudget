package com.hbetz.shopbudget;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Hunter on 11/23/2016.
 */

public class ListAdapter extends ArrayAdapter<Item> {

    Context context;
    ViewHolder holder;

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public ListAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        holder = new ViewHolder();
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            //v = vi.inflate(R.layout.itemlistrow, parent, false);
            v = vi.inflate(R.layout.itemlistrow, null);
        }

        holder.box = (CheckBox) v.findViewById(R.id.checkBox);
        holder.priceEdit = (EditText) v.findViewById(R.id.editPrice);
        holder.qtyEdit = (EditText) v.findViewById(R.id.editQuantity);
        holder.nameTxt = (TextView) v.findViewById(R.id.itemName);

        v.setTag(holder);

        Item item = getItem(position);

        holder = (ViewHolder) v.getTag();

        final View view = v;

        if (item != null) {
            holder.item = item;
            holder.nameTxt.setTag(item);
            holder.nameTxt.setText(item.getName());
            if (Double.compare(item.getPrice(), 0.00) > 0) {
                //Price is set
                holder.priceEdit.setText(Double.toString(item.getPrice()));
            } else {
                holder.priceEdit.setText("");
            }
            if (item.getQuantity() > 0) {
                //Quantity is set
                holder.qtyEdit.setText(Integer.toString(item.getQuantity()));
            } else {
                holder.qtyEdit.setText("");
            }
/*            holder.priceEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    *//* When focus is lost check that the text field
                    * has valid values.
                    *//*
                    if (!hasFocus) {
                        Log.d("HUNTER", "TKJFDKS");
                        ((MainActivity) context).handleChanges(view, position);
                    }
                }
            });
            holder.qtyEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    *//* When focus is lost check that the text field
                    * has valid values.
                    *//*
                    if (!hasFocus) {
                        ((MainActivity) context).handleChanges(view, position);
                    }
                }
            });*/
            if (item.isEditable()) {
                holder.box.setVisibility(View.VISIBLE);
            } else {
                holder.box.setVisibility(View.GONE);
            }

        }

        return v;
    }

}
