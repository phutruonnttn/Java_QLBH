/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import CheckData.CheckData;
import CheckData.DinhDang;
import CheckData.DocSo;
import Object.HoaDon;
import Object.KhachHang;
import Object.NhanVien;
import Object.SanPham;
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
public class AppBanHang {

    private ArrayList<SanPham> listSP = new ArrayList<>();
    private ArrayList<NhanVien> listNV = new ArrayList<>();
    private ArrayList<SanPham> prelistSP = new ArrayList<>();
    private final String fileSP = "SanPham.txt";
    private final String fileNV = "NhanVien.txt";
    private final String fileHD = "HoaDon.txt";
    private ArrayList<HoaDon> gioHang = new ArrayList<>();
    CheckData check = new CheckData();
    DinhDang fm = new DinhDang();
    private ArrayList<KhachHang> listKH = new ArrayList<>();
    private final String nameFile = "KhachHang.txt";
    private int csKH;
    private boolean flag1 = true;
    private DocSo ds = new DocSo();

    public void run(String maNV) {
        System.out.println("------------- BÁN HÀNG -------------");
        System.out.println("");
        docFileKH();

        docFileSP();
        if (listSP.isEmpty()) {
            System.err.println("==>> CHƯA CÓ SẢN PHẦM NÀO TRONG HỆ THỐNG !");
            return;
        }

        Scanner input = new Scanner(System.in);

        KhachHang kh = new KhachHang();
        do {
            System.out.print("Mời bạn nhập mã khách hàng: ");
            kh.setMaKH(input.nextLine());
            if ("".equals(kh.getMaKH())) {
                System.err.println("Mã khách hàng không được bỏ trống !");
            }
        } while ("".equals(kh.getMaKH()));

        if (kiemTraMaKH(kh.getMaKH())) {
            System.out.println("");
            System.out.println("==>> THÔNG TIN KHÁCH HÀNG : ");
            System.out.println("");
            xemKQ();
            System.out.println("");
        } else {
            System.err.println("==>> KHÁCH HÀNG KHÔNG TỒN TẠI TRONG HỆ THỐNG ! VUI LÒNG THÊM MỚI KHÁCH HÀNG ...");
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

            listKH.add(kh);
            ghiFileKH();
            System.err.println("==>> THÊM MỚI KHÁCH HÀNG THÀNH CÔNG !");
            System.out.println("");
        }
        prelistSP = listSP;
        muaSP(kh.getMaKH(), maNV);
        int chon;
        do {
            System.out.println("    +----- BẠN CÓ MUỐN TIẾP TỤC MUA HÀNG ? -----+");
            System.out.println("    |            1. Tiếp tục mua hàng           |");
            System.out.println("    +-------------------------------------------+");
            System.out.println("    |            2. Xem giỏ hàng                |");
            System.out.println("    +-------------------------------------------+");
            System.out.println("    |            3. Thanh toán                  |");
            System.out.println("    +-------------------------------------------+");
            System.out.println("    |            4. Thoát                       |");
            System.out.println("    +-------------------------------------------+");
            System.out.print("     Mời bạn chọn chức năng: ");
            chon = input.nextInt();
            System.out.println("");
            switch (chon) {
                case 1: {
                    muaSP(kh.getMaKH(), maNV);
                    break;
                }
                case 2: {
                    xemGioHang(kh.getMaKH(), maNV, listKH.get(csKH).getHoTen(), listKH.get(csKH).getEmail());
                    if (!flag1) {
                        return;
                    } else {
                        break;
                    }
                }
                case 3: {
                    thanhToan(kh.getMaKH(), maNV, listKH.get(csKH).getHoTen(), listKH.get(csKH).getEmail());
                    if (!flag1) {
                        return;
                    } else {
                        break;
                    }
                }
                case 4: {
                    listSP = prelistSP;
                    ghiFileSP();
                    break;
                }
                default: {
                    System.err.println("==>> BẠN CHỌN SAI CHỨC NĂNG !");
                }
                System.out.println("");
            }
        } while (chon != 4);
    }

