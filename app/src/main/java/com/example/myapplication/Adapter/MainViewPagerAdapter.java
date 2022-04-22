package com.example.myapplication.Adapter;


import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.Fragment.HomeFragment;
import com.example.myapplication.Fragment.SearchFragment;
import com.example.myapplication.Fragment.PersonalFragment;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentStateAdapter {
    FragmentActivity fragmentActivity;
    private ArrayList<String> arrayTitle=new ArrayList<>();
//    private String[] titles = new String[]{
//            getString(R.string.strHeaderPersonal),
//            getString(R.string.strHeaderHomePage),
//            getString(R.string.strHeaderSearch),
//            getString(R.string.strHeaderPlaylist)
//    };

//    private String[] titles;//=new String[]{"Cá nhân","Trang chủ","Tìm kiếm","Playlist"};

    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    private String getString(int resId) {
        return fragmentActivity.getString(resId);
    }

    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.fragmentActivity = fragmentActivity;
//        titles = new String[5];
//        titles.
        registeredFragments.append(0, new PersonalFragment());
        registeredFragments.append(1, new HomeFragment());
        registeredFragments.append(2, new SearchFragment());
        registeredFragments.append(3, new PersonalFragment());
    }
    @NonNull
    @Override
    public androidx.fragment.app.Fragment createFragment(int position) {
//        switch (position){
//            case 0:
//            case 3:
//                return new PersonalFragment();
//            case 1:
//                return new HomeFragment();
//            case 2:
//                return new SearchFragment();
//
//        }
//        return new HomeFragment();
        return position < 0 || position + 1 > registeredFragments.size() ? registeredFragments.get(1) : registeredFragments.get(position);
    }

    @Override
    public int getItemCount() {
//        return titles.length;
        return registeredFragments.size();
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