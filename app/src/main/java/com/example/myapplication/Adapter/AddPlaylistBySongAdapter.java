package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.Playlist_SongDao;
import com.example.myapplication.Dao.UserDao;
import com.example.myapplication.Generic.GeneralHandling;
import com.example.myapplication.Generic.Impls.AddPlaylistOrSongImpl;
import com.example.myapplication.Model.MusicObject;
import com.example.myapplication.Model.Playlist;
import com.example.myapplication.Model.Playlist_Song;
import com.example.myapplication.Model.Song;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AddPlaylistBySongAdapter extends ArrayAdapter<Playlist> {

    ArrayList<AddPlaylistOrSongImpl> addPlaylistOrSongImpls;
    GeneralHandling generalHandling = new GeneralHandling();

    Activity activity;
    int resource;

    ArrayList<Playlist> playlists;
    ArrayList<Playlist> playlistBySongs;
    ArrayList<Playlist> playlistByViewHolders;
    ArrayList<Boolean> old_checks;
    ArrayList<Boolean> new_checks;
    Playlist_Song playlist_song;

    Song song;

    public AddPlaylistBySongAdapter(@NonNull Activity activity, int resource, ArrayList<Playlist> playlists, ArrayList<Playlist> playlistBySongs, Song song) {
        super(activity, resource);
        this.activity = activity;
        this.resource = resource;
        this.playlists = playlists;
        this.playlistBySongs = playlistBySongs;
        this.song = song;
        addPlaylistOrSongImpls = new ArrayList<>();
        playlistByViewHolders = new ArrayList<>();
        old_checks = new ArrayList<>();
        new_checks = new ArrayList<>();
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            convertView = inflater.inflate(R.layout.item_add_playlist, null);
            viewHolder = new ViewHolder();
            viewHolder.txtListIndex = convertView.findViewById(R.id.txtListIndex);
            viewHolder.txtName = convertView.findViewById(R.id.txtName);
            viewHolder.txtDetail = convertView.findViewById(R.id.txtDetail);
            viewHolder.imgViewtop = convertView.findViewById(R.id.imgViewtop);
            viewHolder.cbPlaylist = convertView.findViewById(R.id.cbCheck);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Playlist playlist = getItem(position);

        Glide.with(activity).load(playlist.getImage()).error(R.drawable.ic_playlist).into(viewHolder.imgViewtop);

        viewHolder.txtListIndex.setText(position + 1 + "");
        viewHolder.txtName.setText(playlist.getName());
        viewHolder.txtDetail.setText("User Anonymous");

        UserDao userDao = new UserDao();
        final ViewHolder holder = viewHolder;
        userDao.getAll(new RetrieValEventListener<List<User>>() {
            @Override
            public void OnDataRetrieved(List<User> users) {
                for (User user : users) {
                    if (user.getId().equals(playlist.getId())) {
                        holder.txtDetail.setText(user.getDisplayName());
                        break;
                    }
                }
            }
        });

        boolean isContain = generalHandling.getPlaylistByList(playlistBySongs, playlist) != -1;

        if (!isContain) {
            playlistByViewHolders.add(playlist);
            old_checks.add(false);
            new_checks.add(false);
        }

        viewHolder.cbPlaylist.setChecked(isContain);

        viewHolder.cbPlaylist.setOnCheckedChangeListener((compoundButton, b) -> {
            int positionOfPlaylist = generalHandling.getPlaylistByList(playlistByViewHolders, playlist);
            if (positionOfPlaylist != -1 && new_checks.get(positionOfPlaylist) != b) {
                new_checks.remove(positionOfPlaylist);
                new_checks.add(positionOfPlaylist, b);
            }
        });

        AddPlaylistOrSongImpl addPlaylistOrSongImpl = isCheck -> {
            viewHolder.cbPlaylist.setChecked(isCheck);
        };
        addPlaylistOrSongImpls.add(addPlaylistOrSongImpl);

        return convertView;
    }

    public void saveChange() {
        playlist_song = new Playlist_Song();
        for (int i = 0; i < playlistByViewHolders.size(); i++) {
            Boolean old_check = old_checks.get(i);
            Boolean new_check = new_checks.get(i);
            if (old_check != new_check) {
                Playlist playlist = playlistByViewHolders.get(i);
                Playlist_SongDao playlist_songDao = new Playlist_SongDao();
                playlist_songDao.getAll(new RetrieValEventListener<List<Playlist_Song>>() {
                    @Override
                    public void OnDataRetrieved(List<Playlist_Song> playlist_songs) {
                        if (new_check) {
                            MusicObject musicObject = new MusicObject();
                            String new_id = musicObject.getNewId(musicObject.upCastListPlaylistSong(playlist_songs));
                            playlist_song = new Playlist_Song(new_id, song.getId(), playlist.getId());
                            playlist_songDao.save(playlist_song, playlist_songDao.getNewKey(), new TaskListener() {
                                @Override
                                public void OnSuccess() {
                                    Toast.makeText(getContext(), "Successfully added the song + " + song.getName() + " to the selected playlists", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void OnFail() {
                                    Toast.makeText(getContext(), "Failed to add the song + " + song.getName() + " to selected playlists", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            for (Playlist_Song playlist_song1 : playlist_songs) {
                                if (playlist_song1.getIdPlaylist().equals(playlist.getId()) && playlist_song1.getIdSong().equals(song.getId())) {
                                    playlist_songDao.delete(playlist_song1.key, new TaskListener() {
                                        @Override
                                        public void OnSuccess() {
                                            Toast.makeText(getContext(), "Successfully deleted the song + " + song.getName() + " to the deselected playlists", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void OnFail() {
                                            Toast.makeText(getContext(), "Failed to delete the song + " + song.getName() + " to deselected playlists", Toast.LENGTH_SHORT).show();
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
        old_checks = new_checks;
    }

    public ArrayList<AddPlaylistOrSongImpl> getAddPlaylistImpls() {
        return addPlaylistOrSongImpls;
    }

    public void setAddPlaylistImpls(ArrayList<AddPlaylistOrSongImpl> addPlaylistOrSongImpls) {
        this.addPlaylistOrSongImpls = addPlaylistOrSongImpls;
    }

    static class ViewHolder {
        TextView txtListIndex, txtName, txtDetail;
        ImageView imgViewtop;
        CheckBox cbPlaylist;
    }
}
