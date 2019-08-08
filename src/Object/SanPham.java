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
public class SanPham implements Comparable<SanPham> {

    private String maSP;
    private String ten;
    private String donViTinh;
    private String tenNSX;
    private String giaNhap;
    private String giaBan;
    private String soLuong;
    DinhDang fm = new DinhDang();

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getTenNSX() {
        return tenNSX;
    }

    public void setTenNSX(String tenNSX) {
        this.tenNSX = tenNSX;
    }

    public String getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(String giaNhap) {
        this.giaNhap = giaNhap;
    }

    public String getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(String giaBan) {
        this.giaBan = giaBan;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public DinhDang getFm() {
        return fm;
    }

    public void setFm(DinhDang fm) {
        this.fm = fm;
    }

    public SanPham(String maSP, String ten, String donViTinh, String tenNSX, String giaNhap, String giaBan, String soLuong) {
        this.maSP = maSP;
        this.ten = ten;
        this.donViTinh = donViTinh;
        this.tenNSX = tenNSX;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
    }

    public SanPham() {
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

    public void VietBang2(int khoangTrong, String s) {
        int demKhoangTrong = khoangTrong - s.length();

        for (int i = 1; i <= demKhoangTrong -6; i++) {
            System.out.print(" ");
        }
        System.out.print(s);
        for (int i = 1; i <= 6; i++) {
            System.out.print(" ");
        }
        System.out.print("|");
    }

    public void inTT() {
        VietBang(11, this.maSP);
        VietBang(20, this.ten);
        VietBang2(16, fm.chuanHoaSo(this.soLuong));
        VietBang(15, this.donViTinh);
        VietBang(19, this.tenNSX);
        VietBang(16, fm.chuanHoaSo(this.giaNhap));
        VietBang(17, fm.chuanHoaSo(this.giaBan));
        System.out.println("");
    }

    public void inTT2() {
        VietBang(11, this.maSP);
        VietBang(20, this.ten);
        VietBang(16, fm.chuanHoaSo(this.soLuong));
        VietBang(15, this.donViTinh);
        VietBang(19, this.tenNSX);
        VietBang(19, fm.chuanHoaSo(this.giaBan));
        System.out.println("");
    }

    public void inTT3() {
        VietBang(11, this.maSP);
        VietBang(22, this.ten);
        VietBang(15, this.donViTinh);
        VietBang(16, fm.chuanHoaSo(this.soLuong));
        VietBang(21, this.tenNSX);
        VietBang(20, fm.chuanHoaSo(this.giaNhap));
        VietBang(19, fm.chuanHoaSo(this.giaBan));
        System.out.println("");
    }

    @Override//Viết lại phương thức compare của Comparator interface
    public int compareTo(SanPham sp) {
        return this.maSP.compareTo(sp.maSP);
    }
}
