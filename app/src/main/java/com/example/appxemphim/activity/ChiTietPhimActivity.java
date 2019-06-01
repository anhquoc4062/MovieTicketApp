package com.example.appxemphim.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appxemphim.R;
import com.example.appxemphim.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChiTietPhimActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imgHinh, imgBanner;
    TextView txtTen;
    TextView txtThoiLuong;
    TextView txtMoTa;
    Button btnDatVe;
    WebView webView;

    int id_phim = 0;
    int id_theloai = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phim);
        AnhXa();
        ActionToolbar();
        GetDuLieu();
    }



    private void SetOnClickButton(final String mbanner, final String mtenphim) {
        btnDatVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietPhimActivity.this, SuatChieuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("banner",mbanner);
                bundle.putString("name",mtenphim);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void SetButtonEnabled() {
        if(id_theloai == 2) {
            btnDatVe.setText("COMMING SOON");
        }
    }

    private void SetWebView(String mvideo_id) {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setBackgroundColor(Color.BLACK);
        String youtubeString = "<iframe style=\"position:fixed; top:0px; left:0px; bottom:0px; right:0px; width:100%; height:100%; border:none; margin:0; padding:0; overflow:hidden; z-index:999999;\" src=\"https://www.youtube.com/embed/"+mvideo_id+"\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowFullScreen></iframe>";
        webView.loadData(youtubeString,"text/html","utf-8");
    }

    private void GetDuLieu() {
        id_phim = getIntent().getIntExtra("id_phim", -1);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdanphimtheoid;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String tenphim="", hinhphim="", mota="", ytb_id="", bannerphim = "";
                    int thoiluong = 0;
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        thoiluong = jsonObject.getInt("thoiluong_phim");
                        tenphim = jsonObject.getString("ten_phim");
                        hinhphim = jsonObject.getString("hinh_phim");
                        mota = jsonObject.getString("mota");
                        ytb_id = jsonObject.getString("trailer_phim");
                        bannerphim = jsonObject.getString("banner_phim");

                        //bien toan cuc
                        id_theloai = jsonObject.getInt("id_theloai");
                    }
                    Log.d("tenphim", tenphim+"");
                    txtTen.setText(tenphim);
                    txtMoTa.setText(mota);
                    txtThoiLuong.setText(thoiluong + " phút");
                    SetWebView(ytb_id);

                    SetOnClickButton(bannerphim, tenphim);
                    Picasso.with(getApplicationContext()).load(Server.DuongdanAnhPhim + hinhphim)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.error)
                            .into(imgHinh);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("id_phim", String.valueOf(id_phim));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarchitiet);
        imgHinh = findViewById(R.id.img_hinhphim_chitiet);
        txtTen = findViewById(R.id.txt_tenphim_chitiet);
        txtThoiLuong = findViewById(R.id.txt_thoiluong_chitiet);
        txtMoTa = findViewById(R.id.txt_mota_chitiet);
        btnDatVe = findViewById(R.id.btn_datve_chitiet);
        webView = findViewById(R.id.wv_trailer_phim);
    }
}
