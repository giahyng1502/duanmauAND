package fpoly.hunghvph46928.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.hunghvph46928.duanmau.database.DbHelper;
import fpoly.hunghvph46928.duanmau.models.Sach;

public class DAOSach {
    private DbHelper dbHelper;
    private SQLiteDatabase database;

    public DAOSach(Context context) {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    public long insert (Sach obj) {
        ContentValues values = new ContentValues();
        values.put("tenSach",obj.getTenSach());
        values.put("giaThue",obj.getGiaThue());
        values.put("maLoai",obj.getMaLoai());
        return database.insert("Sach",null,values);
    }
    public long update (Sach obj) {
        ContentValues values = new ContentValues();
        values.put("tenSach",obj.getTenSach());
        values.put("giaThue",obj.getGiaThue());
        values.put("maLoai",obj.getMaLoai());
        return database.update("Sach",values,"maSach = ?",new String[]{obj.getMaSach()+""});
    }
    public long delete(int maSach) {
        return database.delete("Sach","maSach = ?",new String[]{maSach+""});
    }
    public List<Sach> getData(String sql, String...selectionArgs) {
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql,selectionArgs);
        List<Sach> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Sach obj = new Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3));
            list.add(obj);
        }
        cursor.close();
        return list;
    }
    public List<Sach> getAll() {
        String sql = "SELECT * FROM Sach";
        return getData(sql);
    }
    public Sach getID(int maSach) {
        String sql = "SELECT * FROM Sach WHERE maSach = ?";
        List<Sach> list = getData(sql,maSach+"");
            return list.get(0);
    }
    public boolean checkLoaiSach(int maLoai) {
        String sql = "SELECT * FROM Sach WHERE maLoai = ?";
        List<Sach> list = getData(sql,maLoai+"");
        if (list.size() > 0 ) {
            return false;
        }
        return true;
    }
}
