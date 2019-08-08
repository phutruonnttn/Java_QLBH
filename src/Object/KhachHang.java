/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

/**
 *
 * @author Admin
 */
public class KhachHang implements Comparable<KhachHang> {

    private String maKH;
    private String hoTen;
    private String soDT;
    private String email;
    private String diaChi;

    public KhachHang() {
    }

    public KhachHang(String maKH, String hoTen, String soDT, String email, String diaChi) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.soDT = soDT;
        this.email = email;
        this.diaChi = diaChi;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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
        VietBang(11, this.maKH);

        System.out.print("  " + this.hoTen);
        for (int i = 1; i <= (26 - 2 - this.hoTen.length()); i++) {
            System.out.print(" ");
        }
        System.out.print("|");

        VietBang(17, this.soDT);
        VietBang(39, this.email);

        System.out.print(" ");
        if (this.diaChi.length() <= 38) {
            System.out.print(this.diaChi);
            for (int i = 1; i <= (40 - 1 - this.diaChi.length()); i++) {
                System.out.print(" ");
            }
        } else {
            String rutGon = this.diaChi.substring(0, 35);
            System.out.print(rutGon);
            System.out.print("... ");
        }
        System.out.print("|");
        System.out.println("");
    }
    
    @Override//Viết lại phương thức compare của Comparator interface
    public int compareTo(KhachHang kh) {
        return this.maKH.compareTo(kh.maKH);
    }
}
