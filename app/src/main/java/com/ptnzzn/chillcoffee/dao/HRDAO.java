package com.ptnzzn.chillcoffee.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ptnzzn.chillcoffee.database.DBHelper;
import com.ptnzzn.chillcoffee.modal.HR;

import java.util.ArrayList;

public class HRDAO {
    static DBHelper helper;
    static SQLiteDatabase db;

    public HRDAO(Context context) {
        helper = new DBHelper(context);
    }

    public static ArrayList<HR> getAll() {
        ArrayList<HR> list = new ArrayList<>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM HR";
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            String name = cs.getString(1);
            String phone = cs.getString(2);
            String address = cs.getString(3);
            String image = cs.getString(4);
            HR hr = new HR(id,name,phone,address,image);
            list.add(hr);
            cs.moveToNext();
        }
        db.close();
        return list;
    }

    public static boolean insert(HR item) {
        db = helper.getWritableDatabase();
        String sql = "INSERT INTO HR VALUES(null,?,?,?,?)";
        db.execSQL(sql, new Object[]{item.getName(), item.getPhone(), item.getAddress(), item.getImage()});
        db.close();
        return true;
    }

    public static boolean update(HR item) {
        db = helper.getWritableDatabase();
        String sql = "UPDATE HR SET name=?, phone=?, address=?, image=? WHERE id=?";
        db.execSQL(sql, new Object[]{item.getName(), item.getPhone(), item.getAddress(), item.getImage(), item.getId()});
        db.close();
        return true;
    }

    public static boolean delete(int id) {
        db = helper.getWritableDatabase();
        String sql = "DELETE FROM HR WHERE id=?";
        db.execSQL(sql, new Object[]{id});
        db.close();
        return true;
    }

    public static ArrayList<HR> search(String name) {
        ArrayList<HR> list = new ArrayList<>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM HR WHERE name LIKE '%" + name + "%'";
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            String name1 = cs.getString(1);
            String phone = cs.getString(2);
            String address = cs.getString(3);
            String image = cs.getString(4);
            HR hr = new HR(id,name1,phone,address,image);
            list.add(hr);
            cs.moveToNext();
        }
        db.close();
        return list;
    }

    public static int countHR() {
        int count = 0;
        db = helper.getReadableDatabase();
        String sql = "SELECT COUNT(*) FROM HR";
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            count = cs.getInt(0);
            cs.moveToNext();
        }
        db.close();
        return count;
    }
}
