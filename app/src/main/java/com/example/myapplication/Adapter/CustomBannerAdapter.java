package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.SongDao;
import com.example.myapplication.Model.Song;
import com.example.myapplication.R;
import com.example.myapplication.Model.Banner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CustomBannerAdapter extends PagerAdapter {
    Context context;
    ArrayList<Banner> arrayListBanner;
    Song song;

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
        View view = inflater.inflate(R.layout.item_banner, null);

        ImageView imageBackgroundBanner = view.findViewById(R.id.imageViewBackgroundBanner);
        imageBackgroundBanner.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageView imgSongBanner = view.findViewById(R.id.imageViewBanner1);
        TextView tvTitleSongBanner = view.findViewById(R.id.TitleBaiHat1);
        TextView tvNoiDung = view.findViewById(R.id.NoiDung1);

        Glide.with(context).load(arrayListBanner.get(position).getImage()).error(R.drawable.no_image).into(imageBackgroundBanner);
        song = new Song();
        SongDao songDao = new SongDao();
        songDao.get(arrayListBanner.get(position).getIdSong(), new RetrieValEventListener<Song>() {
            @Override
            public void OnDataRetrieved(Song Song) {
                song = Song;
            }
        });
        Picasso.with(context).load(song.getImage()).into(imgSongBanner);
        tvTitleSongBanner.setText(song.getName());
        tvNoiDung.setText(arrayListBanner.get(position).getName());


        Glide.with(context).load(arrayListBanner.get(position).getImage()).into(imageBackgroundBanner);

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