    public void xemGioHang(String maKhachHang, String maNhanVien, String tenKH, String email) {
        System.out.println("");
        int chon;
        Scanner input = new Scanner(System.in);
        do {
            if (gioHang.isEmpty()) {
                System.err.println("   ==>> CHƯA CÓ SẢN PHẨM NÀO TRONG GIỎ HÀNG !");
                return;
            }
            xemGH(gioHang);
            System.out.println("    +---------- CHỈNH SỦA GIỎ HÀNG ----------+");
            System.out.println("    |            1. Xóa 1 sản phẩm           |");
            System.out.println("    +----------------------------------------+");
            System.out.println("    |            2. Xóa tất cả               |");
            System.out.println("    +----------------------------------------+");
            System.out.println("    |            3. Thanh toán               |");
            System.out.println("    +----------------------------------------+");
            System.out.println("    |            4. Quay lại                 |");
            System.out.println("    +----------------------------------------+");
            System.out.print("     Mời bạn chọn chức năng: ");
            chon = input.nextInt();
            switch (chon) {
                case 1: {
                    xoa1SP();
                    break;
                }
                case 2: {
                    xoaTatCa();
                    break;
                }
                case 3: {
                    thanhToan(maKhachHang, maNhanVien, tenKH, email);
                    if (!flag1) {
                        return;
                    } else {
                        break;
                    }
                }
                case 4: {
                    break;
                }
                default: {
                    System.err.println("==>> BẠN CHỌN SAI CHỨC NĂNG !");
                }
                System.out.println("");
            }
        } while (chon != 2 && chon != 4 && chon != 3);
    }

    public void xoa1SP() {
        System.out.println("");
        int stt;
        do {
            System.out.print("   Nhập STT sản phẩm bạn muốn xóa: ");
            Scanner sc = new Scanner(System.in);
            stt = sc.nextInt();
            if (stt <= 0 || stt > gioHang.size()) {
                System.err.println("   ==>> STT bạn chọn không có trong giỏ hàng !");
            }
        } while (stt <= 0 || stt > gioHang.size());

        stt--;
        int chiSoSP = timMaSPtrongListSP(gioHang.get(stt).getMaSP());
        String soLuong = gioHang.get(stt).getSoLuong();
        gioHang.remove(stt);
        listSP.get(chiSoSP).setSoLuong(fm.chuanHoaSo(fm.add2(listSP.get(chiSoSP).getSoLuong(), soLuong)));
        ghiFileSP();
        System.out.println("");
        System.out.println("   ==>> XÓA SẢN PHẨM THÀNH CÔNG !");
    }

    public void xoaTatCa() {
        gioHang.clear();
        listSP = prelistSP;
        ghiFileSP();
        System.out.println("");
        System.out.println("   ==>> XÓA TẤT CẢ SẢN PHẨM THÀNH CÔNG !");
    }

    public int timMaSPtrongListSP(String ma) {
        for (int i = 0; i < listSP.size(); i++) {
            if (ma.equalsIgnoreCase(listSP.get(i).getMaSP())) {
                return i;
            }
        }
        return -1;
    }

    public boolean kiemTraMaSP(String ma) {
        boolean flag = false;
        for (int i = 0; i < listSP.size(); i++) {
            if (ma.equalsIgnoreCase(listSP.get(i).getMaSP())) {
                flag = true;
                csSP = i;
                break;
            }
        }
        return flag;
    }
    private int csSP;

    public void muaSP(String maKhachHang, String maNV) {
        docFileSP();
        Scanner sc = new Scanner(System.in);
        String ma;
        do {
            System.out.print("  Nhập mã sản phẩm cần mua: ");
            ma = sc.nextLine();
            if (!kiemTraMaSP(ma)) {
                System.err.println("  ==>> Mã sản phẩm không tồn tại ! Vui lòng thử lại ...");
            }
        } while (!kiemTraMaSP(ma));
        System.out.println("");
        System.out.println("  ==>> THÔNG TIN SẢN PHẨM: ");
        System.out.println("");
        xemSP(csSP);
        if (fm.chuanHoaNguocSo(listSP.get(csSP).getSoLuong()) == 0) {
            System.out.println("");
            System.err.println(" ==>> XIN LỖI BẠN ! SẢN PHẨM NÀY ĐÃ HẾT HÀNG !");
            return;
        }
        int sl;
        do {
            System.out.print("  Nhập số lượng sản phẩm cần mua: ");
            sl = sc.nextInt();
            if (!check.kiemTraSo(sl + "")) {
                System.err.println("  ==>> Số lượng sản phẩm phải lớn hơn 0 !");
            }
        } while (!checkSL(sl, csSP) || !check.kiemTraSo(sl + ""));
        if (tonTaiTrongGH(ma)) {
            gioHang.get(csGH).setSoLuong(fm.add(gioHang.get(csGH).getSoLuong(), sl));
            listSP.get(csSP).setSoLuong(fm.sub(listSP.get(csSP).getSoLuong(), sl));
            ghiFileSP();
            System.out.println("");
            System.out.println(" ==>> THÊM VÀO GIỎ HÀNG THÀNH CÔNG !");
            System.out.println("");
        } else {
            addToGH(sl, csSP, maKhachHang, maNV);
        }
    }

