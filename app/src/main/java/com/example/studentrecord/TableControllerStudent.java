package com.example.studentrecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableControllerStudent extends DatabaseHandler {

    public TableControllerStudent(Context context) {
        super(context);
    }

    public boolean create(ObjectStudent objectStudent) {

        ContentValues values = new ContentValues();

        values.put("firstname", objectStudent.firstname);
        values.put("email", objectStudent.email);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("students", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM students";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }

    public List<ObjectStudent> read() {

        List<ObjectStudent> recordsList = new ArrayList<ObjectStudent>();

        String sql = "SELECT * FROM students ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String studentFirstname = cursor.getString(cursor.getColumnIndex("firstname"));
                String studentEmail = cursor.getString(cursor.getColumnIndex("email"));

                ObjectStudent objectStudent = new ObjectStudent();
                objectStudent.id = id;
                objectStudent.firstname = studentFirstname;
                objectStudent.email = studentEmail;

                recordsList.add(objectStudent);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public boolean update(ObjectStudent objectStudent) {

        ContentValues values = new ContentValues();

        values.put("firstname", objectStudent.firstname);
        values.put("email", objectStudent.email);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(objectStudent.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("students", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }
    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("students", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }
    public ObjectStudent readSingleRecord(int studentId) {

        ObjectStudent objectStudent = null;
        String sql = "SELECT * FROM students WHERE id = " + studentId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String firstname = cursor.getString(cursor.getColumnIndex("firstname"));
            String email = cursor.getString(cursor.getColumnIndex("email"));

            objectStudent = new ObjectStudent();
            objectStudent.id = id;
            objectStudent.firstname = firstname;
            objectStudent.email = email;

        }

        cursor.close();
        db.close();

        return objectStudent;

    }
}