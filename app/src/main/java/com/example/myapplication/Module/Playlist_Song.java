package com.example.myapplication.Module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Playlist_Song implements Serializable {
    @Exclude
    public String key;

    private String id;
    private String idSong;
    private String idPlaylist;

    public Playlist_Song() {
    }

    public Playlist_Song(String id, String idSong, String idPlaylist) {
        this.id = id;
        this.idSong = idSong;
        this.idPlaylist = idPlaylist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
