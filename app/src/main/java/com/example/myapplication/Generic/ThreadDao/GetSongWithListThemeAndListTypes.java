package com.example.myapplication.Generic.ThreadDao;

import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.SongDao;
import com.example.myapplication.Dao.ThemeDao;
import com.example.myapplication.Generic.GeneralHandling;
import com.example.myapplication.Model.Song;
import com.example.myapplication.Model.Theme;
import com.example.myapplication.Model.Types;

import java.util.ArrayList;
import java.util.List;

public class GetSongWithListThemeAndListTypes {
    GeneralHandling generalHandling = new GeneralHandling();
    private ArrayList<Song> songs;
    private boolean availableTheme = false;
    private boolean availableTypes = false;

    public GetSongWithListThemeAndListTypes(ArrayList<Song> songs, ArrayList<Theme> themes, ArrayList<Types> typess) {
        Producer producer = new Producer(this, themes, typess);
        producer.start();
    }

    public synchronized ArrayList<Song> get() {
        while (availableTheme == false || availableTypes == false) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        availableTheme = false;
        availableTypes = false;
        notifyAll();
        return songs;
    }

    public synchronized void putSongByTheme(ArrayList<Song> songs) {
        while (availableTheme == true) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.songs = generalHandling.combineBothListSong(this.songs, songs);
        availableTheme = true;
        notifyAll();
    }

    public synchronized void putSongByTypes(ArrayList<Song> songs) {
        while (availableTypes == true) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.songs = generalHandling.combineBothListSong(this.songs, songs);
        availableTypes = true;
        notifyAll();
    }

    static class Producer extends Thread {
        private GetSongWithListThemeAndListTypes getSong;
        private ArrayList<Theme> themes;
        private ArrayList<Types> typess;

        public Producer(GetSongWithListThemeAndListTypes getSong, ArrayList<Theme> themes, ArrayList<Types> typess) {
            this.getSong = getSong;
            this.themes = themes;
            this.typess = typess;
        }

        public void run() {
            SongDao songDao = new SongDao();
            songDao.getSongByListTheme(themes, new RetrieValEventListener<List<Song>>() {
                @Override
                public void OnDataRetrieved(List<Song> songs) {
                    getSong.putSongByTheme((ArrayList<Song>) songs);
                }
            });
            songDao.getSongByListTypes(typess, new RetrieValEventListener<List<Song>>() {
                @Override
                public void OnDataRetrieved(List<Song> songs) {
                    getSong.putSongByTypes((ArrayList<Song>) songs);
                }
            });
            try {
                sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
