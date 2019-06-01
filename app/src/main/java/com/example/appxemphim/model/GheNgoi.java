package com.example.appxemphim.model;

public class GheNgoi {
    public String tenGhe;
    public int trangThai;

    public GheNgoi(String tenGhe, int trangThai) {
        this.tenGhe = tenGhe;
        this.trangThai = trangThai;
    }
    public String getTenGhe() {
        return tenGhe;
    }

    public void setTenGhe(String tenGhe) {
        this.tenGhe = tenGhe;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }


}
