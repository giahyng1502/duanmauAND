package fpoly.hunghvph46928.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.hunghvph46928.duanmau.database.DbHelper;
import fpoly.hunghvph46928.duanmau.models.ThuThu;

public class DAOThuThu {
    private DbHelper dbHelper;
    private SQLiteDatabase database;

    public DAOThuThu(Context context) {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    public long insert (ThuThu obj) {
        ContentValues values = new ContentValues();
        values.put("hoTen",obj.getHoTen());
        values.put("maTT",obj.getMaTT());
        values.put("matKhau",obj.getMatKhau());
        return database.insert("ThuThu",null,values);
    }
    public long update (ThuThu obj) {
        ContentValues values = new ContentValues();
        values.put("hoTen",obj.getHoTen());
        values.put("matKhau",obj.getMatKhau());
        return database.update("ThuThu",values,"maTT = ?",new String[]{obj.getMaTT()+""});
    }
    public long updatePass (String maTT,String passNew) {
        ContentValues values = new ContentValues();
        values.put("matKhau",passNew);
        return database.update("ThuThu",values,"maTT = ?",new String[]{maTT});
    }
    public long delete(String maTT) {
        return database.delete("ThuThu","maTT = ?",new String[]{maTT});
    }
    public List<ThuThu> getData(String sql, String...selectionArgs) {
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql,selectionArgs);
        List<ThuThu> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            ThuThu obj = new ThuThu(cursor.getString(0),cursor.getString(1),cursor.getString(2));
            list.add(obj);
        }
        cursor.close();
        return list;
    }
    public List<ThuThu> getAll() {
        String sql = "SELECT * FROM ThuThu";
        return getData(sql);
    }
    public ThuThu getID(String maTT) {
        String sql = "SELECT * FROM ThuThu WHERE maTT = ?";
        List<ThuThu> list = getData(sql,maTT);
        return list.get(0);
    }
    public boolean checkLogin (String id,String password) {
        String sql = "SELECT * FROM ThuThu WHERE maTT = ? AND matKhau = ?";
        List<ThuThu> list = getData(sql,id,password);
        if (list.size() == 0) {
            return false;
        }
        return true;
    }
}
