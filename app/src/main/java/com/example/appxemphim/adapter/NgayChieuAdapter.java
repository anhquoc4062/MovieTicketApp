package com.example.appxemphim.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appxemphim.R;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NgayChieuAdapter extends PagerAdapter {

    private ArrayList<Date> listNgay;
    private Context context;
    private LayoutInflater inflater;

    public NgayChieuAdapter(ArrayList<Date> listNgay, Context context) {
        this.listNgay = listNgay;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listNgay.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_vp_ngaychieu,container,false);
        Date ngay = listNgay.get(position);
        Calendar cal = Calendar.getInstance();
        cal.setTime(ngay);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String chuoiNgay = day +" tháng "+ month;
        TextView ngaychieu;
        ngaychieu = view.findViewById(R.id.txt_ngaychieu_suatchieu);
        ngaychieu.setText(chuoiNgay);
        ngaychieu.setTag("txt_ngaychieu_"+position);
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
