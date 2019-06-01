package com.example.appxemphim.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appxemphim.R;
import com.example.appxemphim.activity.ChiTietPhimActivity;
import com.example.appxemphim.model.Phim;
import com.example.appxemphim.ultil.Server;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewPaperAdapter extends PagerAdapter {
    private ArrayList<Phim> listPhim;
    private LayoutInflater inflater;
    private Context context;
    private static int modelPosition;

    public ViewPaperAdapter(ArrayList<Phim> listPhim, Context context) {
        this.listPhim = listPhim;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listPhim.size();
    }

    public int getRealCount() {
        return listPhim.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = LayoutInflater.from(context);
        View view = (View) inflater.inflate(R.layout.item_in_viewpaper, container, false);
        Phim phim = listPhim.get(position);
        ImageView hinhphim;
        TextView tenphim, thoiluong;
        final int id_phim;

        hinhphim = view.findViewById(R.id.img_hinhphim_viewpaper);
        tenphim = view.findViewById(R.id.txt_tenphim_viewpaper);
        thoiluong = view.findViewById(R.id.txt_thoiluong_viewpaper);

        id_phim = phim.getId_phim();
        String duongdananh = Server.DuongdanAnhPhim + phim.getHinh_phim();

        tenphim.setText(phim.getTen_phim());
        String thoiluongphim = phim.getThoiluong_phim() + " phút";
        thoiluong.setText(thoiluongphim);
        Picasso.with(context).load(duongdananh)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(hinhphim);

        container.addView(view, 0);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChiTietPhimActivity.class);
                intent.putExtra("id_phim", id_phim);
                context.startActivity(intent);
            }
        });

        return view;
    }

    private int mapPagerPositionToModelPosition(int pagerPosition) {
        // Put last page model to the first position.
        if (pagerPosition == 0) {
            return getRealCount() - 1;
        }
        // Put first page model to the last position.
        if (pagerPosition == getRealCount() + 1) {
            return 0;
        }
        return pagerPosition - 1;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
