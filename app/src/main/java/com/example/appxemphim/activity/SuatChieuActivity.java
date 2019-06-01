package com.example.appxemphim.activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.appxemphim.R;
import com.example.appxemphim.adapter.GioChieuAdapter;
import com.example.appxemphim.adapter.NgayChieuAdapter;
import com.example.appxemphim.ultil.Server;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SuatChieuActivity extends AppCompatActivity {
    Toolbar tbSuatChieu;
    ViewPager vpKhungNgayChieu;
    NgayChieuAdapter ngayChieuAdapter;
    ArrayList <Date> listNgayChieu;

    RecyclerView rvGioChieu;
    GioChieuAdapter gioChieuAdapter;
    ArrayList<String> listGioChieu;

    Button btnChonGhe;
    ImageView imgBanner;
    TextView txtTenPhim;

    //Dữ liệu cần gửi;
    String ngayChieu="", gioChieu="", tenPhim="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suat_chieu);
        Anhxa();
        ActionBar();
        AddData();
        SetViewPager();
        SetData();
        ClickButton();
    }

    private void ClickButton() {
        btnChonGhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gioChieu = gioChieuAdapter.getChonGioChieu();
                if(gioChieu!=""){
                    Intent intent = new Intent(SuatChieuActivity.this, ChonGheActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ngaychieu",ngayChieu);
                    bundle.putString("giochieu",gioChieu);
                    bundle.putString("tenphim",tenPhim);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
                else{
                    AlertDialog alertDialog = new AlertDialog.Builder(SuatChieuActivity.this).create();
                    alertDialog.setTitle("Thông báo");
                    alertDialog.setMessage("Bạn chưa chọn giờ chiếu");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });
    }

    private void SetData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String bannerName = bundle.getString("banner");
        String movieName = bundle.getString("name");
        //String bannerName = "";
        String path = Server.DuongdanAnhBanner + bannerName;
        Picasso.with(getApplicationContext()).load(path)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(imgBanner);
        tenPhim = movieName;
        txtTenPhim.setText(movieName);
    }

    private void ActionBar() {
        setSupportActionBar(tbSuatChieu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tbSuatChieu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void SetViewPager() {
        vpKhungNgayChieu.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                TextView textView = vpKhungNgayChieu.findViewWithTag("txt_ngaychieu_"+position);
                ngayChieu = textView.getText().toString();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void AddData() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        listNgayChieu.add(today);

        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String chuoiNgay = day +" tháng "+ month;
        //biến cục bộ
        ngayChieu = chuoiNgay;

        ngayChieuAdapter.notifyDataSetChanged();
        for(int i=1;i<=6;i++){
            calendar.setTime(today);
            calendar.add(Calendar.DATE,i);
            Date nextDay = calendar.getTime();
            listNgayChieu.add(nextDay);
            ngayChieuAdapter.notifyDataSetChanged();
        }

        listGioChieu.add("11:00 AM");
        listGioChieu.add("12:00 AM");
        listGioChieu.add("1:00 PM");
        listGioChieu.add("2:30 PM");
        listGioChieu.add("4:00 PM");
        listGioChieu.add("7:30 PM");
        listGioChieu.add("9:00 PM");
        listGioChieu.add("11:30 PM");
    }

    private void Anhxa() {
        tbSuatChieu = findViewById(R.id.tb_suatchieu);

        listNgayChieu = new ArrayList<>();
        ngayChieuAdapter = new NgayChieuAdapter(listNgayChieu,getApplicationContext());

        vpKhungNgayChieu = findViewById(R.id.vp_ngaychieu_suatchieu);
        vpKhungNgayChieu.setAdapter(ngayChieuAdapter);
        vpKhungNgayChieu.setPadding(250,0,250,0);

        listGioChieu = new ArrayList<>();
        gioChieuAdapter = new GioChieuAdapter(getApplicationContext(),listGioChieu, gioChieu);
        rvGioChieu = findViewById(R.id.rv_giochieu_suatchieu);
        rvGioChieu.setAdapter(gioChieuAdapter);
        rvGioChieu.setHasFixedSize(true);
        rvGioChieu.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        rvGioChieu.setFocusable(false);

        btnChonGhe = findViewById(R.id.btn_chonghe_suatchieu);
        imgBanner = findViewById(R.id.img_banner_suatchieu);
        txtTenPhim = findViewById(R.id.txt_tenphim_suatchieu);
    }
}
