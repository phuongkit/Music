package com.example.myapplication.Adapter.admin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.Generic.Beans.Item;

import java.util.ArrayList;

public class SpinnerDaoAdapter extends ArrayAdapter<Item> {

    ArrayList<Item> items;

    Activity activity;
    int resource;

    int selectedItem = -1;

    public SpinnerDaoAdapter(@NonNull Activity activity, int resource, ArrayList<Item> items) {
        super(activity, resource);
        this.activity = activity;
        this.resource = resource;
        this.items = items;
    }

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
    public Item getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(items.get(position).getName());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View v = null;
        v = super.getDropDownView(position, convertView, parent);
        TextView label = (TextView) v;
        label.setTextColor(Color.BLACK);
        label.setText(items.get(position).getName());

        // If this is the selected item position
        if (position == selectedItem) {
            v.setBackgroundResource(android.R.color.holo_blue_light);
        }
        else {
            // for other views
            v.setBackgroundColor(Color.WHITE);

        }

        return v;
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }
}
