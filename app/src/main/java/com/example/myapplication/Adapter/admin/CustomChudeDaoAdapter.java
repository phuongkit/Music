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

import com.example.myapplication.Activity.admin.AlbumDaoActivity;
import com.example.myapplication.Activity.admin.ChudeDaoActivity;
import com.example.myapplication.Activity.admin.ThemSuaDao;
import com.example.myapplication.Dao.AlbumDao;
import com.example.myapplication.Dao.BannerDao;
import com.example.myapplication.Dao.ChuDeDao;
import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.TheLoaiDao;
import com.example.myapplication.Module.Album;
import com.example.myapplication.Module.Banner;
import com.example.myapplication.Module.Chude;
import com.example.myapplication.Module.Theloai;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomChudeDaoAdapter extends ArrayAdapter<Chude> {
    String control;
    String check,baihatId;
    AlbumDao baihatDaoActivity;
    ArrayList<Theloai> banners;
    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
    public CustomChudeDaoAdapter(@NonNull Context context, int resource, ArrayList<Chude> banners) {
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
        CustomChudeDaoAdapter.ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.baihat_item_dao, null);
            viewHolder = new CustomChudeDaoAdapter.ViewHolder();
            viewHolder.tvMusicListIndex = convertView.findViewById(R.id.tvMusicListIndex);
            viewHolder.tvTenBaiHatMusicList = convertView.findViewById(R.id.tvTenBaiHatMusicList);
            viewHolder.tvTenCaSiMusicList = convertView.findViewById(R.id.tvTenCaSiMusicList);
            viewHolder.imgTopsong = convertView.findViewById(R.id.imageViewtop);
            viewHolder.themBaihat=convertView.findViewById(R.id.btnThembaihat);
            viewHolder.suaBaihat=convertView.findViewById(R.id.btnSuabaihat);
            viewHolder.xoaBaihat=convertView.findViewById(R.id.btnXoabaihat);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomChudeDaoAdapter.ViewHolder) convertView.getTag();
        }
        Chude banner = getItem(position);
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
                ChuDeDao baiHatDao = new ChuDeDao();
                baihatId=banner.id;
                baiHatDao.delete(banner.id, new TaskListener() {
                    @Override
                    public void OnSuccess() {
                       handle();
                    }

                    @Override
                    public void OnFail() {

                    }
                });
                Intent intent = new Intent(getContext(), ChudeDaoActivity.class);
                ((Activity)getContext()).finish();
                getContext().startActivity(intent);
            }
        });

        viewHolder.tvMusicListIndex.setText(String.valueOf(banner.getIdChude()));
        viewHolder.tvTenBaiHatMusicList.setText(banner.getTen());

        return convertView;
    }
    private void handle(){
        TheLoaiDao alBumDao = new TheLoaiDao();
        alBumDao.getAll(new RetrievalEventListener<List<Theloai>>() {
            @Override
            public void OnDataRetrieved(List<Theloai> Albums) {
                banners = new ArrayList<>();
                banners = (ArrayList<Theloai>) Albums;
                int size= banners.size();
                for (int i = 0; i < size; i++) {
                    if(banners.get(i).getIdChude().equals(baihatId)){
                        Log.d("TTT", banners.get(i).id);
                        TheLoaiDao bannerDao = new TheLoaiDao();
                        bannerDao.delete(banners.get(i).id, new TaskListener() {
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
