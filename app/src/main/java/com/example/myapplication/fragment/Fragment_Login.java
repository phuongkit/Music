package com.example.myapplication.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;


import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class Fragment_Login extends Fragment {
//    final String DATABASE_NAME= "data1.sqlite";
//    SQLiteDatabase database;
//    EditText nameUser;
//    EditText passwordUser;
//    View view;
//    Button login,cancel;
//    public MainActivity activity;
//
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        this.activity = (MainActivity) activity;
//    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        view=inflater.inflate(R.layout.fragment__login, container, false);
//        mapping();
//        return view;
//    }
////    public boolean check(){
////        nameUser = view.findViewById(R.id.txtUserName);
////        passwordUser=view.findViewById(R.id.txtPassWord);
////        login=view.findViewById(R.id.login);
////        database= DataConnect.initDatabase( this,DATABASE_NAME);
////        Cursor cursor=database.rawQuery("SELECT * FROM user WHERE nameUser=? and passwordUser=?",new String[]{String.valueOf(nameUser), String.valueOf(passwordUser)});
////        if(cursor.getCount()!=0){
////            return true;
////        }else{return false;}
////    }
//    public void mapping(){
//        nameUser = view.findViewById(R.id.txtUserName);
//        passwordUser=view.findViewById(R.id.txtPassWord);
//        login=view.findViewById(R.id.login);
//        cancel=view.findViewById(R.id.cancel);
//        nameUser.setText("");
//        passwordUser.setText("");
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent=new Intent(activity, LoginActivity.class);
//                intent.putExtra("nameUser", nameUser.getText().toString());
//                intent.putExtra("passwordUser", passwordUser.getText().toString());
//                startActivity(intent);
////                check();
////                if(check()==true){
////                    Intent intent=new Intent(activity, PlaylistofUserActivity.class);
////                }else{
//
//
////                }
//            }
//        });
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                nameUser.setText("");
//                passwordUser.setText("");
//            }
//        });
//    }

}