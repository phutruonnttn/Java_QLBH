/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import CheckData.CheckData;
import CheckData.DinhDang;
import Object.SanPham;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class AppSanPham {

    ArrayList<SanPham> listSP = new ArrayList<>();
    private final String nameFile = "SanPham.txt";
    CheckData check = new CheckData();
    DinhDang fm = new DinhDang();
    private int timKiem;
    private boolean flagTK = false;

    public void menuSP() {
        Scanner sc = new Scanner(System.in);
        int chon;
        do {
            System.out.println("--------------------- QUẢN LÝ SẢN PHẨM ---------------------");
            System.out.println("");
            System.out.println("    +-----+----------------------------------------+");
            System.out.println("    |           HỆ THỐNG QUẢN LÝ SẢN PHẨM          |");
            System.out.println("    +-----+----------------------------------------+");
            System.out.println("    | [1] |   Xem danh sách toàn bộ sản phẩm       |");
            System.out.println("    +-----+----------------------------------------+");
            System.out.println("    | [2] |   Tìm kiếm sản phẩm theo từ khóa       |");
            System.out.println("    +-----+----------------------------------------+");
            System.out.println("    | [3] |   Thêm mới một sản phẩm                |");
            System.out.println("    +-----+----------------------------------------+");
            System.out.println("    | [4] |   Sửa thông tin sản phẩm               |");
            System.out.println("    +-----+----------------------------------------+");
            System.out.println("    | [5] |   Xóa thông tin sản phẩm               |");
            System.out.println("    +-----+----------------------------------------+");
            System.out.println("    | [6] |   Quay lại HỆ THỐNG QUẢN LÝ CHUNG      |");
            System.out.println("    +-----+----------------------------------------+");
            System.out.print("    Mời bạn chọn chức năng: ");
            chon = sc.nextInt();
            System.out.println("");
            switch (chon) {
                case 1: {
                    docFileSP();
                    xemDS(listSP);
                    break;
                }
                case 2: {
                    flagTK = true;
                    xemDS(timKiemTheoTuKhoa());
                    flagTK = false;
                    break;
                }
                case 3: {
                    themSP();
                    break;
                }
                case 4: {
                    suaSP();
                    break;
                }
                case 5: {
                    xoaSP();
                    break;
                }
                case 6: {
                    break;
                }
                default: {
                    System.err.println("==>> BẠN CHỌN SAI CHỨC NĂNG !");
                }
                System.out.println("");
            }
        } while (chon != 6);
    }

    public void docFileSP() {
        try {
            BufferedReader br = null;
            FileReader fr = null;
            listSP = new ArrayList<>();

            fr = new FileReader(this.nameFile);
            br = new BufferedReader(fr);

            String info = null;
            try {
                while ((info = br.readLine()) != null) {
                    String chiaTT[] = info.split("\t");
                    SanPham sp = new SanPham();
                    if (chiaTT.length == 1) {
                        return;
                    }
                    sp.setMaSP(chiaTT[0]);
                    sp.setTen(chiaTT[1]);
                    sp.setSoLuong(chiaTT[2]);
                    sp.setDonViTinh(chiaTT[3]);
                    sp.setTenNSX(chiaTT[4]);
                    sp.setGiaBan(chiaTT[5]);
                    sp.setGiaNhap(chiaTT[6]);
                    listSP.add(sp);
                }
            } catch (IOException ex) {
                Logger.getLogger(AppSanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppSanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ghiFileSP() {
        try {
            BufferedWriter bw = null;
            FileWriter fw = null;
            String data = "";
            for (int i = 0; i < listSP.size(); i++) {
                data += listSP.get(i).getMaSP().toUpperCase() + "\t";
                data += listSP.get(i).getTen() + "\t";
                data += listSP.get(i).getSoLuong() + "\t";
                data += listSP.get(i).getDonViTinh() + "\t";
                data += listSP.get(i).getTenNSX() + "\t";
                data += listSP.get(i).getGiaBan() + "\t";
                data += listSP.get(i).getGiaNhap() + "\n";
            }
            fw = new FileWriter(this.nameFile);
            bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(AppSanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void VietBang(int khoangTrong, String s) {
        System.out.print("|");
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
    private String tuKhoa;

    public void xemDS(ArrayList<SanPham> list) {
        if (flagTK == true && timKiem == 0) {
            System.err.println("==>> KHÔNG TÌM THẤY TỪ KHÓA TRONG DANH SÁCH SẢN PHẨM !");
            return;
        }
        if (list.isEmpty()) {
            System.err.println("==>> CHƯA CÓ SẢN PHẨM NÀO TRONG DANH SÁCH !");
        } else {

            if (flagTK == true) {
                System.out.println("");
                System.err.println("==>> ĐÃ TÌM THẤY " + timKiem + " KẾT QUẢ THÔNG TIN SẢN PHẦM VỚI TỪ KHÓA '" + tuKhoa + "'");
            }
            Collections.sort(listSP);
            ghiFileSP();
            System.out.println("+--------------------------------------------------------------------------------------------------------------------------------+");
            System.out.println("|                                                  DANH SÁCH THÔNG TIN SẢN PHẨM                                                  |");
            System.out.println("+-------+-----------+--------------------+----------------+---------------+-------------------+----------------+-----------------+");
            System.out.println("|  STT  |   MÃ SP   |    TÊN SẢN PHẨM    |    SỐ LƯỢNG    |  ĐƠN VỊ TÍNH  |      TÊN NSX      |    GIÁ NHẬP    |     GIÁ BÁN     |");
            System.out.println("+-------+-----------+--------------------+----------------+---------------+-------------------+----------------+-----------------+");
            for (int i = 0; i < list.size(); i++) {
                String stt = String.valueOf(i + 1);
                VietBang(7, stt);
                list.get(i).inTT();
            }
            System.out.println("+-------+-----------+--------------------+----------------+---------------+-------------------+----------------+-----------------+");
            System.out.println("");
        }
    }

    public ArrayList<SanPham> timKiemTheoTuKhoa() {
        ArrayList<SanPham> listSPTimKiem = new ArrayList<>();
        timKiem = 0;
        Scanner sc = new Scanner(System.in);
        System.out.print("Mời bạn nhập vào thông tin sản phẩm cần tìm: ");
        String input = sc.nextLine();
        for (int i = 0; i < listSP.size(); i++) {
            if (listSP.get(i).getMaSP().contains(input) || listSP.get(i).getTen().contains(input) || listSP.get(i).getTenNSX().contains(input)) {
                listSPTimKiem.add(listSP.get(i));
                timKiem++;
            }
        }
        tuKhoa = input;
        return listSPTimKiem;
    }

    public boolean kiemTraMaSP(String ma) {
        boolean flag = false;
        for (int i = 0; i < listSP.size(); i++) {
            if (ma.equalsIgnoreCase(listSP.get(i).getMaSP())) {//So sánh String với String khác, bỏ qua sự khác nhau về kiểu chữ
                flag = true;
                break;
            }
        }
        return flag;
    }

    public void themSP() {
        SanPham sp = new SanPham();
        Scanner input = new Scanner(System.in);
        System.out.println("NHẬP THÔNG TIN ĐỂ THÊM MỚI SẢN PHẨM");
        System.out.println("");
        do {
            System.out.print("Mã sản phẩm: ");
            sp.setMaSP(input.nextLine());
            if ("".equals(sp.getMaSP())) {
                System.err.println("Mã sản phẩm không được bỏ trống !");
            }
        } while ("".equals(sp.getMaSP()));
        if (kiemTraMaSP(sp.getMaSP())) {
            System.err.println("==>> MÃ SẢN PHẨM ĐÃ TỒN TẠI !");
            return;
        } else {
            do {
                System.out.print("Tên sản phẩm: ");
                sp.setTen(input.nextLine());
                if ("".equals(sp.getTen())) {
                    System.err.println("Tên sản phẩm không được bỏ trống !");
                }
            } while ("".equals(sp.getTen()));

            do {
                System.out.print("Đơn vị tính: ");
                sp.setDonViTinh(input.nextLine());
                if ("".equals(sp.getDonViTinh())) {
                    System.err.println("Đơn vị tính không được bỏ trống !");
                }
            } while ("".equals(sp.getDonViTinh()));

            do {
                System.out.print("Tên nhà sản xuất: ");
                sp.setTenNSX(input.nextLine());
                if ("".equals(sp.getTenNSX())) {
                    System.err.println("Tên nhà sản xuất không được bỏ trống !");
                }
            } while ("".equals(sp.getTenNSX()));

            do {
                System.out.print("Số lượng sản phẩm: ");
                sp.setSoLuong(input.nextLine());
                if (!check.kiemTraSo(sp.getSoLuong())) {
                    System.err.println("Số lượng sản phẩm phải lớn hơn 0 !");
                }
            } while (!check.kiemTraSo(sp.getSoLuong()));

            do {
                System.out.print("Giá nhập của sản phẩm: ");
                sp.setGiaNhap(input.nextLine());
                if (!check.kiemTraSo(sp.getGiaNhap())) {
                    System.err.println("Giá nhập của sản phẩm phải lớn hơn 0 !");
                }
            } while (!check.kiemTraSo(sp.getGiaNhap()));
            sp.setGiaNhap(fm.chuanHoaSo(sp.getGiaNhap()));

            do {
                System.out.print("Giá bán của sản phẩm: ");
                sp.setGiaBan(input.nextLine());
                if (!check.kiemTraSo(sp.getGiaBan())) {
                    System.err.println("Giá bán của sản phẩm phải lớn hơn 0 !");
                } else if (fm.chuanHoaNguocSo(sp.getGiaBan()) < fm.chuanHoaNguocSo(sp.getGiaNhap())) {
                    System.err.println("Giá bán phải lớn hơn hoặc bằng giá nhập !");
                }

            } while (!check.kiemTraSo(sp.getGiaBan()) || fm.chuanHoaNguocSo(sp.getGiaBan()) < fm.chuanHoaNguocSo(sp.getGiaNhap()));
            sp.setGiaBan(fm.chuanHoaSo(sp.getGiaBan()));
        }
        listSP.add(sp);
        ghiFileSP();
        System.err.println("==>> THÊM MỚI SẢN PHẨM THÀNH CÔNG !");
        System.out.println("");
    }

    private int chiSo;

    public SanPham timSP(String ma) {
        for (int i = 0; i < listSP.size(); i++) {
            if (ma.equalsIgnoreCase(listSP.get(i).getMaSP())) {
                chiSo = i;
                return listSP.get(i);
            }
        }
        return null;
    }

    public void xoaSP() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Mời bạn nhập mã sản phẩm cần xóa: ");
        String ma = sc.nextLine();
        SanPham sp = timSP(ma);
        if (sp == null) {
            System.err.println("==>> SẢN PHẨM BẠN VỪA NHẬP KHÔNG TỒN TẠI TRONG HỆ THỐNG !");
        } else {
            listSP.remove(sp);
            System.out.println("");
            System.err.println("==>> XÓA THÀNH CÔNG SẢN PHẨM CÓ MÃ " + sp.getMaSP());
            ghiFileSP();
        }
    }

    public void suaSP() {
        Scanner sc = new Scanner(System.in);
        SanPham sp;
        do {
            System.out.print("Mời bạn nhập mã sản phẩm cần sửa: ");
            String maSP = sc.nextLine();
            sp = timSP(maSP);
            if (sp == null) {
                System.err.println("Mã sản phẩm bạn vừa nhập không tồn tại !");
                System.out.println("Vui lòng thử lại ... ");
            }
        } while (sp == null);
        if (sp != null) {
            String tenCu = sp.getTen();
            String soLuongCu = sp.getSoLuong();
            String donViTinhCu = sp.getDonViTinh();
            String tenNSXcu = sp.getTenNSX();
            String giaNhapCu = sp.getGiaNhap();
            String giaBanCu = sp.getGiaBan();

            System.out.println("");
            System.err.println("MỜI BẠN CẬP NHẬT THÔNG TIN SẢN PHẨM CÓ MÃ " + sp.getMaSP().toUpperCase() + " (LƯU Ý: BỎ TRỐNG nếu bạn không muốn cập nhật thông tin đó !) ");

            System.out.println("Tên sản phẩm cũ: " + sp.getTen());
            System.out.print("==>> Nhập tên mới: ");
            sp.setTen(sc.nextLine());
            if ("".equals(sp.getTen())) {
                sp.setTen(tenCu);
            }

            System.out.println("Đơn vị tính cũ: " + sp.getDonViTinh());
            System.out.print("==>> Nhập đơn vị tính mới: ");
            sp.setDonViTinh(sc.nextLine());
            if ("".equals(sp.getDonViTinh())) {
                sp.setDonViTinh(donViTinhCu);
            }

            System.out.println("Tên NSX cũ: " + sp.getTenNSX());
            System.out.print("==>> Nhập tên NSX mới: ");
            sp.setTenNSX(sc.nextLine());
            if ("".equals(sp.getTenNSX())) {
                sp.setTenNSX(tenNSXcu);
            }

            System.out.println("");
            System.out.println("Số lượng cũ: " + sp.getSoLuong());
            do {
                System.out.print("==>> Nhập số lượng mới: ");
                sp.setSoLuong(sc.nextLine());
                if ("".equals(sp.getSoLuong())) {
                    sp.setSoLuong(soLuongCu);
                    break;
                }
                if (!check.kiemTraSo(sp.getSoLuong())) {
                    System.err.println("Số lượng sản phẩm phải lớn hơn 0 !");
                }
            } while (!check.kiemTraSo(sp.getSoLuong()));

            System.out.println("");
            System.out.println("Giá nhập cũ: " + sp.getGiaNhap());
            do {
                System.out.print("==>> Nhập giá nhập mới: ");
                sp.setGiaNhap(sc.nextLine());
                if ("".equals(sp.getGiaNhap())) {
                    sp.setGiaNhap(giaNhapCu);
                    break;
                }
                if (!check.kiemTraSo(sp.getGiaNhap())) {
                    System.err.println("Giá nhập của sản phẩm phải lớn hơn 0 !");
                }
            } while (!check.kiemTraSo(sp.getGiaNhap()));
            sp.setGiaNhap(fm.chuanHoaSo(sp.getGiaNhap()));

            System.out.println("");
            System.out.println("Giá bán cũ: " + sp.getGiaBan());
            do {
                System.out.print("==>> Nhập giá bán mới: ");
                sp.setGiaBan(sc.nextLine());
                if ("".equals(sp.getGiaBan())) {
                    sp.setGiaBan(giaBanCu);
                    break;
                }
                if (!check.kiemTraSo(sp.getGiaBan())) {
                    System.err.println("Giá bán của sản phẩm phải lớn hơn 0 !");
                } else if (fm.chuanHoaNguocSo(sp.getGiaBan()) < fm.chuanHoaNguocSo(sp.getGiaNhap())) {
                    System.err.println("Giá bán phải lớn hơn hoặc bằng giá nhập !");
                }
            } while (!check.kiemTraSo(sp.getGiaBan()) || fm.chuanHoaNguocSo(sp.getGiaBan()) < fm.chuanHoaNguocSo(sp.getGiaNhap()));
            sp.setGiaBan(fm.chuanHoaSo(sp.getGiaBan()));

            listSP.set(chiSo, sp);
            ghiFileSP();
            System.err.println("==>> CẬP NHẬT THÔNG TIN CỦA SẢN PHẨM THÀNH CÔNG !");
            System.out.println("");
        }
    }
}
