package com.example.myapplication.Adapter.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activity.admin.CRUDDaoActivity;
import com.example.myapplication.Activity.admin.UserDaoActivity;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.PlaylistDao;
import com.example.myapplication.Dao.Playlist_SongDao;
import com.example.myapplication.Dao.UserDao;
import com.example.myapplication.Model.Playlist;
import com.example.myapplication.Model.Playlist_Song;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CustomUserDaoAdapter extends ArrayAdapter<User> {

    Context context;

    String control;
    String check, key;
    public static int indexOf = 1;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public CustomUserDaoAdapter(@NonNull Context context, int resource, ArrayList<User> users) {
        super(context, resource, users);
        this.context = context;
    }

    static class ViewHolder {
        TextView txtListIndex, txtHeaderItemDao, txtTitleItemDao;
        ImageButton imgBtnUpdate;
        ImageButton imgBtnDelete;
        ImageView imgViewtop;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CustomUserDaoAdapter.ViewHolder viewHolder;
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
        Glide.with(getContext()).load(user.getAvatar()).error(R.drawable.ic_person).into(viewHolder.imgViewtop);
        viewHolder.imgBtnUpdate.setOnClickListener(view -> {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                control = "repair";
                Intent intent = new Intent(getContext(), CRUDDaoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("control", control);
                bundle.putString("key", user.key);
                bundle.putString("module", getCheck());
                intent.putExtra("bundle", bundle);
                getContext().startActivity(intent);
            }, 500);
        });
        viewHolder.imgBtnDelete.setOnClickListener(view -> {
            Context context = getContext();
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle(context.getString(R.string.strTitleWarning));
            alert.setMessage(context.getString(R.string.strNotifyDeleteObject));
            alert.setPositiveButton(context.getString(R.string.strResultDialogOK), (dialogInterface, i) -> {
                UserDao userDao = new UserDao();
                key = user.key;
                userDao.delete(user.key, new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        handle(user);
                    }

                    @Override
                    public void OnFail() {

                    }
                });
                Intent intent = new Intent(getContext(), UserDaoActivity.class);
                getContext().startActivity(intent);
            });
            alert.setNegativeButton(context.getString(R.string.strResultDialogCancel), null);
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        });

        indexOf++;
        viewHolder.txtListIndex.setText(String.valueOf(position + 1));
        viewHolder.txtHeaderItemDao.setText(user.getEmail());
        viewHolder.txtTitleItemDao.setText(user.getDisplayName());

        return convertView;
    }

    private void handle(User user) {
        PlaylistDao playlistDao = new PlaylistDao();
        playlistDao.getAll(new RetrieValEventListener<List<Playlist>>() {
            @Override
            public void OnDataRetrieved(List<Playlist> playlists) {
                ArrayList<String> playlist_ids = new ArrayList<>();
                for (Playlist playlist : playlists) {
                    if (playlist.getUid().equals(user.getId())) {
                        playlist_ids.add(playlist.getId());
                        playlistDao.delete(playlist.key, new TaskListener() {
                            @Override
                            public void OnSuccess() {
                                Toast.makeText(context, context.getString(R.string.strNotifySuccessDelete) + " " + Playlist.class.getName() + " " + playlist.getName(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void OnFail() {
                                Toast.makeText(context, context.getString(R.string.strNotifyFailDelete) + " " + Playlist.class.getName() + " " + playlist.getName(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                Playlist_SongDao playlist_songDao = new Playlist_SongDao();
                playlist_songDao.getAll(new RetrieValEventListener<List<Playlist_Song>>() {
                    @Override
                    public void OnDataRetrieved(List<Playlist_Song> playlist_songs) {
                        for (String playlist_id : playlist_ids) {
                            for (Playlist_Song playlist_song : playlist_songs) {
                                if (playlist_song.getIdPlaylist().equals(playlist_id)) {
                                    playlist_songDao.delete(playlist_song.key, new TaskListener() {
                                        @Override
                                        public void OnSuccess() {
                                            
                                        }

                                        @Override
                                        public void OnFail() {

                                        }
                                    });
                                }
                            }
                        }
                    }
                });
            }
        });
    }

}
