package com.example.myapplication.Adapter.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activity.admin.CRUDDaoActivity;
import com.example.myapplication.Activity.admin.SongDaoActivity;
import com.example.myapplication.Dao.BannerDao;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.SongDao;
import com.example.myapplication.Model.Banner;
import com.example.myapplication.Model.Song;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CustomSongDaoAdapter extends ArrayAdapter<Song> {
    String control;
    String check, baihatId;
    ArrayList<Banner> banners;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public CustomSongDaoAdapter(Context context, int resource, ArrayList<Song> baihats) {
        super(context, resource, baihats);
    }

    static class ViewHolder {
        TextView txtListIndex, txtHeaderItemDao, txtTitleItemDao;
        ImageButton imgBtnUpdate;
        ImageButton imgBtnDelete;
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
        Song song = getItem(position);
        Glide.with(getContext()).load(song.getImage()).error(R.drawable.song).into(viewHolder.imgViewtop);
        viewHolder.imgBtnUpdate.setOnClickListener(view -> {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                control = "repair";
                Intent intent = new Intent(getContext(), CRUDDaoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("control", control);
                bundle.putString("key", song.key);
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
                SongDao songDao = new SongDao();
                baihatId = song.key;
                songDao.delete(song.key, new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        handle();
                    }

                    @Override
                    public void OnFail() {

                    }
                });

                Intent intent = new Intent(getContext(), SongDaoActivity.class);
                getContext().startActivity(intent);
            });
            alert.setNegativeButton(context.getString(R.string.strResultDialogCancel), null);
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        });

        viewHolder.txtListIndex.setText(position + 1 + "");
        viewHolder.txtHeaderItemDao.setText(song.getName());
        viewHolder.txtTitleItemDao.setText(song.getSinger());

        return convertView;
    }

    private void handle() {
        BannerDao bannerDao = new BannerDao();
        bannerDao.getAll(new RetrieValEventListener<List<Banner>>() {
            @Override
            public void OnDataRetrieved(List<Banner> Banners) {
                banners = new ArrayList<>();
                banners = (ArrayList<Banner>) Banners;
                int size = banners.size();
                for (int i = 0; i < size; i++) {
                    if (banners.get(i).getId().equals(baihatId)) {
                        Log.d("TTT", banners.get(i).key);
                        BannerDao bannerDao = new BannerDao();
                        bannerDao.delete(banners.get(i).key, new TaskListener() {
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
