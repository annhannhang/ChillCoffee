package com.ptnzzn.chillcoffee.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "ChillCoffee", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE USER (id integer primary key autoincrement, username text, password text)";
        db.execSQL(sql);
        sql = "INSERT INTO USER VALUES (null, 'admin', 'admin')";
        db.execSQL(sql);

        sql = "CREATE TABLE TABLES (id integer primary key autoincrement, name text, time text, price integer, status text)";
        db.execSQL(sql);
        sql = "INSERT INTO TABLES VALUES (null, 'Bàn 1', '12:00', 27000, 'Trống')";
        db.execSQL(sql);
        sql = "INSERT INTO TABLES VALUES (null, 'Bàn 2', '13:00', 25000, 'Còn khách')";
        db.execSQL(sql);
        sql = "INSERT INTO TABLES VALUES (null, 'Bàn 3', '14:00', 20000, 'Trống')";
        db.execSQL(sql);

        sql = "CREATE TABLE PRODUCTS (id integer primary key autoincrement, name text, price integer, image text)";
        db.execSQL(sql);
        sql = "INSERT INTO PRODUCTS VALUES (null, 'Cà phê đen', 20000, 'https://product.hstatic.net/1000365253/product/the_coffee_club_vietnam_-_vietnamese_black_coffee_22a680c6f2394e3b81ee7a0a3ddc72b1_1024x1024.jpg')";
        db.execSQL(sql);
        sql = "INSERT INTO PRODUCTS VALUES (null, 'Cà phê sữa', 25000, 'https://vinafreshfruit.vn/wp-content/uploads/2022/06/1-ca-phe-sua-1586320543.jpg')";
        db.execSQL(sql);
        sql = "INSERT INTO PRODUCTS VALUES (null, 'Bạc xĩu', 27000, 'https://caphekhoanbetong.com/wp-content/uploads/2021/08/bac-xiu-ca-phe-da-giao-hang-online-10.jpg')";
        db.execSQL(sql);

        sql = "CREATE TABLE HR (id integer primary key autoincrement, name text, phone text, address text, image text)";
        db.execSQL(sql);
        sql = "INSERT INTO HR VALUES (null, 'Phan Trọng A', '0123456789', 'Đà Lạt', 'https://media.doisongphapluat.com/500/2016/12/13/trai%20dep%20nuoc%20anh%20dspl1.jpg')";
        db.execSQL(sql);
        sql = "INSERT INTO HR VALUES (null, 'Phan Trọng B', '0123456789', 'TP.HCM', 'https://static2.yan.vn/YanNews/2167221/201904/sau-3-nam-cau-be-dep-trai-nhat-the-gioi-gio-ra-sao-7bad161f.jpg')";
        db.execSQL(sql);
        sql = "INSERT INTO HR VALUES (null, 'Phan Trọng C', '0123456789', 'Lâm Đồng', 'https://pearlharbor75thanniversary.com/wp-content/uploads/2022/07/toi-pham-dep-trai-nhat-nuoc-my-01.jpg')";
        db.execSQL(sql);

        sql = "CREATE TABLE BILL (id integer primary key autoincrement, idtable text, idhr text, time text, total integer)";
        db.execSQL(sql);
        sql = "INSERT INTO BILL VALUES (null, 'Bàn 1', 'Phan Trọng A', '01:39', 35000)";
        db.execSQL(sql);
        sql = "INSERT INTO BILL VALUES (null, 'Bàn 2', 'Phan Trọng B', '01:39', 35000)";
        db.execSQL(sql);
        sql = "INSERT INTO BILL VALUES (null, 'Bàn 3', 'Phan Trọng C', '01:39', 35000)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
        db.execSQL("DROP TABLE IF EXISTS TABLES");
        db.execSQL("DROP TABLE IF EXISTS PRODUCTS");
        db.execSQL("DROP TABLE IF EXISTS HR");
    }
}
