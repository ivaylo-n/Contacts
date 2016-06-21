package com.example.koki.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends SQLiteOpenHelper {
    public StudentDAO(Context context) {
        super(context, "StudentContact", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE students (id INTEGER PRIMARY KEY,name TEXT NOT NULL, address TEXT,phoneNumber TEXT, website TEXT, email TEXT, grading REAL);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void insert(Student student) {
        ContentValues data  = student.toContentValues();
        SQLiteDatabase database = getWritableDatabase();
        database.insert("students",null,data);
    }

    public List<Student> listAll() {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM students;", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String phoneNumber = cursor.getString(cursor.getColumnIndex("phoneNumber"));
            String website = cursor.getString(cursor.getColumnIndex("website"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            double grading = cursor.getDouble(cursor.getColumnIndex("grading"));
            Student student = new Student(id,name,address,phoneNumber,website,email,grading);
            students.add(student);
        }
        return students;
    }

    public void remove(Student student) {
        SQLiteDatabase database = getWritableDatabase();
        String [] params = {student.getId() + ""};
        database.delete("students", "id = ?",params);
    }

    public void update(Student student, int OriginalId) {
        ContentValues data = student.toContentValues();
        SQLiteDatabase database = getWritableDatabase();
        String [] params = { OriginalId + ""};
        database.update("students", data, "id = ?", params);
    }
}
