package com.example.medinventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TableControllerDispensed extends DatabaseHandler {

    public TableControllerDispensed(Context context) {
        super(context);
    }

    public boolean create(ObjectDispensed objectDispensed) {

        ContentValues values = new ContentValues();
        values.put("productId", objectDispensed.productId);
        values.put("productName", objectDispensed.productName);
        values.put("quantity", objectDispensed.quantity);
        values.put("price", objectDispensed.price);
        values.put("patient", objectDispensed.patient);
        values.put("dateDispensed", objectDispensed.dateDispensed);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("dispense", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM dispense";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }

    public List<ObjectDispensed> read() {

        List<ObjectDispensed> recordsList = new ArrayList<>();

        String sql = "SELECT * FROM dispense ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                int productId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("productId")));
                String productName = cursor.getString(cursor.getColumnIndex("productName"));
                int quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantity")));
                int price = Integer.parseInt(cursor.getString(cursor.getColumnIndex("price")));
                String patient = cursor.getString(cursor.getColumnIndex("patient"));
                String dateDispensed = cursor.getString(cursor.getColumnIndex("dateDispensed"));

                ObjectDispensed objectDispensed = new ObjectDispensed();
                objectDispensed.id = id;
                objectDispensed.productId = productId;
                objectDispensed.productName = productName;
                objectDispensed.quantity=quantity;
                objectDispensed.price=price;
                objectDispensed.patient=patient;
                objectDispensed.dateDispensed=dateDispensed;

                recordsList.add(objectDispensed);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public boolean update(ObjectDispensed objectDispensed) {

        ContentValues values = new ContentValues();

        values.put("productId", objectDispensed.productId);
        values.put("productName", objectDispensed.productName);
        values.put("quantity", objectDispensed.quantity);
        values.put("price", objectDispensed.price);
        values.put("patient", objectDispensed.patient);
        values.put("dateDispensed", objectDispensed.dateDispensed);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(objectDispensed.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("dispense", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }

    //Not to be accessed for data integrity purpose
    public boolean delete(int id) {

        //SQLiteDatabase db = this.getWritableDatabase();
        //deleteSuccessful = db.delete("dispense", "id ='" + id + "'", null) > 0;
        //db.close();

        return false;

    }
    public ObjectDispensed readSingleRecord(int productId) {

        ObjectDispensed objectDispensed = null;
        String sql = "SELECT * FROM dispense WHERE id = " + productId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            //int productId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("productId")));
            String productName = cursor.getString(cursor.getColumnIndex("productName"));
            int quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantity")));
            int price = Integer.parseInt(cursor.getString(cursor.getColumnIndex("price")));
            String patient = cursor.getString(cursor.getColumnIndex("patient"));
            String dateDispensed = cursor.getString(cursor.getColumnIndex("dateDispensed"));

            objectDispensed = new ObjectDispensed();
            objectDispensed.id = id;
            objectDispensed.productId = productId;
            objectDispensed.productName = productName;
            objectDispensed.quantity=quantity;
            objectDispensed.price=price;
            objectDispensed.patient=patient;
            objectDispensed.dateDispensed=dateDispensed;

        }

        cursor.close();
        db.close();

        return objectDispensed;

    }

    public int readProductCount(int productId){

        String sql = "SELECT sum(quantity) AS Total FROM dispense WHERE id = " + productId;
        int Total=0;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            String Tot=cursor.getString(cursor.getColumnIndex("Total"));
            if (Tot!=null) Total = Integer.parseInt(Tot);
        }

        cursor.close();
        db.close();

        return Total;

    }
}