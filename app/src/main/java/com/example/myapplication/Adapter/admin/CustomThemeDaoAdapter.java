package com.example.myapplication.Adapter.admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.myapplication.Activity.admin.ThemeDaoActivity;
import com.example.myapplication.Activity.admin.ThemSuaDaoActivity;
import com.example.myapplication.Dao.ThemeDao;
import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.TypesDao;
import com.example.myapplication.Module.Theme;
import com.example.myapplication.Module.Types;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomThemeDaoAdapter extends ArrayAdapter<Theme> {
    String control;
    String check, baihatId;
    ArrayList<Theme> chudes;
    Theme theme;
    ArrayList<Types> typess;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public CustomThemeDaoAdapter(@NonNull Context context, int resource, ArrayList<Theme> banners) {
        super(context, resource, banners);
    }

    class ViewHolder {
        TextView txtListIndex, txtHeaderItemDao, txtTitleItemDao;
        ImageButton imgBtnAdd, imgBtnUpdate, imgBtnDelete;
        ImageView imgViewtop;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_dao, null);
            viewHolder = new ViewHolder();
            viewHolder.txtListIndex = convertView.findViewById(R.id.txtListIndex);
            viewHolder.txtHeaderItemDao = convertView.findViewById(R.id.txtHeaderItemDao);
            viewHolder.txtTitleItemDao = convertView.findViewById(R.id.txtTitleItemDao);
            viewHolder.imgViewtop = convertView.findViewById(R.id.imageViewtop);
            viewHolder.imgBtnUpdate = convertView.findViewById(R.id.imgBtnUpdate);
            viewHolder.imgBtnDelete = convertView.findViewById(R.id.imgBtnDelete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Theme theme = getItem(position);
        Glide.with(getContext()).load(theme.getImage()).into(viewHolder.imgViewtop);
        viewHolder.imgBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control = "repair";
                Intent intent = new Intent(getContext(), ThemSuaDaoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("control", control);
                bundle.putString("key", theme.key);
                bundle.putString("module", getCheck());
                intent.putExtra("bundle", bundle);
                ((Activity) getContext()).finish();
                getContext().startActivity(intent);
            }
        });
        viewHolder.imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemeDao baiHatDao = new ThemeDao();
                baihatId = theme.key;
                baiHatDao.delete(theme.key, new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        handle();
                    }

                    @Override
                    public void OnFail() {

                    }
                });
                Intent intent = new Intent(getContext(), ThemeDaoActivity.class);
                ((Activity) getContext()).finish();
                getContext().startActivity(intent);
            }
        });

        viewHolder.txtListIndex.setText(String.valueOf(theme.getId()));
        viewHolder.txtHeaderItemDao.setText(theme.getName());

        return convertView;
    }

    private void handle() {
        TypesDao typesDao = new TypesDao();
        typesDao.getAll(new RetrievalEventListener<List<Types>>() {
            @Override
            public void OnDataRetrieved(List<Types> Typess) {
                typess = new ArrayList<>();
                typess = (ArrayList<Types>) Typess;
                int size = typess.size();
                for (int i = 0; i < size; i++) {
                    if (typess.get(i).getIdTheme().equals(baihatId)) {
                        Log.d("TTT", typess.get(i).key);
                        TypesDao bannerDao = new TypesDao();
                        bannerDao.delete(typess.get(i).key, new TaskListener() {
                            @Override
                            public void OnSuccess() {

                            }

                            @Override
                            public void OnFail() {

                            }
                        });
                    }
                }
            }
        });
    }

}
