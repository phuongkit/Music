package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.myapplication.Adapter.LibaryAdapter;
import com.example.myapplication.Module.Libary;
import com.example.myapplication.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalFragment extends Fragment {

    LibaryAdapter libaryAdapter;
    ArrayList<Libary> libaries;
    GridView gvLibary;
    View view;

    public PersonalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalFragment newInstance(String param1, String param2) {
        PersonalFragment fragment = new PersonalFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Test", "Node D");
        view = inflater.inflate(R.layout.fragment_personal, container, false);
        addControls();
        addEvents();
        Log.d("Test", "Node F");
        return view;
    }

    private void addEvents() {

    }

    private void addControls() {
        gvLibary = view.findViewById(R.id.gvLibary);
        libaryAdapter = new LibaryAdapter(getActivity(), R.layout.libary_item);
        gvLibary.setAdapter(libaryAdapter);
        libaryAdapter.add(new Libary(1, getString(R.string.strHeaderMusic), R.drawable.ic_music));
        libaryAdapter.add(new Libary(2, getString(R.string.strHeaderOnDevice), R.drawable.ic_on_device));
        libaryAdapter.add(new Libary(3, getString(R.string.strHeaderPlaylist), R.drawable.ic_playlist));
        libaryAdapter.add(new Libary(4, getString(R.string.strHeaderHistory), R.drawable.ic_history));
    }
}