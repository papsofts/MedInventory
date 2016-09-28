package com.example.medinventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableControllerPurchased extends DatabaseHandler {

    public TableControllerPurchased(Context context) {
        super(context);
    }

    public boolean create(ObjectPurchased objectPurchased) {

        ContentValues values = new ContentValues();
        values.put("productId", objectPurchased.productId);
        values.put("productName", objectPurchased.productName);
        values.put("quantity", objectPurchased.quantity);
        values.put("price", objectPurchased.price);
        values.put("dealer", objectPurchased.dealer);
        values.put("datePurchased", objectPurchased.datePurchased);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("purchase", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM purchase";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }

    public List<ObjectPurchased> read() {

        List<ObjectPurchased> recordsList = new ArrayList<>();

        String sql = "SELECT * FROM purchase ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                int productId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("productId")));
                String productName = cursor.getString(cursor.getColumnIndex("productName"));
                int quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantity")));
                int price = Integer.parseInt(cursor.getString(cursor.getColumnIndex("price")));
                String dealer = cursor.getString(cursor.getColumnIndex("dealer"));
                String datePurchased = cursor.getString(cursor.getColumnIndex("datePurchased"));

                ObjectPurchased objectPurchased = new ObjectPurchased();
                objectPurchased.id = id;
                objectPurchased.productId = productId;
                objectPurchased.productName = productName;
                objectPurchased.quantity=quantity;
                objectPurchased.price=price;
                objectPurchased.dealer=dealer;
                objectPurchased.datePurchased=datePurchased;

                recordsList.add(objectPurchased);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public boolean update(ObjectPurchased objectPurchased) {

        ContentValues values = new ContentValues();

        values.put("productId", objectPurchased.productId);
        values.put("productName", objectPurchased.productName);
        values.put("quantity", objectPurchased.quantity);
        values.put("price", objectPurchased.price);
        values.put("dealer", objectPurchased.dealer);
        values.put("datePurchased", objectPurchased.datePurchased);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(objectPurchased.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("purchase", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }

    //Not to be accessed for data integrity purpose
    public boolean delete(int id) {

        //SQLiteDatabase db = this.getWritableDatabase();
        //deleteSuccessful = db.delete("purchase", "id ='" + id + "'", null) > 0;
        //db.close();

        return false;

    }
    public ObjectPurchased readSingleRecord(int productId) {

        ObjectPurchased objectPurchased = null;
        String sql = "SELECT * FROM purchase WHERE id = " + productId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            //int productId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("productId")));
            String productName = cursor.getString(cursor.getColumnIndex("productName"));
            int quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantity")));
            int price = Integer.parseInt(cursor.getString(cursor.getColumnIndex("price")));
            String dealer = cursor.getString(cursor.getColumnIndex("dealer"));
            String datePurchased = cursor.getString(cursor.getColumnIndex("datePurchased"));

            objectPurchased = new ObjectPurchased();
            objectPurchased.id = id;
            objectPurchased.productId = productId;
            objectPurchased.productName = productName;
            objectPurchased.quantity=quantity;
            objectPurchased.price=price;
            objectPurchased.dealer=dealer;
            objectPurchased.datePurchased=datePurchased;

        }

        cursor.close();
        db.close();

        return objectPurchased;

    }
}