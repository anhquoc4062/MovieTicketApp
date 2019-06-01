package com.example.appxemphim.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

public class PhimAdapter extends RecyclerView.Adapter<PhimAdapter.ItemHolder> {
    Context context;
    ArrayList<Phim> arrayphim;

    public PhimAdapter(Context context, ArrayList<Phim> arrayphim) {
        this.context = context;
        this.arrayphim = arrayphim;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_phimmoinhat, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Phim phim = arrayphim.get(position);
        holder.txttenphim.setText(phim.getTen_phim());
        holder.txtthoiluongphim.setText(phim.getThoiluong_phim() + " phút");
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

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhphim;
        public TextView txttenphim, txtthoiluongphim;

        public ItemHolder(View itemView) {
            super(itemView);

            imghinhphim = (ImageView) itemView.findViewById(R.id.imageviewhinhphimmoinhat);
            txttenphim = (TextView) itemView.findViewById(R.id.textviewtenphim);
            txtthoiluongphim = (TextView) itemView.findViewById(R.id.textviewthoiluongphim);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietPhimActivity.class);
                    intent.putExtra("id_phim", arrayphim.get(getPosition()).getId_phim());
                    context.startActivity(intent);
                }
            });
        }
    }

}
