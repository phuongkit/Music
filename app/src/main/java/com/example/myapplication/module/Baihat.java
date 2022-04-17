package com.example.myapplication.module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Baihat implements Serializable {
    @Exclude
    public String id;

    private String tenBaihat;
    private String hinhBaihat;
    private String idAlbum;
    private String idBaihat;
    private String idPlaylist;
    private String caSi;
    private String linkBaihat;


    public Baihat(){

    }

    public String getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(String idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getIdBaihat() {
        return idBaihat;
    }

    public void setIdBaihat(String idBaihat) {
        this.idBaihat = idBaihat;
    }

    public String getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(String idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public String getCaSi() {
        return caSi;
    }

    public void setCaSi(String caSi) {
        this.caSi = caSi;
    }

    public String getLinkBaihat() {
        return linkBaihat;
    }

    public void setLinkBaihat(String linkBaihat) {
        this.linkBaihat = linkBaihat;
    }

    public String getTenBaihat() {
        return tenBaihat;
    }

    public void setTenBaihat(String tenBaihat) {
        this.tenBaihat = tenBaihat;
    }

    public String getHinhBaihat() {
        return hinhBaihat;
    }

    public void setHinhBaihat(String hinhBaihat) {
        this.hinhBaihat = hinhBaihat;
    }

    public Baihat(String idBaihat, String tenBaihat, String hinhBaihat, String caSi, String linkBaihat) {
        this.idBaihat = idBaihat;
        this.tenBaihat = tenBaihat;
        this.hinhBaihat = hinhBaihat;
        this.caSi = caSi;
        this.linkBaihat = linkBaihat;
    }

    @Override
    public String toString() {
        return "Baihat{" +
                "tenBaihat='" + tenBaihat + '\'' +
                ", hinhBaihat='" + hinhBaihat + '\'' +
                ", idAlbum='" + idAlbum + '\'' +
                ", idBaihat='" + idBaihat + '\'' +
                ", idPlaylist='" + idPlaylist + '\'' +
                ", caSi='" + caSi + '\'' +
                ", linkBaihat='" + linkBaihat + '\'' +
                '}';
    }
}

