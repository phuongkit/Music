package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Dao.BaiHatDao;
import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.Activity.PlaybaihatActivity;
import com.example.myapplication.R;
import com.example.myapplication.Adapter.CustomBaihatAdapter;
import com.example.myapplication.Module.Baihat;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Baihat extends Fragment {
    CustomBaihatAdapter customBaihatAdapter;
    View view;
    ListView lvPlayList;
    TextView tvTitlePlayList, tvXemThem;
    ArrayList<Baihat> baihats;
    //    ArrayList<Hinhdianhac> hinhdia;
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
                Intent intent = new Intent(getActivity(), PlaybaihatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("baihats", baihats);
                bundle.putInt("index", i);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    private void GetDetail() {
        baihats =new ArrayList<>();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("baihat");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                for (DataSnapshot data:dataSnapshot.getChildren()) {
//                    Baihat value = data.getValue(Baihat.class);
//                    baihats.add(value);
//                }
//                customBaihatAdapter = new CustomBaihatAdapter(getActivity(),android.R.layout.simple_list_item_1, baihats);
//                lvPlayList.setAdapter(customBaihatAdapter);
////                editText.setText(value.toString());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//            }
//        });
        BaiHatDao baiHatDao = new BaiHatDao();
        baiHatDao.getAll(new RetrievalEventListener<List<Baihat>>() {
            @Override
            public void OnDataRetrieved(List<Baihat> baiHats) {
                Log.d("DAO",baiHats.toString());
                baihats = new ArrayList<>();
                baihats = (ArrayList<Baihat>) baiHats;
                customBaihatAdapter = new CustomBaihatAdapter(getActivity(),android.R.layout.simple_list_item_1, baihats);
                lvPlayList.setAdapter(customBaihatAdapter);
            }
        });
    }

    private void mapping() {
        lvPlayList = view.findViewById(R.id.listViewPlayList);
        tvTitlePlayList = view.findViewById(R.id.tvTitlePlayList);
        tvXemThem = view.findViewById(R.id.tvMorePlayList);
    }
}