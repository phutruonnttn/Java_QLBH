/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login_Main;

import App.AppDangNhap;
import java.util.Random;
import CheckData.CheckData;
import CheckData.DinhDang;
import Object.NhanVien;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author Admin
 */
public class Login {

    ArrayList<NhanVien> listTK = new ArrayList<>();
    private final String fileTK = "NhanVien.txt";
    private String ten, mkCu, tkCu = "";
    private int dem, chiSo, csMail, csSDT;
    private CheckData check = new CheckData();
    private DinhDang fm = new DinhDang();

    public String run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("============= ĐĂNG NHẬP TÀI KHOẢN ===============");
        String tk, mk;
        docFileTK();
        do {
            do {
                System.out.println("");
                System.out.print("        Tên đăng nhập: ");
                tk = sc.nextLine();
                tk = tk.trim();
                if ("".equals(tk)) {
                    System.err.println("Tên đăng nhập không được bỏ trống !");
                }
            } while ("".equals(tk));

            do {
                System.out.print("        Mật khẩu: ");
                mk = sc.nextLine();
                mk = mk.trim();
                if ("".equals(mk)) {
                    System.err.println("Mật khẩu không được bỏ trống !");
                }
            } while ("".equals(mk));

            if (!checkTK(tk, mk)) {
                System.err.println(" ==>> Bạn nhập sai tên đăng nhập hoặc mật khẩu ! Vui lòng nhập lại ...");
                System.err.println(" [ Nhấn phím 0 nếu bạn QUÊN MẬT KHẨU, nhấn các phím khác để THỬ LẠI ]");
                System.err.print("  Bạn có muốn THỬ LẠI: ");
                String func = sc.nextLine();
                if (func.equals("0")) {
                    quenMK();
                    System.exit(0);
                }
            }
        } while (!checkTK(tk, mk));
        System.out.println("");
        ten = ten.toUpperCase();
        System.out.println(" ==>> ĐĂNG NHẬP THÀNH CÔNG ! Xin chào " + ten + " ...");
        System.out.println("");

        Date date = new Date();
        SimpleDateFormat ft1 = new SimpleDateFormat("dd/MM/YYYY - HH:mm:ss");
        String thoiGian = ft1.format(date);
        AppDangNhap adn = new AppDangNhap();
        adn.run(thoiGian, tk);

