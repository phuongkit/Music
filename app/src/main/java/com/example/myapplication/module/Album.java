package com.example.myapplication.module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Album  implements Serializable {
    @Exclude
    public String id;

    private String idAlbum;
    private String tenAlbum;
    private String tenCaSiAlbum;

    public Album() {
    }

    public Album(String idAlbum, String tenAlbum, String tenCaSiAlbum) {
        this.idAlbum = idAlbum;
        this.tenAlbum = tenAlbum;
        this.tenCaSiAlbum = tenCaSiAlbum;
    }

    public String getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(String idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getTenAlbum() {
        return tenAlbum;
    }

    public void setTenAlbum(String tenAlbum) {
        this.tenAlbum = tenAlbum;
    }

    public String getTenCasiAlbum() {
        return tenCaSiAlbum;
    }

    public void setTenCasiAlbum(String tenCaSiAlbum) {
        this.tenCaSiAlbum = tenCaSiAlbum;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id='" + id + '\'' +
                ", idAlbum='" + idAlbum + '\'' +
                ", tenAlbum='" + tenAlbum + '\'' +
                ", tenCaSiAlbum='" + tenCaSiAlbum + '\'' +
                '}';
    }
}
