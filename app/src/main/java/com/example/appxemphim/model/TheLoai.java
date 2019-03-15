package com.example.appxemphim.model;

public class TheLoai {
    public int id_theloai;
    public String ten_theloai;
    public String hinh_theloai;

    public int getId_theloai() {
        return id_theloai;
    }

    public void setId_theloai(int id_theloai) {
        this.id_theloai = id_theloai;
    }

    public String getTen_theloai() {
        return ten_theloai;
    }

    public void setTen_theloai(String ten_theloai) {
        this.ten_theloai = ten_theloai;
    }

    public String getHinh_theloai() {
        return hinh_theloai;
    }

    public void setHinh_theloai(String hinh_theloai) {
        this.hinh_theloai = hinh_theloai;
    }
    public TheLoai(int id_theloai, String ten_theloai, String hinh_theloai) {
        this.id_theloai = id_theloai;
        this.ten_theloai = ten_theloai;
        this.hinh_theloai = hinh_theloai;
    }


}
