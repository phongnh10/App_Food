package dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import data.base.DbHelper;
import model.User;

public class UserDAO {
    private static DbHelper dbHelper;
    public UserDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    //add user


    //update
    public boolean upDateUser(User user){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user", user.getUser());
        values.put("pass", user.getPass());
        values.put("name", user.getName());
        values.put("phone", user.getPhone());
        values.put("cccd", user.getCccd());
        values.put("role", user.getRole());

        int check = sqLiteDatabase.update("user",values,"idUser=?",new String[]{String.valueOf(user.getIdUser())});
        if (check <= 0) return false;
        return true;
    }

    public boolean upDateAddress(User user){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("phone", user.getPhone());
        values.put("address", user.getAddress());
        int check = sqLiteDatabase.update("user",values,"idUser=?",new String[]{String.valueOf(user.getIdUser())});
        if (check <= 0) return false;
        return true;
    }

    public boolean SuspendAccount(User user){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("role", user.getRole());

        int check = sqLiteDatabase.update("user",values,"idUser=?",new String[]{String.valueOf(user.getIdUser())});
        if (check <= 0) return false;
        return true;
    }

    //Login
    public int login(String user, String pass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE user = ? AND pass = ?", new String[]{user, pass});

        int check;
        if (cursor.getCount() > 0) {
            cursor.close();
            sqLiteDatabase.close();
            check = 0; // Login True
        } else {
            cursor.close();
            sqLiteDatabase.close();
            check = 1; // Login false
        }
        return check;
    }

    //get role
    public int getRole(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int role = -1;

        String[] columns = {"role"};
        String selection = "user = ? AND pass = ?";
        String[] selectionArgs = {user.getUser(), user.getPass()};

        Cursor cursor = db.query("user", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            role = cursor.getInt(cursor.getColumnIndexOrThrow("role"));
            cursor.close();
        }

        db.close();
        return role;
    }

    public int getIdShop(int idUser) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int idShop = -1;

        String[] columns = {"idShop"};
        String selection = "idUser = ?";
        String[] selectionArgs = { String.valueOf(idUser) };

        Cursor cursor = db.query("Shop", columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                idShop = cursor.getInt(cursor.getColumnIndexOrThrow("idShop"));
            }
            cursor.close();
        }

        db.close();
        return idShop;
    }
    //get id
    public User getUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        User userData = null;

        String[] columns = {"idUser", "user", "pass", "name", "phone", "cccd", "role","address"};
        String selection = "user = ? AND pass = ?";
        String[] selectionArgs = {user.getUser(), user.getPass()};

        Cursor cursor = db.query("user", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            userData = new User();
            userData.setIdUser(cursor.getInt(cursor.getColumnIndexOrThrow("idUser")));
            userData.setUser(cursor.getString(cursor.getColumnIndexOrThrow("user")));
            userData.setPass(cursor.getString(cursor.getColumnIndexOrThrow("pass")));
            userData.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            userData.setPhone(cursor.getLong(cursor.getColumnIndexOrThrow("phone")));
            userData.setCccd(cursor.getLong(cursor.getColumnIndexOrThrow("cccd")));
            userData.setRole(cursor.getInt(cursor.getColumnIndexOrThrow("role")));
            userData.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));

            cursor.close();
        }

        db.close();
        return userData;
    }
    public User getUserByID(int idUser) {
        User userData = null;

        // Sử dụng try-with-resources để tự động đóng tài nguyên
        try (SQLiteDatabase db = dbHelper.getWritableDatabase();
             Cursor cursor = db.query(
                     "user",
                     new String[]{"idUser", "user", "pass", "name", "phone", "cccd", "role", "address"},
                     "idUser = ?",
                     new String[]{String.valueOf(idUser)},
                     null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                userData = new User();
                userData.setIdUser(cursor.getInt(cursor.getColumnIndexOrThrow("idUser")));
                userData.setUser(cursor.getString(cursor.getColumnIndexOrThrow("user")));
                userData.setPass(cursor.getString(cursor.getColumnIndexOrThrow("pass")));
                userData.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                userData.setPhone(cursor.getLong(cursor.getColumnIndexOrThrow("phone")));
                userData.setCccd(cursor.getLong(cursor.getColumnIndexOrThrow("cccd")));
                userData.setRole(cursor.getInt(cursor.getColumnIndexOrThrow("role")));
                userData.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userData;
    }



    public ArrayList<User> getListUser() {
        ArrayList<User> userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT idUser, user, pass, name, phone,cccd,role,address FROM User", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                userList.add(new User(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getLong(5),
                        cursor.getInt(6),
                        cursor.getString(7)));
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return userList;
    }





}
