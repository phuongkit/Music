package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.PlaylistDao;
import com.example.myapplication.Dao.Playlist_SongDao;
import com.example.myapplication.Dialog.Add_Playlist_Dialog;
import com.example.myapplication.Fragment.MusicFragment;
import com.example.myapplication.Module.Playlist;
import com.example.myapplication.Module.Playlist_Song;
import com.example.myapplication.R;
import com.example.myapplication.Module.Song;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.android.SqlPersistenceStorageEngine;
import com.google.firebase.database.core.persistence.PersistenceStorageEngine;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomSongAdapter extends ArrayAdapter<Song> {

    MusicFragment musicFragment;
    Activity activity;
    ViewHolder viewHolder;
    FirebaseUser user;
    ArrayList<Playlist> playlists;
    Playlist playlist;
    Boolean isPlaylist;

    public CustomSongAdapter(Activity activity, int resource, List<Song> objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.isPlaylist = false;
    }

    public CustomSongAdapter(Activity activity, int resource, List<Song> objects, Playlist playlist, MusicFragment musicFragment) {
        super(activity, resource, objects);
        this.activity = activity;
        this.playlist = playlist;
        this.musicFragment = musicFragment;
        this.isPlaylist = true;
    }

    class ViewHolder {
        TextView tvMusicListIndex, tvTenBaiHatMusicList, tvTenCaSiMusicList, txtOptionsMenu;
        ImageView imgTopsong;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d("Test", "Node F");
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
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                //creating a popup menu
//                ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getContext(), R.style.PopupMenuOverlapAnchor);
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                //inflating menu from xml resource
                popup.inflate(R.menu.song_menu);
                popup.setGravity(Gravity.NO_GRAVITY);

                MenuItem menuItem = popup.getMenu().findItem(R.id.item_remove_from_playlist);
                if (!isPlaylist) {
                    menuItem.setVisible(false);
                }
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
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
                    }
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
        playlistDao.getAll(new RetrieValEventListener<List<Playlist>>() {
            @Override
            public void OnDataRetrieved(List<Playlist> Playlists) {
                ArrayList<String> playlist_ids = new ArrayList<>();
                ArrayList<Playlist> playlists_bysong = new ArrayList<>();
                for (Playlist Playlist : Playlists) {
                    if (Playlist.getUid().equals(uid)) {
                        playlists.add(Playlist);
                    }
                }
                Playlist_SongDao playlist_songDao = new Playlist_SongDao();
                playlist_songDao.getAll(new RetrieValEventListener<List<Playlist_Song>>() {
                    @Override
                    public void OnDataRetrieved(List<Playlist_Song> playlist_songs) {
                        for (Playlist_Song Playlist_Song : playlist_songs) {
                            for (Playlist Playlist : playlists) {
                                if (Playlist_Song.getIdPlaylist().equals(Playlist.getId()) && Playlist_Song.getIdSong().equals(song.id)) {
                                    playlists_bysong.add(Playlist);
                                }
                            }
                        }
                        Add_Playlist_Dialog dialog = new Add_Playlist_Dialog(activity, playlists, playlists_bysong, song);
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
        builder.setTitle(activity.getString(R.string.strtitleWarning));
        // Thiết lập icon
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        // Thiết lập nội dung cho dialog
        builder.setMessage(activity.getString(R.string.strMessageDeleteSongFromPlaylist));
        // Thiết lập các nút lệnh cho người dùng tương tác
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Playlist_SongDao playlist_songDao = new Playlist_SongDao();
                playlist_songDao.getAll(new RetrieValEventListener<List<Playlist_Song>>() {
                    @Override
                    public void OnDataRetrieved(List<Playlist_Song> playlist_songs) {
                        for (Playlist_Song playlist_song : playlist_songs) {
                            if (playlist_song.getIdPlaylist().equals(playlist.getId()) && playlist_song.getIdSong().equals(song.id)) {
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
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                clear();
            }
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

        Log.d("Test", ref.getName());

        File rootPath = new File(Environment.getExternalStorageDirectory(), "Music");
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath, ref.getName());


        ref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.i("firebase", ";local tem file created  created " + localFile.toString());
                //  updateDb(timestamp,localFile.toString(),position);
                Toast.makeText(activity, activity.getString(R.string.strMessageSuccessDownload) + " " + song.getName(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i("firebase", ";local tem file not created  created " + exception.toString());
                Toast.makeText(activity, activity.getString(R.string.strMessageFailDownload) + " " + song.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
