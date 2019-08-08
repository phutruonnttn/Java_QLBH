/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import CheckData.DinhDang;

/**
 *
 * @author Admin
 */
public class HoaDon {

    private String maHD;
    private String maKH;
    private String maNV;
    private String tenNV;
    private String maSP;
    private String tenSP;
    private String donViTinh;
    private String soLuong;
    private String tenNSX;
    private String giaBan;
    private String giaNhap;
    private String date;
    private String gio;
    DinhDang fm = new DinhDang();

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }
    
    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getTenNSX() {
        return tenNSX;
    }

    public void setTenNSX(String tenNSX) {
        this.tenNSX = tenNSX;
    }

    public String getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(String giaBan) {
        this.giaBan = giaBan;
    }

    public String getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(String giaNhap) {
        this.giaNhap = giaNhap;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DinhDang getFm() {
        return fm;
    }

    public void setFm(DinhDang fm) {
        this.fm = fm;
    }

    public HoaDon() {
    }

    public HoaDon(String maHD, String maKH, String maNV, String tenNV, String maSP, String tenSP, String donViTinh, String soLuong, String tenNSX, String giaBan, String giaNhap, String date, String gio) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donViTinh = donViTinh;
        this.soLuong = soLuong;
        this.tenNSX = tenNSX;
        this.giaBan = giaBan;
        this.giaNhap = giaNhap;
        this.date = date;
        this.gio = gio;
    }

    public void VietBang(int khoangTrong, String s) {
        int demKhoangTrong = khoangTrong - s.length();
        if (demKhoangTrong % 2 == 0) {
            for (int i = 1; i <= demKhoangTrong / 2; i++) {
                System.out.print(" ");
            }
            System.out.print(s);
            for (int i = 1; i <= demKhoangTrong / 2; i++) {
                System.out.print(" ");
            }
        } else {
            for (int i = 1; i <= demKhoangTrong / 2; i++) {
                System.out.print(" ");
            }
            System.out.print(s);
            for (int i = 1; i <= demKhoangTrong / 2 + 1; i++) {
                System.out.print(" ");
            }
        }
        System.out.print("|");
    }

    public void inTT() {
        VietBang(11, this.maSP);
        VietBang(20, this.tenSP);
        VietBang(16, fm.chuanHoaSo(this.soLuong));
        VietBang(15, this.donViTinh);
        VietBang(19, this.tenNSX);
        VietBang(19, fm.chuanHoaSo(this.giaBan));
        System.out.println("");
    }

//11-22-15-16-21  
    public void inTT2() {
        VietBang(11, this.maSP);
        VietBang(22, this.tenSP);
        VietBang(15, this.donViTinh);
        VietBang(16, fm.chuanHoaSo(this.soLuong));
    }
    
    public void inTT3() {
        VietBang(30, this.maHD);
        VietBang(14, this.date);
        VietBang(11, this.maSP);
        VietBang(22, this.tenSP);
        VietBang(15, this.donViTinh);
        VietBang(16, fm.chuanHoaSo(this.soLuong));
    }
}
