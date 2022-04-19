package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myapplication.R;
import com.example.myapplication.Module.Banner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CustomBannerAdapter extends PagerAdapter {
    Context context;
    ArrayList<Banner> arrayListBanner;

    public CustomBannerAdapter(Context context, ArrayList<Banner> arrayListBanner) {
        this.context = context;
        this.arrayListBanner = arrayListBanner;
    }

    @Override
    public int getCount() {
        return arrayListBanner.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.banner_item, null);

        ImageView imageBackgroundBanner = view.findViewById(R.id.imageViewBackgroundBanner);
        imageBackgroundBanner.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageView imgSongBanner = view.findViewById(R.id.imageViewBanner1);
        TextView tvTitleSongBanner = view.findViewById(R.id.TitleBaiHat1);
        TextView tvNoiDung = view.findViewById(R.id.NoiDung1);

        Picasso.with(context).load(arrayListBanner.get(position).getHinhAnh()).into(imageBackgroundBanner);
        Picasso.with(context).load(arrayListBanner.get(position).getBaihat().getHinhBaihat()).into(imgSongBanner);
        tvTitleSongBanner.setText(arrayListBanner.get(position).getBaihat().getTenBaihat());
        tvNoiDung.setText(arrayListBanner.get(position).getNoiDung());


        Picasso.with(context).load(arrayListBanner.get(position).getHinhAnh()).into(imageBackgroundBanner);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, MusicListActivity.class);
//                intent.putExtra("banner", arrayListBanner.get(position));
//                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
