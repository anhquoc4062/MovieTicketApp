package com.example.appxemphim.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appxemphim.R;
import com.example.appxemphim.model.TheLoai;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TheLoaiAdapter extends BaseAdapter {
    public TheLoaiAdapter(Context context, ArrayList<TheLoai> arrayListtheloai) {
        this.context = context;
        this.arrayListtheloai = arrayListtheloai;
    }

    Context context;
    ArrayList<TheLoai> arrayListtheloai;

    @Override
    public int getCount() {
        return arrayListtheloai.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListtheloai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView txt_tentheloai;
        ImageView img_hinhtheloai;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
         if(view == null){
             viewHolder = new ViewHolder();
             LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             view = inflater.inflate(R.layout.dong_listview_theloai,null);
             viewHolder.txt_tentheloai = view.findViewById(R.id.textviewtheloai);
             viewHolder.img_hinhtheloai = view.findViewById(R.id.imageviewtheloai);
             view.setTag(viewHolder);
         }else{
             viewHolder = (ViewHolder) view.getTag();

         }
        TheLoai theloai = (TheLoai) this.getItem(i);
        viewHolder.txt_tentheloai.setText(theloai.getTen_theloai());
        Picasso.with(context).load(theloai.getHinh_theloai())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.img_hinhtheloai);
        return view;
    }
}
