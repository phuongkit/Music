package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activity.admin.AlbumDaoActivity;
import com.example.myapplication.Activity.admin.SongDaoActivity;
import com.example.myapplication.Activity.admin.BannerDaoActivity;
import com.example.myapplication.Activity.admin.ThemeDaoActivity;
import com.example.myapplication.Activity.admin.TypesDaoActivity;
import com.example.myapplication.Activity.admin.UserDaoActivity;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.UserDao;
import com.example.myapplication.Dialog.ChangePassworDialog;
import com.example.myapplication.Dialog.LoginDialog;
import com.example.myapplication.Fragment.SearchFragment;
import com.example.myapplication.Module.User;
import com.example.myapplication.R;
import com.example.myapplication.Adapter.MainViewPagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int MY_REQUEST_CODE = 10;
    private static final int FRAGMENT_PERSONAL = 0;
    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_SEARCH = 2;
    private static final int FRAGMENT_PLAYLIST = 3;

    public static final int ROLE_ADMIN = 0;
    public static final int ROLE_USER = 1;

    private int mCurrentFragment = 1;

    MainViewPagerAdapter mainViewPagerAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    DrawerLayout mdrawerLayout;
    NavigationView navigationView;
    TextView txtAccountName, txtAccountEmail;
    public static ImageView imgAvatar;
    Toolbar toolbar;
    Menu menuNav;

    Activity context;
    LoginDialog dialog;
    ChangePassworDialog changePassworDialog;
    private ArrayList<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titles.add(getString(R.string.strHeaderPersonal));
        titles.add(getString(R.string.strHeaderHomePage));
        titles.add(getString(R.string.strHeaderSearch));
        titles.add(getString(R.string.strHeaderPlaylist));
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
                args.putString("filter", s);
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
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabLayout.selectTab(tabLayout.getTabAt(FRAGMENT_SEARCH));
//                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
//                Bundle args = new Bundle();
//                Log.d("Test", String.valueOf(searchView.getQuery()));
//                args.putString("filter", String.valueOf(searchView.getQuery()));
//                for (int i = 0; i < fragmentList.size(); i++) {
//                    if (fragmentList.get(i) instanceof SearchFragment) {
//                        Log.d("Test", "Size: " + fragmentList.size());
//                        fragmentList.get(i).setArguments(args);
//                        ((SearchFragment) fragmentList.get(i)).setBundle();
//                        break;
//                    }
//                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.appbarSearch:
//                tabLayout.selectTab(tabLayout.getTabAt(FRAGMENT_SEARCH));
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void init() {
        context = MainActivity.this;
        mainViewPagerAdapter = new MainViewPagerAdapter(this);
        viewPager2.setAdapter(mainViewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> tab.setText(titles.get(position)))).attach();
        tabLayout.getTabAt(FRAGMENT_PERSONAL).setIcon(R.drawable.ic_person);
        tabLayout.getTabAt(FRAGMENT_HOME).setIcon(R.drawable.icontrangchu);
        tabLayout.getTabAt(FRAGMENT_SEARCH).setIcon(R.drawable.icontimkiem);
        tabLayout.getTabAt(FRAGMENT_PLAYLIST).setIcon(R.drawable.iconmoreplaylist);
        tabLayout.selectTab(tabLayout.getTabAt(mCurrentFragment));

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        //Toobar đã như ActionBar
        actionBar.setDisplayShowTitleEnabled(false);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_user);

        toolbar.setNavigationIcon(R.drawable.ic_user);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog = new LoginDialog(context);
                            dialog.show();
                        }
                    }, 500);
                } else {
                    Log.d("Log", "Display: " + user.getDisplayName() + "; Email: " + user.getEmail());
                    txtAccountName.setText(user.getDisplayName());
                    txtAccountName.setText("Admin");
//                    txtAccountName.setText(user.getDisplayName());
                    txtAccountEmail.setText(user.getEmail());
                    Glide.with(context).load(user.getPhotoUrl()).error(R.mipmap.ic_launcher).into(imgAvatar);
                    mdrawerLayout.openDrawer(GravityCompat.START);
                }
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.nav_personal:
                if (mCurrentFragment != FRAGMENT_PERSONAL) {
                    tabLayout.selectTab(tabLayout.getTabAt(FRAGMENT_PERSONAL));
                }
                return true;
            case R.id.nav_home:
                if (mCurrentFragment != FRAGMENT_HOME) {
                    tabLayout.selectTab(tabLayout.getTabAt(FRAGMENT_HOME));
                }
                return true;
            case R.id.nav_search:
                if (mCurrentFragment != FRAGMENT_SEARCH) {
                    tabLayout.selectTab(tabLayout.getTabAt(FRAGMENT_SEARCH));
                }
                return true;
            case R.id.nav_playlist:
                if (mCurrentFragment != FRAGMENT_PLAYLIST) {
                    tabLayout.selectTab(tabLayout.getTabAt(FRAGMENT_PLAYLIST));
                }
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
//                mdrawerLayout.openDrawer(GravityCompat.END);
                mdrawerLayout.closeDrawers();
                return true;
            case R.id.nav_admin:
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
            case R.id.nav_theme_manager:
                intent = new Intent(this, ThemeDaoActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_types_manager:
                intent = new Intent(this, TypesDaoActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_album_manager:
                intent = new Intent(this, AlbumDaoActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_playlist_manager:
//                intent = new Intent(getApplication(), AlbumDaoActivity.class);
//                startActivity(intent);
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
                }
            });
        } else {
            Log.i("Info", "Node Anonymous");
            searchItem.setVisible(false);
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
}