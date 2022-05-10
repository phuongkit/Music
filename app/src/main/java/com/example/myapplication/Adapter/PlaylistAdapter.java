package com.example.myapplication.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activity.ListMusicActivity;
import com.example.myapplication.Activity.PlaylistActivity;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.PlaylistDao;
import com.example.myapplication.Dialog.InputDialog;
import com.example.myapplication.Module.Playlist;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class PlaylistAdapter  extends ArrayAdapter<Playlist> {

    ImageView imgPlaylist;
    TextView txtPlaylist, txtAuthor;
    ImageButton imgBtnRename, imgBtnDelete;

    View view;
    int position;

    Playlist playlist;

    Activity activity;
    int resource;

    public PlaylistAdapter(@NonNull Activity activity, int resource) {
        super(activity, resource);
        this.activity = activity;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.position = position;
        view = activity.getLayoutInflater().inflate(this.resource, null);
        addControls();
        addEvents();
        return view;
    }

    private void addEvents() {
        imgBtnRename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputDialog inputDialog = new InputDialog();
                Bundle bundle = new Bundle();
                bundle.putBoolean("isUpdate", true);
                bundle.putSerializable("playlist", playlist);
                inputDialog.setArguments(bundle);
                inputDialog.show(((AppCompatActivity)activity).getSupportFragmentManager(), "input dialog");
            }
        });

        imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaylistDao playlistDao = new PlaylistDao();
                playlistDao.delete(playlist.key, new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                        ((PlaylistActivity) activity).initData();
                    }

                    @Override
                    public void OnFail() {
                        Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ListMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("playlist", playlist);
                intent.putExtra("bundle", bundle);
                activity.startActivity(intent);
            }
        });
    }

    private void addControls() {
        imgPlaylist = view.findViewById(R.id.imgPlaylist);
        txtPlaylist = view.findViewById(R.id.txtPlaylist);
        txtAuthor = view.findViewById(R.id.txtAuthor);
        imgBtnRename = view.findViewById(R.id.imgBtnRename);
        imgBtnDelete = view.findViewById(R.id.imgBtnDelete);

        playlist = getItem(position);
        Glide.with(getContext()).load(playlist.getImage()).error(R.drawable.ic_playlist).into(imgPlaylist);
        txtPlaylist.setText(playlist.getName());
        FirebaseAuth auth = FirebaseAuth.getInstance();
        txtAuthor.setText(auth.getCurrentUser().getDisplayName().toString());
    }
}
