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
public class NhanVien implements Comparable<NhanVien> {

    private String maNV;
    private String hoTen;
    private String soDT;
    private String email;
    private String pass;
    private String diaChi;

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public NhanVien() {
    }

    public NhanVien(String maNV, String hoTen, String soDT, String email, String pass, String diaChi) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.soDT = soDT;
        this.email = email;
        this.pass = pass;
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
        VietBang(11, this.maNV);

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
    public int compareTo(NhanVien nv) {
        return this.maNV.compareTo(nv.maNV);
    }
}
