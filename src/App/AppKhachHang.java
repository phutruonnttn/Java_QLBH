/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import CheckData.CheckData;
import CheckData.DinhDang;
import Object.KhachHang;
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
public class AppKhachHang {

    ArrayList<KhachHang> listKH = new ArrayList<>();
    private final String nameFile = "KhachHang.txt";
    CheckData check = new CheckData();
    DinhDang fm = new DinhDang();
    private int timKiem;
    private boolean flagTK = false;

    public void menuKH() {
        Scanner sc = new Scanner(System.in);
        int chon;
        do {
            System.out.println("----------------------- QUẢN LÝ KHÁCH HÀNG -----------------------");
            System.out.println("");
            System.out.println("    +-----+---------------------------------------------+");
            System.out.println("    |            HỆ THỐNG QUẢN LÝ KHÁCH HÀNG            |");
            System.out.println("    +-----+---------------------------------------------+");
            System.out.println("    | [1] |   Xem danh sách toàn bộ khách hàng          |");
            System.out.println("    +-----+---------------------------------------------+");
            System.out.println("    | [2] |   Tìm kiếm khách hàng theo MSKH/SĐT/EMAIL   |");
            System.out.println("    +-----+---------------------------------------------+");
            System.out.println("    | [3] |   Thêm mới một khách hàng                   |");
            System.out.println("    +-----+---------------------------------------------+");
            System.out.println("    | [4] |   Sửa thông tin khách hàng                  |");
            System.out.println("    +-----+---------------------------------------------+");
            System.out.println("    | [5] |   Xóa thông tin khách hàng                  |");
            System.out.println("    +-----+---------------------------------------------+");
            System.out.println("    | [6] |   Quay lại HỆ THỐNG QUẢN LÝ CHUNG           |");
            System.out.println("    +-----+---------------------------------------------+");
            System.out.print("    Mời bạn chọn chức năng: ");
            chon = sc.nextInt();
            System.out.println("");
            switch (chon) {
                case 1: {
                    docFileKH();
                    xemDS(listKH);
                    break;
                }
                case 2: {
                    flagTK = true;
                    xemDS(timKiemTheoTuKhoa());
                    flagTK = false;
                    break;
                }
                case 3: {
                    themKH();
                    break;
                }
                case 4: {
                    suaKH();
                    break;
                }
                case 5: {
                    xoaKH();
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

    public void docFileKH() {
        try {
            BufferedReader br = null;
            FileReader fr = null;
            listKH = new ArrayList<>();

            fr = new FileReader(this.nameFile);
            br = new BufferedReader(fr);

            String info = null;
            try {
                while ((info = br.readLine()) != null) {
                    String chiaTT[] = info.split("\t");
                    KhachHang kh = new KhachHang();
                    if (chiaTT.length == 1) {
                        return;
                    }
                    kh.setMaKH(chiaTT[0]);
                    kh.setHoTen(chiaTT[1]);
                    kh.setSoDT(chiaTT[2]);
                    kh.setEmail(chiaTT[3]);
                    kh.setDiaChi(chiaTT[4]);
                    listKH.add(kh);
                }
            } catch (IOException ex) {
                Logger.getLogger(AppKhachHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppKhachHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ghiFileKH() {
        try {
            BufferedWriter bw = null;
            FileWriter fw = null;
            String data = "";
            for (int i = 0; i < listKH.size(); i++) {
                data += listKH.get(i).getMaKH().toUpperCase() + "\t";
                data += listKH.get(i).getHoTen() + "\t";
                data += listKH.get(i).getSoDT() + "\t";
                data += listKH.get(i).getEmail() + "\t";
                data += listKH.get(i).getDiaChi() + "\n";
            }
            fw = new FileWriter(this.nameFile);
            bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(AppKhachHang.class.getName()).log(Level.SEVERE, null, ex);
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

    public void xemDS(ArrayList<KhachHang> list) {

        if (flagTK == true && timKiem == 0) {
            System.err.println("==>> KHÔNG TÌM THẤY TỪ KHÓA TRONG DANH SÁCH KHÁCH HÀNG !");
            return;
        }
        if (list.isEmpty()) {
            System.err.println("==>> CHƯA CÓ KHÁCH HÀNG NÀO TRONG DANH SÁCH !");
        } else {
            if (flagTK == true) {
                System.out.println("");
                System.err.println("==>> ĐÃ TÌM THẤY " + timKiem + " KẾT QUẢ THÔNG TIN KHÁCH HÀNG VỚI TỪ KHÓA '" + tuKhoa + "'");
            }
            Collections.sort(listKH);
            ghiFileKH();

            System.out.println("+-------------------------------------------------------------------------------------------------------------------------------------------------+");
            System.out.println("|                                                         DANH SÁCH THÔNG TIN KHÁCH HÀNG                                                          |");
            System.out.println("+-------+-----------+--------------------------+-----------------+---------------------------------------+----------------------------------------+");
            System.out.println("|  STT  |   MÃ KH   |        HỌ VÀ TÊN         |       SĐT       |                 EMAIL                 |                  ĐỊA CHỈ               |");
            System.out.println("+-------+-----------+--------------------------+-----------------+---------------------------------------+----------------------------------------+");
            for (int i = 0; i < list.size(); i++) {
                String stt = String.valueOf(i + 1);
                VietBang(7, stt);
                list.get(i).inTT();
            }
            System.out.println("+-------+-----------+--------------------------+-----------------+---------------------------------------+----------------------------------------+");
            System.out.println("");
        }
    }

    public ArrayList<KhachHang> timKiemTheoTuKhoa() {
        ArrayList<KhachHang> listKHTimKiem = new ArrayList<>();
        timKiem = 0;
        Scanner sc = new Scanner(System.in);
        System.out.print("Mời bạn nhập vào thông tin khách hàng cần tìm: ");
        String input = sc.nextLine();
        for (int i = 0; i < listKH.size(); i++) {
            if (listKH.get(i).getMaKH().contains(input) || listKH.get(i).getHoTen().contains(input) || listKH.get(i).getSoDT().contains(input) || listKH.get(i).getEmail().contains(input) || listKH.get(i).getDiaChi().contains(input)) {
                listKHTimKiem.add(listKH.get(i));
                timKiem++;
            }
        }
        tuKhoa = input;
        return listKHTimKiem;
    }

    public boolean kiemTraMaKH(String ma) {
        boolean flag = false;
        for (int i = 0; i < listKH.size(); i++) {
            if (ma.equalsIgnoreCase(listKH.get(i).getMaKH())) {//So sánh String với String khác, bỏ qua sự khác nhau về kiểu chữ
                flag = true;
                break;
            }
        }
        return flag;
    }

    public void themKH() {
        KhachHang kh = new KhachHang();
        Scanner input = new Scanner(System.in);
        System.out.println("NHẬP THÔNG TIN ĐỂ THÊM MỚI KHÁCH HÀNG");
        System.out.println("");
        do {
            System.out.print("Mã khách hàng: ");
            kh.setMaKH(input.nextLine());
            if ("".equals(kh.getMaKH())) {
                System.err.println("Mã khách hàng không được bỏ trống !");
            }
        } while ("".equals(kh.getMaKH()));
        if (kiemTraMaKH(kh.getMaKH())) {
            System.err.println("==>> MÃ KHÁCH HÀNG ĐÃ TỒN TẠI !");
            return;
        } else {
            do {
                System.out.print("Tên khách hàng: ");
                kh.setHoTen(input.nextLine());
                if (check.kiemTraTen(kh.getHoTen())) {
                    System.err.println("Tên không được chứa số hoặc rỗng !");
                }
            } while (check.kiemTraTen(kh.getHoTen()));
            kh.setHoTen(fm.chuanHoaVietHoa(kh.getHoTen()));

            do {
                System.out.print("Nhập SĐT khách hàng: ");
                kh.setSoDT(input.nextLine());
            } while (check.kiemTraSDT(kh.getSoDT()));
            kh.setSoDT(fm.chuanHoaSDT(kh.getSoDT()));

            do {
                System.out.print("Nhập Email khách hàng: ");
                kh.setEmail(input.nextLine());
            } while (check.kiemTraEmail(kh.getEmail()));

            do {
                System.out.print("Nhập địa chỉ khách hàng: ");
                kh.setDiaChi(input.nextLine());
                if ("".equals(kh.getDiaChi())) {
                    System.err.println("Địa chỉ khách hàng không được bỏ trống !");
                }
            } while ("".equals(kh.getDiaChi()));
        }
        listKH.add(kh);
        ghiFileKH();
        System.err.println("==>> THÊM MỚI KHÁCH HÀNG THÀNH CÔNG !");
        System.out.println("");
    }

    private int chiSo;

    public KhachHang timKH(String ma) {
        for (int i = 0; i < listKH.size(); i++) {
            if (ma.equalsIgnoreCase(listKH.get(i).getMaKH())) {
                chiSo = i;
                return listKH.get(i);
            }
        }
        return null;
    }

    public void suaKH() {
        Scanner sc = new Scanner(System.in);
        KhachHang kh;
        do {
            System.out.print("Mời bạn nhập mã khách hàng cần sửa: ");
            String maKH = sc.nextLine();
            kh = timKH(maKH);
            if (kh == null) {
                System.err.println("Mã khách hàng bạn vừa nhập không tồn tại !");
                System.out.println("Vui lòng thử lại ... ");
            }
        } while (kh == null);
        if (kh != null) {
            String hoTenCu = kh.getHoTen();
            String SDTcu = kh.getSoDT();
            String emailCu = kh.getEmail();
            String diaChiCu = kh.getDiaChi();
            System.out.println("");
            System.err.println("MỜI BẠN CẬP NHẬT THÔNG TIN KHÁCH HÀNG CÓ MÃ " + kh.getMaKH().toUpperCase() + " (LƯU Ý: BỎ TRỐNG nếu bạn không muốn cập nhật thông tin đó !) ");

            System.out.println("Tên khách hàng cũ: " + kh.getHoTen());
            do {
                System.out.print("==>> Nhập tên mới: ");
                kh.setHoTen(sc.nextLine());
                if ("".equals(kh.getHoTen())) {
                    kh.setHoTen(hoTenCu);
                    break;
                }
                if (check.kiemTraSuaTen(kh.getHoTen())) {
                    System.err.println("Tên khách hàng không được chứa số !");
                }
            } while (check.kiemTraSuaTen(kh.getHoTen()));
            kh.setHoTen(fm.chuanHoaVietHoa(kh.getHoTen()));

            System.out.println("");
            System.out.println("Số điện thoại cũ: " + kh.getSoDT());
            do {
                System.out.print("==>> Nhập số điện thoại mới: ");
                kh.setSoDT(sc.nextLine());
                if ("".equals(kh.getSoDT())) {
                    kh.setSoDT(SDTcu);
                }
            } while (check.kiemTraSDT(kh.getSoDT()));
            kh.setSoDT(fm.chuanHoaSDT(kh.getSoDT()));

            System.out.println("");
            System.out.println("Email cũ: " + kh.getEmail());
            do {
                System.out.print("==>> Nhập email mới: ");
                kh.setEmail(sc.nextLine());
                if ("".equals(kh.getEmail())) {
                    kh.setEmail(emailCu);
                }
            } while (check.kiemTraEmail(kh.getEmail()));

            System.out.println("");
            System.out.println("Địa chỉ cũ: " + kh.getDiaChi());
            System.out.print("==>> Nhập địa chỉ mới: ");
            kh.setDiaChi(sc.nextLine());
            if ("".equals(kh.getDiaChi())) {
                kh.setDiaChi(diaChiCu);
            }
            listKH.set(chiSo, kh);
            ghiFileKH();
            System.err.println("==>> CẬP NHẬT THÔNG TIN CỦA KHÁCH HÀNG THÀNH CÔNG !");
            System.out.println("");
        }
    }

    public void xoaKH() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Mời bạn nhập mã khách hàng cần xóa: ");
        String ma = sc.nextLine();
        KhachHang kh = timKH(ma);
        if (kh == null) {
            System.err.println("==>> KHÁCH HÀNG BẠN VỪA NHẬP KHÔNG TỒN TẠI TRONG HỆ THỐNG !");
        } else {
            listKH.remove(kh);
            System.out.println("");
            System.err.println("==>> XÓA THÀNH CÔNG KHÁCH HÀNG CÓ MÃ " + kh.getMaKH());
            ghiFileKH();
        }
    }
}
