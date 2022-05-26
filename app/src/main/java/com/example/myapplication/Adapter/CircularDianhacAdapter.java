package com.example.myapplication.Adapter;



import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Generic.Beans.Hinhdianhac;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CircularDianhacAdapter extends PagerAdapter {

    public String control="";
    public String url="";
    public String setAnimator;
    MainActivity mainActivity;
    Context context;
    CircleImageView imageViewDiaNhac;
    ArrayList<Hinhdianhac> hinhdianhacs;
    public ObjectAnimator objectAnimator;
    LinearLayout layout_discMusic;
    ArrayList<Hinhdianhac> arrayHinhdianhac;

    public CircularDianhacAdapter(Context context, ArrayList<Hinhdianhac> arrayHinhdianhac) {
        this.context = context;
        this. arrayHinhdianhac =  arrayHinhdianhac;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {

        this.control = control;

    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_music_disc, null);
        imageViewDiaNhac = view.findViewById(R.id.imageViewDiaNhac2);
        layout_discMusic = view.findViewById(R.id.layout_discMusic);

        Glide.with(context).load(getUrl()).error(R.drawable.music_disc).into(imageViewDiaNhac);
        objectAnimator = ObjectAnimator.ofFloat(imageViewDiaNhac, "rotation", 0f, 360f);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();

        container.addView(view);
        return view;
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


//        Hinhdianhac hinhdianhac = getItem(position);
//        Glide.with(context).load(hinhdianhac.getHinhdianhac()).error(R.drawable.ic_launcher_background).into(imageViewDiaNhac);
//


}