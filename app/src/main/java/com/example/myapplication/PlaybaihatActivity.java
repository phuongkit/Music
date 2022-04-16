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

import com.example.myapplication.adapter.PlaybaihatAdapter;
import com.example.myapplication.module.Baihat;
import com.example.myapplication.module.Hinhdianhac;

import java.io.IOException;
import java.util.ArrayList;

public class PlaybaihatActivity extends AppCompatActivity {

    ViewPager viewPager;
    PlaybaihatAdapter playbaihatAdapter;
    ArrayList<Hinhdianhac> hinhbaihats;
    ArrayList<Baihat> baihats = new ArrayList<>();
    Baihat baihat;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playmucsic);
        hinhbaihats=new ArrayList<>();
        UNI();
        playbaihatAdapter = new PlaybaihatAdapter(this, baihats, index);
        Log.d("AAA", playbaihatAdapter.toString());
        viewPager.setAdapter(playbaihatAdapter);
    }
    public void UNI(){
//        circleImageView=findViewById(R.id.imageViewDiaNhac);
        viewPager=findViewById(R.id.myViewpagerplaymucsic);
        Intent intent=getIntent();
//        hinhnhacs = intent.getStringArrayListExtra("hinhnhacs");
//        links = intent.getStringArrayListExtra("links");
        Bundle bundle = intent.getBundleExtra("bundle");
        baihats = (ArrayList<Baihat>) bundle.getSerializable("baihats");
        index = bundle.getInt("index", -1);
//        hinhbaihats.add(new Hinhdianhac(link,hinhnhac,position));
////        for (int i = 0; i < hinhnhacs.size(); i++) {
////            hinhbaihats.add(new Hinhdianhac(links.get(i), hinhnhacs.get(i)));
////        }
    }

    public void btnPrevious_click(View view) {

    }
}
