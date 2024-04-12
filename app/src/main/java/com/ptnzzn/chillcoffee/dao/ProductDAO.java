package com.ptnzzn.chillcoffee.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ptnzzn.chillcoffee.database.DBHelper;
import com.ptnzzn.chillcoffee.modal.Product;

import java.util.ArrayList;

public class ProductDAO {
    static DBHelper helper;
    static SQLiteDatabase db;

    public ProductDAO(Context context) {
        helper = new DBHelper(context);
    }

    public static ArrayList<Product> getAll() {
        ArrayList<Product> list = new ArrayList<>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM PRODUCTS";
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            String name = cs.getString(1);
            int price = cs.getInt(2);
            String image = cs.getString(3);
            Product products = new Product(id,name,price,image);
            list.add(products);
            cs.moveToNext();
        }
        db.close();
        return list;
    }

    public static boolean insert(Product item) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("price", item.getPrice());
        values.put("image", item.getImage());
        int row = (int) db.insert("PRODUCTS", null, values);
        return (row != -1);
    }

    public static boolean update(Product item) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("price", item.getPrice());
        values.put("image", item.getImage());
        int row = db.update("PRODUCTS", values, "id=?", new String[]{String.valueOf(item.getId())});
        db.close();
        return (row > 0);
    }

    public static boolean delete(int item) {
        db = helper.getWritableDatabase();
        int row = db.delete("PRODUCTS", "id=?", new String[]{String.valueOf(item)});
        db.close();
        return (row > 0);
    }

    public static ArrayList<Product> search(String name) {
        ArrayList<Product> list = new ArrayList<>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM PRODUCTS WHERE name LIKE '%" + name + "%'";
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            String name1 = cs.getString(1);
            int price = cs.getInt(2);
            String image = cs.getString(3);
            Product products = new Product(id,name1,price,image);
            list.add(products);
            cs.moveToNext();
        }
        return list;
    }

    public static int countProduct() {
        int count = 0;
        db = helper.getReadableDatabase();
        String sql = "SELECT COUNT(*) FROM PRODUCTS";
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            count = cs.getInt(0);
            cs.moveToNext();
        }
        return count;
    }
}
