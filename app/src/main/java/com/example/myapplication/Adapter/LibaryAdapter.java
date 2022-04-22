package com.example.myapplication.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Module.Libary;
import com.example.myapplication.R;

public class LibaryAdapter extends ArrayAdapter<Libary> {
    Activity context;
    int resource;

    public LibaryAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = this.context.getLayoutInflater().inflate(this.resource, null);
        ImageView imgContentLibary = view.findViewById(R.id.imgContentLibary);
        TextView txtContenLibary = view.findViewById(R.id.txtContentLibary);

        Libary libary = getItem(position);

        imgContentLibary.setBackgroundResource(libary.getIdIcon());
        txtContenLibary.setText(libary.getName());

        return view;
    }
}
