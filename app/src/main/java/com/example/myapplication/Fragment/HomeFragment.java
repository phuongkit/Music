package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        FragmentTransaction ft2 = getChildFragmentManager().beginTransaction();
//        MusicFragment musicFragment2 = new MusicFragment();
//        Bundle bundle2 = new Bundle();
//        bundle2.putInt("TypeMusic", 0);
//        musicFragment2.setArguments(bundle2);
//        ft2.replace(R.id.framentCDTL, musicFragment2);
//        ft2.commit();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        MusicFragment musicFragment = new MusicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TypeMusic", 0);
        musicFragment.setArguments(bundle);
        ft.replace(R.id.framentBaihat, musicFragment);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onDestroyView() {
        FragmentManager mFragmentMgr = getChildFragmentManager();
        FragmentTransaction mTransaction = mFragmentMgr.beginTransaction();
        Fragment childFragment = mFragmentMgr.findFragmentById(R.id.framentBaihat);
        mTransaction.remove(childFragment);
        mTransaction.commit();
        super.onDestroyView();
    }
}