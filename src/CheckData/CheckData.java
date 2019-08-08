/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckData;

/**
 *
 * @author Admin
 */
public class CheckData {

    public boolean kiemTraTen(String ten) {
        for (int i = 0; i < 10; i++) {
            if (ten.contains(i + "") || ten.equals("")) {
                return true;
            }
        }
        return false;
    }

    public boolean kiemTraSo(String so) {
        so = so.trim();
        so = so.replace(".", "");
        so = so.replace(",", "");
        so = so.replace(" ", "");
        so = so.replaceAll("\\s+", "");
        long a = Long.parseLong(so);
        if (a <= 0) {
            return false;
        }
        return true;
    }

    public boolean kiemTraSuaTen(String ten) {
        for (int i = 0; i < 10; i++) {
            if (ten.contains(i + "")) {
                return true;
            }
        }
        return false;
    }

    public boolean kiemTraSDT(String dt) {
        dt = dt.trim();
        dt = dt.replace(".", "");
        dt = dt.replace(",", "");
        dt = dt.replace(" ", "");
        dt = dt.replaceAll("\\s+", "");
        boolean flag = false;
        try {
            if (!dt.startsWith(0 + "")) {
                System.err.println("Số điện thoại phải bắt đầu bằng số 0 !");
                flag = true;
            } else if (dt.length() < 10 || dt.length() > 11) {
                System.err.println("Số điện thoại phải có 10 hoặc 11 chứ số !");
                flag = true;
            }
        } catch (NumberFormatException ex) {
            System.err.println("Số điện thoại không được chứa chữ cái !");
            flag = true;
        } catch (Exception ex) {
            System.err.println("Fail" + ex.getMessage());
            flag = true;
        }
        return flag;
    }

    public boolean kiemTraEmail(String e) {
        String dinhDangEmail = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        boolean ktEmail = e.matches(dinhDangEmail);
        if (!ktEmail) {
            System.err.println("==>> Email sai, vui lòng nhập lại theo đúng định dạng example@example.com !");
            return true;
        }
        return false;
    }

}
