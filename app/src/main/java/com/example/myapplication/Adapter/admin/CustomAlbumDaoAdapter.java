package com.example.myapplication.Adapter.admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.myapplication.Activity.admin.AlbumDaoActivity;
import com.example.myapplication.Activity.admin.CRUDDaoActivity;
import com.example.myapplication.Dao.AlbumDao;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Model.Album;
import com.example.myapplication.R;

import java.util.ArrayList;

public class CustomAlbumDaoAdapter extends ArrayAdapter<Album> {
    String control;
    String check;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public CustomAlbumDaoAdapter(@NonNull Context context, int resource, ArrayList<Album> banners) {
        super(context, resource, banners);
    }

    static class ViewHolder {
        TextView txtListIndex, txtHeaderItemDao, txtTitleItemDao;
        ImageButton imgBtnUpdate, imgBtnDelete;
        ImageView imgViewtop;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
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
        Album album = getItem(position);
        Glide.with(getContext()).load(album.getImage()).error(R.drawable.album).into(viewHolder.imgViewtop);

        viewHolder.imgBtnUpdate.setOnClickListener(view -> {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                control = "repair";
                Intent intent = new Intent(getContext(), CRUDDaoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("control", control);
                bundle.putString("key", album.key);
                bundle.putString("module", getCheck());
                intent.putExtra("bundle", bundle);
                getContext().startActivity(intent);
            }, 500);
        });
        viewHolder.imgBtnDelete.setOnClickListener(view -> {
            Context context = getContext();
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle(context.getString(R.string.strTitleWarning));
            alert.setMessage(context.getString(R.string.strNotifyDeleteObject));
            alert.setPositiveButton(context.getString(R.string.strResultDialogOK), (dialogInterface, i) -> {
                AlbumDao baiHatDao = new AlbumDao();
                baiHatDao.delete(album.key, new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        Intent intent = new Intent(getContext(), AlbumDaoActivity.class);
                        getContext().startActivity(intent);
                    }

                    @Override
                    public void OnFail() {

                    }
                });
            });
            alert.setNegativeButton(context.getString(R.string.strResultDialogCancel), null);
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        });

        viewHolder.txtListIndex.setText(String.valueOf(album.getId()));
        viewHolder.txtHeaderItemDao.setText(album.getName());
        viewHolder.txtTitleItemDao.setText(String.valueOf(album.getSinger()));

        return convertView;
    }
}
