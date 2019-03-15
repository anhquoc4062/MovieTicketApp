package com.example.appxemphim.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    ImageButton btnDatVe;

    int id_phim = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phim);
        AnhXa();
        ActionToolbar();
        GetDuLieu();

    }

    private void GetDuLieu() {
        id_phim = getIntent().getIntExtra("id_phim", -1);
        //Log.d("id_phim", id_phim+"");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdanphimtheoid;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String tenphim="", hinhphim="", mota="";
                    int thoiluong = 0;
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        thoiluong = jsonObject.getInt("thoiluong_phim");
                        tenphim = jsonObject.getString("ten_phim");
                        hinhphim = jsonObject.getString("hinh_phim");
                        mota = jsonObject.getString("mota");
                    }
                    Log.d("tenphim", tenphim+"");
                    txtTen.setText(tenphim);
                    txtMoTa.setText(mota);
                    txtThoiLuong.setText(thoiluong + " phút");
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarchitiet);
        imgHinh = findViewById(R.id.imageviewchitiet);
        imgBanner = findViewById(R.id.imageviewbanner);
        txtTen = findViewById(R.id.textphimtenphimchitiet);
        txtThoiLuong = findViewById(R.id.textviewthoiluongchitiet);
        txtMoTa = findViewById(R.id.textviewmotachitiet);
        btnDatVe = findViewById(R.id.buttondatve);
    }
}
