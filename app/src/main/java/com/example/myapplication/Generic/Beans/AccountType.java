package com.example.myapplication.Generic.Beans;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class AccountType implements Serializable {
    @Exclude
    public String id;

    private String idAccountType;
    private String name;
    private int idIcon;

    public AccountType(String idAccountType, String name, int idIcon) {
        this.idAccountType = idAccountType;
        this.name = name;
        this.idIcon = idIcon;
    }

    public String getIdAccountType() {
        return idAccountType;
    }

    public void setIdAccountType(String idAccountType) {
        this.idAccountType = idAccountType;
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
        return "AccountType{" +
                "id=" + id +
                ", idAccountType='" + idAccountType + '\'' +
                ", name='" + name + '\'' +
                ", idIcon='" + idIcon + '\'' +
                '}';
    }
}
