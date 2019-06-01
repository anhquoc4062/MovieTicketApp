package com.example.appxemphim.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appxemphim.R;
import com.example.appxemphim.activity.ChiTietPhimActivity;

import java.util.ArrayList;

public class GioChieuAdapter extends RecyclerView.Adapter<GioChieuAdapter.ItemHolder> {
    Context context;
    ArrayList<String> listGioChieu;
    String chonGioChieu;

    public GioChieuAdapter(Context context, ArrayList<String> listGioChieu, String chonGioChieu) {
        this.context = context;
        this.listGioChieu = listGioChieu;
        this.chonGioChieu = chonGioChieu;
    }

    public String getChonGioChieu() {
        return chonGioChieu;
    }

    public void setChonGioChieu(String chonGioChieu) {
        this.chonGioChieu = chonGioChieu;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_giochieu,null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }
    int selected_positon = -1;
    @Override
    public void onBindViewHolder(@NonNull GioChieuAdapter.ItemHolder holder, final int position) {
        String gioChieu = listGioChieu.get(position);
        holder.txtGioChieu.setText(gioChieu);
        holder.itemGioChieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_positon = position;
                chonGioChieu = "";
                chonGioChieu = listGioChieu.get(position);
                notifyDataSetChanged();
            }
        });
        if(selected_positon == position){
            holder.txtGioChieu.setTextColor(ContextCompat.getColor(context,R.color.whiteText));
            holder.txtGioChieu.setBackground(ContextCompat.getDrawable(context,R.drawable.border_selected));
        }
        else{
            holder.txtGioChieu.setTextColor(ContextCompat.getColor(context,R.color.grayText));
            holder.txtGioChieu.setBackground(ContextCompat.getDrawable(context,R.drawable.border));
        }
    }

    @Override
    public int getItemCount() {
        return listGioChieu.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public TextView txtGioChieu;
        public LinearLayout itemGioChieu;
        public ItemHolder(final View itemView) {
            super(itemView);

            txtGioChieu = itemView.findViewById(R.id.txt_giochieu_suatchieu);
            itemGioChieu = itemView.findViewById(R.id.ll_giochieu_suatchieu);

        }
    }
}
