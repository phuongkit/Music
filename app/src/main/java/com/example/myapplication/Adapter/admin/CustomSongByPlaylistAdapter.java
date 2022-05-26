package com.example.myapplication.Adapter.admin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activity.admin.CRUDDaoActivity;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.Playlist_SongDao;
import com.example.myapplication.Model.Playlist;
import com.example.myapplication.Model.Song;
import com.example.myapplication.R;

import java.util.List;

public class CustomSongByPlaylistAdapter extends ArrayAdapter<Song> {

    Activity activity;
    int resource;

    Playlist playlist;

    public CustomSongByPlaylistAdapter(Activity activity, int resource, List<Song> songs, Playlist playlist) {
        super(activity, resource, songs);
        this.activity = activity;
        this.resource = resource;
        this.playlist = playlist;
    }

    static class ViewHolder {
        TextView txtListIndex, txtTitle, txtDetail, txtOptionsMenu;
        ImageView imgViewTop;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(this.resource, null);
            viewHolder = new ViewHolder();
            viewHolder.txtListIndex = convertView.findViewById(R.id.tvMusicListIndex);
            viewHolder.txtTitle = convertView.findViewById(R.id.tvTenBaiHatMusicList);
            viewHolder.txtDetail = convertView.findViewById(R.id.tvTenCaSiMusicList);
            viewHolder.imgViewTop = convertView.findViewById(R.id.imageViewtop);
            viewHolder.txtOptionsMenu = convertView.findViewById(R.id.txtOptionsMenu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Song song = getItem(position);
        Glide.with(getContext()).load(song.getImage()).error(R.drawable.song).into(viewHolder.imgViewTop);

        viewHolder.txtListIndex.setText(String.valueOf(position + 1));
        viewHolder.txtTitle.setText(song.getName());
        viewHolder.txtDetail.setText(song.getSinger());


        viewHolder.txtOptionsMenu.setText("");
        viewHolder.txtOptionsMenu.setBackgroundResource(R.drawable.remove);
        viewHolder.txtOptionsMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Playlist_SongDao playlist_songDao = new Playlist_SongDao();
                playlist_songDao.deletePlaylistSongByIdPlaylistAndIdSong(playlist.getId(), song.getId(), new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        Toast.makeText(activity, activity.getString(R.string.strNotifySuccessDelete) + " " + activity.getString(R.string.strHeaderSong) + " " + song.getName(), Toast.LENGTH_SHORT).show();
                        ((CRUDDaoActivity)activity).loadListViewSong();
                    }

                    @Override
                    public void OnFail() {
                        Toast.makeText(activity, activity.getString(R.string.strNotifyFailDelete) + " " + activity.getString(R.string.strHeaderSong) + " " + song.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return convertView;
    }
}