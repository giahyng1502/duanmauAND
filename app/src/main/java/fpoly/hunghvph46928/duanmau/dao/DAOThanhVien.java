package fpoly.hunghvph46928.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.hunghvph46928.duanmau.database.DbHelper;
import fpoly.hunghvph46928.duanmau.models.Sach;
import fpoly.hunghvph46928.duanmau.models.ThanhVien;

public class DAOThanhVien {
    private DbHelper dbHelper;
    private SQLiteDatabase database;

    public DAOThanhVien(Context context) {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    public long insert (ThanhVien obj) {
        ContentValues values = new ContentValues();
        values.put("hoTen",obj.getHoTen());
        values.put("namSinh",obj.getNamSinh());
        return database.insert("ThanhVien",null,values);
    }
    public long update (ThanhVien obj) {
        ContentValues values = new ContentValues();
        values.put("hoTen",obj.getHoTen());
        values.put("namSinh",obj.getNamSinh());
        return database.update("ThanhVien",values,"ID = ?",new String[]{obj.getID()+""});
    }
    public long delete(int ID) {
        return database.delete("ThanhVien","ID = ?",new String[]{ID+""});
    }
    public List<ThanhVien> getData(String sql, String...selectionArgs) {
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql,selectionArgs);
        List<ThanhVien> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            ThanhVien obj = new ThanhVien(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
            list.add(obj);
        }
        cursor.close();
        return list;
    }
    public List<ThanhVien> getAll() {
        String sql = "SELECT * FROM ThanhVien";
        return getData(sql);
    }
    public ThanhVien getID(int ID) {
        String sql = "SELECT * FROM ThanhVien WHERE ID = ?";
        List<ThanhVien> list = getData(sql,ID+"");
        return list.get(0);
    }
}
