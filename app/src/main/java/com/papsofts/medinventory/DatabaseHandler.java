package com.papsofts.medinventory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ProductDatabase";

    DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CreateProductsTable(db);
        CreatePurchaseTable(db);
        CreateSoldTable(db);

    }

    private void CreateSoldTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE dispense " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "productId INTEGER, " +
                "productName TEXT, " +
                "quantity INTEGER, " +
                "price INTEGER, " +
                "patient TEXT, " +
                "dateDispensed TEXT)";

        db.execSQL(sql);
    }

    private void CreatePurchaseTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE purchase " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "productId INTEGER, " +
                "productName TEXT, " +
                "quantity INTEGER, " +
                "price INTEGER, " +
                "dealer TEXT, " +
                "datePurchased TEXT)";

        db.execSQL(sql);
    }


    private void CreateProductsTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE products " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "productName TEXT, " +
                "productType TEXT, " +
                "currentStock INTEGER )";

        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS products";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS purchase";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS sell";
        db.execSQL(sql);
        onCreate(db);
    }

}
