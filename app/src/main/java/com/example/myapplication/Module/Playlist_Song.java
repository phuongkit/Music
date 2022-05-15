package com.example.myapplication.Module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Playlist_Song extends MusicObject implements Serializable {
    private String idSong;
    private String idPlaylist;

    public Playlist_Song() {
    }

    public Playlist_Song(String id, String idSong, String idPlaylist) {
        super(id);
        this.idSong = idSong;
        this.idPlaylist = idPlaylist;
    }

    public String getIdSong() {
        return idSong;
    }

    public void setIdSong(String idSong) {
        this.idSong = idSong;
    }

    public String getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(String idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    @Override
    public String toString() {
        return "Playlist_Song{" +
                "key='" + key + '\'' +
                ", id='" + id + '\'' +
                ", idSong='" + idSong + '\'' +
                ", idPlaylist='" + idPlaylist + '\'' +
                '}';
    }

    public List<MusicObject> upCastList(List<Playlist_Song> playlist_songs) {
        List<MusicObject> musicObjects = new ArrayList<>();
        for (Playlist_Song playlist_song : playlist_songs) {
            musicObjects.add(playlist_song);
        }
        return musicObjects;
    }
}
