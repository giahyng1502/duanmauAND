package fpoly.hunghvph46928.duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ThuVien";
    private static final int VERSION =1;
    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }
    private static final String CREATE_TABLE_THUTHU =
            "CREATE TABLE ThuThu(maTT TEXT PRIMARY KEY ," +
                    "hoTen TEXT NOT NULL," +
                    "matKhau TEXT NOT NULL);";
    private static final String CREATE_TABLE_THANHVIEN =
            "CREATE TABLE ThanhVien(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "hoTen TEXT NOT NULL," +
                    "namSinh TEXT NOT NULL);";
    private static final String CREATE_TABLE_LOAISACH =
            "CREATE TABLE LoaiSach(maLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tenLoai TEXT NOT NULL);";
    private static final String CREATE_TABLE_SACH =
            "CREATE TABLE Sach(maSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tenSach TEXT NOT NULL," +
                    "giaThue INTEGER NOT NULL," +
                    "maLoai INTEGER REFERENCES LoaiSach(maLoai));";
    private static final String CREATE_TABLE_PHIEUMUON =
            "CREATE TABLE PhieuMuon(maPM INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "maTT TEXT REFERENCES ThuThu(maTT)," +
                    "ID INTEGER REFERENCES ThanhVien(ID)," +
                    "maSach INTEGER REFERENCES Sach(maSach)," +
                    "tienThue INTERGER NOT NULL," +
                    "Ngay DATE NOT NULL," +
                    "traSach INTEGER NOT NULL);";
    private static final String AlterPhieuMuon = "ALTER TABLE PhieuMuon ADD COLUMN gioMuon TEXT;";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_THUTHU);
        db.execSQL(CREATE_TABLE_THANHVIEN);
        db.execSQL(CREATE_TABLE_LOAISACH);
        db.execSQL(CREATE_TABLE_SACH);
        db.execSQL(CREATE_TABLE_PHIEUMUON);
        db.execSQL(AlterPhieuMuon);
        db.execSQL(INSERT_DATA_LOAISACH);
        db.execSQL(INSERT_DATA_PHIEUMUON);
        db.execSQL(INSERT_DATA_SACH);
        db.execSQL(INSERT_DATA_THANHVIEN);
        db.execSQL(INSERT_DATA_THUTHU);

    }
    // Bảng ThuThu
    private static final String INSERT_DATA_THUTHU =
            "INSERT INTO ThuThu (maTT, hoTen, matKhau) VALUES " +
                    "('TT001', 'Nguyen Van A', '123'), " +
                    "('TT002', 'Tran Thi B', '123'), " +
                    "('TT003', 'Le Van C', '123'), " +
                    "('TT004', 'Pham Thi D', '123'), " +
                    "('admin', 'Hoang Van E', 'admin');";

//     Bảng ThanhVien
    private static final String INSERT_DATA_THANHVIEN =
            "INSERT INTO ThanhVien (hoTen, namSinh) VALUES " +
                    "('Hoàng Văn Hưng', '2002'), " +
                    "('Nguyễn Xuân Quan', '2002'), " +
                    "('Bùi Thế Khải', '2002'), " +
                    "('Ngô Trung Kiên', '2000'), " +
                    "('Phùng Đại Dương', '2004');";

    // Bảng LoaiSach
    private static final String INSERT_DATA_LOAISACH =
            "INSERT INTO LoaiSach (tenLoai) VALUES " +
                    "('Loai 1'), " +
                    "('Loai 2'), " +
                    "('Loai 3'), " +
                    "('Loai 4'), " +
                    "('Loai 5');";

    // Bảng Sach
    private static final String INSERT_DATA_SACH =
            "INSERT INTO Sach (tenSach, giaThue, maLoai) VALUES " +
                    "('Sach 1', 1000, 1), " +
                    "('Sach 2', 1500, 2), " +
                    "('Sach 3', 2000, 3), " +
                    "('Sach 4', 2500, 4), " +
                    "('Sach 5', 3000, 5);";

    // Bảng PhieuMuon
    private static final String INSERT_DATA_PHIEUMUON =
            "INSERT INTO PhieuMuon (maTT, ID, maSach, tienThue, Ngay, traSach,gioMuon) VALUES " +
                    "('TT001', 1, 1, 1000, '2023-05-01', 0,'1'), " +
                    "('TT002', 2, 2, 1500, '2023-05-02', 1,'2'), " +
                    "('TT003', 3, 3, 2000, '2023-05-03', 0,'3'), " +
                    "('TT004', 4, 4, 2500, '2023-05-04', 1,'4'), " +
                    "('TT005', 5, 5, 3000, '2023-05-05', 0,'5');";


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ThuThu");
        db.execSQL("DROP TABLE IF EXISTS ThanhVien");
        db.execSQL("DROP TABLE IF EXISTS Sach");
        db.execSQL("DROP TABLE IF EXISTS LoaiSach");
        db.execSQL("DROP TABLE IF EXISTS PhieuMuon");
        onCreate(db);

    }
}
