package com.example.myapplication.Generic;

import com.example.myapplication.Model.Playlist;
import com.example.myapplication.Model.Song;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralHandling {

    public ArrayList<Song> combineBothListSong(ArrayList<Song> songs, ArrayList<Song> mSongs) {

        for (Song song : mSongs) {
            if (getSongByList(songs, song) == -1) {
                songs.add(song);
            }
        }
        return songs;
    }

    public int getSongByList(ArrayList<Song> songs, Song song) {
        int i = 0;
        for (Song mSong : songs) {
            if (mSong.getId().equals(song.getId())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int getPlaylistByList(ArrayList<Playlist> playlists, Playlist playlist) {
        int i = 0;
        for (Playlist mPlaylist : playlists) {
            if (mPlaylist.getId().equals(playlist.getId())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public boolean isEmailValid(CharSequence email) {
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        String expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isUserNameValid(CharSequence name) {
        String expression = "[\\w.-]{3,30}";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
