package com.example.myapplication.Generic.ThreadDao;

import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.TypesDao;
import com.example.myapplication.Model.Types;

import java.util.ArrayList;
import java.util.List;

public class GetTypes {
    private ArrayList<Types> typess;
    private boolean available = false;

    public GetTypes() {
        Producer producer = new Producer(this);
        producer.start();
        try {
            producer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized ArrayList<Types> get() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        available = false;
        notifyAll();
        return typess;
    }

    public synchronized void put(ArrayList<Types> typess) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.typess = typess;
        available = true;
        notifyAll();
    }

    class Producer extends Thread {
        private GetTypes getTypes;

        public Producer(GetTypes getTypes) {
            this.getTypes = getTypes;
            TypesDao typesDao = new TypesDao();
            typesDao.getAll(new RetrieValEventListener<List<Types>>() {
                @Override
                public void OnDataRetrieved(List<Types> typess) {
                    getTypes.put((ArrayList<Types>) typess);
                }
            });
        }

        public void run() {
            try {
                sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}