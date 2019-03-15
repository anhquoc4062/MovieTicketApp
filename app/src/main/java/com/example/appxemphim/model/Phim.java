package com.example.appxemphim.model;

import java.io.Serializable;

public class Phim implements Serializable {
    public int id_phim;
    public String ten_phim;
    public int id_theloai;
    public String hinh_phim;
    public int thoiluong_phim;
    public String mota;

    public Phim(int id_phim, String ten_phim, int id_theloai, String hinh_phim, int thoiluong_phim, String mota) {
        this.id_phim = id_phim;
        this.ten_phim = ten_phim;
        this.id_theloai = id_theloai;
        this.hinh_phim = hinh_phim;
        this.thoiluong_phim = thoiluong_phim;
        this.mota = mota;
    }

    public int getId_phim() {
        return id_phim;
    }

    public void setId_phim(int id_phim) {
        this.id_phim = id_phim;
    }

    public String getTen_phim() {
        return ten_phim;
    }

    public void setTen_phim(String ten_phim) {
        this.ten_phim = ten_phim;
    }

    public int getId_theloai() {
        return id_theloai;
    }

    public void setId_theloai(int id_theloai) {
        this.id_theloai = id_theloai;
    }

    public String getHinh_phim() {
        return hinh_phim;
    }

    public void setHinh_phim(String hinh_phim) {
        this.hinh_phim = hinh_phim;
    }

    public int getThoiluong_phim() {
        return thoiluong_phim;
    }

    public void setThoiluong_phim(int thoiluong_phim) {
        this.thoiluong_phim = thoiluong_phim;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
