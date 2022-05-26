package com.example.myapplication.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MusicDiscFragment extends Fragment {
    View view;
    CircleImageView circleImageView;
    public ObjectAnimator objectAnimator;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dia_nhac, container, false);
        circleImageView = view.findViewById(R.id.imageViewDiaNhac);
        objectAnimator = ObjectAnimator.ofFloat(circleImageView, "rotation", 0f, 360f);
        objectAnimator.setDuration(35000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
        return view;
    }

    public void playMusic(String image){
        Glide.with(requireActivity()).load(image).error(R.drawable.music_disc).into(circleImageView);
    }
}
