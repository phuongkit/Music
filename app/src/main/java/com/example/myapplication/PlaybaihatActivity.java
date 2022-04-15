package com.example.myapplication;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.myapplication.adapter.PlaybaihatAdapter;
import com.example.myapplication.module.Hinhdianhac;

import java.io.IOException;
import java.util.ArrayList;

public class PlaybaihatActivity extends AppCompatActivity {

    String link,hinhnhac;
    ViewPager viewPager;
    PlaybaihatAdapter playbaihatAdapter;
    ArrayList<Hinhdianhac> hinhbaihats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playmucsic);
        hinhbaihats=new ArrayList<>();
        UNI();
        playbaihatAdapter = new PlaybaihatAdapter(this,hinhbaihats);
        Log.d("AAA", playbaihatAdapter.toString());
        viewPager.setAdapter(playbaihatAdapter);

    }
    public void UNI(){
//        circleImageView=findViewById(R.id.imageViewDiaNhac);
        viewPager=findViewById(R.id.myViewpagerplaymucsic);
        Intent intent=getIntent();
        hinhnhac=intent.getStringExtra("hinhdia");
        link=intent.getStringExtra("link");
        hinhbaihats.add(new Hinhdianhac(link,hinhnhac));
    }

    public void btnPrevious_click(View view) {

    }
}
