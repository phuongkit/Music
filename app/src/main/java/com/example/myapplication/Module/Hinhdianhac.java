package com.example.myapplication.Module;

public class Hinhdianhac {
    private String linkbaihat;
    private String hinhdianhac;
    private int position;

    public Hinhdianhac(String linkbaihat, String hinhdianhac, int position) {
        this.linkbaihat = linkbaihat;
        this.hinhdianhac = hinhdianhac;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getLinkbaihat() {
        return linkbaihat;
    }

    public void setLinkbaihat(String linkbaihat) {
        this.linkbaihat = linkbaihat;
    }

    public String getHinhdianhac() {
        return hinhdianhac;
    }

    public void setHinhdianhac(String hinhdianhac) {
        this.hinhdianhac = hinhdianhac;
    }
}
