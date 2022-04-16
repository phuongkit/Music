package com.example.myapplication.module;

import java.io.Serializable;

public class Banner implements Serializable {
    private String id;
    private String hinhAnh;
    private String noiDung;
    private Baihat baihat;

    public Banner(){

    }

    public Banner(String id, String hinhAnh, String noiDung, Baihat baihat) {
        this.id = id;
        this.hinhAnh = hinhAnh;
        this.noiDung = noiDung;
        this.baihat = baihat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
