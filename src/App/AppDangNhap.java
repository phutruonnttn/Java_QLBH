/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import Object.NhanVien;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class AppDangNhap {

    private final String fileNV = "NhanVien.txt";
    private final String fileDN = "DangNhap.txt";
    ArrayList<NhanVien> listNV = new ArrayList<>();

    public void run(String tg, String maNV) {
        docFileNV();
        String tenNV = timNV(maNV);
        ghiFileDN(maNV, tenNV, tg);
    }

    public String timNV(String maNV) {
        for (int i = 0; i < listNV.size(); i++) {
            if (listNV.get(i).getMaNV().equalsIgnoreCase(maNV)) {
                return listNV.get(i).getHoTen();
            }
        }
        return "ADMINISTRATOR";
    }

    public void ghiFileDN(String maNV, String tenNV, String tg) {
        try {
            BufferedWriter bw = null;
            FileWriter fw = null;
            String data = "";
            data += maNV + "\t";
            data += tenNV + "\t";
            data += tg + "\n";
            fw = new FileWriter(this.fileDN, true);
            bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(AppDangNhap.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(AppDangNhap.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppDangNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
