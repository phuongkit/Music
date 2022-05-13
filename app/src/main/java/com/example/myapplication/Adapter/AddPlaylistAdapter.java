package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Dao.Listeners.RetrieNewKeyEventListener;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.Playlist_SongDao;
import com.example.myapplication.Dao.UserDao;
import com.example.myapplication.Module.MusicObject;
import com.example.myapplication.Module.Playlist;
import com.example.myapplication.Module.Playlist_Song;
import com.example.myapplication.Module.Song;
import com.example.myapplication.Module.User;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AddPlaylistAdapter extends ArrayAdapter<Playlist> {

    ViewHolder viewHolder;
    ArrayList<Playlist> playlists;
    ArrayList<Playlist> playlists_by_song;
    Song song;

    public AddPlaylistAdapter(@NonNull Context context, int resource, ArrayList<Playlist> playlists, ArrayList<Playlist> playlists_by_song, Song song) {
        super(context, resource);
        this.playlists = playlists;
        this.playlists_by_song = playlists_by_song;
        this.song = song;
    }

    class ViewHolder {
        TextView txtListIndex, txtName, txtDetail;
        ImageView imgAvatar;
        CheckBox cbPlaylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.add_playlist_item, null);
            viewHolder = new AddPlaylistAdapter.ViewHolder();
            viewHolder.txtListIndex = convertView.findViewById(R.id.txtListIndex);
            viewHolder.txtName = convertView.findViewById(R.id.txtName);
            viewHolder.txtDetail = convertView.findViewById(R.id.txtDetail);
            viewHolder.imgAvatar = convertView.findViewById(R.id.imgAvatar);
            viewHolder.cbPlaylist = convertView.findViewById(R.id.cbPlaylist);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AddPlaylistAdapter.ViewHolder) convertView.getTag();
        }

        Playlist playlist = getItem(position);
        Picasso.with(getContext()).load(playlist.getImage()).into(viewHolder.imgAvatar);

        viewHolder.txtName.setText(playlist.getName());
        viewHolder.txtDetail.setText("User Anonymous");
        UserDao userDao = new UserDao();
        userDao.getAll(new RetrieValEventListener<List<User>>() {
            @Override
            public void OnDataRetrieved(List<User> users) {
                for (User user : users) {
                    if (user.getId().equals(playlist.getId())) {
                        viewHolder.txtDetail.setText(user.getDisplayName());
                        break;
                    }
                }
            }
        });

        boolean check = false;

        for (Playlist playlist1 : playlists_by_song) {
            if (playlist1.getId().equals(playlist.getId())) {
                check = true;
            }
        }

        viewHolder.cbPlaylist.setChecked(check);

        viewHolder.cbPlaylist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Playlist_SongDao playlist_songDao = new Playlist_SongDao();
                if (b) {
                    playlist_songDao.getAll(new RetrieValEventListener<List<Playlist_Song>>() {
                        @Override
                        public void OnDataRetrieved(List<Playlist_Song> playlist_songs) {
                            if (b) {
                                Playlist_Song playlist_song = new Playlist_Song();
                                List<MusicObject> musicObjects = playlist_song.upCastList(playlist_songs);
                                String new_id = playlist_song.GetNewId(musicObjects);
                                playlist_song = new Playlist_Song(new_id, playlist.getId(), song.getId());
                                Playlist_Song finalPlaylist_song = playlist_song;
                                playlist_songDao.getNewKey(new RetrieNewKeyEventListener() {
                                    @Override
                                    public void OnNewKeyRetrieved(String newKey) {
                                        playlist_songDao.save(finalPlaylist_song, newKey, new TaskListener() {
                                            @Override
                                            public void OnSuccess() {
                                                Toast.makeText(getContext(), "Successful to added song to playlist", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void OnFail() {
                                                Toast.makeText(getContext(), "Failed to add song to playlist", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            } else {
                                for (Playlist_Song playlist_song : playlist_songs) {
                                    if (playlist_song.getIdPlaylist().equals(playlist.getId()) && playlist_song.getIdSong().equals(song.id)) {
                                        playlist_songDao.delete(playlist_song.key, new TaskListener() {
                                            @Override
                                            public void OnSuccess() {
                                                Toast.makeText(getContext(), "Successful to removed song to playlist", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void OnFail() {
                                                Toast.makeText(getContext(), "Failed to remove song to playlist", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        break;
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });

        return convertView;
    }
}
