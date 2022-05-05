package com.example.myapplication.Adapter.admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Activity.LoginDialog;
import com.example.myapplication.Activity.admin.BaihatDaoActivity;
import com.example.myapplication.Activity.admin.BannerDaoActivity;
import com.example.myapplication.Activity.admin.ThemSuaDao;
import com.example.myapplication.Dao.BaiHatDao;
import com.example.myapplication.Dao.BannerDao;
import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Module.Baihat;
import com.example.myapplication.Module.Banner;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomBaihatDAOAdapter extends ArrayAdapter<Baihat> {
    LoginDialog loginDialog;
    String control;
    BaihatDaoActivity baihatDaoActivity;
    String check,baihatId;
    ArrayList<Baihat> baihats;
    ArrayList<Banner> banners;
    Boolean delete=false;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
    public CustomBaihatDAOAdapter(Context context, int resource, ArrayList<Baihat> baihats) {
        super(context, resource, baihats);
    }
    class ViewHolder{
        TextView tvMusicListIndex,tvTenBaiHatMusicList,tvTenCaSiMusicList;
        ImageButton themBaihat,suaBaihat,xoaBaihat;
        ImageView imgTopsong;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CustomBaihatDAOAdapter.ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.baihat_item_dao, null);
            viewHolder = new CustomBaihatDAOAdapter.ViewHolder();
            viewHolder.tvMusicListIndex = convertView.findViewById(R.id.tvMusicListIndex);
            viewHolder.tvTenBaiHatMusicList = convertView.findViewById(R.id.tvTenBaiHatMusicList);
            viewHolder.tvTenCaSiMusicList = convertView.findViewById(R.id.tvTenCaSiMusicList);
            viewHolder.imgTopsong = convertView.findViewById(R.id.imageViewtop);
            viewHolder.themBaihat=convertView.findViewById(R.id.btnThembaihat);
            viewHolder.suaBaihat=convertView.findViewById(R.id.btnSuabaihat);
            viewHolder.xoaBaihat=convertView.findViewById(R.id.btnXoabaihat);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomBaihatDAOAdapter.ViewHolder) convertView.getTag();
        }
        baihatDaoActivity = new BaihatDaoActivity();
        Baihat baihat = getItem(position);
        Picasso.with(getContext()).load(baihat.getHinhBaihat()).into(viewHolder.imgTopsong);
        viewHolder.themBaihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control="add";
                Intent intent = new Intent(getContext(), ThemSuaDao.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("banners", baihats);
                bundle.putString("control", control);
                bundle.putString("idBaihat", baihat.id);
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
                bundle.putSerializable("banners", baihats);
                bundle.putString("control", control);
                bundle.putString("idBaihat", baihat.id);
                bundle.putString("Module",getCheck());
                intent.putExtra("bundle", bundle);
                ((Activity)getContext()).finish();
                getContext().startActivity(intent);
            }
        });
        viewHolder.xoaBaihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaiHatDao baiHatDao = new BaiHatDao();
                baihatId=baihat.id;
                baiHatDao.delete(baihat.id, new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        handle();
                    }

                    @Override
                    public void OnFail() {

                    }
                });

                Intent intent = new Intent(getContext(), BaihatDaoActivity.class);
                ((Activity)getContext()).finish();
                getContext().startActivity(intent);
            }
        });

        viewHolder.tvMusicListIndex.setText(String.valueOf(baihat.getIdBaihat()));
        viewHolder.tvTenBaiHatMusicList.setText(baihat.getTenBaihat());
        viewHolder.tvTenCaSiMusicList.setText(baihat.getCaSi());

        return convertView;
    }
    private void handle(){
        BannerDao alBumDao = new BannerDao();
        alBumDao.getAll(new RetrievalEventListener<List<Banner>>() {
            @Override
            public void OnDataRetrieved(List<Banner> Albums) {
                banners = new ArrayList<>();
                banners = (ArrayList<Banner>) Albums;
                int size= banners.size();
                for (int i = 0; i < size; i++) {
                    if(banners.get(i).getIdBaihat().equals(baihatId)){
                        Log.d("TTT", banners.get(i).id);
                        BannerDao bannerDao = new BannerDao();
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
