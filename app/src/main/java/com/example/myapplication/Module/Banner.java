package com.example.myapplication.Module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Banner implements Serializable {
    @Exclude
    public String id;

    private String idBanner;
    private String hinhAnh;
    private String noiDung;
    private String idBaihat;

    public Banner(){

    }

    public Banner(String idBanner, String hinhAnh, String noiDung, String idBaihat) {
        this.idBanner = idBanner;
        this.hinhAnh = hinhAnh;
        this.noiDung = noiDung;
        this.idBaihat = idBaihat;
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

    public String getIdBaihat() {
        return idBaihat;
    }

    public void setIdBaihat(String idBaihat) {
        this.idBaihat = idBaihat;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id='" + id + '\'' +
                ", idBanner='" + idBanner + '\'' +
                ", hinhAnh='" + hinhAnh + '\'' +
                ", noiDung='" + noiDung + '\'' +
                ", idBaihat='" + idBaihat + '\'' +
                '}';
    }
}
