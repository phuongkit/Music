package com.example.myapplication.Activity.admin;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Adapter.admin.CustomTheloaiDaoAdapter;
import com.example.myapplication.Adapter.admin.CustomTheloaiDaoAdapter;
import com.example.myapplication.Dao.TheLoaiDao;

import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.Module.Theloai;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class TheloaiDaoActivity extends AppCompatActivity {
    CustomTheloaiDaoAdapter CustomBaihatDAOAdapter;
    View view;
    public ListView lvPlayList;
    TextView tvTitlePlayList, tvXemThem;
    ArrayList<Theloai> baihats;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_listbaihat_dao);
        activity=this;
        mapping();
        GetDetail();

        baihats =new ArrayList<>();
//        lvPlayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(mainActivity, PlaybaihatActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("baihats", baihats);
//                bundle.putInt("index", i);
//                intent.putExtra("bundle", bundle);
//                startActivity(intent);
//            }
//        });
    }

    private void GetDetail() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                baihats =new ArrayList<>();
                TheLoaiDao baiHatDao = new TheLoaiDao();
                baiHatDao.getAll(new RetrievalEventListener<List<Theloai>>() {
                    @Override
                    public void OnDataRetrieved(List<Theloai> baiHats) {
                        baihats = new ArrayList<>();
                        baihats = (ArrayList<Theloai>) baiHats;
                        CustomBaihatDAOAdapter = new CustomTheloaiDaoAdapter(activity,android.R.layout.simple_list_item_1, baihats);
                        CustomBaihatDAOAdapter.setCheck("Theloai");
                        lvPlayList.setAdapter(CustomBaihatDAOAdapter);
                    }
                });
            }
        }, 100);

    }

    private void mapping() {
        lvPlayList = findViewById(R.id.listViewPlayListBaihatDao);
        tvTitlePlayList = findViewById(R.id.tvTitlePlayListBaihatDao);
//        tvXemThem = findViewById(R.id.tvMorePlayListBaihatDao);
    }

}