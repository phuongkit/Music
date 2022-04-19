package com.example.myapplication.Adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.Fragment.Fragment_TChu;
import com.example.myapplication.Fragment.Fragment_TKiem;
import com.example.myapplication.Fragment.PersonalFragment;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentStateAdapter {
    private ArrayList<String> arrayTitle=new ArrayList<>();
    private String[] titles=new String[]{"Cá nhân","Trang chủ","Tìm kiếm","Playlist"};

    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public androidx.fragment.app.Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new PersonalFragment();
            case 1:
                return new Fragment_TChu();
            case 2:
                return new Fragment_TKiem();
            case 3:
                return new PersonalFragment();

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
