package fpoly.hunghvph46928.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.hunghvph46928.duanmau.database.DbHelper;
import fpoly.hunghvph46928.duanmau.models.LoaiSach;
import fpoly.hunghvph46928.duanmau.models.ThuThu;

public class DAOLoaiSach {
    private DbHelper dbHelper;
    private SQLiteDatabase database;

    public DAOLoaiSach(Context context) {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    public long insert (LoaiSach obj) {
        ContentValues values = new ContentValues();
        values.put("tenLoai",obj.getTenLoai());
        return database.insert("LoaiSach",null,values);
    }
    public long update (LoaiSach obj) {
        ContentValues values = new ContentValues();
        values.put("tenLoai",obj.getTenLoai());
        return database.update("LoaiSach",values,"maLoai = ?",new String[]{obj.getMaLoai()+""});
    }
    public long delete(int maLoai) {
        return database.delete("LoaiSach","maLoai = ?",new String[]{maLoai+""});
    }
    public List<LoaiSach> getData(String sql, String...selectionArgs) {
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql,selectionArgs);
        List<LoaiSach> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            LoaiSach obj = new LoaiSach(cursor.getInt(0),cursor.getString(1));
            list.add(obj);
        }
        cursor.close();
        return list;
    }
    public List<LoaiSach> getAll() {
        String sql = "SELECT * FROM LoaiSach";
        return getData(sql);
    }
    public LoaiSach getID(int maTT) {
        String sql = "SELECT * FROM LoaiSach WHERE maLoai = ?";
        List<LoaiSach> list = getData(sql,maTT+"");
        return list.get(0);
    }
}
