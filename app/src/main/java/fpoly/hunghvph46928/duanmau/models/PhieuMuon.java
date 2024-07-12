package fpoly.hunghvph46928.duanmau.models;
import java.util.Date;

public class PhieuMuon {
    private int maPM;
    private int maTV;
    private int maSach;
    private int tienThue;
    private Date ngay;
    private int traSach;
    private String gioMuon;

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }


    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }

    public PhieuMuon() {
    }

    public PhieuMuon(int maPM, int maTV, int maSach, int tienThue, Date ngay, int traSach,String gioMuon) {
        this.maPM = maPM;
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.ngay = ngay;
        this.traSach = traSach;
        this.gioMuon = gioMuon;
    }

    public String getGioMuon() {
        return gioMuon;
    }

    public void setGioMuon(String gioMuon) {
        this.gioMuon = gioMuon;
    }
}
