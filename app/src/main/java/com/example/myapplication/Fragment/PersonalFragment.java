package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Adapter.LibaryAdapter;
import com.example.myapplication.Generic.Beans.Libary;
import com.example.myapplication.R;

public class PersonalFragment extends Fragment {

    LibaryAdapter libaryAdapter;
    GridView gvLibary;
    View view;

    public PersonalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_personal, container, false);
        addControls();
        addEvents();
        return view;
    }

    private void addEvents() {

    }

    private void addControls() {
        gvLibary = view.findViewById(R.id.gvLibary);
        libaryAdapter = new LibaryAdapter(requireActivity(), R.layout.item_libary);
        gvLibary.setAdapter(libaryAdapter);
        libaryAdapter.add(new Libary("1", getString(R.string.strHeaderSong), R.drawable.ic_music));
        libaryAdapter.add(new Libary("2", getString(R.string.strHeaderOnDevice), R.drawable.ic_on_device));
        libaryAdapter.add(new Libary("3", getString(R.string.strHeaderPlaylist), R.drawable.ic_playlist));
        libaryAdapter.add(new Libary("4", getString(R.string.strHeaderHistory), R.drawable.ic_history));
    }
}