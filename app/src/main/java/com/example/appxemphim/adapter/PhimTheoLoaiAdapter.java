package com.example.appxemphim.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appxemphim.ItemClickListener;
import com.example.appxemphim.R;
import com.example.appxemphim.activity.ChiTietPhimActivity;
import com.example.appxemphim.model.Phim;
import com.example.appxemphim.ultil.Server;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhimTheoLoaiAdapter extends RecyclerView.Adapter<PhimTheoLoaiAdapter.ItemHolder> {
    Context context;
    ArrayList<Phim> arrayphim;


    public PhimTheoLoaiAdapter(Context context, ArrayList<Phim> arrayphim) {
        this.context = context;
        this.arrayphim = arrayphim;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_phim_theo_loai, null);
        final ItemHolder itemHolder = new ItemHolder(v);
        itemHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, ChiTietPhimActivity.class);
                intent.putExtra("id_phim",arrayphim.get(position).id_phim);
                context.startActivity(intent);
            }
        });
        return itemHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Phim phim = arrayphim.get(position);
        holder.txttenphim.setText(phim.getTen_phim());
        holder.txtthoiluong.setText(phim.getThoiluong_phim() + " phút");
        holder.txtmota.setMaxLines(2);
        holder.txtmota.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtmota.setText(phim.getMota());
        String duongdananh = Server.DuongdanAnhPhim + phim.getHinh_phim();
        Picasso.with(context).load(duongdananh)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imghinhphim);
    }

    @Override
    public int getItemCount() {
        return arrayphim.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        public ImageView imghinhphim;
        public TextView txttenphim, txtthoiluong, txtmota;

        private ItemClickListener itemClickListener;

        public ItemHolder(View itemView) {
            super(itemView);
            this.imghinhphim = (ImageView) itemView.findViewById(R.id.imageviewhinhphim);
            this.txttenphim = (TextView) itemView.findViewById(R.id.textviewtenphim);
            this.txtthoiluong = (TextView) itemView.findViewById(R.id.textviewthoiluong);
            this.txtmota = (TextView) itemView.findViewById(R.id.textviewmota);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }
}
