package com.example.myapplication.module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Banner implements Serializable {
    @Exclude
    public String id;

    private String idBanner;
    private String hinhAnh;
    private String noiDung;
    private Baihat baihat;

    public Banner(){

    }

    public Banner(String idBanner, String hinhAnh, String noiDung, Baihat baihat) {
        this.idBanner = idBanner;
        this.hinhAnh = hinhAnh;
        this.noiDung = noiDung;
        this.baihat = baihat;
    }

    public String getIdBanner() {
        return idBanner;
    }

    public void setIdBanner(String idBanner) {
        this.idBanner = idBanner;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Baihat getBaihat() {
        return baihat;
    }

    public void setBaihat(Baihat baihat) {
        this.baihat = baihat;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id='" + id + '\'' +
                ", idBanner='" + idBanner + '\'' +
                ", hinhAnh='" + hinhAnh + '\'' +
                ", noiDung='" + noiDung + '\'' +
                ", baihat=" + baihat +
                '}';
    }
}
