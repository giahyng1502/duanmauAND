package fpoly.hunghvph46928.duanmau.models;

public class ThanhVien {
    private int ID;
    private String hoTen;
    private String namSinh;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }

    public ThanhVien() {
    }

    public ThanhVien(int ID, String hoTen, String namSinh) {
        this.ID = ID;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
    }
}
