package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Adapter.CustomBannerAdapter;
import com.example.myapplication.Module.Banner;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class Fragment_Baneer extends Fragment {
    View view;
    ViewPager viewPager;
    CustomBannerAdapter customBannerAdapter;
    CircleIndicator circleIndicator;
    Runnable runnable;
    Handler handler;
    int currentItem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment__banner, container, false);
        mapping();
        GetDetail();
        return view;
    }
    private void GetDetail() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("banner");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Banner> banners =new ArrayList<>();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot data:dataSnapshot.getChildren()) {
                    Banner value = data.getValue(Banner.class);
                    banners.add(value);
                }
                customBannerAdapter = new CustomBannerAdapter(getActivity(), banners);
                viewPager.setAdapter(customBannerAdapter);
//                editText.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        circleIndicator.setViewPager(viewPager);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                currentItem = viewPager.getCurrentItem();
                currentItem = (currentItem+1) % viewPager.getAdapter().getCount();
                viewPager.setCurrentItem(currentItem, true);
                handler.postDelayed(runnable, 4500);
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    private void mapping() {
        circleIndicator = view.findViewById(R.id.Cricaleindicator);
        viewPager=view.findViewById(R.id.viewpager);
    }
}