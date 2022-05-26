package com.example.myapplication.Model;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class MusicObject implements Serializable {
    @Exclude
    public String key;

    private String id;

    String[] classNames = new String[] {
            User.class.getName(),
            Song.class.getName(),
            Banner.class.getName(),
            Album.class.getName(),
            Theme.class.getName(),
            Types.class.getName(),
            Playlist.class.getName(),
            Playlist_Song.class.getName()
    };

    String[] firebaseNames = new String[] {
            "user",
            "song",
            "banner",
            "album",
            "theme",
            "types",
            "playlist",
            "playlist_song"
    };

    int countClass = 8;

    public MusicObject() {
    }

    public MusicObject(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewId(List<MusicObject> musicObjects) {
        int id = 0;
        for (MusicObject musicObject : musicObjects) {
            int old_id = Integer.parseInt(musicObject.getId());
            if (old_id > id) {
                id = old_id;
            }
        }
        id++;
        return String.valueOf(id);
    }

    @NonNull
    @Override
    public String toString() {
        return "MusicObject{" +
                "key='" + key + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getFirebaseName(String className) {
        String firebaseName =  firebaseNames[0];
        for (int i = 0; i < countClass; i++) {
            if (className.equals(classNames[i])) {
                firebaseName = firebaseNames[i];
                break;
            }
        }
        return firebaseName;
    }

    public List<MusicObject> upCastListBanner(List<Banner> banners) {
        return new ArrayList<>(banners);
    }

    public List<MusicObject> upCastListPlaylist(List<Playlist> playlists) {
        return new ArrayList<>(playlists);
    }

    public List<MusicObject> upCastListPlaylistSong(List<Playlist_Song> playlist_songs) {
        return new ArrayList<>(playlist_songs);
    }
}
