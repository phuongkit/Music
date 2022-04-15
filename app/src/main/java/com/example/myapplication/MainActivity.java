package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.adapter.MainViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    MainViewPagerAdapter mainViewPagerAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    private String[] titles=new String[]{"Trang chủ","Tìm kiếm","Playlist"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("baihat");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                Object value = dataSnapshot.getValue(Object.class);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//            }
//        });
        mapping();
        init();

    }
    private void init(){
        mainViewPagerAdapter=new MainViewPagerAdapter(this);
        viewPager2.setAdapter(mainViewPagerAdapter);
        new TabLayoutMediator(tabLayout,viewPager2,((tab, position) -> tab.setText(titles[position]))).attach();
        tabLayout.getTabAt(0).setIcon(R.drawable.icontrangchu);
        tabLayout.getTabAt(1).setIcon(R.drawable.icontimkiem);
        tabLayout.getTabAt(2).setIcon(R.drawable.iconmoreplaylist);

    }
    private void mapping(){
        tabLayout=findViewById(R.id.myTabLayout);
        viewPager2=findViewById(R.id.myViewpager);
    }
}