package com.example.myapplication.Generic.Beans;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Libary implements Serializable {
    @Exclude
    public String id;

    private String idLibary;
    private String name;
    private int idIcon;

    public Libary(String idLibary, String name, int idIcon) {
        this.idLibary = idLibary;
        this.name = name;
        this.idIcon = idIcon;
    }

    public String getIdLibary() {
        return idLibary;
    }

    public void setIdLibary(String idLibary) {
        this.idLibary = idLibary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdIcon() {
        return idIcon;
    }

    public void setIdIcon(int idIcon) {
        this.idIcon = idIcon;
    }

    @NonNull
    @Override
    public String toString() {
        return "Libary{" +
                "id=" + id +
                ", idLibary=" + idLibary +
                ", name='" + name + '\'' +
                ", idIcon=" + idIcon +
                '}';
    }
}
