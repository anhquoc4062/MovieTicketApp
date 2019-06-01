package com.example.appxemphim.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appxemphim.R;
import com.example.appxemphim.adapter.GheNgoiAdapter;
import com.example.appxemphim.model.GheNgoi;
import com.example.appxemphim.ultil.Server;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChonGheActivity extends AppCompatActivity {
    Toolbar tbChonGhe;
    TextView txtTenPhim, txtNgayGioChieu;
    RecyclerView rvGheNgoi;
    GheNgoiAdapter gheNgoiAdapter;
    ArrayList<GheNgoi> listGheNgoi;
    Button btnThanhToan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_ghe);
        AnhXa();
        ActionBar();
        SetData();
        GenerateGheNgoi();

    }
    private void ActionBar() {
        setSupportActionBar(tbChonGhe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tbChonGhe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void SetData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String tenphim = bundle.getString("tenphim");
        String ngayChieu = bundle.getString("ngaychieu");
        String gioChieu = bundle.getString("giochieu");

        String ngayGioChieu = ngayChieu + " - " + gioChieu;
        txtTenPhim.setText(tenphim);
        txtNgayGioChieu.setText(ngayGioChieu);
    }

    private void GenerateGheNgoi() {
        for(int i=1;i<=13;i++){
            String seatName = (char)(65+i)+"";
            for(int j=1;j<=10;j++){
                if((j==1&&i==1)||(j==1&&i==2)||(j==2&&i==1)||(i==1&&j==10)||(i==2&&j==10)||(i==1&&j==9)||(i==7)||(i==13&&j==1)||(i==13&&j==10)){
                    listGheNgoi.add(new GheNgoi("none",-1));
                    gheNgoiAdapter.notifyDataSetChanged();
                }
                else{
                    seatName+=(char)j;
                    listGheNgoi.add(new GheNgoi(seatName,0));
                    gheNgoiAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void AnhXa() {
        tbChonGhe = findViewById(R.id.tb_chonghe);
        btnThanhToan = findViewById(R.id.btn_chonghe_suatchieu);

        listGheNgoi = new ArrayList<>();
        gheNgoiAdapter = new GheNgoiAdapter(getApplicationContext(),listGheNgoi, btnThanhToan);
        rvGheNgoi = findViewById(R.id.rv_ghengoi_chonghe);
        rvGheNgoi.setAdapter(gheNgoiAdapter);
        rvGheNgoi.setFocusable(false);
        rvGheNgoi.setLayoutManager(new GridLayoutManager(getApplicationContext(),10));

        txtNgayGioChieu = findViewById(R.id.txt_ngaygiochieu_chonghe);
        txtTenPhim = findViewById(R.id.txt_tenphim_chonghe);
    }
}
