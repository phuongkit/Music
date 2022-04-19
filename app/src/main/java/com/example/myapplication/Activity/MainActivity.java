package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Adapter.MainViewPagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int FRAGMENT_PERSONAL = 0;
    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_SEARCH = 2;
    private static final int FRAGMENT_PLAYLIST = 3;

    private int mCurrentFragment = 1;

    MainViewPagerAdapter mainViewPagerAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    DrawerLayout mdrawerLayout;
    NavigationView navigationView;
    TextView txtAccountName, txtAccountEmail;
    Toolbar toolbar;
    Activity context;
    LoginDialog dialog;
    private String[] titles=new String[]{"Cá nhân","Trang chủ","Tìm kiếm","Playlist"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.appbarSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                Toast.makeText(MainActivity.this, "Expanding", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                Toast.makeText(MainActivity.this, "Collapsing", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                provinceAdapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void init(){
        context = MainActivity.this;
        mainViewPagerAdapter = new MainViewPagerAdapter(this);
        viewPager2.setAdapter(mainViewPagerAdapter);
        new TabLayoutMediator(tabLayout,viewPager2,((tab, position) -> tab.setText(titles[position]))).attach();
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
                }
                else {
                    Log.d("Log", "Display: " + user.getDisplayName() + "; Email: " + user.getEmail());
                    txtAccountName.setText("Admin");
//                    txtAccountName.setText(user.getDisplayName());
                    txtAccountEmail.setText(user.getEmail());
                    mdrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

    private void mapping(){
        tabLayout=findViewById(R.id.myTabLayout);
        viewPager2=findViewById(R.id.myViewpager);
        toolbar = findViewById(R.id.toolBar);
        mdrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        txtAccountName = navigationView.getHeaderView(0).findViewById(R.id.txtAccountName);
        txtAccountEmail = navigationView.getHeaderView(0).findViewById(R.id.txtAccountEmail);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_personal:
                if (mCurrentFragment != FRAGMENT_PERSONAL) {
                    tabLayout.selectTab(tabLayout.getTabAt(FRAGMENT_PERSONAL));
                }
                break;
            case R.id.nav_home:
                if (mCurrentFragment != FRAGMENT_HOME) {
                    tabLayout.selectTab(tabLayout.getTabAt(FRAGMENT_HOME));
                }
                break;
            case R.id.nav_search:
                if (mCurrentFragment != FRAGMENT_SEARCH) {
                    tabLayout.selectTab(tabLayout.getTabAt(FRAGMENT_SEARCH));
                }
                break;
            case R.id.nav_playlist:
                if (mCurrentFragment != FRAGMENT_PLAYLIST) {
                    tabLayout.selectTab(tabLayout.getTabAt(FRAGMENT_PLAYLIST));
                }
                break;
            case R.id.nav_myprofile:
                break;
            case R.id.nav_changepassword:
                break;
            case R.id.nav_signout:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
//                mdrawerLayout.openDrawer(GravityCompat.END);
                mdrawerLayout.closeDrawers();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mdrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mdrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}