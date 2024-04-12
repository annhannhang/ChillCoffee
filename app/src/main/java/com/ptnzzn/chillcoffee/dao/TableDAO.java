package com.ptnzzn.chillcoffee.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ptnzzn.chillcoffee.database.DBHelper;
import com.ptnzzn.chillcoffee.modal.Table;

import java.util.ArrayList;

public class TableDAO {
    static DBHelper helper;
    static SQLiteDatabase db;

    public TableDAO(Context context) {
        helper = new DBHelper(context);
    }

    public static ArrayList<Table> getAll() {
        ArrayList<Table> list = new ArrayList<>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM TABLES";
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            String name = cs.getString(1);
            String time = cs.getString(2);
            int price = cs.getInt(3);
            String status = cs.getString(4);
            Table tables = new Table(id,name,time,price,status);
            list.add(tables);
            cs.moveToNext();
        }
        return list;
    }

    public static boolean insert(Table item) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("time", item.getTime());
        values.put("price", item.getPrice());
        values.put("status", item.getStatus());
        int row = (int) db.insert("TABLES", null, values);
        return (row != -1);
    }

    public static boolean update(Table item) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("time", item.getTime());
        values.put("price", item.getPrice());
        values.put("status", item.getStatus());
        int row = db.update("TABLES", values, "id=?", new String[]{String.valueOf(item.getId())});
        db.close();
        return (row > 0);
    }

    public static boolean delete(int item) {
        db = helper.getWritableDatabase();
        int row = db.delete("TABLES", "id=?", new String[]{String.valueOf(item)});
        db.close();
        return (row > 0);
    }

    public static ArrayList<Table> search(String name) {
        ArrayList<Table> list = new ArrayList<>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM TABLES WHERE name LIKE '%" + name + "%'";
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            String name1 = cs.getString(1);
            String time = cs.getString(2);
            int price = cs.getInt(3);
            String status = cs.getString(4);
            Table tables = new Table(id,name1,time,price,status);
            list.add(tables);
            cs.moveToNext();
        }
        return list;
    }

    public static int countTable() {
        int count = 0;
        db = helper.getReadableDatabase();
        String sql = "SELECT COUNT(*) FROM TABLES";
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            count = cs.getInt(0);
            cs.moveToNext();
        }
        return count;
    }
}
