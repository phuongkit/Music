package com.example.myapplication.Module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

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
                "id='" + id + '\'' +
                ", id='" + id + '\'' +
                ", idSong='" + idSong + '\'' +
                ", idPlaylist='" + idPlaylist + '\'' +
                '}';
    }
}
