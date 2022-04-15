package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.myapplication.R;
import com.example.myapplication.module.Baihat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomBaihatAdapter extends ArrayAdapter<Baihat> {

    public CustomBaihatAdapter(Context context, int resource,List<Baihat> objects) {
        super(context, resource, objects);
    }
    class ViewHolder{
    TextView tvMusicListIndex,tvTenBaiHatMusicList,tvTenCaSiMusicList;
    ImageView imgTopsong;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.baihat_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvMusicListIndex = convertView.findViewById(R.id.tvMusicListIndex);
            viewHolder.tvTenBaiHatMusicList = convertView.findViewById(R.id.tvTenBaiHatMusicList);
            viewHolder.tvTenCaSiMusicList = convertView.findViewById(R.id.tvTenCaSiMusicList);
            viewHolder.imgTopsong = convertView.findViewById(R.id.imageViewtop);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Baihat baihat = getItem(position);
        Picasso.with(getContext()).load(baihat.getHinhBaihat()).into(viewHolder.imgTopsong);

        viewHolder.tvMusicListIndex.setText(String.valueOf(baihat.getIdBaihat()));
        viewHolder.tvTenBaiHatMusicList.setText(baihat.getTenBaihat());
        viewHolder.tvTenCaSiMusicList.setText(baihat.getCaSi());

        return convertView;
    }
    //    List<Baihat> baihatList;
//    Context context;
//
//    public CustomBaihatAdapter(List<Baihat> baihatList) {
//        this.baihatList = baihatList;
//    }
//
//    @NonNull
//    @Override
//    public BaihatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.baihat_item,parent,false);
//        return new BaihatHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull BaihatHolder holder, int position) {
//        Baihat baihat=baihatList.get(position);
//        Picasso.with(context).load(baihat.getHinhBaihat()).into(holder.imgbaihat);
//        holder.tvMusicListIndex.setText(String.valueOf(baihat.getIdBaihat()));
//        holder.tvTenBaiHatMusicList.setText(baihat.getTenBaihat());
//        holder.tvTenCaSiMusicList.setText(baihat.getCaSi());
//    }
//
//    @Override
//    public int getItemCount() {
//        return baihatList.size();
//    }
//
//    public class BaihatHolder extends ViewHolder{
//        TextView tvMusicListIndex,tvTenBaiHatMusicList,tvTenCaSiMusicList;
//        ImageView imgbaihat;
//        public BaihatHolder(@NonNull View itemView) {
//            super(itemView);
//            tvMusicListIndex=itemView.findViewById(R.id.tvMusicListIndex);
//            tvTenBaiHatMusicList=itemView.findViewById(R.id.tvTenBaiHatMusicList);
//            tvTenCaSiMusicList=itemView.findViewById(R.id.tvTenCaSiMusicList);
//            imgbaihat=itemView.findViewById(R.id.imageViewtop);
//        }
//    }
}
