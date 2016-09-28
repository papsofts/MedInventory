package com.example.medinventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableControllerProduct extends DatabaseHandler {

    public TableControllerProduct(Context context) {
        super(context);
    }

    public boolean create(ObjectProduct objectProduct) {

        ContentValues values = new ContentValues();

        values.put("productName", objectProduct.productName);
        values.put("productType", objectProduct.productType);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("products", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM products";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();
        return recordCount;

    }

    public List<ObjectProduct> read() {

        List<ObjectProduct> recordsList = new ArrayList<ObjectProduct>();

        String sql = "SELECT * FROM products ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String productName = cursor.getString(cursor.getColumnIndex("productName"));
                String productType = cursor.getString(cursor.getColumnIndex("productType"));

                ObjectProduct objectProduct = new ObjectProduct();
                objectProduct.id = id;
                objectProduct.productName = productName;
                objectProduct.productType = productType;

                recordsList.add(objectProduct);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public boolean update(ObjectProduct objectProduct) {

        ContentValues values = new ContentValues();

        values.put("productName", objectProduct.productName);
        values.put("productType", objectProduct.productType);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(objectProduct.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("products", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }
    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("products", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }
    public ObjectProduct readSingleRecord(int productId) {

        ObjectProduct objectProduct = null;
        String sql = "SELECT * FROM products WHERE id = " + productId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String productName = cursor.getString(cursor.getColumnIndex("productName"));
            String productType = cursor.getString(cursor.getColumnIndex("productType"));

            objectProduct = new ObjectProduct();
            objectProduct.id = id;
            objectProduct.productName = productName;
            objectProduct.productType = productType;

        }

        cursor.close();
        db.close();

        return objectProduct;

    }


}