package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Activity.PlaylistActivity;
import com.example.myapplication.Activity.SongOnDeviceActivity;
import com.example.myapplication.Generic.Beans.Libary;
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
        @SuppressLint("ViewHolder") View view = this.context.getLayoutInflater().inflate(this.resource, null);
        ImageView imgContentLibary = view.findViewById(R.id.imgContentLibary);
        TextView txtContenLibary = view.findViewById(R.id.txtContentLibary);

        Libary libary = getItem(position);

        imgContentLibary.setBackgroundResource(libary.getIdIcon());
        txtContenLibary.setText(libary.getName());

        view.setOnClickListener(view1 -> {
            Intent intent;
            switch (libary.getIdLibary()) {
                case "1":
                case "4":
                    Toast.makeText(context.getApplication(), context.getString(R.string.strNotifyComingSoon), Toast.LENGTH_SHORT).show();
                    break;
                case "2":
                    intent = new Intent(context, SongOnDeviceActivity.class);
                    context.startActivity(intent);
                    break;
                case "3":
                    intent = new Intent(context, PlaylistActivity.class);
                    context.startActivity(intent);
                    break;
                default: break;
            }
        });
        return view;
    }
}
