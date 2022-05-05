package com.example.myapplication.Module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Chude implements Serializable {
    @Exclude
    public String id;

    private String idChude;
    private String hinh;
    private String ten;

    public Chude() {
    }

    public Chude(String idChude, String ten,String hinh) {
        this.idChude = idChude;
        this.hinh = hinh;
        this.ten = ten;
    }

    public String getIdChude() {
        return idChude;
    }

    public void setIdChude(String idChude) {
        this.idChude = idChude;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    @Override
    public String toString() {
        return "Chude{" +
                "id='" + id + '\'' +
                ", idChude='" + idChude + '\'' +
                ", hinh='" + hinh + '\'' +
                ", ten='" + ten + '\'' +
                '}';
    }
}
