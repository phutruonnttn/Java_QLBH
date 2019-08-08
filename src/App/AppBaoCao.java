/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import CheckData.DinhDang;
import CheckData.DocSo;
import Object.DangNhap;
import Object.HoaDon;
import Object.NhanVien;
import Object.SanPham;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class AppBaoCao {

    private ArrayList<SanPham> listSP = new ArrayList<>();
    private ArrayList<NhanVien> listNV = new ArrayList<>();
    private ArrayList<HoaDon> listHD = new ArrayList<>();
    private ArrayList<DangNhap> listDN = new ArrayList<>();
    private final String fileHD = "HoaDon.txt";
    private final String fileDN = "DangNhap.txt";
    private final String fileNV = "NhanVien.txt";
    private final String fileSP = "SanPham.txt";
    private DinhDang fm = new DinhDang();
    private DocSo ds = new DocSo();

    public void run() {
        Scanner sc = new Scanner(System.in);
        int chon;
        do {
            System.out.println("");
            System.out.println("---------------------------- BÁO CÁO THỐNG KÊ ----------------------------");
            System.out.println("");
            System.out.println("    +-----+-----------------------------------------------------------+");
            System.out.println("    |                    HỆ THỐNG QUẢN LÝ SẢN PHẨM                    |");
            System.out.println("    +-----+-----------------------------------------------------------+");
            System.out.println("    | [1] |   Báo cáo doanh thu theo NGÀY                             |");
            System.out.println("    +-----+-----------------------------------------------------------+");
            System.out.println("    | [2] |   Báo cáo doanh thu theo THÁNG                            |");
            System.out.println("    +-----+-----------------------------------------------------------+");
            System.out.println("    | [3] |   Báo cáo doanh thu theo THÁNG của NHÂN VIÊN              |");
            System.out.println("    +-----+-----------------------------------------------------------+");
            System.out.println("    | [4] |   Thống kê các mặt hàng SẮP HẾT trong kho (sl < 10sp)     |");
            System.out.println("    +-----+-----------------------------------------------------------+");
            System.out.println("    | [5] |   Thống kê các mặt hàng TỒN KHO (sl > 100sp)              |");
            System.out.println("    +-----+-----------------------------------------------------------+");
            System.out.println("    | [6] |   Tìm kiếm thông tin hóa đơn theo MÃ HÓA ĐƠN              |");
            System.out.println("    +-----+-----------------------------------------------------------+");
            System.out.println("    | [7] |   Kiểm tra LỊCH SỬ ĐĂNG NHẬP                              |");
            System.out.println("    +-----+-----------------------------------------------------------+");
            System.out.println("    | [8] |   Quay lại HỆ THỐNG QUẢN LÝ CHUNG                         |");
            System.out.println("    +-----+-----------------------------------------------------------+");
            System.out.print("    Mời bạn chọn chức năng: ");
            chon = sc.nextInt();
            System.out.println("");
            switch (chon) {
                case 1: {
                    bcNgay();
                    break;
                }
                case 2: {
                    bcThang();
                    break;
                }
                case 3: {
                    bcThangNV();
                    break;
                }
                case 4: {
                    docFileSP();
                    DShangSapHet(listSP);
                    break;
                }
                case 5: {
                    docFileSP();
                    DShangTonKho(listSP);
                    break;
                }
                case 6: {
                    docFileHD();
                    timHDtheoMHD();
                    break;
                }
                case 7: {
                    ktDangNhap();
                    break;
                }
                case 8: {
                    break;
                }
                default: {
                    System.err.println("==>> BẠN CHỌN SAI CHỨC NĂNG !");
                }
            }
        } while (chon != 8);
    }

    public void ktDangNhap() {
        Scanner sc = new Scanner(System.in);
        String maNV, thang;
        System.out.println("");
        docFileNV();
        do {
            System.out.print("Mời bạn nhập vào mã nhân viên cần kiểm tra: ");
            maNV = sc.nextLine();
            maNV = maNV.trim();
            if (!checkMaNV(maNV)) {
                System.err.println("==>> Mã nhân viên không tồn tại ! Vui lòng nhập lại ...");
            }
        } while (!checkMaNV(maNV));

        System.out.println("");
        do {
            System.out.print("Mời bạn nhập vào tháng cần thống kê (Lưu ý: Định dạng tháng năm VD: 03/2000 - Các tháng nhỏ hơn 10 phải thêm số 0 vào trước !): ");
            thang = sc.nextLine();
            thang = thang.trim();
            if (!checkThang(thang)) {
                System.err.println("==>> Nhập sai định dạng tháng năm ! Vui lòng nhập lại ...");
            }
        } while (!checkThang(thang));

        loginHistory(maNV, tenNhanVien, thang);
    }

    public void docFileDN() {

        try {
            BufferedReader br = null;
            FileReader fr = null;
            listDN = new ArrayList<>();

            fr = new FileReader(this.fileDN);
            br = new BufferedReader(fr);

            String info = null;

            try {
                while ((info = br.readLine()) != null) {
                    String chiaTT[] = info.split("\t");
                    DangNhap dn = new DangNhap();
                    if (chiaTT.length == 1) {
                        return;
                    }
                    dn.setMaNV(chiaTT[0]);
                    dn.setTenNV(chiaTT[1]);
                    dn.setTime(chiaTT[2]);
                    listDN.add(dn);
                }
            } catch (IOException ex) {
                Logger.getLogger(AppBaoCao.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppBaoCao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loginHistory(String maNV, String tenNV, String thang) {
        docFileDN();
        System.out.println("+--------------------------------------------------------+");
        System.out.println("|                    LỊCH SỬ ĐĂNG NHẬP                   |");
        System.out.println("+--------------------------------------------------------+");
        System.out.println("|                   Mã nhân viên: " + VietBangTien6(5, maNV.toUpperCase()) + "                  |");
        System.out.println("|            Tên nhân viên: " + VietBangTien6(19, tenNV.toUpperCase()) + "          |");
        System.out.println("|                     Tháng: " + thang + "                     |");
        System.out.println("+-------+------------------+-----------------------------+");
        System.out.println("|  STT  |   Mã nhân viên   |     Thời gian đăng nhập     |");
        System.out.println("+-------+------------------+-----------------------------+");//18/29
        int dem = 0;
        for (int i = 0; i < listDN.size(); i++) {
            if (maNV.equalsIgnoreCase(listDN.get(i).getMaNV())) {
                String stt = String.valueOf(++dem);
                VietBang(7, stt);
                listDN.get(i).inTT();
            }
        }
        System.out.println("+-------+------------------+-----------------------------+");
        System.out.println("");
    }

    public void timHDtheoMHD() {
        System.out.println("");
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập mã hóa đơn cần kiểm tra: ");
        String maHD = sc.nextLine();
        System.out.println("");
        int demSL = 0;
        for (int i = 0; i < listHD.size(); i++) {
            if (listHD.get(i).getMaHD().contains(maHD)) {
                demSL++;
            }
        }
        if (demSL == 0) {
            System.err.println("==>> KHÔNG TÌM THẤY HÓA ĐƠN NÀO VỚI TỪ KHÓA '" + maHD + "' !");
        } else {
            System.err.println("==>> ĐÃ TÌM THẤY " + demSL + " KẾT QUẢ VỚI TỪ KHÓA '" + maHD + "' !");
            xemHD(maHD, listHD);
        }
    }

    public String VietBang2(int khoangTrong, String s) {
        int demKhoangTrong = khoangTrong - s.length();
        for (int i = 1; i <= demKhoangTrong; i++) {
            s = s + " ";
        }
        return s;
    }

    public String VietBang4(int khoangTrong, String s) {
        int demKhoangTrong = khoangTrong - s.length();
        if ((demKhoangTrong % 2 == 0)) {
            for (int i = 1; i <= demKhoangTrong / 2; i++) {
                s = " " + s;
            }
            for (int i = 1; i <= demKhoangTrong / 2; i++) {
                s = s + " ";
            }
        } else {
            for (int i = 1; i <= demKhoangTrong / 2 + 1; i++) {
                s = " " + s;
            }
            for (int i = 1; i <= demKhoangTrong / 2; i++) {
                s = s + " ";
            }
        }
        return s;
    }

    public void xemHD(String maHD, ArrayList<HoaDon> list) {
        System.out.println("");

        System.out.println("+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|           HỆ THỐNG SIÊU THỊ ĐIỆN MÁY NPT                                                                                                                                                         |");
        System.out.println("|     Đ.c: Số 112 - Bạch Mai - Hai Bà Trưng - Hà Nội                                                                                                                                               |");
        System.out.println("|        MST: 012269874566   Tel: 0945.108.999                                                                                                                                                     |");
        System.out.println("|   * Sự hài lòng của bạn là niềm vui của chúng tôi *                                                                                                                                              |");
        System.out.println("|                                                                                                                                                                                                  |");
        System.out.println("|                                                                                 KẾT QUẢ TÌM KIẾM THEO MÃ HÓA ĐƠN                                                                                 |");
        System.out.println("|                                                                                                                                                                                                  |");
        System.out.println("+-------+------------------------------+--------------+-----------+----------------------+---------------+----------------+---------------------+------------------------+-------------------------+");
        System.out.println("|  STT  |          MÃ HÓA ĐƠN          |   NGÀY BÁN   |   MÃ SP   |     TÊN SẢN PHẨM     |  ĐƠN VỊ TÍNH  |    SỐ LƯỢNG    |       ĐƠN GIÁ       |       THÀNH TIỀN       |  TIỀN SAU THUẾ VÀ CKSP  |");
        System.out.println("+-------+------------------------------+--------------+-----------+----------------------+---------------+----------------+---------------------+------------------------+-------------------------+");
        int dem = 0;
        float vat = (float) 1 / 10;
        float ck = (float) 5 / 100;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMaHD().contains(maHD)) {
                String stt = String.valueOf(++dem);
                VietBang(7, stt);
                list.get(i).inTT3();
                String thanhTien = fm.chuanHoaSo(fm.mul(list.get(i).getSoLuong(), list.get(i).getGiaBan()));

                VietBangTien3(21, list.get(i).getGiaBan());
                VietBangTien(24, thanhTien);

                thanhTien = fm.add2(thanhTien, fm.mul1(thanhTien, vat));
                thanhTien = fm.add2(thanhTien, fm.mul1(thanhTien, ck));

                VietBangTien(25, thanhTien);

                System.out.println("");
            }
        }
        System.out.println("+-------+------------------------------+--------------+-----------+----------------------+---------------+----------------+---------------------+------------------------+-------------------------+");
        System.out.println("");
    }

    public void docFileSP() {

        try {
            BufferedReader br = null;
            FileReader fr = null;
            listSP = new ArrayList<>();

            fr = new FileReader(this.fileSP);
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
                Logger.getLogger(AppBaoCao.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppBaoCao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void docFileNV() {
        try {
            BufferedReader br = null;
            FileReader fr = null;
            listNV = new ArrayList<>();
            fr = new FileReader(this.fileNV);
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
                Logger.getLogger(AppBaoCao.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppBaoCao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkMaNV(String ma) {
        for (int i = 0; i < listNV.size(); i++) {
            if (ma.equalsIgnoreCase(listNV.get(i).getMaNV())) {
                tenNhanVien = listNV.get(i).getHoTen();
                return true;
            }
        }
        if (ma.equalsIgnoreCase("admin")) {
            tenNhanVien = "ADMINISTRATOR";
            return true;
        }
        return false;
    }

    private String tenNhanVien;

    public void bcThangNV() {
        Scanner sc = new Scanner(System.in);
        String thang, maNV;
        System.out.println("");
        docFileNV();
        do {
            System.out.print("Mời bạn nhập vào mã nhân viên cần thống kê: ");
            maNV = sc.nextLine();
            maNV = maNV.trim();
            if (!checkMaNV(maNV)) {
                System.err.println("==>> Mã nhân viên không tồn tại ! Vui lòng nhập lại ...");
            }
        } while (!checkMaNV(maNV));

        System.out.println("");
        do {
            System.out.print("Mời bạn nhập vào tháng cần thống kê (Lưu ý: Định dạng tháng năm VD: 03/2000 - Các tháng nhỏ hơn 10 phải thêm số 0 vào trước !): ");
            thang = sc.nextLine();
            thang = thang.trim();
            if (!checkThang(thang)) {
                System.err.println("==>> Nhập sai định dạng tháng năm ! Vui lòng nhập lại ...");
            }
        } while (!checkThang(thang));
        docFileHD();
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss - dd/MM/YYYY");
        String thoiGian = ft.format(date);
        xemHDthangNV(thang, maNV, tenNhanVien, listHD, thoiGian);
    }

    public void DShangTonKho(ArrayList<SanPham> list) {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss - dd/MM/YYYY");
        String thoiGian = ft.format(date);
        System.out.println("");
        System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|           HỆ THỐNG SIÊU THỊ ĐIỆN MÁY NPT                                                                                                 |");
        System.out.println("|     Đ.c: Số 112 - Bạch Mai - Hai Bà Trưng - Hà Nội                                                                                       |");
        System.out.println("|        MST: 012269874566   Tel: 0945.108.999                                                                                             |");
        System.out.println("|   * Sự hài lòng của bạn là niềm vui của chúng tôi *                                                                                      |");
        System.out.println("|                                                                                                                                          |");
        System.out.println("|                                                 DANH SÁCH THỐNG KÊ CÁC MẶT HÀNG TỒN KHO                                                  |");
        System.out.println("|                                                                                                                                          |");
        System.out.println("|                                                    Thống kê lúc " + thoiGian + "                                                    |");
        System.out.println("+-------+-----------+----------------------+---------------+----------------+---------------------+--------------------+-------------------+");
        System.out.println("|  STT  |   MÃ SP   |     TÊN SẢN PHẨM     |  ĐƠN VỊ TÍNH  |    SỐ LƯỢNG    |       TÊN NSX       |      GIÁ NHẬP      |      GIÁ BÁN      |");
        System.out.println("+-------+-----------+----------------------+---------------+----------------+---------------------+--------------------+-------------------+");
        boolean ban = false;
        int demSP = 0;
        for (int i = 0; i < list.size(); i++) {
            if (fm.chuanHoaNguocSo(list.get(i).getSoLuong()) > 100) {
                ban = true;
                demSP++;
                String stt = String.valueOf(demSP);
                VietBang(7, stt);
                list.get(i).inTT3();
            }
        }
        if (!ban) {
            System.out.println("|                                                     Không có mặt hàng nào tồn kho !                                                      |");
            System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------+");
        } else {
            System.out.println("+-------+-----------+----------------------+---------------+----------------+---------------------+--------------------+-------------------+");
            String dong = "TỔNG SỐ SẢN PHẨM TỒN KHO: " + (demSP + "") + " sản phẩm";
            System.out.println("|                                                                    " + VietBangTien2(68, dong) + "  |");
            System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------+");
        }
    }

    public void DShangSapHet(ArrayList<SanPham> list) {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss - dd/MM/YYYY");
        String thoiGian = ft.format(date);
        System.out.println("");
        System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|           HỆ THỐNG SIÊU THỊ ĐIỆN MÁY NPT                                                                                                 |");
        System.out.println("|     Đ.c: Số 112 - Bạch Mai - Hai Bà Trưng - Hà Nội                                                                                       |");
        System.out.println("|        MST: 012269874566   Tel: 0945.108.999                                                                                             |");
        System.out.println("|   * Sự hài lòng của bạn là niềm vui của chúng tôi *                                                                                      |");
        System.out.println("|                                                                                                                                          |");
        System.out.println("|                                                 DANH SÁCH THỐNG KÊ CÁC MẶT HÀNG SẮP HẾT                                                  |");
        System.out.println("|                                                                                                                                          |");
        System.out.println("|                                                    Thống kê lúc " + thoiGian + "                                                    |");
        System.out.println("+-------+-----------+----------------------+---------------+----------------+---------------------+--------------------+-------------------+");
        System.out.println("|  STT  |   MÃ SP   |     TÊN SẢN PHẨM     |  ĐƠN VỊ TÍNH  |    SỐ LƯỢNG    |       TÊN NSX       |      GIÁ NHẬP      |      GIÁ BÁN      |");
        System.out.println("+-------+-----------+----------------------+---------------+----------------+---------------------+--------------------+-------------------+");
        boolean ban = false;
        int demSP = 0;
        for (int i = 0; i < list.size(); i++) {
            if (fm.chuanHoaNguocSo(list.get(i).getSoLuong()) < 10) {
                ban = true;
                demSP++;
                String stt = String.valueOf(demSP);
                VietBang(7, stt);
                list.get(i).inTT3();
            }
        }
        if (!ban) {
            System.out.println("|                                                     Không có mặt hàng nào sắp hết !                                                      |");
            System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------+");
        } else {
            System.out.println("+-------+-----------+----------------------+---------------+----------------+---------------------+--------------------+-------------------+");
            String dong = "TỔNG SỐ SẢN PHẨM SẮP HẾT: " + (demSP + "") + " sản phẩm";
            System.out.println("|                                                                    " + VietBangTien2(68, dong) + "  |");
            System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------+");
        }
    }

    public void xemHDthangNV(String thang, String maNV, String tenNV, ArrayList<HoaDon> list, String thoiGian) {
        System.out.println("");
        System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|           HỆ THỐNG SIÊU THỊ ĐIỆN MÁY NPT                                                                                                |");
        System.out.println("|     Đ.c: Số 112 - Bạch Mai - Hai Bà Trưng - Hà Nội                                                                                      |");
        System.out.println("|        MST: 012269874566   Tel: 0945.108.999                                                                                            |");
        System.out.println("|   * Sự hài lòng của bạn là niềm vui của chúng tôi *                                                                                     |");
        System.out.println("|                                                                                                                                         |");
        System.out.println("|                                                           THỐNG KÊ DOANH THU                                                            |");
        System.out.println("|                                                                                                                                         |");
        System.out.println("|                                                           Mã nhân viên: " + VietBangTien6(13, maNV.toUpperCase()) + "                                                   |");
        System.out.println("|                                                     Tên nhân viên: " + VietBangTien6(30, tenNV.toUpperCase()) + "                                       |");
        System.out.println("|                                                             Tháng: " + thang + "                                                              |");
        System.out.println("|                                                    Thống kê lúc " + thoiGian + "                                                   |");
        System.out.println("+-------+--------------+-----------+----------------------+---------------+----------------+---------------------+------------------------+");
        System.out.println("|  STT  |   NGÀY BÁN   |   MÃ SP   |     TÊN SẢN PHẨM     |  ĐƠN VỊ TÍNH  |    SỐ LƯỢNG    |       ĐƠN GIÁ       |       THÀNH TIỀN       |");
        System.out.println("+-------+--------------+-----------+----------------------+---------------+----------------+---------------------+------------------------+");
        String sum = "0";
        String sumVon = "0";
        boolean ban = false;
        int dem = 0;
        for (int i = 0; i < list.size(); i++) {
            if (thang.equalsIgnoreCase(list.get(i).getDate().substring(3, 10)) && maNV.equalsIgnoreCase(list.get(i).getMaNV())) {
                ban = true;
                String stt = String.valueOf(++dem);
                VietBang(7, stt);
                System.out.print("  " + list.get(i).getDate() + "  |");
                list.get(i).inTT2();
                String thanhTien = fm.chuanHoaSo(fm.mul(list.get(i).getSoLuong(), list.get(i).getGiaBan()));
                VietBangTien3(21, list.get(i).getGiaBan());
                VietBangTien(24, thanhTien);
                System.out.println("");
                sum = fm.add2(sum, thanhTien);
                sumVon = fm.add2(sumVon, fm.mul(list.get(i).getGiaNhap(), list.get(i).getSoLuong()));
            }
        }

        float vat = (float) 1 / 10;
        float ck = (float) 5 / 100;
        if (!ban) {
            System.out.println("|                                                   Không có sản phẩm nào được bán ra !                                                   |");
            System.out.println("+----------------------------------------------------------------------------------------------------------------+------------------------+");
        } else {
            System.out.println("+-------+--------------+-----------+----------------------+---------------+----------------+---------------------+------------------------+");
        }
        System.out.println("|                                                                                           TỔNG TIỀN SẢN PHẨM   |" + VietBangTien2(24, sum + " VNĐ") + "|");
        String sumThuVe = sum;
        sum = fm.add2(sum, fm.mul1(sum, vat));
        sum = fm.add2(sum, fm.mul1(sum, ck));
        System.out.println("|                                                                    TỔNG TIỀN SAU THUẾ VÀ CHIẾT KHẤU SẢN PHẨM   |" + VietBangTien2(24, sum + " VNĐ") + "|");
        System.out.println("+----------------------------------------------------------------------------------------------------------------+------------------------+");
        System.out.println("|" + VietBangTien2(137, ds.inchuthongke(fm.chuanHoaSo2(sum))) + "|");
        System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|                                                         DOANH THU THÁNG " + thang + "                                                         |");
        System.out.println("|                                                           Mã nhân viên: " + VietBangTien6(12, maNV.toUpperCase()) + "                                                    |");
        System.out.println("|                                                                                                                                         |");
        System.out.println("|                                                Tổng số tiền vốn:   " + VietBangTien5(20, sumVon) + "                                                 |");
        System.out.println("|                                                Tổng số tiền thu về: " + VietBangTien5(19, sumThuVe) + "                                                 |");
        System.out.println("|                                                Tổng số tiền lãi:   " + VietBangTien5(20, fm.sub2(sumThuVe, sumVon)) + "                                                 |");
        System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------+");
    }

    public void bcNgay() {
        Scanner sc = new Scanner(System.in);
        String ngay;
        System.out.println("");
        do {
            System.out.print("Mời bạn nhập vào ngày cần thống kê (Lưu ý: Định dạng ngày tháng năm VD: 20/03/2000 - Các số nhỏ hơn 10 phải thêm số 0 vào trước !): ");
            ngay = sc.nextLine();
            ngay = ngay.trim();
            if (!checkNgay(ngay)) {
                System.err.println("==>> Nhập sai định dạng ngày tháng năm ! Vui lòng nhập lại ...");
            }
        } while (!checkNgay(ngay));
        docFileHD();
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss - dd/MM/YYYY");
        String thoiGian = ft.format(date);
        xemHDngay(ngay, listHD, thoiGian);
    }

    public void bcThang() {
        Scanner sc = new Scanner(System.in);
        String thang;
        System.out.println("");
        do {
            System.out.print("Mời bạn nhập vào tháng cần thống kê (Lưu ý: Định dạng tháng năm VD: 03/2000 - Các tháng nhỏ hơn 10 phải thêm số 0 vào trước !): ");
            thang = sc.nextLine();
            thang = thang.trim();
            if (!checkThang(thang)) {
                System.err.println("==>> Nhập sai định dạng tháng năm ! Vui lòng nhập lại ...");
            }
        } while (!checkThang(thang));
        docFileHD();
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss - dd/MM/YYYY");
        String thoiGian = ft.format(date);
        xemHDthang(thang, listHD, thoiGian);
    }

    public void xemHDthang(String thang, ArrayList<HoaDon> list, String thoiGian) {
        System.out.println("");
        System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|           HỆ THỐNG SIÊU THỊ ĐIỆN MÁY NPT                                                                                                |");
        System.out.println("|     Đ.c: Số 112 - Bạch Mai - Hai Bà Trưng - Hà Nội                                                                                      |");
        System.out.println("|        MST: 012269874566   Tel: 0945.108.999                                                                                            |");
        System.out.println("|   * Sự hài lòng của bạn là niềm vui của chúng tôi *                                                                                     |");
        System.out.println("|                                                                                                                                         |");
        System.out.println("|                                                           THỐNG KÊ DOANH THU                                                            |");
        System.out.println("|                                                                                                                                         |");
        System.out.println("|                                                             Tháng: " + thang + "                                                              |");
        System.out.println("|                                                    Thống kê lúc " + thoiGian + "                                                   |");
        System.out.println("+-------+--------------+-----------+----------------------+---------------+----------------+---------------------+------------------------+");
        System.out.println("|  STT  |   NGÀY BÁN   |   MÃ SP   |     TÊN SẢN PHẨM     |  ĐƠN VỊ TÍNH  |    SỐ LƯỢNG    |       ĐƠN GIÁ       |       THÀNH TIỀN       |");
        System.out.println("+-------+--------------+-----------+----------------------+---------------+----------------+---------------------+------------------------+");
        String sum = "0";
        String sumVon = "0";
        boolean ban = false;
        int dem = 0;
        for (int i = 0; i < list.size(); i++) {
            if (thang.equalsIgnoreCase(list.get(i).getDate().substring(3, 10))) {
                ban = true;
                String stt = String.valueOf(++dem);
                VietBang(7, stt);
                System.out.print("  " + list.get(i).getDate() + "  |");
                list.get(i).inTT2();
                String thanhTien = fm.chuanHoaSo(fm.mul(list.get(i).getSoLuong(), list.get(i).getGiaBan()));
                VietBangTien3(21, list.get(i).getGiaBan());
                VietBangTien(24, thanhTien);
                System.out.println("");
                sum = fm.add2(sum, thanhTien);
                sumVon = fm.add2(sumVon, fm.mul(list.get(i).getGiaNhap(), list.get(i).getSoLuong()));
            }
        }

        float vat = (float) 1 / 10;
        float ck = (float) 5 / 100;
        if (!ban) {
            System.out.println("|                                                   Không có sản phẩm nào được bán ra !                                                   |");
            System.out.println("+----------------------------------------------------------------------------------------------------------------+------------------------+");
        } else {
            System.out.println("+-------+--------------+-----------+----------------------+---------------+----------------+---------------------+------------------------+");
        }
        System.out.println("|                                                                                           TỔNG TIỀN SẢN PHẨM   |" + VietBangTien2(24, sum + " VNĐ") + "|");
        String sumThuVe = sum;
        sum = fm.add2(sum, fm.mul1(sum, vat));
        sum = fm.add2(sum, fm.mul1(sum, ck));
        System.out.println("|                                                                    TỔNG TIỀN SAU THUẾ VÀ CHIẾT KHẤU SẢN PHẨM   |" + VietBangTien2(24, sum + " VNĐ") + "|");
        System.out.println("+----------------------------------------------------------------------------------------------------------------+------------------------+");
        System.out.println("|" + VietBangTien2(137, ds.inchuthongke(fm.chuanHoaSo2(sum))) + "|");
        System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|                                                         DOANH THU THÁNG " + thang + "                                                         |");
        System.out.println("|                                                                                                                                         |");
        System.out.println("|                                                Tổng số tiền vốn:   " + VietBangTien5(20, sumVon) + "                                                 |");
        System.out.println("|                                                Tổng số tiền thu về: " + VietBangTien5(19, sumThuVe) + "                                                 |");
        System.out.println("|                                                Tổng số tiền lãi:   " + VietBangTien5(20, fm.sub2(sumThuVe, sumVon)) + "                                                 |");
        System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------+");
    }

    public boolean checkThang(String t) {
        if (t.length() != 7) {
            return false;
        }
        if (t.charAt(2) != '/') {
            return false;
        }
        return true;
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

    public void xemHDngay(String ngayBan, ArrayList<HoaDon> list, String thoiGian) {
        System.out.println("");
        System.out.println("+--------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|           HỆ THỐNG SIÊU THỊ ĐIỆN MÁY NPT                                                                                 |");
        System.out.println("|     Đ.c: Số 112 - Bạch Mai - Hai Bà Trưng - Hà Nội                                                                       |");
        System.out.println("|        MST: 012269874566   Tel: 0945.108.999                                                                             |");
        System.out.println("|   * Sự hài lòng của bạn là niềm vui của chúng tôi *                                                                      |");
        System.out.println("|                                                                                                                          |");
        System.out.println("|                                                    THỐNG KÊ DOANH THU                                                    |");
        System.out.println("|                                                                                                                          |");
        System.out.println("|                                                   Ngày bán: " + ngayBan + "                                                   |");
        System.out.println("|                                            Thống kê lúc " + thoiGian + "                                            |");
        System.out.println("+-------+-----------+----------------------+---------------+----------------+---------------------+------------------------+");
        System.out.println("|  STT  |   MÃ SP   |     TÊN SẢN PHẨM     |  ĐƠN VỊ TÍNH  |    SỐ LƯỢNG    |       ĐƠN GIÁ       |       THÀNH TIỀN       |");
        System.out.println("+-------+-----------+----------------------+---------------+----------------+---------------------+------------------------+");
        String sum = "0";
        boolean ban = false;
        String sumVon = "0";
        int dem = 0;
        for (int i = 0; i < list.size(); i++) {
            if (ngayBan.equalsIgnoreCase(list.get(i).getDate())) {
                ban = true;
                String stt = String.valueOf(++dem);
                VietBang(7, stt);
                list.get(i).inTT2();
                String thanhTien = fm.chuanHoaSo(fm.mul(list.get(i).getSoLuong(), list.get(i).getGiaBan()));
                VietBangTien3(21, list.get(i).getGiaBan());
                VietBangTien(24, thanhTien);
                System.out.println("");
                sum = fm.add2(sum, thanhTien);
                sumVon = fm.add2(sumVon, fm.mul(list.get(i).getGiaNhap(), list.get(i).getSoLuong()));
            }
        }

        float vat = (float) 1 / 10;
        float ck = (float) 5 / 100;
        if (!ban) {
            System.out.println("|                                            Không có sản phẩm nào được bán ra !                                           |");
            System.out.println("+-------------------------------------------------------------------------------------------------+------------------------+");
        } else {
            System.out.println("+-------+-----------+----------------------+---------------+----------------+---------------------+------------------------+");
        }
        System.out.println("|                                                                            TỔNG TIỀN SẢN PHẨM   |" + VietBangTien2(24, sum + " VNĐ") + "|");
        String sumThuVe = sum;
        sum = fm.add2(sum, fm.mul1(sum, vat));
        sum = fm.add2(sum, fm.mul1(sum, ck));
        System.out.println("|                                                     TỔNG TIỀN SAU THUẾ VÀ CHIẾT KHẤU SẢN PHẨM   |" + VietBangTien2(24, sum + " VNĐ") + "|");
        System.out.println("+-------------------------------------------------------------------------------------------------+------------------------+");
        System.out.println("|" + VietBangTien2(122, ds.inchuthongke(fm.chuanHoaSo2(sum))) + "|");
        System.out.println("+--------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|                                                DOANH THU NGÀY " + ngayBan + "                                                 |");
        System.out.println("|                                                                                                                          |");
        System.out.println("|                                         Tổng số tiền vốn:   " + VietBangTien5(20, sumVon) + "                                         |");
        System.out.println("|                                         Tổng số tiền thu về: " + VietBangTien5(19, sumThuVe) + "                                         |");
        System.out.println("|                                         Tổng số tiền lãi:   " + VietBangTien5(20, fm.sub2(sumThuVe, sumVon)) + "                                         |");
        System.out.println("+--------------------------------------------------------------------------------------------------------------------------+");
    }

    public String VietBangTien6(int khoangTrong, String s) {
        int demKhoangTrong = khoangTrong - s.length();
        for (int i = 1; i <= demKhoangTrong; i++) {
            s = s + " ";
        }
        return s;
    }

    public String VietBangTien5(int khoangTrong, String s) {
        int demKhoangTrong = khoangTrong - s.length();
        for (int i = 1; i <= demKhoangTrong; i++) {
            s = " " + s;
        }
        return s;
    }

    public String VietBangTien2(int khoangTrong, String s) {
        int demKhoangTrong = khoangTrong - s.length();
        for (int i = 1; i <= demKhoangTrong - 3; i++) {
            s = " " + s;
        }
        for (int i = 1; i <= 3; i++) {
            s = s + " ";
        }
        return s;
    }

    public void VietBangTien3(int khoangTrong, String s) {
        int demKhoangTrong = khoangTrong - s.length();
        for (int i = 1; i <= demKhoangTrong - 5; i++) {
            System.out.print(" ");
        }
        System.out.print(s);
        for (int i = 1; i <= 5; i++) {
            System.out.print(" ");
        }
        System.out.print("|");
    }

    public void VietBangTien(int khoangTrong, String s) {
        int demKhoangTrong = khoangTrong - s.length();
        for (int i = 1; i <= demKhoangTrong - 7; i++) {
            System.out.print(" ");
        }
        System.out.print(s);
        for (int i = 1; i <= 7; i++) {
            System.out.print(" ");
        }
        System.out.print("|");
    }

    public boolean checkNgay(String ngay) {
        if (ngay.length() != 10) {
            return false;
        }
        if (ngay.charAt(2) != '/') {
            return false;
        }
        if (ngay.charAt(5) != '/') {
            return false;
        }
        return true;
    }

    public void docFileHD() {
        try {
            BufferedReader br = null;
            FileReader fr = null;
            listHD = new ArrayList<>();
            fr = new FileReader(this.fileHD);
            br = new BufferedReader(fr);
            String info = null;
            try {
                while ((info = br.readLine()) != null) {
                    String chiaTT[] = info.split("\t");
                    HoaDon hd = new HoaDon();
                    if (chiaTT.length == 1) {
                        return;
                    }
                    hd.setMaHD(chiaTT[0]);
                    hd.setMaNV(chiaTT[1]);
                    hd.setTenNV(chiaTT[2]);
                    hd.setMaKH(chiaTT[3]);
                    hd.setMaSP(chiaTT[4]);
                    hd.setTenSP(chiaTT[5]);
                    hd.setSoLuong(chiaTT[6]);
                    hd.setDonViTinh(chiaTT[7]);
                    hd.setTenNSX(chiaTT[8]);
                    hd.setGiaBan(chiaTT[9]);
                    hd.setGiaNhap(chiaTT[10]);
                    hd.setDate(chiaTT[11]);
                    listHD.add(hd);
                }
            } catch (IOException ex) {
                Logger.getLogger(AppBaoCao.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppBaoCao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
