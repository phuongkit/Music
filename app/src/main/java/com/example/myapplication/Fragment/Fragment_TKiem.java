package com.example.myapplication.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Dao.BaiHatDao;
import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.R;
import com.example.myapplication.Adapter.CustomBaihatAdapter;
import com.example.myapplication.Module.Baihat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_TKiem extends Fragment {
    CustomBaihatAdapter customBaihatAdapter;
    EditText txtSearch;
    ListView lvSearch;
    View view;
    RecyclerView listSearch;
    ArrayList<Baihat> baihats1 = new ArrayList<>();
    Button btnSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_timkiem, container, false);
        lvSearch = view.findViewById(R.id.lvSearch);
        customBaihatAdapter = new CustomBaihatAdapter(getActivity(), android.R.layout.simple_list_item_1, baihats1);
        lvSearch.setAdapter(customBaihatAdapter);
        return view;
    }

    public void setBundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.getString("query") != null) {
                Log.d("Test", "nodeA");
                String query = bundle.getString("query");
                Log.d("Info", "Search: " + query);
                query = query.toLowerCase();
                search(query);
            }
            else {
                Log.d("Test", "nodeB");
                String filter = bundle.getString("filter");
                Log.d("Info", "Search Filter: " + filter);
                filter = filter.toLowerCase();
                searchFilter(filter);
            }
        }
    }

    private void search(String query) {
        String textSearch = query;
//        baihats = new ArrayList<>();
        BaiHatDao baiHatDao = new BaiHatDao();
        baiHatDao.getAll(new RetrievalEventListener<List<Baihat>>() {
            @Override
            public void OnDataRetrieved(List<Baihat> baihats) {
                baihats1 = new ArrayList<>();
                if (textSearch != null) {
                    for (Baihat baihat : baihats) {
                        if (baihat.getTenBaihat().toLowerCase().contains(textSearch)
                                || baihat.getCaSi().toLowerCase().contains(textSearch)) {
                            baihats1.add(baihat);
                        }
                    }
                    customBaihatAdapter.clear();
                    customBaihatAdapter.addAll(baihats1);
                }
            }
        });
    }

    private void searchFilter(String filter) {
        String textSearch = filter;
//        baihats = new ArrayList<>();
        BaiHatDao baiHatDao = new BaiHatDao();
        baiHatDao.getAll(new RetrievalEventListener<List<Baihat>>() {
            @Override
            public void OnDataRetrieved(List<Baihat> baihats) {
                customBaihatAdapter.clear();
                customBaihatAdapter.addAll(baihats);
                customBaihatAdapter.getFilter().filter(filter);
            }
        });
    }
}
