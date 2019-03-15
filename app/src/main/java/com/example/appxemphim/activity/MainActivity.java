package com.example.appxemphim.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;
import android.support.v7.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appxemphim.R;
import com.example.appxemphim.adapter.PhimAdapter;
import com.example.appxemphim.adapter.TheLoaiAdapter;
import com.example.appxemphim.model.Phim;
import com.example.appxemphim.model.TheLoai;
import com.example.appxemphim.ultil.CheckConnection;
import com.example.appxemphim.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;

    ArrayList<TheLoai> mangtheloai;
    TheLoaiAdapter theLoaiAdapter;

    int id_theloai = 0;
    String ten_theloai = "";
    String hinh_theloai = "";

    ArrayList<Phim> mangphim;
    PhimAdapter phimAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
//        if(CheckConnection.isNetworkAvailable(getApplicationContext())){
//            ActionBar();
//            ActionViewFlipper();
//            GetDuLieuTheLoai();
//        }
//        else{
//            CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại kết nối mạng");
//            finish();
//        }

       ActionBar();
       ActionViewFlipper();
       GetDuLieuTheLoai();
       GetDuLieuPhimMoiNhat();
       CatchOnItemListView();
    }

    private void CatchOnItemListView() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i){
                    case 0:
                        intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, PhimTheoTheLoaiActivity.class);
                        intent.putExtra("id_theloai", mangtheloai.get(i).getId_theloai());
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, PhimTheoTheLoaiActivity.class);
                        intent.putExtra("id_theloai", mangtheloai.get(i).getId_theloai());
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, LienHeActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, ThongTinActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                }
            }
        });
    }

    private void GetDuLieuPhimMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdanphimmoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int id_phim = 0;
                    String ten_phim = "";
                    int thoiluong_phim = 0;
                    String hinh_phim = "";
                    String mota = "";
                    int id_theloai = 0;
                    for(int i = 0;i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id_phim = jsonObject.getInt("id_phim");
                            ten_phim = jsonObject.getString("ten_phim");
                            hinh_phim = jsonObject.getString("hinh_phim");
                            thoiluong_phim = jsonObject.getInt("thoiluong_phim");
                            id_theloai = jsonObject.getInt("id_theloai");
                            mota = jsonObject.getString("mota");

                            mangphim.add(new Phim(id_phim, ten_phim, id_theloai, hinh_phim, thoiluong_phim, mota));
                            phimAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewmanhinhchinh = findViewById(R.id.recycleview);
        navigationView = findViewById(R.id.navigationview);
        listViewmanhinhchinh = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        mangtheloai = new ArrayList<>();
        mangtheloai.add(0,new TheLoai(0,"Trang chủ",Server.DuongdanAnh + "homepage.png"));
        theLoaiAdapter = new TheLoaiAdapter(getApplicationContext(), mangtheloai);
        listViewmanhinhchinh.setAdapter(theLoaiAdapter);
        mangphim = new ArrayList<>();
        phimAdapter = new PhimAdapter(getApplicationContext(), mangphim);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewmanhinhchinh.setAdapter(phimAdapter);
    }

    private void GetDuLieuTheLoai() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdanloaisanpham, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    for(int i =0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id_theloai = jsonObject.getInt("id_theloai");
                            ten_theloai = jsonObject.getString("ten_theloai");
                            hinh_theloai = Server.DuongdanAnh + jsonObject.getString("hinh_theloai");
                            mangtheloai.add(new TheLoai(id_theloai, ten_theloai, hinh_theloai));
                            theLoaiAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    mangtheloai.add(new TheLoai(0,"Thông tin",Server.DuongdanAnh + "account.png"));
                    mangtheloai.add(new TheLoai(0,"Liên hệ",Server.DuongdanAnh +  "contact.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add(Server.DuongdanAnh + "/slide/inception_movie_poster_banner_04.jpg");
        mangquangcao.add(Server.DuongdanAnh + "/slide/hobbit-wide-banner_02.jpg");
        mangquangcao.add(Server.DuongdanAnh + "/slide/twilight-movie-wide.jpg");

        for(int i = 0; i< mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);

        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);

        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);

    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

}
