package com.example.myapplication.Module;

import java.io.Serializable;

public class AccountType implements Serializable {
    public int id;
    private String idAccountType;
    private String name;
    private int idIcon;

    public AccountType() {
    }

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
