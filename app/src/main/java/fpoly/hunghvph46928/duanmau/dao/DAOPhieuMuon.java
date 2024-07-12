package fpoly.hunghvph46928.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fpoly.hunghvph46928.duanmau.database.DbHelper;
import fpoly.hunghvph46928.duanmau.models.PhieuMuon;
import fpoly.hunghvph46928.duanmau.models.Sach;
import fpoly.hunghvph46928.duanmau.models.ThanhVien;
import fpoly.hunghvph46928.duanmau.models.Top;

public class DAOPhieuMuon {
    private DbHelper dbHelper;
    private SQLiteDatabase database;
    Context context;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public DAOPhieuMuon(Context context) {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
        this.context = context;
    }
    public long insert (PhieuMuon obj) {
        ContentValues values = new ContentValues();
        values.put("ID",obj.getMaTV());
        values.put("maSach",obj.getMaSach());
        values.put("tienThue",obj.getTienThue());
        values.put("Ngay",simpleDateFormat.format(obj.getNgay()));
        values.put("traSach",obj.getTraSach());
        values.put("gioMuon",obj.getGioMuon());
        return database.insert("PhieuMuon",null,values);
    }
    public long update (PhieuMuon obj) {
        ContentValues values = new ContentValues();
        values.put("ID",obj.getMaTV());
        values.put("maSach",obj.getMaSach());
        values.put("tienThue",obj.getTienThue());
        values.put("Ngay",simpleDateFormat.format(obj.getNgay()));
        values.put("traSach",obj.getTraSach());
        values.put("gioMuon",obj.getGioMuon());
        return database.update("PhieuMuon",values,"maPM = ?",new String[]{obj.getMaPM()+""});
    }
    public long delete(int maPM) {
        return database.delete("PhieuMuon","maPM = ?",new String[]{maPM+""});
    }
    public List<PhieuMuon> getData(String sql, String...selectionArgs) {
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql,selectionArgs);
        List<PhieuMuon> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            PhieuMuon p = new PhieuMuon();
            p.setMaPM(cursor.getInt(0));
            p.setMaTV(cursor.getInt(2));
            p.setMaSach(cursor.getInt(3));
            p.setTienThue(cursor.getInt(4));
            try {
                p.setNgay((Date) simpleDateFormat.parse(cursor.getString(5)));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            p.setTraSach(cursor.getInt(6));
            p.setGioMuon(cursor.getString(7));
            list.add(p);
        }
        cursor.close();
        return list;
    }
    public List<PhieuMuon> getAll() {
        String sql = "SELECT * FROM PhieuMuon";
        return getData(sql);
    }
    public PhieuMuon getID(int maPM) {
        String sql = "SELECT * FROM PhieuMuon WHERE maPM = ?";
        List<PhieuMuon> list = getData(sql,maPM+"");
        return list.get(0);
    }
    public List<Top> getTop10() {
        database = dbHelper.getReadableDatabase();
        List<Top> list = new ArrayList<>();
        DAOSach daoSach = new DAOSach(context);
        Cursor cursor = database.rawQuery("SELECT maSach,COUNT(maSach) as soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10",null);
        while (cursor.moveToNext()) {
            Sach sach = daoSach.getID(cursor.getInt(0));
            Top top = new Top(sach.getTenSach(),cursor.getInt(1));
            list.add(top);
        }
        return list;
    }
    public int getDoanhThu(String tuNgay , String denNgay) {
        database = dbHelper.getReadableDatabase();
        int doanhThu = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT SUM(tienThue) AS doanhThu FROM PhieuMuon WHERE Ngay BETWEEN ? AND ?",new String[]{tuNgay,denNgay});
            if (cursor != null && cursor.moveToFirst()) {
                doanhThu = cursor.getInt(0);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return doanhThu;
    }
    public boolean checkThanhVien(int ID) {
        String sql = "SELECT * FROM PhieuMuon WHERE ID = ?";
        List<PhieuMuon> list = getData(sql,ID+"");
        if (list.size() > 0 ) {
            return false;
        }
        return true;
    }
    public boolean checkSach(int maSach) {
        String sql = "SELECT * FROM PhieuMuon WHERE maSach = ?";
        List<PhieuMuon> list = getData(sql,maSach+"");
        if (list.size() > 0 ) {
            return false;
        }
        return true;
    }
}
