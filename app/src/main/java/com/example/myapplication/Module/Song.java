package com.example.myapplication.Module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Song extends MusicObject implements Serializable {

    private String name;
    private String image;
    private String singer;
    private String idTheme;
    private String idTypes;
    private String idAlbum;
    private String idPlaylist;
    private String linkSong;


    public Song(){

    }

    public Song(String id, String name, String image, String singer, String idTheme, String idTypes, String idAlbum, String idPlaylist, String linkSong) {
        super(id);
        this.name = name;
        this.image = image;
        this.singer = singer;
        this.idTheme = idTheme;
        this.idTypes = idTypes;
        this.idAlbum = idAlbum;
        this.idPlaylist = idPlaylist;
        this.linkSong = linkSong;
    }

    public void update(String id, String name, String image, String singer, String idTheme, String idTypes, String idAlbum, String idPlaylist, String linkSong) {
        super.setId(id);
        this.name = name;
        this.image = image;
        this.singer = singer;
        this.idTheme = idTheme;
        this.idTypes = idTypes;
        this.idAlbum = idAlbum;
        this.idPlaylist = idPlaylist;
        this.linkSong = linkSong;
    }

    public String getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(String idTheme) {
        this.idTheme = idTheme;
    }

    public String getIdTypes() {
        return idTypes;
    }

    public void setIdTypes(String idTypes) {
        this.idTypes = idTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(String idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(String idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getLinkSong() {
        return linkSong;
    }

    public void setLinkSong(String linkSong) {
        this.linkSong = linkSong;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", idAlbum='" + idAlbum + '\'' +
                ", id='" + id + '\'' +
                ", idPlaylist='" + idPlaylist + '\'' +
                ", singer='" + singer + '\'' +
                ", linkSong='" + linkSong + '\'' +
                '}';
    }
}