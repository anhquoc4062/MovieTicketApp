package com.example.appxemphim.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appxemphim.R;
import com.example.appxemphim.adapter.PhimTheoLoaiAdapter;
import com.example.appxemphim.model.Phim;
import com.example.appxemphim.ultil.CheckConnection;
import com.example.appxemphim.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhimTheoTheLoaiActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recycleview;
    PhimTheoLoaiAdapter phimTheoLoaiAdapter;
    ArrayList<Phim> mangphim;
    View footerView;
    mHandler mHandler;

    int id_theloai = 0;
    int page = 1;

    boolean isLoading = false;
    boolean limitData = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phim_theo_loai);
        Anhxa();
        GetIdTheLoai();
        ActionBar();
        GetPhimTheoTheLoai(page);
        LoadMoreData();
    }

    private void LoadMoreData() {
        recycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    Log.d("test", "đã scroll");
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int totalItem = linearLayoutManager.getItemCount();
                    int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
                    int visibleItem = 4;
                    if(totalItem == (firstItem + visibleItem) && totalItem !=0 && !isLoading && !limitData){
                        Log.d("test", "vô if rồi");
                        isLoading = true;
                        ThreadData threadData = new ThreadData();
                        threadData.start();
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void GetPhimTheoTheLoai(int m_page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdanphimtheotheloai + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tenphim = "";
                int thoiluong = 0;
                String hinhanh = "";
                String mota = "";
                int id_theloai = 0;

                if(response != null && response.length() !=  2){
                    recycleview.removeView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id_phim");
                            tenphim = jsonObject.getString("ten_phim");
                            hinhanh = jsonObject.getString("hinh_phim");
                            thoiluong = jsonObject.getInt("thoiluong_phim");
                            id_theloai = jsonObject.getInt("id_theloai");
                            mota = jsonObject.getString("mota");

                            mangphim.add(new Phim(id, tenphim, id_theloai, hinhanh, thoiluong, mota));
                            phimTheoLoaiAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    limitData = true;
                    recycleview.removeView(footerView);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Đã đến cuối danh sách");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("id_theloai", String.valueOf(id_theloai));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void GetIdTheLoai() {
        id_theloai = getIntent().getIntExtra("id_theloai", -1);
        if(id_theloai == 1){
            toolbar.setTitle("Phim đang chiếu");
        }
        else{
            toolbar.setTitle("Phim sắp chiếu");
        }
        Log.d("Giatritheloai", id_theloai+"");

    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarmanhinhphim);
        recycleview = findViewById(R.id.recycleviewphim);
        mangphim = new ArrayList<>();
        phimTheoLoaiAdapter = new PhimTheoLoaiAdapter(getApplicationContext(), mangphim);
        recycleview.setAdapter(phimTheoLoaiAdapter);
        recycleview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progress_bar, null);
        mHandler = new mHandler();

    }

    public class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Đang tải phim...");
                    //recycleview.addView(footerView);
                    break;
                case 1:
                    GetPhimTheoTheLoai(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
