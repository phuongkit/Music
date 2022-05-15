package com.example.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activity.admin.SongDaoActivity;
import com.example.myapplication.Dao.Listeners.RetrieNewKeyEventListener;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.Playlist_SongDao;
import com.example.myapplication.Dao.SongDao;
import com.example.myapplication.Dao.UserDao;
import com.example.myapplication.Fragment.MusicFragment;
import com.example.myapplication.Module.Album;
import com.example.myapplication.Module.MusicObject;
import com.example.myapplication.Module.Playlist;
import com.example.myapplication.Module.Playlist_Song;
import com.example.myapplication.Module.Song;
import com.example.myapplication.Module.Types;
import com.example.myapplication.Module.User;
import com.example.myapplication.R;
import com.example.myapplication.Service.AddPlaylistImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddPlaylistAdapter extends ArrayAdapter<Playlist> {

    public ArrayList<AddPlaylistImpl> addPlaylistImpls;

    Activity activity;
    int resource;

    ArrayList<View> views;
    static ArrayList<ViewHolder> viewHolders;
    static ArrayList<Playlist> playlists_byViewHolder;
    ArrayList<Playlist> playlists;
    ArrayList<Playlist> playlists_by_song;
    ArrayList<Playlist> playlists_byCheckChange;
    ArrayList<Boolean> old_checks;
    ArrayList<Boolean> new_checks;
    Playlist_Song playlist_song;
    boolean newCheck;

    Song song;

    public AddPlaylistAdapter(@NonNull Activity activity, int resource, ArrayList<Playlist> playlists, ArrayList<Playlist> playlists_by_song, Song song) {
        super(activity, resource);
        this.activity = activity;
        this.resource = resource;
        this.playlists = playlists;
        this.playlists_by_song = playlists_by_song;
        this.song = song;
        addPlaylistImpls = new ArrayList<>();
        views = new ArrayList<>();
        viewHolders = new ArrayList<>();
        playlists_byViewHolder = new ArrayList<>();
        playlists_byCheckChange = new ArrayList<>();
        old_checks = new ArrayList<>();
        new_checks = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            convertView = inflater.inflate(R.layout.add_playlist_item, null);
            viewHolder = new ViewHolder();
            viewHolder.txtListIndex = convertView.findViewById(R.id.txtListIndex);
            viewHolder.txtName = convertView.findViewById(R.id.txtName);
            viewHolder.txtDetail = convertView.findViewById(R.id.txtDetail);
            viewHolder.imgViewtop = convertView.findViewById(R.id.imgViewtop);
            viewHolder.cbPlaylist = convertView.findViewById(R.id.cbPlaylist);
            convertView.setTag(viewHolder);
            views.add(position, convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Playlist playlist = getItem(position);
        viewHolder.playlist = playlist;

        Glide.with(activity).load(playlist.getImage()).error(R.drawable.ic_playlist).into(viewHolder.imgViewtop);

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

        viewHolder.isCheck = false;

        for (Playlist playlist1 : playlists_by_song) {
            if (playlist1.getId().equals(playlist.getId())) {
                viewHolder.isCheck = true;
            }
        }

        newCheck = true;

        viewHolder.cbPlaylist.setChecked(viewHolder.isCheck);

        final ViewHolder viewHolder1 = viewHolder;

        viewHolder.cbPlaylist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Boolean contain = playlists_byCheckChange.contains(playlist);
                if (viewHolder1.isCheck != b && !contain) {
                    playlists_byCheckChange.add(playlist);
                    old_checks.add(!b);
                    new_checks.add(b);
                } else {
                    if (contain) {
                        int index = playlists_byCheckChange.indexOf(playlist);
                        if (old_checks.get(index) == b) {
                            playlists_byCheckChange.remove(index);
                            old_checks.remove(index);
                            new_checks.remove(index);
                        } else {
                            new_checks.remove(index);
                            new_checks.add(index, b);
                        }
                    }
                }
            }
        });

        AddPlaylistImpl addPlaylistImpl = new AddPlaylistImpl() {
            @Override
            public void setSelectAll(Boolean isCheck) {
                if (viewHolder1.cbPlaylist.isChecked() != isCheck) {
                    viewHolder1.cbPlaylist.setChecked(isCheck);
                }
            }
        };
        addPlaylistImpls.add(addPlaylistImpl);

        return convertView;
    }

    public View getView(int position) {
        return views.get(position);
    }


    public void saveChange() {
        playlist_song = new Playlist_Song();
        for (int i = 0; i < playlists_byCheckChange.size(); i++) {
            Boolean old_check = old_checks.get(i);
            Boolean new_check = new_checks.get(i);
            if (old_check != new_check) {
                Playlist playlist = playlists_byCheckChange.get(i);
                Playlist_SongDao playlist_songDao = new Playlist_SongDao();
                playlist_songDao.getAll(new RetrieValEventListener<List<Playlist_Song>>() {
                    @Override
                    public void OnDataRetrieved(List<Playlist_Song> playlist_songs) {
                        if (new_check) {
                            List<MusicObject> musicObjects = playlist_song.upCastList(playlist_songs);
                            String new_id = playlist_song.GetNewId(musicObjects);
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
                                if (playlist_song1.getIdPlaylist().equals(playlist.getId()) && playlist_song1.getIdSong().equals(song.id)) {
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
        playlists_byCheckChange = new ArrayList<>();
        old_checks = new ArrayList<>();
        new_checks = new ArrayList<>();
    }

    public ArrayList<AddPlaylistImpl> getAddPlaylistImpls() {
        return addPlaylistImpls;
    }

    public void setAddPlaylistImpls(ArrayList<AddPlaylistImpl> addPlaylistImpls) {
        this.addPlaylistImpls = addPlaylistImpls;
    }

    class ViewHolder {
        TextView txtListIndex, txtName, txtDetail;
        ImageView imgViewtop;
        CheckBox cbPlaylist;
        Playlist playlist;
        Boolean isCheck;
    }
}
