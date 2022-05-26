package com.example.myapplication.Generic.ThreadDao;

import android.util.Log;

import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.ThemeDao;
import com.example.myapplication.Model.Theme;

import java.util.ArrayList;
import java.util.List;

public class GetTheme {
    private ArrayList<Theme> themes;
    private boolean available = false;
    int test = 0;

    public GetTheme() {
        Producer producer = new Producer(this);
        producer.start();
    }

    public synchronized ArrayList<Theme> get() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        }
        available = false;
        notifyAll();
        return themes;
    }

    public synchronized void put(ArrayList<Theme> themes) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        }
        this.themes = themes;
        available = true;
        notifyAll();
    }

    static class Producer extends Thread {
        private GetTheme getTheme;

        public Producer(GetTheme getTheme) {
            this.getTheme = getTheme;
        }

        public void run() {
            for (int i = 0; i < 100; i++) {
                ThemeDao themeDao = new ThemeDao();
                themeDao.getAll(new RetrieValEventListener<List<Theme>>() {
                    @Override
                    public void OnDataRetrieved(List<Theme> themes) {

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
}