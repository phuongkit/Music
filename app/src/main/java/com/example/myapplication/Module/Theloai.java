package com.example.myapplication.Module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Theloai implements Serializable {
    @Exclude
    public String id;

    private String idTheloai;
    private String hinh;
    private String ten;
    private String idChude;

    public Theloai() {
    }

    public Theloai(String idTheloai, String ten, String hinh, String idChude) {
        this.idTheloai = idTheloai;
        this.hinh = hinh;
        this.ten = ten;
        this.idChude = idChude;
    }

    public String getIdTheloai() {
        return idTheloai;
    }

    public void setIdTheloai(String idTheloai) {
        this.idTheloai = idTheloai;
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

    public String getIdChude() {
        return idChude;
    }

    public void setIdChude(String idChude) {
        this.idChude = idChude;
    }

    @Override
    public String toString() {
        return "Theloai{" +
                "id='" + id + '\'' +
                ", idTheloai='" + idTheloai + '\'' +
                ", hinh='" + hinh + '\'' +
                ", ten='" + ten + '\'' +
                ", idChude='" + idChude + '\'' +
                '}';
    }
}
