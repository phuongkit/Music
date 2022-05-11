package com.example.myapplication.Dao.Listeners;

public abstract class RetrieValEventListener<T> extends AbstractEventListener {
    public abstract void OnDataRetrieved(T t);
}
