package com.example.myapplication.Module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Types implements Serializable {
    @Exclude
    public String key;

    private String id;
    private String name;
    private String image;
    private String idTheme;

    public Types() {
    }

    public Types(String id, String name, String image, String idTheme) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.idTheme = idTheme;
    }

    public void update(String id, String name, String image, String idTheme) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.idTheme = idTheme;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(String idTheme) {
        this.idTheme = idTheme;
    }

    @Override
    public String toString() {
        return "Types{" +
                "id='" + id + '\'' +
                ", id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", idTheme='" + idTheme + '\'' +
                '}';
    }
}

