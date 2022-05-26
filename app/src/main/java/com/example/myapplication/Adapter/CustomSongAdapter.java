package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.PlaylistDao;
import com.example.myapplication.Dao.Playlist_SongDao;
import com.example.myapplication.Dialog.AddPlaylistBySongDialog;
import com.example.myapplication.Fragment.MusicFragment;
import com.example.myapplication.Generic.GeneralHandling;
import com.example.myapplication.Generic.ThreadDao.GetSongWithListThemeAndListTypes;
import com.example.myapplication.Generic.ThreadDao.GetTheme;
import com.example.myapplication.Generic.ThreadDao.GetTypes;
import com.example.myapplication.Model.Playlist;
import com.example.myapplication.Model.Playlist_Song;
import com.example.myapplication.Model.Theme;
import com.example.myapplication.Model.Types;
import com.example.myapplication.R;
import com.example.myapplication.Model.Song;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomSongAdapter extends ArrayAdapter<Song> implements Filterable {

    MusicFragment musicFragment;
    Activity activity;
    ViewHolder viewHolder;
    FirebaseUser user;
    ArrayList<Playlist> playlists;
    Playlist playlist;
    Boolean isPlaylist = false;

    public CustomSongAdapter(Activity activity, int resource, List<Song> objects) {
        super(activity, resource, objects);
        this.activity = activity;
    }

    public CustomSongAdapter(Activity activity, int resource, List<Song> objects, Playlist playlist, MusicFragment musicFragment) {
        super(activity, resource, objects);
        this.activity = activity;
        this.playlist = playlist;
        this.musicFragment = musicFragment;
        this.isPlaylist = true;
    }

    static class ViewHolder {
        TextView tvMusicListIndex, tvTenBaiHatMusicList, tvTenCaSiMusicList, txtOptionsMenu;
        ImageView imgTopsong;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.song_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvMusicListIndex = convertView.findViewById(R.id.tvMusicListIndex);
            viewHolder.tvTenBaiHatMusicList = convertView.findViewById(R.id.tvTenBaiHatMusicList);
            viewHolder.tvTenCaSiMusicList = convertView.findViewById(R.id.tvTenCaSiMusicList);
            viewHolder.imgTopsong = convertView.findViewById(R.id.imageViewtop);
            viewHolder.txtOptionsMenu = convertView.findViewById(R.id.txtOptionsMenu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Song song = getItem(position);
        Glide.with(getContext()).load(song.getImage()).error(R.drawable.song).into(viewHolder.imgTopsong);

        viewHolder.tvMusicListIndex.setText(String.valueOf(position + 1));
        viewHolder.tvTenBaiHatMusicList.setText(song.getName());
        viewHolder.tvTenCaSiMusicList.setText(song.getSinger());

        viewHolder.txtOptionsMenu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                //inflating menu from xml resource
                popup.inflate(R.menu.song_menu);
                popup.setGravity(Gravity.NO_GRAVITY);

                MenuItem menuItem = popup.getMenu().findItem(R.id.item_remove_from_playlist);
                if (!isPlaylist) {
                    menuItem.setVisible(false);
                }
                //adding click listener
                popup.setOnMenuItemClickListener(menuItem1 -> {
                    switch (menuItem1.getItemId()) {
                        case R.id.item_add_to_favorites:
                            addToFavorites(song);
                            return true;
                        case R.id.item_add_to_playlist:
                            addToPlaylist(song);
                            return true;
                        case R.id.item_remove_from_playlist:
                            removeFromPlaylist(song);
                            return true;
                        case R.id.item_download:
                            download(song);
                            return true;
                    }
                    return false;
                });
                //displaying the popup
                popup.show();
            }
        });

        return convertView;
    }

    private void addToFavorites(Song song) {

    }

    private void addToPlaylist(Song song) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        playlists = new ArrayList<>();

        String uid = user.getUid();
        PlaylistDao playlistDao = new PlaylistDao();
        playlistDao.getPlaylistByUser(uid, new RetrieValEventListener<List<Playlist>>() {
            @Override
            public void OnDataRetrieved(List<Playlist> playlistByUsers) {
                playlists = (ArrayList<Playlist>) playlistByUsers;

                playlistDao.getPlaylistBySong(song.getId(), new RetrieValEventListener<List<Playlist>>() {
                    @Override
                    public void OnDataRetrieved(List<Playlist> playlistBySongs) {
                        AddPlaylistBySongDialog dialog = new AddPlaylistBySongDialog(activity, playlists, (ArrayList<Playlist>) playlistBySongs, song);
                        dialog.show();
                    }
                });
            }
        });
    }

    private void getData(List<Playlist> Playlists, String uid) {
        for (Playlist Playlist : Playlists) {
            if (Playlist.getUid().equals(uid)) {
                playlists.add(Playlist);
            }
        }
    }

    private void removeFromPlaylist(Song song) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Thiết lập tiêu đề
        builder.setTitle(activity.getString(R.string.strTitleWarning));
        // Thiết lập icon
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        // Thiết lập nội dung cho dialog
        builder.setMessage(activity.getString(R.string.strMessageDeleteSongFromPlaylist));
        // Thiết lập các nút lệnh cho người dùng tương tác
        builder.setPositiveButton("Có", (dialogInterface, i) -> {
            Playlist_SongDao playlist_songDao = new Playlist_SongDao();
            playlist_songDao.getAll(new RetrieValEventListener<List<Playlist_Song>>() {
                @Override
                public void OnDataRetrieved(List<Playlist_Song> playlist_songs) {
                    for (Playlist_Song playlist_song : playlist_songs) {
                        if (playlist_song.getIdPlaylist().equals(playlist.getId()) && playlist_song.getIdSong().equals(song.getId())) {
                            playlist_songDao.delete(playlist_song.key, new TaskListener() {
                                @Override
                                public void OnSuccess() {
                                    musicFragment.getListMusicByPlaylist(playlist);
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
            });
            dialogInterface.dismiss();
        });
        builder.setNegativeButton("Không", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            clear();
        });
        // Tạo cửa sổ Dialog
        AlertDialog dialog = builder.create();
        // Tắt tự động đóng cửa sổ khi nhấn ngoài vùng Dialog
        dialog.setCanceledOnTouchOutside(false);
        // Hiển thị cửa sổ
        dialog.show();
    }

    private void download(Song song) {
//        6.54
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReferenceFromUrl(song.getLinkSong());

        File rootPath = new File(Environment.getExternalStorageDirectory(), "Music");
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath, ref.getName());


        ref.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
            Log.i("firebase", ";local tem file created  created " + localFile);
            //  updateDb(timestamp,localFile.toString(),position);
            Toast.makeText(activity, activity.getString(R.string.strMessageSuccessDownload) + " " + song.getName(), Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(exception -> {
            Log.i("firebase", ";local tem file not created  created " + exception);
            Toast.makeText(activity, activity.getString(R.string.strMessageFailDownload) + " " + song.getName(), Toast.LENGTH_SHORT).show();
        });
    }
}
