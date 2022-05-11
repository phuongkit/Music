package com.example.myapplication.Module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Song extends MusicObject implements Serializable {

    private String name;
    private String image;
    private String idAlbum;
    private String idPlaylist;
    private String singer;
    private String linkSong;


    public Song(){

    }

    public Song(String id, String name, String image, String singer, String linkSong) {
        super(id);
        this.name = name;
        this.image = image;
        this.singer = singer;
        this.linkSong = linkSong;
    }

    public Song(String id, String name, String image, String idAlbum, String idPlaylist, String singer, String linkSong) {
        super.setId(id);
        this.name = name;
        this.image = image;
        this.idAlbum = idAlbum;
        this.idPlaylist = idPlaylist;
        this.singer = singer;
        this.linkSong = linkSong;
    }

    public void update(String id, String name, String image, String idAlbum, String idPlaylist, String singer, String linkSong) {
        super.setId(id);
        this.name = name;
        this.image = image;
        this.idAlbum = idAlbum;
        this.idPlaylist = idPlaylist;
        this.singer = singer;
        this.linkSong = linkSong;
    }

    public Song(String name, String singer, String image, String idAlbum, String idPlaylist, String linkSong) {

        this.name = name;
        this.singer = singer;
        this.image = image;
        this.idAlbum = idAlbum;
        this.idPlaylist = idPlaylist;

        this.linkSong = linkSong;
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