    public void addToGH(int sl, int cs, String maKhachHang, String maNV) {
        HoaDon hd = new HoaDon();
        hd.setDonViTinh(listSP.get(cs).getDonViTinh());
        hd.setGiaBan(listSP.get(cs).getGiaBan());
        hd.setGiaNhap(listSP.get(cs).getGiaNhap());
        hd.setSoLuong(fm.chuanHoaSo(sl + ""));
        hd.setTenNSX(listSP.get(cs).getTenNSX());
        hd.setTenSP(listSP.get(cs).getTen());
        hd.setMaSP(listSP.get(cs).getMaSP());
        hd.setMaKH(maKhachHang);
        hd.setMaKH(maNV);

        listSP.get(cs).setSoLuong(fm.sub(listSP.get(cs).getSoLuong(), sl));
        ghiFileSP();
        gioHang.add(hd);
        System.out.println("");
        System.out.println(" ==>> THÊM VÀO GIỎ HÀNG THÀNH CÔNG !");
        System.out.println("");
    }

    public boolean checkSL(int sl, int cs) {
        if (sl > fm.chuanHoaNguocSo(listSP.get(cs).getSoLuong())) {
            System.err.println("  ==>> Chỉ còn lại " + listSP.get(cs).getSoLuong() + " sản phẩm này trong kho ! Vui lòng nhập số lượng nhỏ hơn ...");
            return false;
        }
        return true;
    }

    public String layTenNV(String ma) {
        docFileNV();
        for (int i = 0; i < listNV.size(); i++) {
            if (ma.equalsIgnoreCase(listNV.get(i).getMaNV())) {
                return listNV.get(i).getHoTen();
            }
        }
        System.err.println("  ==>> Xảy ra lỗi !");
        return null;
    }

    public void thanhToan(String maKhachHang, String maNhanVien, String tenKH, String email) {

        System.out.println("");
        if (gioHang.isEmpty()) {
            System.err.println("   ==>> KHÔNG THỂ THANH TOÁN VÌ CHƯA CÓ SẢN PHẨM NÀO TRONG GIỎ HÀNG !");
            return;
        }
        String sum = "0";
        for (int i = 0; i < gioHang.size(); i++) {
            String thanhTien = fm.chuanHoaSo(fm.mul(gioHang.get(i).getSoLuong(), gioHang.get(i).getGiaBan()));
            sum = fm.add2(sum, thanhTien);
        }
        float vat = (float) 1 / 10;
        float ck = (float) 5 / 100;
        sum = fm.add2(sum, fm.mul1(sum, vat));
        sum = fm.add2(sum, fm.mul1(sum, ck));
        long tong = fm.chuanHoaNguocSo(sum);
        System.out.println("   TỔNG TẠM TÍNH (ĐÃ BAO GỒM THUẾ GTGT VÀ CHIẾT KHẤU SẢN PHẨM): " + sum + " VNĐ");

        long tienKhach;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("   Nhập số tiền khách đưa: ");
            tienKhach = sc.nextLong();
            if (tienKhach < tong) {
                System.err.println("   ==>> Số tiền này phải lớn hơn hoặc bằng tổng !");
            }
        } while (tienKhach < tong);

        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("YYYY_MM_dd_HH_mm_ss");
        SimpleDateFormat ft1 = new SimpleDateFormat("dd/MM/YYYY - HH:mm:ss");
        String maHD = maKhachHang + "_" + ft.format(date);
        String thoiGian = ft1.format(date);
        String tenNV = layTenNV(maNhanVien);
        xemHD(gioHang, maHD, thoiGian, maNhanVien, tenNV, maKhachHang, tenKH, tienKhach);

