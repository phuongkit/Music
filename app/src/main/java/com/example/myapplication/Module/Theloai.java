package com.example.myapplication.Module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Theloai implements Serializable {
    @Exclude
    public String id;

    private String idTheloai;
    private String hinh;
    private String ten;
    private Chude chude;

    public Theloai() {
    }

    public Theloai(String idTheloai, String hinh, String ten, Chude chude) {
        this.idTheloai = idTheloai;
        this.hinh = hinh;
        this.ten = ten;
        this.chude = chude;
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

    public Chude getChude() {
        return chude;
    }

    public void setChude(Chude chude) {
        this.chude = chude;
    }

    @Override
    public String toString() {
        return "Theloai{" +
                "id='" + id + '\'' +
                ", idTheloai='" + idTheloai + '\'' +
                ", hinh='" + hinh + '\'' +
                ", ten='" + ten + '\'' +
                ", chude=" + chude +
                '}';
    }
}
