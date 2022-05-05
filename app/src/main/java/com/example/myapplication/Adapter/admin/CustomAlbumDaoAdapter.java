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

import com.example.myapplication.Activity.admin.AlbumDaoActivity;
import com.example.myapplication.Activity.admin.BannerDaoActivity;
import com.example.myapplication.Activity.admin.ThemSuaDao;
import com.example.myapplication.Dao.AlbumDao;
import com.example.myapplication.Dao.BannerDao;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Module.Album;
import com.example.myapplication.Module.Banner;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAlbumDaoAdapter extends ArrayAdapter<Album> {
    String control;
    String check;
    AlbumDao baihatDaoActivity;
    ArrayList<Album> banners;
    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
    public CustomAlbumDaoAdapter(@NonNull Context context, int resource, ArrayList<Album> banners) {
        super(context, resource, banners);
    }
    class ViewHolder{
        TextView tvMusicListIndex,tvTenBaiHatMusicList,tvTenCaSiMusicList;
        ImageButton themBaihat,suaBaihat,xoaBaihat;
        ImageView imgTopsong;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CustomAlbumDaoAdapter.ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.baihat_item_dao, null);
            viewHolder = new CustomAlbumDaoAdapter.ViewHolder();
            viewHolder.tvMusicListIndex = convertView.findViewById(R.id.tvMusicListIndex);
            viewHolder.tvTenBaiHatMusicList = convertView.findViewById(R.id.tvTenBaiHatMusicList);
            viewHolder.tvTenCaSiMusicList = convertView.findViewById(R.id.tvTenCaSiMusicList);
            viewHolder.imgTopsong = convertView.findViewById(R.id.imageViewtop);
            viewHolder.themBaihat=convertView.findViewById(R.id.btnThembaihat);
            viewHolder.suaBaihat=convertView.findViewById(R.id.btnSuabaihat);
            viewHolder.xoaBaihat=convertView.findViewById(R.id.btnXoabaihat);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomAlbumDaoAdapter.ViewHolder) convertView.getTag();
        }
        baihatDaoActivity = new AlbumDao();
        Album banner = getItem(position);
        Picasso.with(getContext()).load(banner.getHinh()).into(viewHolder.imgTopsong);
        viewHolder.themBaihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control="add";
                Intent intent = new Intent(getContext(), ThemSuaDao.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("banners", banners);
                bundle.putString("control", control);
                bundle.putString("idBaihat", banner.id);
                bundle.putString("Module",getCheck());
                intent.putExtra("bundle", bundle);
                ((Activity)getContext()).finish();
                getContext().startActivity(intent);
            }
        });
        viewHolder.suaBaihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control="repair";
                Intent intent = new Intent(getContext(), ThemSuaDao.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("banners", banners);
                bundle.putString("control", control);
                bundle.putString("idBaihat", banner.id);
                bundle.putString("Module",getCheck());
                intent.putExtra("bundle", bundle);
                ((Activity)getContext()).finish();
                getContext().startActivity(intent);
            }
        });
        viewHolder.xoaBaihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlbumDao baiHatDao = new AlbumDao();
                baiHatDao.delete(banner.id, new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        Intent intent = new Intent(getContext(), AlbumDaoActivity.class);
                        ((Activity)getContext()).finish();
                        getContext().startActivity(intent);
                    }

                    @Override
                    public void OnFail() {

                    }
                });
            }
        });

        viewHolder.tvMusicListIndex.setText(String.valueOf(banner.getIdAlbum()));
        viewHolder.tvTenBaiHatMusicList.setText(banner.getTenAlbum());
        viewHolder.tvTenCaSiMusicList.setText(String.valueOf(banner.getTenCaSiAlbum()));

        return convertView;
    }
}