        System.out.println("    +--------- XÁC NHẬN THANH TOÁN ---------+");
        System.out.println("    |        1. Xác nhận thanh toán         |");
        System.out.println("    +---------------------------------------+");
        System.out.println("    |        2. Quay lại                    |");
        System.out.println("    +---------------------------------------+");
        System.out.print("     Mời bạn chọn chức năng: ");
        Scanner input = new Scanner(System.in);
        int chon = input.nextInt();
        switch (chon) {
            case 1: {
                flag1 = false;
                ghiFileHD(gioHang, maHD, thoiGian, maNhanVien, tenNV, maKhachHang);
                xacNhanTT(tenKH, email, sum);
                break;
            }
            case 2: {
                break;
            }
            default: {
                System.err.println("  ==>> BẠN CHỌN SAI CHỨC NĂNG !");
            }
        }
    }

    public void ghiFileHD(ArrayList<HoaDon> list, String maHD, String thoiGian, String maNhanVien, String tenNV, String maKhachHang) {
        try {
            BufferedWriter bw = null;
            FileWriter fw = null;
            String data = "";
            for (int i = 0; i < list.size(); i++) {
                data += maHD + "\t";
                data += maNhanVien + "\t";
                data += tenNV + "\t";
                data += maKhachHang + "\t";
                data += list.get(i).getMaSP().toUpperCase() + "\t";
                data += list.get(i).getTenSP() + "\t";
                data += list.get(i).getSoLuong() + "\t";
                data += list.get(i).getDonViTinh() + "\t";
                data += list.get(i).getTenNSX() + "\t";
                data += list.get(i).getGiaBan() + "\t";
                data += list.get(i).getGiaNhap() + "\t";
                data += thoiGian.substring(0, 10) + "\n";
            }
            fw = new FileWriter(this.fileHD, true);// ghi de
            bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();

        } catch (IOException ex) {
            Logger.getLogger(AppBanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void xacNhanTT(String tenKH, String email, String sum) {
        System.out.println("");
        System.out.println(" Cảm ơn " + tenKH.toUpperCase() + " đã mua hàng tại hệ thống siêu thị của chúng tôi !");
        System.out.println(" Đang thực hiện gửi hóa đơn đến Email ! Vui lòng chờ trong giây lát...");
        System.out.println("");
        sendHDtoEmail(gioHang, email, sum);
        System.out.println(" ==>> THÔNG TIN HÓA ĐƠN ĐÃ ĐƯỢC GỬI TỚI EMAIL CỦA QUÝ KHÁCH ! TRÂN TRỌNG !");
        System.out.println("");
    }

    public void sendHDtoEmail(ArrayList<HoaDon> list, String mail, String sum) {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("hethongsieuthidienmaynpt@gmail.com", "Pt230572"));
            email.setSSLOnConnect(true);
            email.setFrom("hethongsieuthidienmaynpt@gmail.com", "HỆ THỐNG SIÊU THỊ ĐIỆN MÁY NPT");
            email.setSubject("HỆ THỐNG SIÊU THỊ ĐIỆN MÁY NPT: HÓA ĐƠN THANH TOÁN MUA HÀNG");
            String tt = "";
            tt = tt + "                                             ------- THÔNG TIN HÓA ĐƠN MUA HÀNG -------\n\n";
            for (int i = 0; i < gioHang.size(); i++) {
                tt = tt + " Tên sản phẩm: " + list.get(i).getTenSP().toUpperCase() + "\t\t\t\tSố lượng: " + list.get(i).getSoLuong()+"\t\t\t\tĐơn giá: " + list.get(i).getGiaBan() + "\n\n";
            }
            email.setMsg(tt + " TỔNG TIỀN SẢN PHẨM (ĐÃ BAO GỒM THUẾ GTGT VÀ CHIẾT KHẤU SẢN PHẨM): " + sum + " VNĐ\n\n"
                    + " Cảm ơn quý khách đã mua hàng tại hệ thống siêu thị của chúng tôi !\n\n"
                    + "               Trân trọng cảm ơn !");
            email.addTo(mail);
            email.send();

        } catch (EmailException e) {
            System.err.println(" ==>> GỬI KHÔNG THÀNH CÔNG !");
            System.exit(0);
        }
    }

    public void xemHD(ArrayList<HoaDon> list, String maHD, String thoiGian, String maNhanVien, String tenNV, String maKhachHang, String tenKH, long tienKhach) {

        System.out.println("+--------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|           HỆ THỐNG SIÊU THỊ ĐIỆN MÁY NPT                                                                                 |");
        System.out.println("|     Đ.c: Số 112 - Bạch Mai - Hai Bà Trưng - Hà Nội                                                                       |");
        System.out.println("|        MST: 012269874566   Tel: 0945.108.999                                                                             |");
        System.out.println("|   * Sự hài lòng của bạn là niềm vui của chúng tôi *                                                                      |");
        System.out.println("|                                                                                                                          |");
        System.out.println("|                                                    HÓA ĐƠN THANH TOÁN                                                    |");
        System.out.println("|                                                                                                                          |");
        System.out.println("|                                            Mã hóa đơn: " + VietBang2(40, maHD.toUpperCase()) + "                          |");
        System.out.println("|                                                Time: " + thoiGian + "                                               |");
        System.out.println("+--------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|                             Mã nhân viên: " + VietBang2(14, maNhanVien.toUpperCase()) + "      Tên nhân viên: " + VietBang2(30, tenNV) + "              |");
        System.out.println("|                             Mã khách hàng: " + VietBang2(14, maKhachHang.toUpperCase()) + "     Tên khách hàng: " + VietBang2(30, tenKH) + "             |");
        System.out.println("+-------+-----------+----------------------+---------------+----------------+---------------------+------------------------+");
        System.out.println("|  STT  |   MÃ SP   |     TÊN SẢN PHẨM     |  ĐƠN VỊ TÍNH  |    SỐ LƯỢNG    |       ĐƠN GIÁ       |       THÀNH TIỀN       |");
        System.out.println("+-------+-----------+----------------------+---------------+----------------+---------------------+------------------------+");
        String sum = "0";
        for (int i = 0; i < list.size(); i++) {
            String stt = String.valueOf(i + 1);
            VietBang(7, stt);
            list.get(i).inTT2();
            String thanhTien = fm.chuanHoaSo(fm.mul(list.get(i).getSoLuong(), list.get(i).getGiaBan()));
            VietBangTien3(21, list.get(i).getGiaBan());
            VietBangTien(24, thanhTien);
            System.out.println("");
            sum = fm.add2(sum, thanhTien);
            list.get(i).setMaHD(maHD);
            list.get(i).setDate(thoiGian);
        }
        float vat = (float) 1 / 10;
        float ck = (float) 5 / 100;
        System.out.println("+-------+-----------+----------------------+---------------+----------------+---------------------+------------------------+");
        System.out.println("|                                                                            TỔNG TIỀN SẢN PHẨM   |" + VietBangTien2(24, sum + " VNĐ") + "|");
        System.out.println("|                                                                         THUẾ GTGT (VAT = 10%)   |" + VietBangTien2(24, fm.mul1(sum, vat) + " VNĐ") + "|");
        sum = fm.add2(sum, fm.mul1(sum, vat));
        System.out.println("|                                                       TỔNG TIỀN CHIẾT KHẤU SẢN PHẨM (CK = 5%)   |" + VietBangTien2(24, fm.mul1(sum, ck) + " VNĐ") + "|");
        sum = fm.add2(sum, fm.mul1(sum, ck));
        System.out.println("|                                                          TỔNG TIỀN KHÁCH HÀNG PHẢI THANH TOÁN   |" + VietBangTien2(24, sum + " VNĐ") + "|");
        System.out.println("+-------------------------------------------------------------------------------------------------+------------------------+");
        System.out.println("|" + VietBangTien2(122, ds.inchuthongke(fm.chuanHoaSo2(sum))) + "|");
        System.out.println("+-------------------------------------------------------------------------------------------------+------------------------+");
        System.out.println("|                                                                             SỐ TIỀN KHÁCH ĐƯA   |" + VietBangTien2(24, fm.chuanHoaSo(tienKhach + "") + " VNĐ") + "|");
        System.out.println("|                                                                 SỐ TIỀN KHÁCH PHẢI THANH TOÁN   |" + VietBangTien2(24, sum + " VNĐ") + "|");
        System.out.println("|                                                                               SỐ TIỀN TRẢ LẠI   |" + VietBangTien2(24, fm.sub2(fm.chuanHoaSo(tienKhach + ""), sum) + " VNĐ") + "|");
        System.out.println("+-------------------------------------------------------------------------------------------------+------------------------+");
        System.out.println("|                     Khách hàng                                                 Nhân viên bán hàng                        |");
        System.out.println("|                      (Ký tên)                                                       (Ký tên)                             |");
        System.out.println("|                                                                                                                          |");
        System.out.println("|                                                                                                                          |");
        System.out.println("|           " + VietBang4(30, tenKH) + "                                 " + VietBang4(30, tenNV) + "                  |");
        System.out.println("+--------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("");
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

    private int csGH;

    public boolean tonTaiTrongGH(String ma) {
        for (int i = 0; i < gioHang.size(); i++) {
            if (ma.equalsIgnoreCase(gioHang.get(i).getMaSP())) {
                csGH = i;
                return true;
            }
        }
        return false;
    }

    public void xemGH(ArrayList<HoaDon> list) {
        if (list.isEmpty()) {
            System.err.println("==>> CHƯA CÓ SẢN PHẨM NÀO TRONG GIỎ HÀNG CỦA BẠN !");
        } else {
            System.out.println("+-----------------------------------------------------------------------------------------------------------------+");
            System.out.println("|                                              GIỎ HÀNG CỦA BẠN                                                   |");
            System.out.println("+-------+-----------+--------------------+----------------+---------------+-------------------+-------------------+");
            System.out.println("|  STT  |   MÃ SP   |    TÊN SẢN PHẨM    |    SỐ LƯỢNG    |  ĐƠN VỊ TÍNH  |      TÊN NSX      |     GIÁ THÀNH     |");
            System.out.println("+-------+-----------+--------------------+----------------+---------------+-------------------+-------------------+");
            for (int i = 0; i < list.size(); i++) {
                String stt = String.valueOf(i + 1);
                VietBang(7, stt);
                list.get(i).inTT();
            }
            System.out.println("+-------+-----------+--------------------+----------------+---------------+-------------------+-------------------+");
        }
        System.out.println("");
    }

    public void xemSP(int cs) {
        System.out.println("+---------------------------------------------------------------------------------------------------------+");
        System.out.println("|                                          THÔNG TIN SẢN PHẨM                                             |");
        System.out.println("+-----------+--------------------+----------------+---------------+-------------------+-------------------+");
        System.out.println("|   MÃ SP   |    TÊN SẢN PHẨM    |    SỐ LƯỢNG    |  ĐƠN VỊ TÍNH  |      TÊN NSX      |     GIÁ THÀNH     |");
        System.out.println("+-----------+--------------------+----------------+---------------+-------------------+-------------------+");
        System.out.print("|");
        listSP.get(cs).inTT2();
        System.out.println("+-----------+--------------------+----------------+---------------+-------------------+-------------------+");
        System.out.println("");
    }

    public String VietBang2(int khoangTrong, String s) {
        int demKhoangTrong = khoangTrong - s.length();
        for (int i = 1; i <= demKhoangTrong; i++) {
            s = s + " ";
        }
        return s;
    }

    public boolean kiemTraMaKH(String ma) {
        boolean flag = false;
        for (int i = 0; i < listKH.size(); i++) {
            if (ma.equalsIgnoreCase(listKH.get(i).getMaKH())) {
                csKH = i;
                flag = true;
                break;
            }
        }
        return flag;
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

    public void xemKQ() {
        System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|                                                          THÔNG TIN KHÁCH HÀNG                                                           |");
        System.out.println("+-----------+--------------------------+-----------------+---------------------------------------+----------------------------------------+");
        System.out.println("|   MÃ KH   |        HỌ VÀ TÊN         |       SĐT       |                 EMAIL                 |                  ĐỊA CHỈ               |");
        System.out.println("+-----------+--------------------------+-----------------+---------------------------------------+----------------------------------------+");
        System.out.print("|");
        listKH.get(csKH).inTT();
        System.out.println("+-----------+--------------------------+-----------------+---------------------------------------+----------------------------------------+");
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
                Logger.getLogger(AppBanHang.class
                        .getName()).log(Level.SEVERE, null, ex);

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppBanHang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ghiFileKH() {
        try {
            BufferedWriter bw = null;
            FileWriter fw = null;
            String data = "";
            for (int i = 0; i < listKH.size(); i++) {
                data += listKH.get(i).getMaKH() + "\t";
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
            Logger.getLogger(AppBanHang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
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
                Logger.getLogger(AppBanHang.class
                        .getName()).log(Level.SEVERE, null, ex);

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppBanHang.class
                    .getName()).log(Level.SEVERE, null, ex);
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
            fw = new FileWriter(this.fileSP);
            bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();

        } catch (IOException ex) {
            Logger.getLogger(AppBanHang.class
                    .getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(AppBanHang.class
                        .getName()).log(Level.SEVERE, null, ex);

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppBanHang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
