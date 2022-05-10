package com.example.myapplication.Adapter.admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Activity.admin.ThemSuaDaoActivity;
import com.example.myapplication.Activity.admin.UserDaoActivity;
import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.UserDao;
import com.example.myapplication.Module.User;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomUserDaoAdapter extends ArrayAdapter<User> {
    String control;
    String check, key;
    ArrayList<User> users;
    public static int indexOf = 1;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public CustomUserDaoAdapter(@NonNull Context context, int resource, ArrayList<User> users) {
        super(context, resource, users);
    }

    class ViewHolder {
        TextView txtListIndex, txtHeaderItemDao, txtTitleItemDao;
        ImageButton imgBtnAdd, imgBtnUpdate, imgBtnDelete;
        ImageView imgViewtop;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CustomUserDaoAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_dao, null);
            viewHolder = new ViewHolder();
            viewHolder.txtListIndex = convertView.findViewById(R.id.txtListIndex);
            viewHolder.txtHeaderItemDao = convertView.findViewById(R.id.txtHeaderItemDao);
            viewHolder.txtTitleItemDao = convertView.findViewById(R.id.txtTitleItemDao);
            viewHolder.imgViewtop = convertView.findViewById(R.id.imageViewtop);
            viewHolder.imgBtnUpdate = convertView.findViewById(R.id.imgBtnUpdate);
            viewHolder.imgBtnDelete = convertView.findViewById(R.id.imgBtnDelete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomUserDaoAdapter.ViewHolder) convertView.getTag();
        }
        User user = getItem(position);
        Picasso.with(getContext()).load(user.getAvatar()).error(R.drawable.ic_person).into(viewHolder.imgViewtop);
        viewHolder.imgBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control = "repair";
                Intent intent = new Intent(getContext(), ThemSuaDaoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("control", control);
                bundle.putString("key", user.key);
                bundle.putString("module", getCheck());
                intent.putExtra("bundle", bundle);
                ((Activity) getContext()).finish();
                getContext().startActivity(intent);
            }
        });
        viewHolder.imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDao userDao = new UserDao();
                key = user.key;
                userDao.delete(user.key, new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        handle();
                    }

                    @Override
                    public void OnFail() {

                    }
                });
                Intent intent = new Intent(getContext(), UserDaoActivity.class);
                ((Activity) getContext()).finish();
                getContext().startActivity(intent);
            }
        });

        indexOf++;
        viewHolder.txtListIndex.setText(String.valueOf(position + 1));
        viewHolder.txtHeaderItemDao.setText(user.getEmail());
        viewHolder.txtTitleItemDao.setText(user.getDisplayName());

        return convertView;
    }

    private void handle() {
//        UserDao userDao = new UserDao();
//        userDao.getAll(new RetrievalEventListener<List<User>>() {
//            @Override
//            public void OnDataRetrieved(List<User> userss) {
//                users = new ArrayList<>();
//                users = (ArrayList<User>) userss;
//                int size = users.size();
//                for (int i = 0; i < size; i++) {
//                    if (users.get(i).getId().equals(key)) {
//                        Log.d("TTT", users.get(i).key);
//                        UserDao userDaoo = new UserDao();
//                        userDaoo.delete(users.get(i).key, new TaskListener() {
//                            @Override
//                            public void OnSuccess() {
//
//                            }
//
//                            @Override
//                            public void OnFail() {
//
//                            }
//                        });
//                    }
//                }
//            }
//        });
    }

}
