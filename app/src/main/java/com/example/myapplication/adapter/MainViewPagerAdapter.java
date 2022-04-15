package com.example.myapplication.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.fragment.Fragment_Login;
import com.example.myapplication.fragment.Fragment_TChu;
import com.example.myapplication.fragment.Fragment_TKiem;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentStateAdapter {
    private ArrayList<String> arrayTitle=new ArrayList<>();
    private String[] titles=new String[]{"Trang chủ","Tìm kiếm","Playlist"};

    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public androidx.fragment.app.Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Fragment_TChu();
            case 1:
                return new Fragment_TKiem();
            case 2:
                return new Fragment_Login();
        }
        return new Fragment_TChu();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }


//
//    @Override
//        public Fragment getItem(int position) {
//            return arrayFrament.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return arrayFrament.size();
//    }
//    public void addFragment(Fragment fragment,String title)
//    {
//        arrayFrament.add(fragment);
//        arrayTitle.add(title);
//    }
}