        menuTK();
        return tk;
    }

    public void sendMail() {
        try {
            Email email = new SimpleEmail();

            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("tgroup20032000@gmail.com", "Pt230572"));
            email.setSSLOnConnect(true);
            email.setFrom("tgroup20032000@gmail.com", "T - GROUP");
            email.setSubject("T - GROUP: Lấy lại mật khẩu");

            Date date = new Date();

            Random rd = new Random();
            int number = 100000 + rd.nextInt(999999 - 100000);

            String code = String.valueOf(number);
            SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss dd/MM/YYYY");
            email.setMsg("Chào bạn " + listTK.get(csMail).getHoTen() + ",\n\n"
                    + "Bạn vừa yêu cầu reset mật khẩu trên hệ thống quản lý tài khoản của T - Group\n\n"
                    + "Thời gian: " + ft.format(date) + "\n\n"
                    + "Mã xác nhận để cập nhật mật khẩu là: " + code + "\n\n"
                    + "Trân trọng,\n\n"
                    + "T - GROUP");

            email.addTo(listTK.get(csMail).getEmail());
            System.out.println("     Vui lòng chờ trong giây lát ... ");
            System.out.println("");
            email.send();
            System.err.println(" ==>> ĐÃ GỬI THÀNH CÔNG MÃ XÁC NHẬN ĐẾN EMAIL CỦA BẠN ! (Lưu ý: Nếu không thấy Email thì kiểm tra trong mục Spam !)");
            System.err.println("");
            capNhatPass(code);

            System.out.println("");
            System.out.println("CẢM ƠN BẠN ĐÃ SỬ DỤNG PHẦN MỀM CỦA T - GROUP !");
            System.out.println("@copyright 2018 By Nguyen Phu Truong");
            System.exit(0);
        } catch (EmailException e) {
            System.err.println(" ==>> GỬI KHÔNG THÀNH CÔNG !");
            System.exit(0);
        }
    }

    public void capNhatPass(String code) {
        Scanner sc = new Scanner(System.in);
        String ip;
        System.out.println("------------- CẬP NHẬT MẬT KHẨU MỚI -------------");
        System.out.println("");
        do {
            System.out.print("      Nhập mã xác nhận: ");
            ip = sc.nextLine();
            if (!code.equals(ip)) {
                System.err.println("      ==>> Mã bạn vừa nhập không chính xác! Vui lòng nhập lại ...");
            }
        } while (!code.equals(ip));

        String mkMoi = "", mkMoi2 = "";
        int dem1;
        System.out.println("");
        do {
            System.out.print("      Nhập mật khẩu mới: ");
            mkMoi = sc.nextLine();
            dem1 = 0;
            do {
                dem1++;
                System.out.print("      Nhập lại mật khẩu mới: ");
                mkMoi2 = sc.nextLine();
                if (!mkMoi2.equals(mkMoi)) {
                    if (dem1 < 2) {
                        System.err.println("      ==>> Mật khẩu không khớp ! Vui lòng nhập lại ...");
                    } else {
                        System.err.println("      ==>> Mật khẩu không khớp !");
                        System.err.println("      [ Nhấn phím 0 để THAY ĐỔI MẬT KHẨU MỚI, nhấn phím bất kì khác 0 để TIẾP TỤC ]");
                        System.err.print("      Bạn có muốn TIẾP TỤC: ");
                        String func = sc.nextLine();
                        if (func.equals("0")) {
                            break;
                        }
                    }
                }
            } while (!mkMoi2.equals(mkMoi));
        } while (!mkMoi2.equals(mkMoi));

        listTK.get(csMail).setPass(fm.md5(mkMoi));
        ghiFileTK();

        System.out.println("");
        System.out.println("      ==>> CẬP NHẬT MẬT KHẨU MỚI THÀNH CÔNG !");
    }

    public boolean timEmail(String mail) {
        for (int i = 0; i < listTK.size(); i++) {
            if (mail.equalsIgnoreCase(listTK.get(i).getEmail())) {
                csMail = i;
                return true;
            }
        }
        return false;
    }

    public boolean timSDT(String sdt) {
        for (int i = 0; i < listTK.size(); i++) {
            if (sdt.equals(listTK.get(i).getSoDT())) {
                csSDT = i;
                return true;
            }
        }
        return false;
    }

    public void layMKEmail() {
        Scanner sc = new Scanner(System.in);
        System.out.println("");
        String mail = "";
        docFileTK();
        Scanner input = new Scanner(System.in);
        do {
            do {
                System.out.print("     Nhập Email đăng kí tài khoản: ");
                mail = input.nextLine();
            } while (check.kiemTraEmail(mail));

            if (!timEmail(mail)) {
                System.err.println("     ==>> Email bạn vừa nhập KHÔNG TỒN TẠI trong hệ thống !");
                System.err.println("    [ Nhấn phím 0 để QUAY LẠI, nhấn 1 để THOÁT KHỎI HỆ THỐNG, nhấn các phím khác để THỬ LẠI ]");
                System.err.print("      Bạn có muốn THỬ LẠI: ");
                String func = sc.nextLine();
                if (func.equals("0")) {
                    break;
                }
                if (func.equals("1")) {
                    System.exit(0);
                }
            }
        } while (!timEmail(mail));
        sendMail();
    }

    public void layMKDt() {
        Scanner sc = new Scanner(System.in);
        System.out.println("");
        String sdt = "";
        docFileTK();
        Scanner input = new Scanner(System.in);
        do {
            do {
                System.out.print("     Nhập SĐT của bạn: ");
                sdt = input.nextLine();
            } while (check.kiemTraSDT(sdt));
            sdt = fm.chuanHoaSDT(sdt);
            if (!timSDT(sdt)) {
                System.err.println("     ==>> SĐT bạn vừa nhập KHÔNG TỒN TẠI trong hệ thống !");
                System.err.println("    [ Nhấn phím 0 để QUAY LẠI, nhấn các phím khác để THỬ LẠI ]");
                System.err.print("      Bạn có muốn THỬ LẠI: ");
                String func = sc.nextLine();
                if (func.equals("0")) {
                    break;
                }
            }
        } while (!timSDT(sdt));

        Random rd = new Random();
        int number = 100000 + rd.nextInt(999999 - 100000);
        String code = String.valueOf(number);

        sendSDT(sdt, code);
    }

    public void sendSDT(String sdt, String code) {
        //gui
        //ND: code + "là mã xác nhận lấy lại mật khẩu của bạn tại Hệ thống Quản lý Bán hàng T - Group."
        
        System.err.println("");
        capNhatPass(code);
        System.out.println("");
        System.out.println("CẢM ƠN BẠN ĐÃ SỬ DỤNG PHẦN MỀM CỦA T - GROUP !");
        System.out.println("@copyright 2018 By Nguyen Phu Truong");
    }

    public void quenMK() {
        Scanner sc = new Scanner(System.in);
        int chon;
        do {
            System.out.println("");
            System.out.println("        +-----+-----------------------+");
            System.out.println("        |       LẤY LẠI MẬT KHẨU      |");
            System.out.println("        +-----+-----------------------+");
            System.out.println("        | [1] |   Qua email           |");
            System.out.println("        +-----+-----------------------+");
            System.out.println("        | [2] |   Qua số điện thoại   |");
            System.out.println("        +-----+-----------------------+");
            System.out.println("        | [3] |   Thoát               |");
            System.out.println("        +-----+-----------------------+");
            System.out.print("     Bạn muốn lấy lại MẬT KHẨU thông qua: ");
            chon = sc.nextInt();
            System.out.println("");
            switch (chon) {
                case 1: {
                    layMKEmail();
                    break;
                }
                case 2: {
                    layMKDt();
                    return;
                }
                case 3: {
                    System.out.println("");
                    System.out.println("CẢM ƠN BẠN ĐÃ SỬ DỤNG PHẦN MỀM CỦA T - GROUP !");
                    System.out.println("@copyright 2018 By Nguyen Phu Truong");
                    System.exit(0);
                }
                default: {
                    System.err.println("==>> BẠN CHỌN SAI CHỨC NĂNG !");
                }
                System.out.println("");
            }
        } while (chon != 3);
    }

    public void menuTK() {
        Scanner sc = new Scanner(System.in);
        int chon;
        do {
            System.out.println("        +-----+----------------------+");
            System.out.println("        |      QUẢN LÝ TÀI KHOẢN     |");
            System.out.println("        +-----+----------------------+");
            System.out.println("        | [1] |   Đổi mật khẩu       |");
            System.out.println("        +-----+----------------------+");
            System.out.println("        | [2] |   Quản lý BÁN HÀNG   |");
            System.out.println("        +-----+----------------------+");
            System.out.println("        | [3] |   Thoát              |");
            System.out.println("        +-----+----------------------+");
            System.out.print("        Mời bạn chọn chức năng: ");
            chon = sc.nextInt();
            System.out.println("");
            switch (chon) {
                case 1: {
                    doiPass();
                    break;
                }
                case 2: {
                    return;
                }
                case 3: {
                    System.out.println("CẢM ƠN BẠN ĐÃ SỬ DỤNG PHẦN MỀM CỦA T - GROUP !");
                    System.out.println("@copyright 2018 By Nguyen Phu Truong");
                    System.exit(0);
                }
                default: {
                    System.err.println("==>> BẠN CHỌN SAI CHỨC NĂNG !");
                }
                System.out.println("");
            }
        } while (chon != 3);
    }

    public void doiPass() {
        Scanner sc = new Scanner(System.in);
        String s;
        dem = 0;
        if (tkCu.equals("admin") && mkCu.equals("admin")) {
            System.err.println("==>> TÀI KHOẢN NÀY KHÔNG THỂ ĐỔI MẬT KHẨU !");
            return;
        } else {
            System.out.println("----------------- ĐỔI MẬT KHẨU -----------------");
            System.out.println("");
            do {
                dem++;
                System.out.print("        Mật khẩu hiện tại: ");
                s = sc.nextLine();
                if (!mkCu.equals(fm.md5(s))) {
                    if (dem < 2) {
                        System.err.println("        ==>> Mật khẩu của bạn không đúng ! Vui lòng nhập lại ...");
                    } else {
                        System.err.println("        ==>> Mật khẩu của bạn không đúng !");
                        System.err.println("        [ Nhấn phím 0 để QUAY LẠI, nhấn các phím khác để TIẾP TỤC ]");
                        System.err.print("        Bạn có muốn TIẾP TỤC: ");
                        String func = sc.nextLine();
                        if (func.equals("0")) {
                            return;
                        }
                    }
                }
            } while (!mkCu.equals(fm.md5(s)));
        }
        String mkMoi = "", mkMoi2 = "";
        int dem1;
        System.out.println("");
        do {
            System.out.print("        Mật khẩu mới: ");
            mkMoi = sc.nextLine();
            dem1 = 0;
            do {
                dem1++;
                System.out.print("        Nhập lại mật khẩu mới: ");
                mkMoi2 = sc.nextLine();
                if (!mkMoi2.equals(mkMoi)) {
                    if (dem1 < 2) {
                        System.err.println("        ==>> Mật khẩu không khớp ! Vui lòng nhập lại ...");
                    } else {
                        System.err.println("        ==>> Mật khẩu không khớp !");
                        System.err.println("        [ Nhấn phím 0 để THAY ĐỔI MẬT KHẨU MỚI, nhấn phím bất kì khác 0 để TIẾP TỤC ]");
                        System.err.print("        Bạn có muốn TIẾP TỤC: ");
                        String func = sc.nextLine();
                        if (func.equals("0")) {
                            break;
                        }
                    }
                }
            } while (!mkMoi2.equals(mkMoi));
        } while (!mkMoi2.equals(mkMoi));
        listTK.get(chiSo).setPass(fm.md5(mkMoi));
        ghiFileTK();
        System.out.println("");
        System.err.println("        ==>> ĐỔI MẬT KHẨU THÀNH CÔNG !");
        System.out.println("");
    }

    public void docFileTK() {
        try {
            BufferedReader br = null;
            FileReader fr = null;
            listTK = new ArrayList<>();

            fr = new FileReader(this.fileTK);
            br = new BufferedReader(fr);

            String info = null;

            try {
                while ((info = br.readLine()) != null) {
                    String chiaTT[] = info.split("\t");
                    if (chiaTT.length == 1) {
                        return;
                    }
                    NhanVien tk = new NhanVien();
                    tk.setMaNV(chiaTT[0]);
                    tk.setHoTen(chiaTT[1]);
                    tk.setSoDT(chiaTT[2]);
                    tk.setEmail(chiaTT[3]);
                    tk.setPass(chiaTT[4]);
                    tk.setDiaChi(chiaTT[5]);
                    listTK.add(tk);
                }
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ghiFileTK() {
        try {
            BufferedWriter bw = null;
            FileWriter fw = null;
            String data = "";
            for (int i = 0; i < listTK.size(); i++) {
                data += listTK.get(i).getMaNV() + "\t";
                data += listTK.get(i).getHoTen() + "\t";
                data += listTK.get(i).getSoDT() + "\t";
                data += listTK.get(i).getEmail() + "\t";
                data += listTK.get(i).getPass() + "\t";
                data += listTK.get(i).getDiaChi() + "\n";
            }
            fw = new FileWriter(this.fileTK);
            bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkTK(String tk, String mk) {
        for (int i = 0; i < listTK.size(); i++) {
            //System.out.println(listTK.get(i).getMaNV()+" "+listTK.get(i).getPass());
            if (listTK.get(i).getMaNV().equalsIgnoreCase(tk) && listTK.get(i).getPass().equals(fm.md5(mk))) {
                ten = listTK.get(i).getHoTen();
                mkCu = listTK.get(i).getPass();
                tkCu = listTK.get(i).getMaNV();
                chiSo = i;
                return true;
            }
        }
        if (tk.equalsIgnoreCase("admin") && mk.equals("admin")) {
            ten = "quản trị viên";
            mkCu = "admin";
            tkCu = "admin";
            return true;
        }
        return false;
    }
}
