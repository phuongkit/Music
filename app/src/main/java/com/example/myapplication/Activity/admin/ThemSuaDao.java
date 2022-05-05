package com.example.myapplication.Activity.admin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.Adapter.CustomBannerAdapter;
import com.example.myapplication.Adapter.admin.CustomBannerDaoAdapter;
import com.example.myapplication.Dao.AlbumDao;
import com.example.myapplication.Dao.BaiHatDao;
import com.example.myapplication.Dao.BannerDao;
import com.example.myapplication.Dao.ChuDeDao;
import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.TheLoaiDao;
import com.example.myapplication.Module.Album;
import com.example.myapplication.Module.Baihat;
import com.example.myapplication.Module.Banner;
import com.example.myapplication.Module.Chude;
import com.example.myapplication.Module.Theloai;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ThemSuaDao extends AppCompatActivity {
    CustomBannerDaoAdapter customBannerDaoAdapter;

    Baihat baihat;
    Baihat baihat1;
    Banner banner;
    Banner banner1;
    Album album;
    Album album1;
    Chude chude;
    Chude chude1;
    Theloai theloai;
    Theloai theloai1;

    Spinner spinner;
    TextView txtIdbaihat,txtTenbaihat,txtCasi,txtHinhbaihat,txtIdplaylist,txtLinkbaihat,txtspinerbaihat;
    Button btnAddbaihat,btnSuabaihat,btnDeletebaihat;
    EditText edtIdbaihat,edtTenbaihat,edtCasi,edtHinhbaihat,edtIdplaylist,edtLinkbaihat;
    String getSpiner="";

    ArrayList<Album> albums;
    ArrayList<Baihat> baihats;
    ArrayList<Banner> banners=new ArrayList<>();

    ArrayList<Chude> chudes;


    String id,control,module;
    ArrayAdapter<String> adapter;

    Activity activity;
    BannerDaoActivity bannerDaoActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themsua_baihat_dao);
        activity=this;
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        banners = (ArrayList<Banner>) bundle.getSerializable("banners");
        id = bundle.getString("idBaihat");
        control=bundle.getString("control");
        module=bundle.getString("Module");
        bundle.remove("control");
        bundle.remove("idBaihat");
        bundle.remove("Module");
        mapping();
        if(control.equals("add")){
            if(module.equals("Baihat")){

                btnSuabaihat.setVisibility(View.INVISIBLE);
                btnAddbaihat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getSpiner=spinner.getSelectedItem().toString();
                        for (int i = 0; i < albums.size(); i++) {
                            if(albums.get(i).getTenAlbum().equals(getSpiner)){
                                getSpiner=albums.get(i).id;
                                Log.d("TTT", getSpiner);
                            }
                        }
                        baihat = new Baihat(edtIdbaihat.getText().toString(),edtTenbaihat.getText().toString(),edtCasi.getText().toString(),edtHinhbaihat.getText().toString(),getSpiner,edtIdplaylist.getText().toString(),edtLinkbaihat.getText().toString());
                        postDao();
                        Intent intent1 = new Intent(activity, BaihatDaoActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                });
            }
            if(module.equals("Banner")){
                edtCasi.setVisibility(View.INVISIBLE);
                txtCasi.setVisibility(View.INVISIBLE);
                edtLinkbaihat.setVisibility(View.INVISIBLE);
                txtLinkbaihat.setVisibility(View.INVISIBLE);
                edtIdbaihat.setVisibility(View.INVISIBLE);
                txtIdbaihat.setVisibility(View.INVISIBLE);
                btnSuabaihat.setVisibility(View.INVISIBLE);
                txtTenbaihat.setText("NDBanner");
                txtHinhbaihat.setText("HinhBaiHat");
                txtIdplaylist.setText("IdBanner");
                txtspinerbaihat.setText("IdBaiHat");
                btnAddbaihat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        banner = new Banner(edtIdbaihat.getText().toString(), edtTenbaihat.getText().toString(), edtHinhbaihat.getText().toString(), spinner.getSelectedItem().toString());
                        postDao();
                        Intent intent1 = new Intent(activity, BannerDaoActivity.class);
                        startActivity(intent1);
//                        bannerDaoActivity.lvPlayList.getAdapter().notify();
//                        customBannerDaoAdapter = new CustomBannerDaoAdapter(activity,android.R.layout.simple_list_item_1,banners);
//                        customBannerDaoAdapter.notifyDataSetChanged();
                        finish();
                    }
                });
            }
            if(module.equals("Album")) {
                btnSuabaihat.setVisibility(View.INVISIBLE);
//                    edtCasi.setVisibility(View.INVISIBLE);
//                    txtCasi.setVisibility(View.INVISIBLE);
                spinner.setVisibility(View.INVISIBLE);
                txtspinerbaihat.setVisibility(View.INVISIBLE);
                edtLinkbaihat.setVisibility(View.INVISIBLE);
                txtLinkbaihat.setVisibility(View.INVISIBLE);
                edtIdbaihat.setVisibility(View.INVISIBLE);
                txtIdbaihat.setVisibility(View.INVISIBLE);

                txtTenbaihat.setText("TenAlbum");
                txtHinhbaihat.setText("HinhAlbum");
                txtIdplaylist.setText("IdBanner");

                btnAddbaihat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        album = new Album(edtIdplaylist.getText().toString(), edtTenbaihat.getText().toString(), edtCasi.getText().toString(), edtHinhbaihat.getText().toString());
                        postDao();
                        Intent intent1 = new Intent(activity, AlbumDaoActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                });
            }
            if(module.equals("Chude")) {
                btnSuabaihat.setVisibility(View.INVISIBLE);
                edtCasi.setVisibility(View.INVISIBLE);
                txtCasi.setVisibility(View.INVISIBLE);
                spinner.setVisibility(View.INVISIBLE);
                txtspinerbaihat.setVisibility(View.INVISIBLE);
                edtLinkbaihat.setVisibility(View.INVISIBLE);
                txtLinkbaihat.setVisibility(View.INVISIBLE);
                edtIdbaihat.setVisibility(View.INVISIBLE);
                txtIdbaihat.setVisibility(View.INVISIBLE);

                txtTenbaihat.setText("TenChude");
                txtHinhbaihat.setText("HinhChude");
                txtIdplaylist.setText("IdChude");

                btnAddbaihat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chude = new Chude(edtIdplaylist.getText().toString(), edtTenbaihat.getText().toString(), edtHinhbaihat.getText().toString());
                        postDao();
                        Intent intent1 = new Intent(activity, ChudeDaoActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                });
            }
            if(module.equals("Theloai")) {
                btnSuabaihat.setVisibility(View.INVISIBLE);
                edtCasi.setVisibility(View.INVISIBLE);
                txtCasi.setVisibility(View.INVISIBLE);

                edtLinkbaihat.setVisibility(View.INVISIBLE);
                txtLinkbaihat.setVisibility(View.INVISIBLE);
                edtIdbaihat.setVisibility(View.INVISIBLE);
                txtIdbaihat.setVisibility(View.INVISIBLE);

                txtTenbaihat.setText("TenTheloai");
                txtHinhbaihat.setText("HinhTheloai");
                txtIdplaylist.setText("IdTheloai");
                txtspinerbaihat.setText("IdChude");

                btnAddbaihat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getSpiner=spinner.getSelectedItem().toString();
                        for (int i = 0; i < chudes.size(); i++) {
                            if(chudes.get(i).getTen().equals(getSpiner)){
                                getSpiner=chudes.get(i).id;
                                Log.d("TTT", getSpiner);
                            }
                        }
                        theloai = new Theloai(edtIdplaylist.getText().toString(), edtTenbaihat.getText().toString(), edtHinhbaihat.getText().toString(),getSpiner);
                        postDao();
                        Intent intent1 = new Intent(activity, TheloaiDaoActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                });
            }
        }else {
            if (control.equals("repair")) {
                if(module.equals("Baihat")){
                    btnAddbaihat.setVisibility(View.INVISIBLE);
                    BaiHatDao baiHatDao = new BaiHatDao();
                    baiHatDao.get(id, new RetrievalEventListener<Baihat>() {
                        @Override
                        public void OnDataRetrieved(Baihat baihat) {
                            baihat1 = new Baihat();
                            baihat1 = (Baihat) baihat;
                            edtIdbaihat.setText(baihat.getIdBaihat());
                            edtTenbaihat.setText(baihat.getTenBaihat());
                            edtCasi.setText(baihat.getCaSi());
                            edtHinhbaihat.setText(baihat.getHinhBaihat());
                            edtIdplaylist.setText(baihat.getIdPlaylist().toString());
                            edtLinkbaihat.setText(baihat.getLinkBaihat().toString());
                            spinner.setAdapter(adapter);
                        }
                    });

                    btnSuabaihat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getSpiner=spinner.getSelectedItem().toString();
                            for (int i = 0; i < albums.size(); i++) {
                                if(albums.get(i).getTenAlbum().equals(getSpiner)){
                                    getSpiner=albums.get(i).id;
                                    Log.d("TTT", getSpiner);
                                }
                            }
                            baihat1 = new Baihat(edtIdbaihat.getText().toString(), edtTenbaihat.getText().toString(), edtCasi.getText().toString(), edtHinhbaihat.getText().toString(), getSpiner, edtIdplaylist.getText().toString(), edtLinkbaihat.getText().toString());
                            repairDao();
                            Intent intent1 = new Intent(activity, BaihatDaoActivity.class);
                            startActivity(intent1);
                            finish();
                        }
                    });
                }
                if(module.equals("Banner")) {
                    btnAddbaihat.setVisibility(View.INVISIBLE);
                    edtCasi.setVisibility(View.INVISIBLE);
                    txtCasi.setVisibility(View.INVISIBLE);
                    edtLinkbaihat.setVisibility(View.INVISIBLE);
                    txtLinkbaihat.setVisibility(View.INVISIBLE);
                    edtIdbaihat.setVisibility(View.INVISIBLE);
                    txtIdbaihat.setVisibility(View.INVISIBLE);
                    BannerDao baiHatDao = new BannerDao();
                    baiHatDao.get(id, new RetrievalEventListener<Banner>() {
                        @Override
                        public void OnDataRetrieved(Banner baihat) {
                            banner1 = new Banner();
                            banner1 = (Banner) baihat;

                            txtTenbaihat.setText("NDBanner");
                            edtTenbaihat.setText(baihat.getNoiDung());
                            txtHinhbaihat.setText("HinhBaiHat");
                            edtHinhbaihat.setText(baihat.getHinhAnh().toString());
                            txtIdplaylist.setText("IdBanner");
                            edtIdplaylist.setText(baihat.getIdBanner());
                            txtspinerbaihat.setText("IdBaiHat");
                            spinner.setAdapter(adapter);
                        }
                    });
                    btnSuabaihat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            banner1 = new Banner(edtIdbaihat.getText().toString(), edtTenbaihat.getText().toString(), edtHinhbaihat.getText().toString(), spinner.getSelectedItem().toString());
                            repairDao();
                            Intent intent1 = new Intent(activity, BannerDaoActivity.class);
                            startActivity(intent1);
                            finish();
                        }
                    });
                }

                if(module.equals("Album")) {
                    btnAddbaihat.setVisibility(View.INVISIBLE);
//                    edtCasi.setVisibility(View.INVISIBLE);
//                    txtCasi.setVisibility(View.INVISIBLE);
                    edtLinkbaihat.setVisibility(View.INVISIBLE);
                    txtLinkbaihat.setVisibility(View.INVISIBLE);
                    edtIdbaihat.setVisibility(View.INVISIBLE);
                    txtIdbaihat.setVisibility(View.INVISIBLE);
                    AlbumDao baiHatDao = new AlbumDao();
                    baiHatDao.get(id, new RetrievalEventListener<Album>() {
                        @Override
                        public void OnDataRetrieved(Album baihat) {
                            album1 = new Album();
                            album1 = (Album) baihat;

                            txtTenbaihat.setText("TenAlbum");
                            edtTenbaihat.setText(baihat.getTenAlbum());
                            txtCasi.setText("CasiAlbum");
                            edtCasi.setText(baihat.getTenCaSiAlbum());
                            txtHinhbaihat.setText("HinhAlbum");
                            edtHinhbaihat.setText(baihat.getHinh());
                            txtIdplaylist.setText("IdAlbum");
                            edtIdplaylist.setText(baihat.getIdAlbum());
//                            txtspinerbaihat.setText("IdBaiHat");
//                            spinner.setAdapter(adapter);
                        }
                    });
                    btnSuabaihat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            album1 = new Album(edtIdplaylist.getText().toString(), edtTenbaihat.getText().toString(),edtCasi.getText().toString(), edtHinhbaihat.getText().toString());
                            repairDao();
                            Intent intent1 = new Intent(activity, AlbumDaoActivity.class);
                            startActivity(intent1);
                            finish();
                        }
                    });
                }
                if(module.equals("Chude")) {
                    btnAddbaihat.setVisibility(View.INVISIBLE);
                    edtCasi.setVisibility(View.INVISIBLE);
                    txtCasi.setVisibility(View.INVISIBLE);
                    edtLinkbaihat.setVisibility(View.INVISIBLE);
                    txtLinkbaihat.setVisibility(View.INVISIBLE);
                    edtIdbaihat.setVisibility(View.INVISIBLE);
                    txtIdbaihat.setVisibility(View.INVISIBLE);
                    ChuDeDao baiHatDao = new ChuDeDao();
                    baiHatDao.get(id, new RetrievalEventListener<Chude>() {
                        @Override
                        public void OnDataRetrieved(Chude baihat) {
                            chude1 = new Chude();
                            chude1 = (Chude) baihat;

                            txtTenbaihat.setText("TenChude");
                            edtTenbaihat.setText(baihat.getTen());
                            txtHinhbaihat.setText("HinhChude");
                            edtHinhbaihat.setText(baihat.getHinh());
                            txtIdplaylist.setText("IdChude");
                            edtIdplaylist.setText(baihat.getIdChude());
//                            txtspinerbaihat.setText("IdBaiHat");
//                            spinner.setAdapter(adapter);
                        }
                    });
                    btnSuabaihat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            chude1 = new Chude(edtIdplaylist.getText().toString(), edtTenbaihat.getText().toString(), edtHinhbaihat.getText().toString());
                            repairDao();
                            Intent intent1 = new Intent(activity, ChudeDaoActivity.class);
                            startActivity(intent1);
                            finish();
                        }
                    });
                }
                if(module.equals("Theloai")) {
                    btnAddbaihat.setVisibility(View.INVISIBLE);
                    edtCasi.setVisibility(View.INVISIBLE);
                    txtCasi.setVisibility(View.INVISIBLE);

                    edtLinkbaihat.setVisibility(View.INVISIBLE);
                    txtLinkbaihat.setVisibility(View.INVISIBLE);
                    edtIdbaihat.setVisibility(View.INVISIBLE);
                    txtIdbaihat.setVisibility(View.INVISIBLE);


                    TheLoaiDao baiHatDao = new TheLoaiDao();
                    baiHatDao.get(id, new RetrievalEventListener<Theloai>() {
                        @Override
                        public void OnDataRetrieved(Theloai baihat) {
                            theloai1 = new Theloai();
                            theloai1 = (Theloai) baihat;

                            txtTenbaihat.setText("TenTheloai");
                            txtHinhbaihat.setText("HinhTheloai");
                            txtIdplaylist.setText("IdTheloai");
                            txtspinerbaihat.setText("IdChude");

                            edtTenbaihat.setText(baihat.getTen());
                            edtHinhbaihat.setText(baihat.getHinh());
                            edtIdplaylist.setText(baihat.getIdTheloai());
                            spinner.setAdapter(adapter);
                        }
                    });
                    btnSuabaihat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getSpiner=spinner.getSelectedItem().toString();
                            for (int i = 0; i < chudes.size(); i++) {
                                if(chudes.get(i).getTen().equals(getSpiner)){
                                    getSpiner=chudes.get(i).id;
                                    Log.d("TTT", getSpiner);
                                }
                            }
                            theloai1 = new Theloai(edtIdplaylist.getText().toString(), edtTenbaihat.getText().toString(), edtHinhbaihat.getText().toString(),getSpiner);
                            repairDao();
                            Intent intent1 = new Intent(activity, TheloaiDaoActivity.class);
                            startActivity(intent1);
                            finish();
                        }
                    });
                }
            }
        }

    }
    private void repairDao() {
        if(module.equals("Baihat")){
            BaiHatDao baiHatDao = new BaiHatDao();
            baiHatDao.save(baihat1, id, new TaskListener() {
                @Override
                public void OnSuccess() {
                    Toast.makeText(ThemSuaDao.this, "Successfull", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnFail() {
                    Toast.makeText(ThemSuaDao.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(module.equals("Banner")){
            BannerDao baiHatDao = new BannerDao();
            baiHatDao.save(banner1, id, new TaskListener() {
                @Override
                public void OnSuccess() {
                    Toast.makeText(ThemSuaDao.this, "Successfull", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnFail() {
                    Toast.makeText(ThemSuaDao.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(module.equals("Album")){
            AlbumDao baiHatDao = new AlbumDao();
            baiHatDao.save(album1, id, new TaskListener() {
                @Override
                public void OnSuccess() {
                    Toast.makeText(ThemSuaDao.this, "Successfull", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnFail() {
                    Toast.makeText(ThemSuaDao.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(module.equals("Chude")){
            ChuDeDao baiHatDao = new ChuDeDao();
            baiHatDao.save(chude1, id, new TaskListener() {
                @Override
                public void OnSuccess() {
                    Toast.makeText(ThemSuaDao.this, "Successfull", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnFail() {
                    Toast.makeText(ThemSuaDao.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(module.equals("Theloai")){
            TheLoaiDao baiHatDao = new TheLoaiDao();
            baiHatDao.save(theloai1, id, new TaskListener() {
                @Override
                public void OnSuccess() {
                    Toast.makeText(ThemSuaDao.this, "Successfull", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnFail() {
                    Toast.makeText(ThemSuaDao.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void postDao() {
        if(module.equals("Baihat")){
            BaiHatDao baiHatDao = new BaiHatDao();
            baiHatDao.save(baihat, baiHatDao.GetNewKey(), new TaskListener() {
                @Override
                public void OnSuccess() {
                    Toast.makeText(ThemSuaDao.this, "Successfull", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnFail() {
                    Toast.makeText(ThemSuaDao.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(module.equals("Banner")){
            BannerDao baiHatDao = new BannerDao();
            baiHatDao.save(banner, baiHatDao.GetNewKey(), new TaskListener() {
                @Override
                public void OnSuccess() {
                    Toast.makeText(ThemSuaDao.this, "Successfull", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnFail() {
                    Toast.makeText(ThemSuaDao.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(module.equals("Album")){
            AlbumDao baiHatDao = new AlbumDao();
            baiHatDao.save(album, baiHatDao.GetNewKey(), new TaskListener() {
                @Override
                public void OnSuccess() {
                    Toast.makeText(ThemSuaDao.this, "Successfull", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnFail() {
                    Toast.makeText(ThemSuaDao.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(module.equals("Chude")){
            ChuDeDao baiHatDao = new ChuDeDao();
            baiHatDao.save(chude, baiHatDao.GetNewKey(), new TaskListener() {
                @Override
                public void OnSuccess() {
                    Toast.makeText(ThemSuaDao.this, "Successfull", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnFail() {
                    Toast.makeText(ThemSuaDao.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(module.equals("Theloai")){
            TheLoaiDao baiHatDao = new TheLoaiDao();
            baiHatDao.save(theloai, baiHatDao.GetNewKey(), new TaskListener() {
                @Override
                public void OnSuccess() {
                    Toast.makeText(ThemSuaDao.this, "Successfull", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnFail() {
                    Toast.makeText(ThemSuaDao.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void mapping() {
        edtIdbaihat=findViewById(R.id.edtIdbaihat);
        edtTenbaihat=findViewById(R.id.edtTenbaihat);
        edtCasi=findViewById(R.id.edtCasi);
        edtHinhbaihat=findViewById(R.id.edtHinhbaihat);
        edtIdplaylist=findViewById(R.id.edtIdplaylist);
        edtLinkbaihat=findViewById(R.id.edtLinkbaihat);

        txtIdbaihat=findViewById(R.id.txtIdbaihat);
        txtTenbaihat=findViewById(R.id.txtTenbaihat);
        txtCasi=findViewById(R.id.txtCasi);
        txtHinhbaihat=findViewById(R.id.txtHinhbaihat);
        txtIdplaylist=findViewById(R.id.txtIdplaylist);
        txtLinkbaihat=findViewById(R.id.txtLinkbaihat);
        txtspinerbaihat=findViewById(R.id.txtspinerbaihat);

        btnAddbaihat=findViewById(R.id.btnAddbaihat);
        btnSuabaihat=findViewById(R.id.btnSuabaihat);
        spinner=findViewById(R.id.spinerbaihat);

        if(module.equals("Baihat")){
            AlbumDao alBumDao = new AlbumDao();
            alBumDao.getAll(new RetrievalEventListener<List<Album>>() {
                @Override
                public void OnDataRetrieved(List<Album> Albums) {
                    albums = new ArrayList<>();
                    albums = (ArrayList<Album>) Albums;
                    String[] str = new String[albums.size()];
                    for (int i = 0; i < albums.size(); i++) {
                        str[i] = String.valueOf(albums.get(i).getTenAlbum());
                    }
                    adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, str);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });
                }
            });
        }
        if(module.equals("Banner")){
            BaiHatDao alBumDao = new BaiHatDao();
            alBumDao.getAll(new RetrievalEventListener<List<Baihat>>() {
                @Override
                public void OnDataRetrieved(List<Baihat> Albums) {
                    baihats = new ArrayList<>();
                    baihats = (ArrayList<Baihat>) Albums;
                    String[] str = new String[baihats.size()];
                    for (int i = 0; i < baihats.size(); i++) {
                        str[i] = String.valueOf(baihats.get(i).getTenBaihat());
                    }
                    adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, str);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });
                }
            });
        }
        if(module.equals("Theloai")){
            ChuDeDao alBumDao = new ChuDeDao();
            alBumDao.getAll(new RetrievalEventListener<List<Chude>>() {
                @Override
                public void OnDataRetrieved(List<Chude> Albums) {
                    chudes = new ArrayList<>();
                    chudes = (ArrayList<Chude>) Albums;
                    String[] str = new String[chudes.size()];
                    for (int i = 0; i < chudes.size(); i++) {
                        str[i] = String.valueOf(chudes.get(i).getTen());
                    }
                    adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, str);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });
                }
            });
        }
    }
}
