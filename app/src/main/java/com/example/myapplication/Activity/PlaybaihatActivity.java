package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.Adapter.PlaybaihatAdapter;
import com.example.myapplication.Module.Baihat;
import com.example.myapplication.Module.Hinhdianhac;

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
        viewPager.setAdapter(playbaihatAdapter);
    }
    public void UNI(){
        viewPager=findViewById(R.id.myViewpagerplaymucsic);
        Intent intent=getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        baihats = (ArrayList<Baihat>) bundle.getSerializable("baihats");
        index = bundle.getInt("index", -1);
    }
}