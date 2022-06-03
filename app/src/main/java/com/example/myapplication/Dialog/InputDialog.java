package com.example.myapplication.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.Activity.PlaylistActivity;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.PlaylistDao;
import com.example.myapplication.Model.MusicObject;
import com.example.myapplication.Model.Playlist;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InputDialog extends AppCompatDialogFragment {
    Activity activity;
    TextView txtData;
    EditText edtData;
    boolean isUpdate = false;
    Playlist playlist = new Playlist();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_input, null);

        Bundle bundle = getArguments();
        isUpdate = bundle.getBoolean("isUpdate", false);


        builder.setView(view);
        String title = "";
        if (isUpdate) {
            title = getString(R.string.strUpdatePlaylist);
            playlist = (Playlist) bundle.getSerializable("playlist");

        } else {
            title = getString(R.string.strCreatePlaylist);
        }
        builder.setTitle(title);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String name = edtData.getText().toString();
                    String uid = user.getUid();
                    PlaylistDao playlistDao = new PlaylistDao();
                    if (isUpdate) {
                        playlist.setName(name);
                        playlistDao.getAll(new RetrieValEventListener<List<Playlist>>() {
                            @Override
                            public void OnDataRetrieved(List<Playlist> playlists) {
                                playlistDao.save(playlist, playlist.key, new TaskListener() {
                                    @Override
                                    public void OnSuccess() {
                                        Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                                        ((PlaylistActivity) activity).initData();
                                    }

                                    @Override
                                    public void OnFail() {
                                        Toast.makeText(activity, "Fail", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    } else {
                        playlistDao.getAll(new RetrieValEventListener<List<Playlist>>() {
                            @Override
                            public void OnDataRetrieved(List<Playlist> playlists) {
                                boolean isExist = false;
                                for (Playlist playlist : playlists) {
                                    if (playlist.getUid().equals(uid) && playlist.getName().equals(name)) {
                                        isExist = true;
                                        break;
                                    }
                                }

                                if (!isExist) {
                                    Date date = new Date();
                                    String dateString = formatter.format(date);
                                    Playlist playlist = new Playlist();
                                    MusicObject musicObject = new MusicObject();
                                    String new_id = musicObject.getNewId(musicObject.upCastListPlaylist(playlists));
                                    playlist = new Playlist(new_id, name, "", false, uid , dateString);
                                    playlistDao.save(playlist, playlistDao.getNewKey(), new TaskListener() {
                                        @Override
                                        public void OnSuccess() {
                                            Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                                            ((PlaylistActivity) activity).initData();
                                        }

                                        @Override
                                        public void OnFail() {
                                            Toast.makeText(activity, "Fail", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(getActivity(), "No user", Toast.LENGTH_SHORT).show();
                }
            }
        });
        edtData = view.findViewById(R.id.edtData);
        return builder.create();
    }
}
