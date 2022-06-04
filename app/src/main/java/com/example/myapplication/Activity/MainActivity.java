package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activity.admin.AlbumDaoActivity;
import com.example.myapplication.Activity.admin.BannerDaoActivity;
import com.example.myapplication.Activity.admin.PlaylistDaoActivity;
import com.example.myapplication.Activity.admin.SongDaoActivity;
import com.example.myapplication.Activity.admin.ThemeDaoActivity;
import com.example.myapplication.Activity.admin.TypesDaoActivity;
import com.example.myapplication.Activity.admin.UserDaoActivity;
import com.example.myapplication.Adapter.MainViewPagerAdapter;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.UserDao;
import com.example.myapplication.Dialog.ChangePassworDialog;
import com.example.myapplication.Dialog.LoginDialog;
import com.example.myapplication.Fragment.SearchFragment;
import com.example.myapplication.Generic.Beans.NotifyObject;
import com.example.myapplication.Generic.Download.PRDownloader;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.example.myapplication.Service.MyDownloadService;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int MY_REQUEST_CODE = 10;
    private static final int FRAGMENT_PERSONAL = 0;
    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_SEARCH = 2;
    private static final int FRAGMENT_EXPLORE = 3;
    public static final int mCurrentFragment = 3;

    public static final int ROLE_ADMIN = 0;
    public static final int ROLE_USER = 1;

    public int downloadId;
    public boolean downloading = false;

    MainViewPagerAdapter mainViewPagerAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    DrawerLayout mdrawerLayout;
    NavigationView navigationView;
    TextView txtAccountName, txtAccountEmail;
    @SuppressLint("StaticFieldLeak")
    public static ImageView imgAvatar;
    Toolbar toolbar;
    Menu menuNav;

    Activity context;
    LoginDialog dialog;
    ChangePassworDialog changePassworDialog;
    private final ArrayList<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titles.add(getString(R.string.strHeaderPersonal));
        titles.add(getString(R.string.strHeaderHomePage));
        titles.add(getString(R.string.strHeaderSearch));
        titles.add(getString(R.string.strHeaderExplore));
        mapping();
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        setIfAdmin();

        MenuItem searchItem = menu.findItem(R.id.appbarSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                Bundle args = new Bundle();
                args.putString("query", s);
                for (int i = 0; i < fragmentList.size(); i++) {
                    if (fragmentList.get(i) instanceof SearchFragment) {
                        fragmentList.get(i).setArguments(args);
                        ((SearchFragment) fragmentList.get(i)).setBundle();
                        break;
                    }
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                Bundle args = new Bundle();
                args.putString("query", s);
                for (int i = 0; i < fragmentList.size(); i++) {
                    if (fragmentList.get(i) instanceof SearchFragment) {
                        fragmentList.get(i).setArguments(args);
                        ((SearchFragment) fragmentList.get(i)).setBundle();
                        break;
                    }
                }
                return true;
            }
        });
        searchView.setOnSearchClickListener(view -> tabLayout.selectTab(tabLayout.getTabAt(FRAGMENT_SEARCH)));
        return super.onCreateOptionsMenu(menu);
    }

    private void init() {
        context = MainActivity.this;
        mainViewPagerAdapter = new MainViewPagerAdapter(this);
        viewPager2.setAdapter(mainViewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> tab.setText(titles.get(position)))).attach();
        Objects.requireNonNull(tabLayout.getTabAt(FRAGMENT_PERSONAL)).setIcon(R.drawable.ic_person);
        Objects.requireNonNull(tabLayout.getTabAt(FRAGMENT_HOME)).setIcon(R.drawable.icontrangchu);
        Objects.requireNonNull(tabLayout.getTabAt(FRAGMENT_SEARCH)).setIcon(R.drawable.icontimkiem);
        Objects.requireNonNull(tabLayout.getTabAt(FRAGMENT_EXPLORE)).setIcon(R.drawable.iconmoreplaylist);
        tabLayout.selectTab(tabLayout.getTabAt(mCurrentFragment));

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        //Toobar đã như ActionBar
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_user);

        toolbar.setNavigationIcon(R.drawable.ic_user);
        toolbar.setNavigationOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Handler handler = new Handler();
            if (user == null) {
                handler.postDelayed(() -> {
                    dialog = new LoginDialog(context);
                    dialog.show();
                }, 500);
            } else {
                handler.postDelayed(() -> {
                    Log.d("Log", "Display: " + user.getDisplayName() + "; Email: " + user.getEmail());
                    UserDao userDao = new UserDao();
                    userDao.getOneById(user.getUid(), new RetrieValEventListener<User>() {
                        @Override
                        public void OnDataRetrieved(User user) {
                            String username = user.getDisplayName();
                            username = username.equals("") ? "User" : username;
                            txtAccountName.setText(username);
                            txtAccountEmail.setText(user.getEmail());
                            Glide.with(context).load(user.getAvatar()).error(R.drawable.user).into(imgAvatar);
                        }
                    });
                    mdrawerLayout.openDrawer(GravityCompat.START);
                }, 500);
            }
        });

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

    private void mapping() {
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager2 = findViewById(R.id.myViewpager);
        toolbar = findViewById(R.id.toolBar);
        mdrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        txtAccountName = navigationView.getHeaderView(0).findViewById(R.id.txtAccountName);
        txtAccountEmail = navigationView.getHeaderView(0).findViewById(R.id.txtAccountEmail);
        imgAvatar = navigationView.getHeaderView(0).findViewById(R.id.imgAvatar);
        menuNav = navigationView.getMenu();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.nav_personal:
                tabLayout.selectTab(tabLayout.getTabAt(FRAGMENT_PERSONAL));
                return true;
            case R.id.nav_home:
            case R.id.nav_admin:
                return true;
            case R.id.nav_search:
                tabLayout.selectTab(tabLayout.getTabAt(FRAGMENT_SEARCH));
                return true;
            case R.id.nav_playlist:
                tabLayout.selectTab(tabLayout.getTabAt(FRAGMENT_EXPLORE));
                return true;
            case R.id.nav_myprofile:
                intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_changepassword:
                changePassworDialog = new ChangePassworDialog(context);
                changePassworDialog.show();
                return true;
            case R.id.nav_signout:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                mdrawerLayout.closeDrawers();
                return true;
            case R.id.nav_user_manager:
                intent = new Intent(getApplication(), UserDaoActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_song_manager:
                intent = new Intent(this, SongDaoActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_banner_manager:
                intent = new Intent(this, BannerDaoActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_album_manager:
                intent = new Intent(this, AlbumDaoActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_theme_manager:
                intent = new Intent(this, ThemeDaoActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_types_manager:
                intent = new Intent(this, TypesDaoActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_playlist_manager:
                intent = new Intent(this, PlaylistDaoActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

    public void setIfAdmin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        MenuItem searchItem = menuNav.findItem(R.id.nav_admin);
        if (user != null) {
            String uid = user.getUid();
            UserDao userDao = new UserDao();
            userDao.getAll(new RetrieValEventListener<List<User>>() {
                @Override
                public void OnDataRetrieved(List<User> users) {
                    for (User userDb : users) {
                        if (userDb.getId().equals(uid)) {

                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }

                            toolbar.setNavigationIcon(R.drawable.ic_exists_user);
                            if (userDb.getRole() == ROLE_ADMIN) {
                                Log.i("Info", "Node Admin");
                                searchItem.setVisible(true);
                            } else if (userDb.getRole() == ROLE_USER) {
                                Log.i("Info", "Node User");
                                searchItem.setVisible(false);
                            }
                            return;
                        }
                    }
                    Log.i("Info", "Node User not in DB");
                    searchItem.setVisible(false);
                    toolbar.setNavigationIcon(R.drawable.ic_user);
                }
            });
        } else {
            Log.i("Info", "Node Anonymous");
            searchItem.setVisible(false);
            toolbar.setNavigationIcon(R.drawable.ic_user);
        }
    }

    @Override
    public void onBackPressed() {
        if (mdrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mdrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setIfAdmin();
    }

    public void pauseOrResumeDownload() {
        downloading = !downloading;
        if (downloading) {
            Log.i("Info", "runned  main resumeDownload");
            PRDownloader.resume(downloadId);
            startService("", MyDownloadService.ACTION_RESUME, -1);
        } else {
            Log.i("Info", "runned  main pauseDownload");
            PRDownloader.pause(downloadId);
            startService("", MyDownloadService.ACTION_PAUSE, -1);
        }
    }

    public void cancelDownload() {
        Log.i("Info", "runned  main cancelDownload");
        PRDownloader.cancel(downloadId);
        startService("", MyDownloadService.ACTION_CANCEL, -1);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                stopService();
            }
        }, 10000);
    }

    public void startService(String link, int status, int progress) {
        Intent intent = new Intent(this, MyDownloadService.class);
        NotifyObject notifyObject = new NotifyObject(link, status, progress);
        intent.putExtra("notifyObject", notifyObject);
        startService(intent);
    }

    public void stopService() {
        Intent intent = new Intent(this, MyDownloadService.class);
        stopService(intent);
    }
}