/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckData;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Admin
 */
public class DinhDang {

    public String chuanHoaVietHoa(String s) {
        s = s.trim();
        s = s.replaceAll("\\s+", " "); // sử dụng \\s+ đại diện cho khoảng trắng
        String arr[] = s.split(" ");
        s = "";
        for (int i = 0; i < arr.length; i++) {
            s += String.valueOf(arr[i].charAt(0)).toUpperCase() + arr[i].substring(1);
            if (i < arr.length - 1) {
                s += " ";
            }
        }
        return s;
    }

    public String mul(String s1, String s2){
        long ss1 = chuanHoaNguocSo(s1);
        long ss2 = chuanHoaNguocSo(s2);
        return chuanHoaSo(String.valueOf(ss1*ss2));
    }
    
    public String mul1(String s1, float s2){
        long ss1 = chuanHoaNguocSo(s1);
        long kq = (long) (ss1*s2);
        return chuanHoaSo(String.valueOf(kq));
    }
    
    public String chuanHoaSDT(String n) {
        n = n.trim().replace(".", "");
        n = n.replace(",", "");
        n = n.replaceAll("\\s+", "");
        return n.substring(0, 4) + " " + n.substring(4, 7) + " " + n.substring(7, n.length());
    }

    public String sub(String a, long b) {
        long aa = chuanHoaNguocSo(a);
        return chuanHoaSo(String.valueOf(aa - b));
    }
    
    public String sub2(String a, String b) {
        long aa = chuanHoaNguocSo(a);
        long bb = chuanHoaNguocSo(b);
        return chuanHoaSo(String.valueOf(aa - bb));
    }
    
    public String add(String a, long b) {
        long aa = chuanHoaNguocSo(a);
        return chuanHoaSo(String.valueOf(aa + b));
    }

    public String add2(String a, String b) {
        long aa = chuanHoaNguocSo(a);
        long bb = chuanHoaNguocSo(b);
        return chuanHoaSo(String.valueOf(aa + bb));
    }
    
    public String chuanHoaSo(String n) {
        n = n.trim().replace(".", "");
        n = n.replace(",", "");
        n = n.replaceAll("\\s+", "");
        String ans = "";
        int count = 0;
        for (int i = n.length() - 1; i >= 0; i--) {
            count++;
            ans = n.charAt(i) + ans;
            if (count == 3 && i != 0) {
                ans = ',' + ans;
                count = 0;
            }
        }
        return ans;
    }

    public String chuanHoaSo2(String n) {
        n = n.trim().replace(".", "");
        n = n.replace(",", "");
        n = n.replaceAll("\\s+", "");
        return n;
    }
    
    public long chuanHoaNguocSo(String n) {
        n = n.trim().replace(".", "");
        n = n.replace(",", "");
        n = n.replaceAll("\\s+", "");
        return Long.parseLong(n);
    }

    public String md5(String str) {//Mã hóa mật khẩu
        String result = "";
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(str.getBytes());
            BigInteger bigInteger = new BigInteger(1, digest.digest());
            result = bigInteger.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
}
