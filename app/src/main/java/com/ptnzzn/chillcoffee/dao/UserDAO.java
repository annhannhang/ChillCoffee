package com.ptnzzn.chillcoffee.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ptnzzn.chillcoffee.database.DBHelper;

public class UserDAO {
    static DBHelper helper;
    static SQLiteDatabase db;

    public UserDAO(Context context) {
        helper = new DBHelper(context);
    }

    public static boolean checkLogin(String username, String password) {
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM USER WHERE username=? AND password=?";
        String[] args = {username, password};
        Cursor cs = db.rawQuery(sql, args);
        if (cs.getCount() <= 0) {
            cs.close();
            return false;
        }
        cs.close();
        db.close();
        return true;
    }

    public static boolean checkUser(String username, String password) {
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM USER WHERE username=?";
        String[] args = {username};
        Cursor cs = db.rawQuery(sql, args);
        if (cs.getCount() <= 0) {
            cs.close();
            return false;
        }
        cs.close();
        db.close();
        return true;
    }

    public static boolean checkUsername(String username) {
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM USER WHERE username=?";
        String[] args = {username};
        Cursor cs = db.rawQuery(sql, args);
        if (cs.getCount() <= 0) {
            cs.close();
            return false;
        }
        cs.close();
        db.close();
        return true;
    }

    public static boolean insert(String username, String password) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long row = db.insert("USER", null, values);
        db.close();
        return (row != -1);
    }

    public static boolean update(String username, String password) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long row = db.update("USER", values, "username=?", new String[]{username});
        db.close();
        return (row != -1);
    }
}
