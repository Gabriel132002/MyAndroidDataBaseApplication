package com.example.mydatabaseapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class UserDAO extends SQLiteOpenHelper {

    private static final int versionDB = 1;
    private static final String nameDB = "db_user";

    private static final String tbl_user = "user";
    private static final String c_cod = "cod";
    private static final String c_name = "name";
    private static final String c_email = "email";
    private static final String c_phone = "phone";
    public Context cont;

    public UserDAO(@Nullable Context context) {
        super(context, nameDB, null, versionDB);
        cont = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE " + tbl_user + "(" +
                c_cod + " INTEGER PRIMARY KEY, " +
                c_name + " TEXT, " +
                c_email + " TEXT, " +
                c_phone + " TEXT)";

        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addUser(User u) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(c_name, u.getName());
        cv.put(c_email, u.getEmail());
        cv.put(c_phone, u.getCellPhone());

        db.insert(tbl_user, null, cv);
        db.close();

    }

    public void deleteUser(User u) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tbl_user, c_cod + " = ?", new String[]{String.valueOf(u.getCod())});
        db.close();
    }

    public User getUser(int cod) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(tbl_user,
                new String[]{c_cod, c_name, c_email, c_phone},
                c_cod + " = ?", new String[]{String.valueOf(cod)},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        return new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));

    }

    public List<User> getUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<User> u = new ArrayList<User>();

        String query = "SELECT * FROM " + tbl_user;
        Cursor cursor;
        cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                User user;
                user = new User(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));
                u.add(user);
            } while (cursor.moveToNext());
        }
        return u;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(c_name, user.getName());
        contentValues.put(c_email, user.getEmail());
        contentValues.put(c_phone, user.getCellPhone());
        db.update(tbl_user, contentValues, c_cod + " = ?", new String[]{String.valueOf(user.getCod())});
        db.close();
    }
}
