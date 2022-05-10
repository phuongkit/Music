package com.example.myapplication.Adapter.admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activity.admin.TypesDaoActivity;
import com.example.myapplication.Activity.admin.ThemSuaDaoActivity;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.TypesDao;
import com.example.myapplication.Module.Types;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomTypesDaoAdapter extends ArrayAdapter<Types> {
    String control;
    String check;
    ArrayList<Types> typess;
    Types types;
    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
    public CustomTypesDaoAdapter(@NonNull Context context, int resource, ArrayList<Types> banners) {
        super(context, resource, banners);
    }
    class ViewHolder{
        TextView txtListIndex,txtHeaderItemDao,txtTitleItemDao;
        ImageButton imgBtnAdd,imgBtnUpdate,imgBtnDelete;;
        ImageView imgViewtop;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_dao, null);
            viewHolder = new ViewHolder();
            viewHolder.txtListIndex = convertView.findViewById(R.id.txtListIndex);
            viewHolder.txtHeaderItemDao = convertView.findViewById(R.id.txtHeaderItemDao);
            viewHolder.txtTitleItemDao = convertView.findViewById(R.id.txtTitleItemDao);
            viewHolder.imgViewtop = convertView.findViewById(R.id.imageViewtop);
            viewHolder.imgBtnUpdate=convertView.findViewById(R.id.imgBtnUpdate);
            viewHolder.imgBtnDelete =convertView.findViewById(R.id.imgBtnDelete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Types types = getItem(position);
        Glide.with(getContext()).load(types.getImage()).into(viewHolder.imgViewtop);
        viewHolder.imgBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control="repair";
                Intent intent = new Intent(getContext(), ThemSuaDaoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("control", control);
                bundle.putString("key", types.key);
                bundle.putString("module",getCheck());
                intent.putExtra("bundle", bundle);
                ((Activity)getContext()).finish();
                getContext().startActivity(intent);
            }
        });
        viewHolder.imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TypesDao baiHatDao = new TypesDao();
                baiHatDao.delete(types.key, new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        Intent intent = new Intent(getContext(), TypesDaoActivity.class);
                        ((Activity)getContext()).finish();
                        getContext().startActivity(intent);

                    }

                    @Override
                    public void OnFail() {

                    }
                });
            }
        });

        viewHolder.txtListIndex.setText(String.valueOf(types.getId()));
        viewHolder.txtHeaderItemDao.setText(types.getName());

        return convertView;
    }

}