package com.example.myapplication.Activity.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.Activity.PlaybaihatActivity;
import com.example.myapplication.Adapter.admin.CustomBaihatDAOAdapter;
import com.example.myapplication.Dao.BaiHatDao;
import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.Module.Baihat;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class BaihatDaoActivity extends AppCompatActivity {
    CustomBaihatDAOAdapter CustomBaihatDAOAdapter;
    View view;
    ListView lvPlayList;
    TextView tvTitlePlayList, tvXemThem;
    ArrayList<Baihat> baihats;
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
    public void load(){
        lvPlayList.getAdapter().notify();
    }
    private void GetDetail() {
        baihats =new ArrayList<>();
        BaiHatDao baiHatDao = new BaiHatDao();
        baiHatDao.getAll(new RetrievalEventListener<List<Baihat>>() {
            @Override
            public void OnDataRetrieved(List<Baihat> baiHats) {
                baihats = new ArrayList<>();
                baihats = (ArrayList<Baihat>) baiHats;
                CustomBaihatDAOAdapter = new CustomBaihatDAOAdapter(activity,android.R.layout.simple_list_item_1, baihats);
                CustomBaihatDAOAdapter.setCheck("Baihat");
                lvPlayList.setAdapter(CustomBaihatDAOAdapter);
            }
        });

    }

    private void mapping() {
        lvPlayList = findViewById(R.id.listViewPlayListBaihatDao);
        tvTitlePlayList = findViewById(R.id.tvTitlePlayListBaihatDao);
//        tvXemThem = findViewById(R.id.tvMorePlayListBaihatDao);
    }


}
