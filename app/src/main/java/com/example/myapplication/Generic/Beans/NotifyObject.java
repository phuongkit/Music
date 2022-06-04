package com.example.myapplication.Generic.Beans;

import java.io.Serializable;

public class NotifyObject implements Serializable {
    private String link;
    private int status;
    private int progress;

    public NotifyObject(String link, int status, int progress) {
        this.link = link;
        this.status = status;
        this.progress = progress;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "NotifyObject{" +
                "link='" + link + '\'' +
                ", status=" + status +
                ", progress=" + progress +
                '}';
    }
}
