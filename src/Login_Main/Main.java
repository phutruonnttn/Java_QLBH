/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login_Main;

import App.AppBanHang;
import App.AppBaoCao;
import App.AppKhachHang;
import App.AppNhanVien;
import App.AppSanPham;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login log = new Login();
        String taiKhoan = log.run();
        
        int chon;
        do {
            System.out.println("============== HỆ THỐNG BÁN HÀNG ================");
            System.out.println("");
            System.out.println("    +-----+-----------------------------+");
            System.out.println("    |     HỆ THỐNG QUẢN LÝ BÁN HÀNG     |");
            System.out.println("    +-----+-----------------------------+");
            System.out.println("    | [1] |   Bán hàng                  |");
            System.out.println("    +-----+-----------------------------+");
            System.out.println("    | [2] |   Quản lý khách hàng        |");
            System.out.println("    +-----+-----------------------------+");
            System.out.println("    | [3] |   Quản lý sản phẩm          |");
            System.out.println("    +-----+-----------------------------+");
            System.out.println("    | [4] |   Quản lý nhân viên         |");
            System.out.println("    +-----+-----------------------------+");
            System.out.println("    | [5] |   Báo cáo thống kê          |");
            System.out.println("    +-----+-----------------------------+");
            System.out.println("    | [6] |   Đăng xuất                 |");
            System.out.println("    +-----+-----------------------------+");
            System.out.println("    | [7] |   Thoát khỏi chương trình   |");
            System.out.println("    +-----+-----------------------------+");
            System.out.print("    Mời bạn chọn chức năng: ");
            chon = sc.nextInt();
            System.out.println("");
            switch (chon) {
                case 1: {
                    if (taiKhoan.equalsIgnoreCase("admin")) {
                        System.err.println("==>> CHỨC NĂNG NÀY KHÔNG DÀNH CHO ADMIN !");
                    } else {
                        AppBanHang abh = new AppBanHang();
                        abh.run(taiKhoan);
                    }
                    break;
                }
                case 2: {
                    AppKhachHang akh = new AppKhachHang();
                    akh.docFileKH();
                    akh.menuKH();
                    break;
                }
                case 3: {
                    AppSanPham asp = new AppSanPham();
                    asp.docFileSP();
                    asp.menuSP();
                    break;
                }
                case 4: {
                    if (!taiKhoan.equalsIgnoreCase("admin")) {
                        System.err.println("==>> CHỨC NĂNG NÀY KHÔNG DÀNH CHO NHÂN VIÊN !");
                    } else {
                        AppNhanVien anv = new AppNhanVien();
                        anv.docFileNV();
                        anv.menuNV();
                    }
                    break;
                }
                case 5: {

                    if (!taiKhoan.equalsIgnoreCase("admin")) {
                        System.err.println("==>> CHỨC NĂNG NÀY KHÔNG DÀNH CHO NHÂN VIÊN !");
                    } else {
                        AppBaoCao abc = new AppBaoCao();
                        abc.run();
                    }
                    break;
                }
                case 6: {                  
                    taiKhoan = log.run();
                    break;
                }
                case 7: {
                    break;
                }
                default: {
                    System.err.println("==>> BẠN CHỌN SAI CHỨC NĂNG !");
                }
                System.out.println("");
            }
        } while (chon != 7);
        System.out.println("");
        System.out.println("CẢM ƠN BẠN ĐÃ SỬ DỤNG PHẦN MỀM CỦA T - GROUP !");
        System.out.println("@copyright 2018 By Nguyen Phu Truong");
    }
}
