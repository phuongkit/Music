package com.example.myapplication.Activity.admin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Adapter.admin.CustomSongByPlaylistAdapter;
import com.example.myapplication.Adapter.admin.SpinnerDaoAdapter;
import com.example.myapplication.Dao.AlbumDao;
import com.example.myapplication.Dao.SongDao;
import com.example.myapplication.Dao.BannerDao;
import com.example.myapplication.Dao.ThemeDao;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.PlaylistDao;
import com.example.myapplication.Dao.TypesDao;
import com.example.myapplication.Dao.UserDao;
import com.example.myapplication.Dialog.AddSongByPlaylistDialog;
import com.example.myapplication.Model.Album;
import com.example.myapplication.Model.Song;
import com.example.myapplication.Model.Banner;
import com.example.myapplication.Generic.Beans.Item;
import com.example.myapplication.Model.Playlist;
import com.example.myapplication.Model.Theme;
import com.example.myapplication.Model.Types;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CRUDDaoActivity extends AppCompatActivity {

    User user;
    ArrayList<User> users;
    ArrayList<String> textUser = new ArrayList<>();
    ArrayList<String> hintUser = new ArrayList<>();

    Song song;
    ArrayList<Song> songs;
    ArrayList<Song> songByPlaylists;
    ArrayList<String> textSong = new ArrayList<>();
    ArrayList<String> hintSong = new ArrayList<>();

    Banner banner;
    ArrayList<Banner> banners;
    ArrayList<String> textBanner = new ArrayList<>();
    ArrayList<String> hintBanner = new ArrayList<>();

    Album album;
    ArrayList<Album> albums;
    ArrayList<String> textAlbum = new ArrayList<>();
    ArrayList<String> hintAlbum = new ArrayList<>();

    Theme theme;
    ArrayList<Theme> themes;
    ArrayList<String> textTheme = new ArrayList<>();
    ArrayList<String> hintTheme = new ArrayList<>();

    Types types;
    ArrayList<Types> typess;
    ArrayList<String> textTypes = new ArrayList<>();
    ArrayList<String> hintTypes = new ArrayList<>();

    Playlist playlist;
    ArrayList<Playlist> playlists;
    ArrayList<String> textPlaylist = new ArrayList<>();
    ArrayList<String> hintPlaylist = new ArrayList<>();

    ArrayList<TextView> textViews = new ArrayList<>();
    ArrayList<EditText> editTexts = new ArrayList<>();
    ArrayList<Spinner> spinners = new ArrayList<>();
    ListView lvSong;
    CustomSongByPlaylistAdapter customSongByPlaylistAdapter;

    Toolbar toolbar;
    ActionBar actionBar;
    Button btnUpdate, btnCancel;


    String key;
    SpinnerDaoAdapter spinnerDaoAdapter, spinnerDaoAdapter1;
    ArrayList<Item> items, items1;

    Item item, item1;
    Boolean isAdd = true;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    private static final int MODULE_USER = 0;
    private static final int MODULE_SONG = 1;
    private static final int MODULE_BANNER = 2;
    private static final int MODULE_THEME = 3;
    private static final int MODULE_TYPES = 4;
    private static final int MODULE_ALBUM = 5;
    private static final int MODULE_PLAYLIST = 6;

    private int mCurrentModule = MODULE_SONG;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_dao);
        activity = this;
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        String control = bundle.getString("control");
        String module = bundle.getString("module");
        mCurrentModule = getCurentModule(module);
        bundle.remove("control");
        bundle.remove("module");
        mapping();
        addEvents();
        initGUI();
        if (control.equals("add")) {
            initData();
            addObject();
        } else if (control.equals("repair")) {
            isAdd = false;
            key = bundle.getString("key");
            bundle.remove("key");
            updateObject();
            btnUpdate.setText(getString(R.string.strBtnSave));
        }
    }

    private void addEvents() {
        btnCancel.setOnClickListener(view -> finish());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void initGUI() {
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        linearLayout.setVisibility(View.GONE);

        TableLayout.LayoutParams paramContainer = new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );

        TableRow.LayoutParams paramElements = new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );

        int paddingDp = 8;
        float densityDp = getResources().getDisplayMetrics().density;
        int paddingPixel = (int) (paddingDp * densityDp);

        int sizeSp = 7;
        float densitySp = getResources().getDisplayMetrics().scaledDensity;
        int sizePixel = (int) (sizeSp * densitySp);

        switch (mCurrentModule) {
            case MODULE_USER:
                actionBar.setTitle(R.string.strHeaderUser);

                textUser.add(getString(R.string.strHeaderId));
                textUser.add(getString(R.string.strHeaderUsername));
                textUser.add(getString(R.string.strHeaderPassword));
                textUser.add(getString(R.string.strHeaderEmail));
                textUser.add(getString(R.string.strHeaderPhone));
                textUser.add(getString(R.string.strHeaderAvatar));
                textUser.add(getString(R.string.strHeaderPermission));

                hintUser.add(getString(R.string.strHintId));
                hintUser.add(getString(R.string.strHintUsername));
                hintUser.add(getString(R.string.strHintPassword));
                hintUser.add(getString(R.string.strHintEmail));
                hintUser.add(getString(R.string.strHintPhone));
                hintUser.add(getString(R.string.strHintAvatar));
                for (int i = 0; i < 7; i++) {
                    TableRow tableRow = new TableRow(this);
                    tableRow.setPadding(0, paddingDp, 0, paddingDp);

                    TextView textView = new TextView(this);
                    textView.setText(textUser.get(i) + ":");
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizePixel);

                    tableRow.addView(textView, 0, paramElements);
                    textViews.add(textView);

                    if (i != 6) {
                        EditText editText = new EditText(this);
                        editText.setHint(hintUser.get(i));
                        editText.setEms(10);
                        setUiView(editText);

                        tableRow.addView(editText, 1, paramElements);
                        editTexts.add(editText);
                    } else {
                        Spinner spinner = new Spinner(this);
                        spinner.setPadding(0, paddingPixel, 0, paddingPixel);
                        setUiView(spinner);

                        tableRow.addView(spinner, 1, paramElements);
                        spinners.add(spinner);
                        tableRow.setPadding(0, paddingPixel, 0, paddingPixel);
                    }
                    tableLayout.addView(tableRow, i, paramContainer);
                }

                break;
            case MODULE_SONG:
                actionBar.setTitle(R.string.strHeaderSong);

                textSong.add(getString(R.string.strHeaderId));
                textSong.add(getString(R.string.strHeaderName));
                textSong.add(getString(R.string.strHeaderImage));
                textSong.add(getString(R.string.strHeaderAlbum));
                textSong.add(getString(R.string.strHeaderPlaylist));
                textSong.add(getString(R.string.strHeaderSinger));
                textSong.add(getString(R.string.strHeaderLinkSong));

                hintSong.add(getString(R.string.strHintId));
                hintSong.add(getString(R.string.strHintName));
                hintSong.add(getString(R.string.strHintImage));
                hintSong.add(getString(R.string.strHintSinger));
                hintSong.add(getString(R.string.strHintLinkSong));
                for (int i = 0; i < 7; i++) {
                    TableRow tableRow = new TableRow(this);
                    tableRow.setPadding(0, paddingDp, 0, paddingDp);

                    TextView textView = new TextView(this);
                    textView.setText(textSong.get(i) + ":");
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizePixel);

                    tableRow.addView(textView, 0, paramElements);
                    textViews.add(textView);

                    if (i == 3 || i == 4) {
                        Spinner spinner = new Spinner(this);
                        spinner.setPadding(0, paddingPixel, 0, paddingPixel);
                        setUiView(spinner);

                        tableRow.addView(spinner, 1, paramElements);
                        spinners.add(spinner);
                        tableRow.setPadding(0, paddingPixel, 0, paddingPixel);
                    } else {
                        EditText editText = new EditText(this);
                        editText.setHint(hintSong.get(i < 3 ? i : i - 2));
                        editText.setEms(10);
                        setUiView(editText);

                        tableRow.addView(editText, 1, paramElements);
                        editTexts.add(editText);
                    }

                    tableLayout.addView(tableRow, i, paramContainer);
                }
                break;
            case MODULE_BANNER:
                actionBar.setTitle(R.string.strHeaderBanner);

                textBanner.add(getString(R.string.strHeaderId));
                textBanner.add(getString(R.string.strHeaderName));
                textBanner.add(getString(R.string.strHeaderImage));
                textBanner.add(getString(R.string.strHeaderSong));

                hintBanner.add(getString(R.string.strHintId));
                hintBanner.add(getString(R.string.strHintName));
                hintBanner.add(getString(R.string.strHintImage));
                for (int i = 0; i < 4; i++) {
                    TableRow tableRow = new TableRow(this);
                    tableRow.setPadding(0, paddingDp, 0, paddingDp);

                    TextView textView = new TextView(this);
                    textView.setText(textBanner.get(i) + ":");
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizePixel);

                    tableRow.addView(textView, 0, paramElements);
                    textViews.add(textView);

                    if (i == 3) {
                        Spinner spinner = new Spinner(this);
                        spinner.setPadding(0, paddingPixel, 0, paddingPixel);
                        setUiView(spinner);

                        tableRow.addView(spinner, 1, paramElements);
                        spinners.add(spinner);
                        tableRow.setPadding(0, paddingPixel, 0, paddingPixel);
                    } else {
                        EditText editText = new EditText(this);
                        editText.setHint(hintBanner.get(i));
                        editText.setEms(10);
                        setUiView(editText);

                        tableRow.addView(editText, 1, paramElements);
                        editTexts.add(editText);
                    }

                    tableLayout.addView(tableRow, i, paramContainer);
                }
                break;
            case MODULE_ALBUM:
                actionBar.setTitle(R.string.strHeaderAlbum);

                textAlbum.add(getString(R.string.strHeaderId));
                textAlbum.add(getString(R.string.strHeaderName));
                textAlbum.add(getString(R.string.strHeaderImage));
                textAlbum.add(getString(R.string.strHeaderSinger));

                hintAlbum.add(getString(R.string.strHintId));
                hintAlbum.add(getString(R.string.strHintName));
                hintAlbum.add(getString(R.string.strHintImage));
                hintAlbum.add(getString(R.string.strHintSinger));
                for (int i = 0; i < 4; i++) {
                    TableRow tableRow = new TableRow(this);
                    tableRow.setPadding(0, paddingDp, 0, paddingDp);

                    TextView textView = new TextView(this);
                    textView.setText(textAlbum.get(i) + ":");
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizePixel);

                    tableRow.addView(textView, 0, paramElements);
                    textViews.add(textView);

                    EditText editText = new EditText(this);
                    editText.setHint(hintAlbum.get(i));
                    editText.setEms(10);
                    setUiView(editText);

                    tableRow.addView(editText, 1, paramElements);
                    editTexts.add(editText);

                    tableLayout.addView(tableRow, i, paramContainer);
                }
                break;
            case MODULE_THEME:
                actionBar.setTitle(R.string.strHeaderTheme);

                textTheme.add(getString(R.string.strHeaderId));
                textTheme.add(getString(R.string.strHeaderName));
                textTheme.add(getString(R.string.strHeaderImage));

                hintTheme.add(getString(R.string.strHintId));
                hintTheme.add(getString(R.string.strHintName));
                hintTheme.add(getString(R.string.strHintImage));
                for (int i = 0; i < 3; i++) {
                    TableRow tableRow = new TableRow(this);
                    tableRow.setPadding(0, paddingDp, 0, paddingDp);

                    TextView textView = new TextView(this);
                    textView.setText(textTheme.get(i) + ":");
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizePixel);

                    tableRow.addView(textView, 0, paramElements);
                    textViews.add(textView);

                    EditText editText = new EditText(this);
                    editText.setHint(hintTheme.get(i));
                    editText.setEms(10);
                    setUiView(editText);

                    tableRow.addView(editText, 1, paramElements);
                    editTexts.add(editText);

                    tableLayout.addView(tableRow, i, paramContainer);
                }
                break;
            case MODULE_TYPES:
                actionBar.setTitle(R.string.strHeaderTypes);

                textTypes.add(getString(R.string.strHeaderId));
                textTypes.add(getString(R.string.strHeaderName));
                textTypes.add(getString(R.string.strHeaderImage));

                hintTypes.add(getString(R.string.strHintId));
                hintTypes.add(getString(R.string.strHintName));
                hintTypes.add(getString(R.string.strHintImage));
                for (int i = 0; i < 3; i++) {
                    TableRow tableRow = new TableRow(this);
                    tableRow.setPadding(0, paddingDp, 0, paddingDp);

                    TextView textView = new TextView(this);
                    textView.setText(textTypes.get(i) + ":");
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizePixel);

                    tableRow.addView(textView, 0, paramElements);
                    textViews.add(textView);

                    EditText editText = new EditText(this);
                    editText.setHint(hintTypes.get(i));
                    editText.setEms(10);
                    setUiView(editText);

                    tableRow.addView(editText, 1, paramElements);
                    editTexts.add(editText);

                    tableLayout.addView(tableRow, i, paramContainer);
                }
                break;
            case MODULE_PLAYLIST:
                actionBar.setTitle(R.string.strHeaderPlaylist);

                textPlaylist.add(getString(R.string.strHeaderId));
                textPlaylist.add(getString(R.string.strHeaderName));
                textPlaylist.add(getString(R.string.strHeaderImage));
                textPlaylist.add(getString(R.string.strHeaderPermission));
                textPlaylist.add(getString(R.string.strHeaderUser));
                textPlaylist.add(getString(R.string.strHeaderDateCreated));

                hintPlaylist.add(getString(R.string.strHintId));
                hintPlaylist.add(getString(R.string.strHintName));
                hintPlaylist.add(getString(R.string.strHintImage));
                hintPlaylist.add(getString(R.string.strHintDateCreated));
                for (int i = 0; i < 6; i++) {
                    TableRow tableRow = new TableRow(this);
                    tableRow.setPadding(0, paddingDp, 0, paddingDp);

                    TextView textView = new TextView(this);
                    textView.setText(textPlaylist.get(i) + ":");
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizePixel);

                    tableRow.addView(textView, 0, paramElements);
                    textViews.add(textView);

                    if (i == 3 || i == 4) {
                        Spinner spinner = new Spinner(this);
                        spinner.setPadding(0, paddingPixel, 0, paddingPixel);
                        setUiView(spinner);

                        tableRow.addView(spinner, 1, paramElements);
                        spinner.setPadding(0, paddingPixel, 0, paddingPixel);

                        spinners.add(spinner);
                        tableRow.setPadding(0, paddingPixel, 0, paddingPixel);
                    } else {
                        EditText editText = new EditText(this);
                        editText.setHint(hintPlaylist.get(i < 3 ? i : i - 2));
                        editText.setEms(10);
                        setUiView(editText);

                        tableRow.addView(editText, 1, paramElements);
                        editTexts.add(editText);
                    }
                    tableLayout.addView(tableRow, i, paramContainer);
                }
                linearLayout.setVisibility(View.VISIBLE);
                songByPlaylists = new ArrayList<>();

                break;
        }
        editTexts.get(0).setEnabled(false);
    }

    private void initData() {
        items = new ArrayList<>();
        items.add(new Item("", getString(R.string.noValue)));

        items1 = new ArrayList<>();
        items1.add(new Item("", getString(R.string.noValue)));

        switch (mCurrentModule) {
            case MODULE_USER:
                items.remove(0);
                items.add(new Item("0", getString(R.string.strHeaderAdmin)));
                items.add(new Item("1", getString(R.string.strHeaderUser)));
                int position = 1;
                if (!isAdd && user.getRole() == 0) {
                    position = 0;
                }
                spinnerDaoAdapter = new SpinnerDaoAdapter(CRUDDaoActivity.this, android.R.layout.simple_spinner_item, items);
                spinnerDaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinners.get(0).setAdapter(spinnerDaoAdapter);
                spinners.get(0).setSelection(position);
                spinners.get(0).setEnabled(false);
                spinners.get(0).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                        spinnerDaoAdapter.setSelectedItem(arg2);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                        spinnerDaoAdapter.setSelectedItem(-1);
                    }
                });
                break;
            case MODULE_SONG:
                AlbumDao alBumDao = new AlbumDao();
                alBumDao.getAll(new RetrieValEventListener<List<Album>>() {
                    @Override
                    public void OnDataRetrieved(List<Album> mAlbums) {
                        albums = new ArrayList<>();
                        albums = (ArrayList<Album>) mAlbums;
                        int position = 0;
                        for (int i = 0; i < albums.size(); i++) {
                            String id = albums.get(i).getId();
                            String name = albums.get(i).getName();
                            if (!isAdd && id.equals(song.getId())) {
                                position = i;
                            }
                            item = new Item(id, name);
                            items.add(item);
                        }
                        spinnerDaoAdapter = new SpinnerDaoAdapter(CRUDDaoActivity.this, android.R.layout.simple_spinner_item, items);
                        spinnerDaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinners.get(0).setAdapter(spinnerDaoAdapter);
                        spinners.get(0).setSelection(position);
                        spinners.get(0).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                                spinnerDaoAdapter.setSelectedItem(arg2);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub
                                spinnerDaoAdapter.setSelectedItem(-1);
                            }
                        });
                    }
                });
                PlaylistDao playlistDao = new PlaylistDao();
                playlistDao.getAll(new RetrieValEventListener<List<Playlist>>() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void OnDataRetrieved(List<Playlist> mPlaylists) {
                        playlists = new ArrayList<>();
                        playlists = (ArrayList<Playlist>) mPlaylists;
                        int position = 0;
                        for (int i = 0; i < playlists.size(); i++) {
                            String id = playlists.get(i).getId();
                            String name = playlists.get(i).getName();
                            if (!isAdd && id.equals(song.getId())) {
                                position = i;
                            }
                            item1 = new Item(id, name);
                            items1.add(item1);
                        }
                        spinnerDaoAdapter1 = new SpinnerDaoAdapter(CRUDDaoActivity.this, android.R.layout.simple_spinner_item, items1);
                        spinnerDaoAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinners.get(1).setAdapter(spinnerDaoAdapter1);
                        spinners.get(1).setSelection(position);
                        spinners.get(1).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                                spinnerDaoAdapter1.setSelectedItem(arg2);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub
                                spinnerDaoAdapter1.setSelectedItem(-1);
                            }
                        });
                    }
                });
                break;
            case MODULE_BANNER:
                SongDao songDao = new SongDao();
                songDao.getAll(new RetrieValEventListener<List<Song>>() {
                    @Override
                    public void OnDataRetrieved(List<Song> mSongs) {
                        songs = new ArrayList<>();
                        songs = (ArrayList<Song>) mSongs;
                        int position = 0;
                        for (int i = 0; i < songs.size(); i++) {
                            String id = songs.get(i).getId();
                            String name = songs.get(i).getName();
                            if (!isAdd && id.equals(banner.getIdSong())) {
                                position = i;
                            }
                            item = new Item(id, name);
                            items.add(item);
                        }
                        spinnerDaoAdapter = new SpinnerDaoAdapter(CRUDDaoActivity.this, android.R.layout.simple_spinner_item, items);
                        spinnerDaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinners.get(0).setAdapter(spinnerDaoAdapter);
                        spinners.get(0).setSelection(position);
                        spinners.get(0).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                                spinnerDaoAdapter.setSelectedItem(arg2);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub
                                spinnerDaoAdapter.setSelectedItem(-1);
                            }
                        });
                    }
                });
                break;
            case MODULE_PLAYLIST:
                items.remove(0);
                items.add(new Item("0", getString(R.string.strHeaderAdmin)));
                items.add(new Item("1", getString(R.string.strHeaderUser)));
                position = 1;
                if (!isAdd && playlist.isAdmin()) {
                    position = 0;
                }
                spinnerDaoAdapter = new SpinnerDaoAdapter(CRUDDaoActivity.this, android.R.layout.simple_spinner_item, items);
                spinnerDaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinners.get(0).setAdapter(spinnerDaoAdapter);
                spinners.get(0).setSelection(position);
                spinners.get(0).setEnabled(false);
                spinners.get(0).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                        spinnerDaoAdapter.setSelectedItem(arg2);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                        spinnerDaoAdapter.setSelectedItem(-1);
                    }
                });
                UserDao userDao = new UserDao();
                userDao.getAll(new RetrieValEventListener<List<User>>() {
                    @Override
                    public void OnDataRetrieved(List<User> mUsers) {
                        users = new ArrayList<>();
                        users = (ArrayList<User>) mUsers;
                        int position = 0;
                        for (int i = 0; i < users.size(); i++) {
                            String id = users.get(i).getId();
                            String name = users.get(i).getDisplayName();
                            name = name.equals("") ? "User" : name;
                            if (!isAdd && id.equals(playlist.getUid())) {
                                position = i;
                            }
                            item = new Item(id, name);
                            items1.add(item);
                        }
                        spinnerDaoAdapter1 = new SpinnerDaoAdapter(CRUDDaoActivity.this, android.R.layout.simple_spinner_item, items1);
                        spinnerDaoAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinners.get(1).setAdapter(spinnerDaoAdapter1);
                        spinners.get(1).setSelection(position);
                        spinners.get(1).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                                spinnerDaoAdapter.setSelectedItem(arg2);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub
                                spinnerDaoAdapter.setSelectedItem(-1);
                            }
                        });
                    }
                });
                ImageButton imgBtnInsertSong = findViewById(R.id.imgBtnInsertSong);
                imgBtnInsertSong.setOnClickListener(view -> {
                    SongDao songDao1 = new SongDao();
                    songDao1.getAll(new RetrieValEventListener<List<Song>>() {
                        @Override
                        public void OnDataRetrieved(List<Song> mSongs) {
                            songs = (ArrayList<Song>) mSongs;
                            if (isAdd) {
                                AddSongByPlaylistDialog dialog = new AddSongByPlaylistDialog(activity, songs, songByPlaylists);
                                dialog.show();
                            } else {
                                songDao1.getSongByPlaylist(playlist.getId(), new RetrieValEventListener<List<Song>>() {
                                    @Override
                                    public void OnDataRetrieved(List<Song> mSongs) {
                                        songByPlaylists = (ArrayList<Song>) mSongs;
                                        AddSongByPlaylistDialog dialog = new AddSongByPlaylistDialog(activity, songs, songByPlaylists, playlist);
                                        dialog.show();
                                    }
                                });
                            }
                        }
                    });
                });
                break;
        }
    }

    private void repairDao() {
        switch (mCurrentModule) {
            case MODULE_USER:
                UserDao userDao = new UserDao();
                userDao.save(user, key, new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifySuccess), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnFail() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifyFail), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case MODULE_SONG:
                SongDao songDao = new SongDao();
                songDao.save(song, key, new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifySuccess), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnFail() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifyFail), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case MODULE_BANNER:
                BannerDao bannerDao = new BannerDao();
                bannerDao.save(banner, key, new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifySuccess), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnFail() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifyFail), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case MODULE_ALBUM:
                AlbumDao albumDao = new AlbumDao();
                albumDao.save(album, key, new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifySuccess), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnFail() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifyFail), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case MODULE_THEME:
                ThemeDao themeDao = new ThemeDao();
                themeDao.save(theme, key, new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifySuccess), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnFail() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifyFail), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case MODULE_TYPES:
                TypesDao typesDao = new TypesDao();
                typesDao.save(types, key, new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifySuccess), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnFail() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifyFail), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case MODULE_PLAYLIST:
                break;
        }
    }

    private void postDao() {
        switch (mCurrentModule) {
            case MODULE_SONG:
                SongDao songDao = new SongDao();
                songDao.save(song, songDao.getNewKey(), new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifySuccess), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnFail() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifyFail), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case MODULE_BANNER:
                BannerDao bannerDao = new BannerDao();
                bannerDao.save(banner, bannerDao.getNewKey(), new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifySuccess), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnFail() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifyFail), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case MODULE_ALBUM:
                AlbumDao albumDao = new AlbumDao();
                albumDao.save(album, albumDao.getNewKey(), new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifySuccess), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnFail() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifyFail), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case MODULE_THEME:
                ThemeDao themeDao = new ThemeDao();
                themeDao.save(theme, themeDao.getNewKey(), new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifySuccess), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnFail() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifyFail), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case MODULE_TYPES:
                TypesDao typesDao = new TypesDao();
                typesDao.save(types, typesDao.getNewKey(), new TaskListener() {
                    @Override
                    public void OnSuccess() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifySuccess), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnFail() {
                        Toast.makeText(CRUDDaoActivity.this, getString(R.string.strNotifyFail), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case MODULE_PLAYLIST:
                break;
        }
    }

    @SuppressLint("ResourceAsColor")
    private void addObject() {
        switch (mCurrentModule) {
            case MODULE_USER:
                editTexts.get(0).setText(getString(R.string.strHintNotTypeId));

                btnUpdate.setOnClickListener(view -> {
                    String displayName = editTexts.get(1).getText().toString();
                    String password = editTexts.get(2).getText().toString();
                    String email = editTexts.get(3).getText().toString();
                    String phone = editTexts.get(4).getText().toString();
                    String avatar = editTexts.get(5).getText().toString();
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener(authResult -> {
                                user = new User(Objects.requireNonNull(authResult.getUser()).getUid(), displayName, password, email, phone, avatar);
                                postDao();
                                Intent intent1 = new Intent(activity, UserDaoActivity.class);
                                startActivity(intent1);
                                finish();
                            })
                            .addOnFailureListener(e -> {

                            });
                });
                break;
            case MODULE_SONG:
                SongDao songDao = new SongDao();
                editTexts.get(0).setText(songDao.getNewKey());
                btnUpdate.setOnClickListener(view -> {
                    String id = editTexts.get(0).getText().toString();
                    String name = editTexts.get(1).getText().toString();
                    String image = editTexts.get(2).getText().toString();
                    String idTheme = "";
                    String idTypes = "";
                    item = (Item) spinners.get(0).getSelectedItem();
                    String idAlbum = item.getId();
                    item1 = (Item) spinners.get(1).getSelectedItem();
                    String idPlaylist = item1.getId();
                    String singer = editTexts.get(3).getText().toString();
                    String linkSong = editTexts.get(4).getText().toString();
                    song = new Song(id, name, image, singer, idTheme, idTypes, idAlbum, idPlaylist, linkSong);

                    postDao();
                    Intent intent1 = new Intent(activity, SongDaoActivity.class);
                    startActivity(intent1);
                    finish();
                });
                break;
            case MODULE_BANNER:
                BannerDao bannerDao = new BannerDao();
                editTexts.get(0).setText(bannerDao.getNewKey());
                btnUpdate.setOnClickListener(view -> {
                    String id = editTexts.get(0).getText().toString();
                    String name = editTexts.get(1).getText().toString();
                    String image = editTexts.get(2).getText().toString();
                    item = (Item) spinners.get(0).getSelectedItem();
                    String idBaihat = item.getId();
                    banner = new Banner(id, name, image, idBaihat);

                    postDao();
                    Intent intent1 = new Intent(activity, BannerDaoActivity.class);
                    startActivity(intent1);
                    finish();
                });
                break;
            case MODULE_ALBUM:
                AlbumDao albumDao = new AlbumDao();
                editTexts.get(0).setText(albumDao.getNewKey());
                btnUpdate.setOnClickListener(view -> {
                    String id = editTexts.get(0).getText().toString();
                    String name = editTexts.get(1).getText().toString();
                    String image = editTexts.get(2).getText().toString();
                    String singer = editTexts.get(3).getText().toString();
                    album = new Album(id, name, image, singer);

                    postDao();
                    Intent intent1 = new Intent(activity, AlbumDaoActivity.class);
                    startActivity(intent1);
                    finish();
                });
                break;
            case MODULE_THEME:
                ThemeDao themeDao = new ThemeDao();
                editTexts.get(0).setText(themeDao.getNewKey());
                btnUpdate.setOnClickListener(view -> {
                    String id = editTexts.get(0).getText().toString();
                    String name = editTexts.get(1).getText().toString();
                    String image = editTexts.get(2).getText().toString();
                    theme = new Theme(id, name, image);

                    postDao();
                    Intent intent1 = new Intent(activity, ThemeDaoActivity.class);
                    startActivity(intent1);
                    finish();
                });
                break;
            case MODULE_TYPES:
                TypesDao typesDao = new TypesDao();
                editTexts.get(0).setText(typesDao.getNewKey());
                btnUpdate.setOnClickListener(view -> {
                    String id = editTexts.get(0).getText().toString();
                    String name = editTexts.get(1).getText().toString();
                    String image = editTexts.get(2).getText().toString();
                    types = new Types(id, name, image);

                    postDao();
                    Intent intent1 = new Intent(activity, TypesDaoActivity.class);
                    startActivity(intent1);
                    finish();
                });
                break;
            case MODULE_PLAYLIST:
                PlaylistDao playlistDao = new PlaylistDao();
                editTexts.get(0).setText(playlistDao.getNewKey());
                btnUpdate.setOnClickListener(view -> {
                    String id = editTexts.get(0).toString();
                    String name = editTexts.get(1).toString();
                    String image = editTexts.get(2).toString();
                    item = (Item) spinners.get(0).getSelectedItem();
                    boolean isAdmin = Boolean.parseBoolean(item.getId());
                    item1 = (Item) spinners.get(1).getSelectedItem();
                    String uid = item1.getId();
                    String dateCreated = editTexts.get(3).toString();
                    playlist = new Playlist(id, name, image, isAdmin, uid, dateCreated);

                    postDao();
                    Intent intent1 = new Intent(activity, PlaylistDaoActivity.class);
                    startActivity(intent1);
                    finish();
                });
                break;
        }
    }

    private void updateObject() {
        switch (mCurrentModule) {
            case MODULE_USER:
                UserDao userDao = new UserDao();
                userDao.get(key, new RetrieValEventListener<User>() {
                    @Override
                    public void OnDataRetrieved(User mUser) {
                        user = new User();
                        user = mUser;
                        editTexts.get(0).setText(user.getId());
                        editTexts.get(1).setText(user.getDisplayName());
                        editTexts.get(2).setText(user.getPassword());
                        editTexts.get(3).setText(user.getEmail());
                        editTexts.get(4).setText(user.getPhone());
                        editTexts.get(5).setText(user.getAvatar());

                        initData();
                    }
                });

                btnUpdate.setOnClickListener(view -> {
                    user = new User();
                    String displayName = editTexts.get(1).getText().toString();
                    String password = editTexts.get(2).getText().toString();
                    String email = editTexts.get(3).getText().toString();
                    String phone = editTexts.get(4).getText().toString();
                    String avatar = editTexts.get(5).getText().toString();
                    item = (Item) spinners.get(0).getSelectedItem();
                    int role = Integer.parseInt(item.getId());
                    user.update(displayName, password, email, phone, avatar);
                    user.setRole(role);

                    repairDao();
                    finish();
                });
                break;
            case MODULE_SONG:
                SongDao songDao = new SongDao();
                songDao.get(key, new RetrieValEventListener<Song>() {
                    @Override
                    public void OnDataRetrieved(Song mSong) {
                        song = new Song();
                        song = mSong;
                        editTexts.get(0).setText(song.getId());
                        editTexts.get(1).setText(song.getName());
                        editTexts.get(2).setText(song.getImage());
                        editTexts.get(3).setText(song.getSinger());
                        editTexts.get(4).setText(song.getLinkSong());
                        spinners.get(0).setAdapter(spinnerDaoAdapter);
                        spinners.get(1).setAdapter(spinnerDaoAdapter);

                        initData();
                    }
                });

                btnUpdate.setOnClickListener(view -> {
                    String name = editTexts.get(1).getText().toString();
                    String image = editTexts.get(2).getText().toString();
                    String idTheme = "";
                    String idTypes = "";
                    item = (Item) spinners.get(0).getSelectedItem();
                    String idAlbum = item.getId();
                    item1 = (Item) spinners.get(1).getSelectedItem();
                    String idPlaylist = item1.getId();
                    String singer = editTexts.get(3).getText().toString();
                    String linkSong = editTexts.get(4).getText().toString();
                    song.update(name, image, singer, idTheme, idTypes, idAlbum, idPlaylist, linkSong);

                    repairDao();
                    finish();
                });
                break;
            case MODULE_BANNER:
                BannerDao bannerDao = new BannerDao();
                bannerDao.get(key, new RetrieValEventListener<Banner>() {
                    @Override
                    public void OnDataRetrieved(Banner mBanner) {
                        banner = new Banner();
                        banner = mBanner;

                        editTexts.get(0).setText(banner.getId());
                        editTexts.get(1).setText(banner.getName());
                        editTexts.get(2).setText(banner.getImage());
                        spinners.get(0).setAdapter(spinnerDaoAdapter);

                        initData();
                    }
                });
                btnUpdate.setOnClickListener(view -> {
                    String name = editTexts.get(1).getText().toString();
                    String image = editTexts.get(2).getText().toString();
                    item = (Item) spinners.get(0).getSelectedItem();
                    String idBaihat = item.getId();
                    banner.update(name, image, idBaihat);

                    repairDao();
                    finish();
                });
                break;
            case MODULE_ALBUM:
                AlbumDao albumDao = new AlbumDao();
                albumDao.get(key, new RetrieValEventListener<Album>() {
                    @Override
                    public void OnDataRetrieved(Album mAlbum) {
                        album = new Album();
                        album = mAlbum;

                        editTexts.get(0).setText(album.getId());
                        editTexts.get(1).setText(album.getName());
                        editTexts.get(2).setText(album.getImage());
                        editTexts.get(3).setText(album.getSinger());

                        initData();
                    }
                });
                btnUpdate.setOnClickListener(view -> {
                    String name = editTexts.get(1).getText().toString();
                    String image = editTexts.get(2).getText().toString();
                    String singer = editTexts.get(3).getText().toString();
                    album.update(name, image, singer);

                    repairDao();
                    finish();
                });
                break;
            case MODULE_THEME:
                ThemeDao themeDao = new ThemeDao();
                themeDao.get(key, new RetrieValEventListener<Theme>() {
                    @Override
                    public void OnDataRetrieved(Theme mTheme) {
                        theme = new Theme();
                        theme = mTheme;

                        editTexts.get(0).setText(theme.getId());
                        editTexts.get(1).setText(theme.getName());
                        editTexts.get(2).setText(theme.getImage());

                        initData();
                    }
                });
                btnUpdate.setOnClickListener(view -> {
                    String name = editTexts.get(1).getText().toString();
                    String image = editTexts.get(2).getText().toString();
                    theme.update(name, image);

                    repairDao();
                    finish();
                });
                break;
            case MODULE_TYPES:
                TypesDao typesDao = new TypesDao();
                typesDao.get(key, new RetrieValEventListener<Types>() {
                    @Override
                    public void OnDataRetrieved(Types mTypes) {
                        types = new Types();
                        types = mTypes;

                        editTexts.get(0).setText(types.getId());
                        editTexts.get(1).setText(types.getName());
                        editTexts.get(2).setText(types.getImage());

                        initData();
                    }
                });
                btnUpdate.setOnClickListener(view -> {
                    String name = editTexts.get(1).getText().toString();
                    String image = editTexts.get(2).getText().toString();
                    item = (Item) spinners.get(0).getSelectedItem();
                    types.update(name, image);

                    repairDao();
                    finish();
                });
                break;
            case MODULE_PLAYLIST:
                PlaylistDao playlistDao = new PlaylistDao();
                playlistDao.get(key, new RetrieValEventListener<Playlist>() {
                    @Override
                    public void OnDataRetrieved(Playlist mPlaylist) {
                        playlist = new Playlist();
                        playlist = mPlaylist;

                        editTexts.get(0).setText(playlist.getId());
                        editTexts.get(1).setText(playlist.getName());
                        editTexts.get(2).setText(playlist.getImage());
                        editTexts.get(3).setText(playlist.getDateCreated());

                        loadListViewSong();

                        initData();
                    }
                });
                btnUpdate.setOnClickListener(view -> {
                    String name = editTexts.get(1).toString();
                    String image = editTexts.get(2).toString();
                    item = (Item) spinners.get(0).getSelectedItem();
                    boolean isAdmin = Boolean.parseBoolean(item.getId());
                    item1 = (Item) spinners.get(1).getSelectedItem();
                    String uid = item1.getId();
                    String dateCreated = editTexts.get(3).toString();
                    playlist.update(name, image, isAdmin, uid, dateCreated);

                    repairDao();
                    finish();
                });
                break;
        }
    }

    private void mapping() {
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        //Toobar đã như ActionBar
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        lvSong = findViewById(R.id.lvSong);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);
    }

    public int getCurentModule(String module) {
        if (module.equals(User.class.getName()))
            return MODULE_USER;
        if (module.equals(Song.class.getName()))
            return MODULE_SONG;
        if (module.equals(Banner.class.getName()))
            return MODULE_BANNER;
        if (module.equals(Album.class.getName()))
            return MODULE_ALBUM;
        if (module.equals(Theme.class.getName()))
            return MODULE_THEME;
        if (module.equals(Types.class.getName()))
            return MODULE_TYPES;
        if (module.equals(Playlist.class.getName()))
            return MODULE_PLAYLIST;
        return -1;
    }

    private void setUiView(View view) {
        view.setBackgroundResource(R.drawable.circle_view);
    }

    public ArrayList<Song> getSongByPlaylists() {
        return songByPlaylists;
    }

    public void setSongByPlaylists(ArrayList<Song> songByPlaylists) {
        this.songByPlaylists = songByPlaylists;
        customSongByPlaylistAdapter.clear();
        customSongByPlaylistAdapter.addAll(songByPlaylists);
    }

    public void loadListViewSong() {
        SongDao songDao = new SongDao();
        songDao.getSongByPlaylist(playlist.getId(), new RetrieValEventListener<List<Song>>() {
            @Override
            public void OnDataRetrieved(List<Song> songs) {
                customSongByPlaylistAdapter = new CustomSongByPlaylistAdapter(activity, R.layout.item_song, songs, playlist);
                lvSong.setAdapter(customSongByPlaylistAdapter);
            }
        });
    }
}
