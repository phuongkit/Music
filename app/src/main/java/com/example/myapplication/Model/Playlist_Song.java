package com.example.myapplication.Model;

import androidx.annotation.NonNull;

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

    @NonNull
    @Override
    public String toString() {
        return "Playlist_Song{" +
                "key='" + key + '\'' +
                ", id='" + super.getId() + '\'' +
                ", idSong='" + idSong + '\'' +
                ", idPlaylist='" + idPlaylist + '\'' +
                '}';
    }
}
