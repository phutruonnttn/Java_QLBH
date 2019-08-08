/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import CheckData.CheckData;
import CheckData.DinhDang;
import Object.NhanVien;
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
public class AppNhanVien {

    ArrayList<NhanVien> listNV = new ArrayList<>();
    private final String nameFile = "NhanVien.txt";
    CheckData check = new CheckData();
    DinhDang fm = new DinhDang();
    private int timKiem;
    private boolean flagTK = false;

    public void menuNV() {
        Scanner sc = new Scanner(System.in);
        int chon;
        do {
            System.out.println("----------------------- QUẢN LÝ NHÂN VIÊN -----------------------");
            System.out.println("");
            System.out.println("    +-----+---------------------------------------------+");
            System.out.println("    |            HỆ THỐNG QUẢN LÝ NHÂN VIÊN             |");
            System.out.println("    +-----+---------------------------------------------+");
            System.out.println("    | [1] |   Xem danh sách toàn bộ nhân viên           |");
            System.out.println("    +-----+---------------------------------------------+");
            System.out.println("    | [2] |   Tìm kiếm nhân viên theo MSNV/SĐT/EMAIL    |");
            System.out.println("    +-----+---------------------------------------------+");
            System.out.println("    | [3] |   Thêm mới một nhân viên                    |");
            System.out.println("    +-----+---------------------------------------------+");
            System.out.println("    | [4] |   Sửa thông tin nhân viên                   |");
            System.out.println("    +-----+---------------------------------------------+");
            System.out.println("    | [5] |   Xóa thông tin nhân viên                   |");
            System.out.println("    +-----+---------------------------------------------+");
            System.out.println("    | [6] |   Quay lại HỆ THỐNG QUẢN LÝ CHUNG           |");
            System.out.println("    +-----+---------------------------------------------+");
            System.out.print("    Mời bạn chọn chức năng: ");
            chon = sc.nextInt();
            System.out.println("");
            switch (chon) {
                case 1: {
                    docFileNV();
                    xemDS(listNV);
                    break;
                }
                case 2: {
                    flagTK = true;
                    xemDS(timKiemTheoTuKhoa());
                    flagTK = false;
                    break;
                }
                case 3: {
                    themNV();
                    break;
                }
                case 4: {
                    suaNV();
                    break;
                }
                case 5: {
                    xoaNV();
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

    public void docFileNV() {
        try {
            BufferedReader br = null;
            FileReader fr = null;
            listNV = new ArrayList<>();

            fr = new FileReader(this.nameFile);
            br = new BufferedReader(fr);

            String info = null;
            try {
                while ((info = br.readLine()) != null) {
                    String chiaTT[] = info.split("\t");
                    NhanVien nv = new NhanVien();
                    if (chiaTT.length == 1) {
                        return;
                    }
                    nv.setMaNV(chiaTT[0]);
                    nv.setHoTen(chiaTT[1]);
                    nv.setSoDT(chiaTT[2]);
                    nv.setEmail(chiaTT[3]);
                    nv.setPass(chiaTT[4]);
                    nv.setDiaChi(chiaTT[5]);
                    listNV.add(nv);
                }
            } catch (IOException ex) {
                Logger.getLogger(AppNhanVien.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ghiFileNV() {
        try {
            BufferedWriter bw = null;
            FileWriter fw = null;
            String data = "";
            for (int i = 0; i < listNV.size(); i++) {
                data += listNV.get(i).getMaNV().toUpperCase() + "\t";
                data += listNV.get(i).getHoTen() + "\t";
                data += listNV.get(i).getSoDT() + "\t";
                data += listNV.get(i).getEmail() + "\t";
                data += listNV.get(i).getPass() + "\t";
                data += listNV.get(i).getDiaChi() + "\n";
            }
            fw = new FileWriter(this.nameFile);
            bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(AppNhanVien.class.getName()).log(Level.SEVERE, null, ex);
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

    public void xemDS(ArrayList<NhanVien> list) {
        if (flagTK == true && timKiem == 0) {
            System.err.println("==>> KHÔNG TÌM THẤY TỪ KHÓA TRONG DANH SÁCH NHÂN VIÊN !");
            return;
        }
        if (list.isEmpty()) {
            System.err.println("==>> CHƯA CÓ NHÂN VIÊN NÀO TRONG DANH SÁCH !");
        } else {

            if (flagTK == true) {
                System.out.println("");
                System.err.println("==>> ĐÃ TÌM THẤY " + timKiem + " KẾT QUẢ THÔNG TIN NHÂN VIÊN VỚI TỪ KHÓA '" + tuKhoa + "'");
            }
            Collections.sort(listNV);
            ghiFileNV();
            System.out.println("+-------------------------------------------------------------------------------------------------------------------------------------------------+");
            System.out.println("|                                                        DANH SÁCH THÔNG TIN NHÂN VIÊN                                                            |");
            System.out.println("+-------+-----------+--------------------------+-----------------+---------------------------------------+----------------------------------------+");
            System.out.println("|  STT  |   MÃ NV   |        HỌ VÀ TÊN         |       SĐT       |                 EMAIL                 |                  ĐỊA CHỈ               |");
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

    public ArrayList<NhanVien> timKiemTheoTuKhoa() {
        ArrayList<NhanVien> listNVTimKiem = new ArrayList<>();
        timKiem = 0;
        Scanner sc = new Scanner(System.in);
        System.out.print("Mời bạn nhập vào thông tin nhân viên cần tìm: ");
        String input = sc.nextLine();
        for (int i = 0; i < listNV.size(); i++) {
            if (listNV.get(i).getMaNV().contains(input) || listNV.get(i).getHoTen().contains(input) || listNV.get(i).getSoDT().contains(input) || listNV.get(i).getEmail().contains(input) || listNV.get(i).getDiaChi().contains(input)) {
                listNVTimKiem.add(listNV.get(i));
                timKiem++;
            }
        }
        tuKhoa = input;
        return listNVTimKiem;
    }

    public boolean kiemTraMaNV(String ma) {
        boolean flag = false;
        for (int i = 0; i < listNV.size(); i++) {
            if (ma.equalsIgnoreCase(listNV.get(i).getMaNV())) {//So sánh String với String khác, bỏ qua sự khác nhau về kiểu chữ
                flag = true;
                break;
            }
        }
        return flag;
    }

    public boolean kiemTraMail(String mail) {
        boolean flag = false;
        for (int i = 0; i < listNV.size(); i++) {
            if (mail.equalsIgnoreCase(listNV.get(i).getEmail())) {//So sánh String với String khác, bỏ qua sự khác nhau về kiểu chữ
                flag = true;
                break;
            }
        }
        return flag;
    }

    public void themNV() {
        NhanVien nv = new NhanVien();
        Scanner input = new Scanner(System.in);
        System.out.println("NHẬP THÔNG TIN ĐỂ THÊM MỚI NHÂN VIÊN");
        System.out.println("");
        do {
            System.out.print("Mã nhân viên: ");
            nv.setMaNV(input.nextLine());
            if ("".equals(nv.getMaNV())) {
                System.err.println("Mã nhân viên không được bỏ trống !");
            }
        } while ("".equals(nv.getMaNV()));
        if (kiemTraMaNV(nv.getMaNV())) {
            System.err.println("==>> MÃ NHÂN VIÊN ĐÃ TỒN TẠI !");
            return;
        } else {
            do {
                System.out.print("Tên nhân viên: ");
                nv.setHoTen(input.nextLine());
                if (check.kiemTraTen(nv.getHoTen())) {
                    System.err.println("Tên không được chứa số hoặc rỗng !");
                }
            } while (check.kiemTraTen(nv.getHoTen()));
            nv.setHoTen(fm.chuanHoaVietHoa(nv.getHoTen()));

            do {
                System.out.print("Nhập SĐT nhân viên: ");
                nv.setSoDT(input.nextLine());
            } while (check.kiemTraSDT(nv.getSoDT()));
            nv.setSoDT(fm.chuanHoaSDT(nv.getSoDT()));

            do {
                System.out.print("Nhập Email nhân viên: ");
                nv.setEmail(input.nextLine());
                if (kiemTraMail(nv.getEmail())) {
                    System.err.println("==>> Email này đã tồn tại trong hệ thống ! Vui lòng nhập Email khác ...");
                }
            } while (check.kiemTraEmail(nv.getEmail()) || kiemTraMail(nv.getEmail()));

            do {
                System.out.print("Nhập dãy kí tự để thiết lập mật khẩu: ");
                nv.setPass(input.nextLine());
                if ("".equals(nv.getPass())) {
                    System.err.println("Mật khẩu của nhân viên không được bỏ trống !");
                }
            } while ("".equals(nv.getPass()));
            nv.setPass(fm.md5(nv.getPass()));

            do {
                System.out.print("Nhập địa chỉ nhân viên: ");
                nv.setDiaChi(input.nextLine());
                if ("".equals(nv.getDiaChi())) {
                    System.err.println("Địa chỉ nhân viên không được bỏ trống !");
                }
            } while ("".equals(nv.getDiaChi()));
        }
        listNV.add(nv);
        ghiFileNV();
        System.err.println("==>> THÊM MỚI NHÂN VIÊN THÀNH CÔNG !");
        System.out.println("");
    }

    private int chiSo;

    public NhanVien timNV(String ma) {
        for (int i = 0; i < listNV.size(); i++) {
            if (ma.equalsIgnoreCase(listNV.get(i).getMaNV())) {
                chiSo = i;
                return listNV.get(i);
            }
        }
        return null;
    }

    public boolean kiemTraMail2(String mail, String mailCu) {
        boolean flag = false;
        for (int i = 0; i < listNV.size(); i++) {
            if (mail.equalsIgnoreCase(listNV.get(i).getEmail())) {//So sánh String với String khác, bỏ qua sự khác nhau về kiểu chữ
                flag = true;
                break;
            }
        }
        if (mail.equalsIgnoreCase(mailCu)) {
            flag = false;
        }
        return flag;
    }

    public void suaNV() {
        Scanner sc = new Scanner(System.in);
        NhanVien nv;
        do {
            System.out.print("Mời bạn nhập mã nhân viên cần sửa: ");
            String maNV = sc.nextLine();
            nv = timNV(maNV);
            if (nv == null) {
                System.err.println("Mã nhân viên bạn vừa nhập không tồn tại !");
                System.out.println("Vui lòng thử lại ... ");
            }
        } while (nv == null);
        if (nv != null) {
            String hoTenCu = nv.getHoTen();
            String SDTcu = nv.getSoDT();
            String emailCu = nv.getEmail();
            String diaChiCu = nv.getDiaChi();
            System.out.println("");
            System.err.println("MỜI BẠN CẬP NHẬT THÔNG TIN NHÂN VIÊN CÓ MÃ " + nv.getMaNV().toUpperCase() + " (LƯU Ý: BỎ TRỐNG nếu bạn không muốn cập nhật thông tin đó !) ");

            System.out.println("Tên nhân viên cũ: " + nv.getHoTen());
            do {
                System.out.print("==>> Nhập tên mới: ");
                nv.setHoTen(sc.nextLine());
                if ("".equals(nv.getHoTen())) {
                    nv.setHoTen(hoTenCu);
                    break;
                }
                if (check.kiemTraSuaTen(nv.getHoTen())) {
                    System.err.println("Tên nhân viên không được chứa số !");
                    System.out.println("");
                }
            } while (check.kiemTraSuaTen(nv.getHoTen()));
            nv.setHoTen(fm.chuanHoaVietHoa(nv.getHoTen()));

            System.out.println("");
            System.out.println("Số điện thoại cũ: " + nv.getSoDT());
            do {
                System.out.print("==>> Nhập số điện thoại mới: ");
                nv.setSoDT(sc.nextLine());
                if ("".equals(nv.getSoDT())) {
                    nv.setSoDT(SDTcu);
                }
            } while (check.kiemTraSDT(nv.getSoDT()));
            nv.setSoDT(fm.chuanHoaSDT(nv.getSoDT()));

            System.out.println("");
            System.out.println("Email cũ: " + nv.getEmail());
            do {
                System.out.print("==>> Nhập email mới: ");
                nv.setEmail(sc.nextLine());
                if ("".equals(nv.getEmail())) {
                    nv.setEmail(emailCu);
                }
                if (kiemTraMail2(nv.getEmail(), emailCu)) {
                    System.err.println("==>> Email này đã tồn tại trong hệ thống ! Vui lòng nhập Email khác ...");
                }
            } while (check.kiemTraEmail(nv.getEmail()) || kiemTraMail2(nv.getEmail(), emailCu));

            System.out.println("");
            System.out.println("Địa chỉ cũ: " + nv.getDiaChi());
            System.out.print("==>> Nhập địa chỉ mới: ");
            nv.setDiaChi(sc.nextLine());
            if ("".equals(nv.getDiaChi())) {
                nv.setDiaChi(diaChiCu);
            }
            listNV.set(chiSo, nv);
            ghiFileNV();
            System.err.println("==>> CẬP NHẬT THÔNG TIN CỦA NHÂN VIÊN THÀNH CÔNG !");
            System.out.println("");
        }
    }

    public void xoaNV() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Mời bạn nhập mã nhân viên cần xóa: ");
        String ma = sc.nextLine();
        NhanVien nv = timNV(ma);
        if (nv == null) {
            System.err.println("==>> NHÂN VIÊN BẠN VỪA NHẬP KHÔNG TỒN TẠI TRONG HỆ THỐNG !");
        } else {
            listNV.remove(nv);
            System.out.println("");
            System.err.println("==>> XÓA THÀNH CÔNG NHÂN VIÊN CÓ MÃ " + nv.getMaNV());
            ghiFileNV();
        }
    }
}
