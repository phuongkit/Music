package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.PlaybaihatActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.CustomBaihatAdapter;
import com.example.myapplication.adapter.CustomBannerAdapter;
import com.example.myapplication.adapter.PlaybaihatAdapter;
import com.example.myapplication.module.Baihat;
import com.example.myapplication.module.Banner;
import com.example.myapplication.module.Hinhdianhac;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_Baihat extends Fragment {
    CustomBaihatAdapter customBaihatAdapter;
    View view;
    ListView lvPlayList;
    TextView tvTitlePlayList, tvXemThem;
    ArrayList<Baihat> baihats;
    ArrayList<Hinhdianhac> hinhdia;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_baihat, container, false);
        mapping();
        GetDetail();
        baihats =new ArrayList<>();
        lvPlayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Baihat baihat=baihats.get(i);
                String hinhdia=baihat.getHinhBaihat();
                String link=baihat.getLinkBaihat();
                Intent intent = new Intent(getActivity(), PlaybaihatActivity.class);
                intent.putExtra("link",link);
                intent.putExtra("hinhdia",hinhdia);
                startActivity(intent);
            }
        });
        return view;
    }

    private void GetDetail() {
        baihats =new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("baihat");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot data:dataSnapshot.getChildren()) {
                    Baihat value = data.getValue(Baihat.class);
                    baihats.add(value);
                }
                customBaihatAdapter = new CustomBaihatAdapter(getActivity(),android.R.layout.simple_list_item_1, baihats);
                lvPlayList.setAdapter(customBaihatAdapter);
//                editText.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    private void mapping() {
        lvPlayList = view.findViewById(R.id.listViewPlayList);
        tvTitlePlayList = view.findViewById(R.id.tvTitlePlayList);
        tvXemThem = view.findViewById(R.id.tvMorePlayList);
    }
}
