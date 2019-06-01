package com.example.appxemphim.activity;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appxemphim.R;
import com.example.appxemphim.adapter.ViewPaperAdapter;
import com.example.appxemphim.model.Phim;
import com.example.appxemphim.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class ThongTinActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewPaperAdapter viewPaperAdapter;
    ArrayList<Phim> listPhim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_actitity);

        Anhxa();

        //listPhim.add(new Phim(1,"abc",1,"abc.jpg",12,"sdafs"));
        //listPhim.add(new Phim(1,"def",1,"abc.jpg",31231,"sdafsdf"));
        //listPhim.add(new Phim(1,"aghibc",1,"abc.jpg",213,"sdfasd"));
        //viewPaperAdapter.notifyDataSetChanged();
        GetData();
    }

    private void GetData() {
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

                            listPhim.add(new Phim(id_phim, ten_phim, id_theloai, hinh_phim, thoiluong_phim, mota));
                            viewPaperAdapter.notifyDataSetChanged();

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
        listPhim = new ArrayList<>();
        viewPager = findViewById(R.id.viewpaper);
        viewPaperAdapter = new ViewPaperAdapter(listPhim, getApplicationContext());
        viewPager.setAdapter(viewPaperAdapter);
        viewPager.setPadding(0,0,50,0);
        viewPager.setPageMargin(10);
    }
}
