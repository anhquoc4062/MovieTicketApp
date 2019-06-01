package com.example.appxemphim.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.appxemphim.R;
import com.example.appxemphim.model.GheNgoi;

import java.util.ArrayList;

public class GheNgoiAdapter extends RecyclerView.Adapter<GheNgoiAdapter.ItemHolder> {
    public Context context;
    public ArrayList<GheNgoi> listGheNgoi;
    public Button btnThanhToan;

    public GheNgoiAdapter(Context context, ArrayList<GheNgoi> listGheNgoi, Button btnThanhToan) {
        this.context = context;
        this.listGheNgoi = listGheNgoi;
        this.btnThanhToan = btnThanhToan;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_chonghe,null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    public int getMoney(String a){
        String[] arStr = a.split(" - ");
        return Integer.parseInt(arStr[arStr.length-1].substring(1));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
        final GheNgoi gheNgoi = listGheNgoi.get(position);
        holder.llGheNgoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int curMoney = getMoney(btnThanhToan.getText()+"");
                if(gheNgoi.trangThai == 0){
                    gheNgoi.trangThai = 2;
                    curMoney+=9;
                }
                else if(gheNgoi.trangThai == 2){
                    gheNgoi.trangThai = 0;
                    curMoney-=9;
                }
                btnThanhToan.setText("THANH TOÁN - $"+curMoney);
                notifyItemChanged(position);
            }
        });
        if(gheNgoi.getTrangThai() == 0){//chua ai dat
            holder.llGheNgoi.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.whiteText));
        }
        else if (gheNgoi.getTrangThai()==1){//co nguoi dat
            holder.llGheNgoi.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.darkGrayText));
        }
        else if(gheNgoi.getTrangThai()==-1){//disabled
            holder.llGheNgoi.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.blackBackground));
        }
        else{//chon ghe
            holder.llGheNgoi.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.pinkText));
        }
    }

    @Override
    public int getItemCount() {
        return listGheNgoi.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public LinearLayout llGheNgoi;
        public String tenGhe;
        public int trangThai;
        public ItemHolder(final View itemView) {
            super(itemView);

            llGheNgoi = itemView.findViewById(R.id.ll_ghe_chonghe);
        }
    }
}
