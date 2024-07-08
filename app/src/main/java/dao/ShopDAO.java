package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import data.base.DbHelper;
import model.Shop;
import model.User;

public class ShopDAO {
    private DbHelper dbHelper;

    public ShopDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Shop> getListShopNew() {
        ArrayList<Shop> shopList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT Shop.idShop, Shop.idUser, Shop.name, Shop.address " +
                "FROM Shop JOIN User ON Shop.idUser = User.idUser " +
                "WHERE User.role = 2", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                shopList.add(new Shop(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3)));
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return shopList;
    }

    public ArrayList<Shop> getListShop() {
        ArrayList<Shop> shopList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT idShop, idUser, name, address FROM Shop", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                shopList.add(new Shop(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3)));
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return shopList;
    }

    public boolean deleteShop(int idShop) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int check = sqLiteDatabase.delete("shop", "idShop=?", new String[]{String.valueOf(idShop)});

        if (check <= 0) return false;
        return true;

    }

    public int addShop(int idUser, String name, String address) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Shop where idUser =?", new String[]{String.valueOf(idUser)});

        long check;
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return 0;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("idUser", idUser);
            contentValues.put("name", name);
            contentValues.put("address", address);

            check = db.insert("Shop", null, contentValues);
        }
        cursor.close();
        db.close();
        return (check == -1) ? -1 : 1;
    }
    public Shop getShopByID(int idUser) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Shop shop1 = null;

        String[] columns = {"name", "address"};
        String selection = "idUser = ?";
        String[] selectionArgs = { String.valueOf(idUser) };

        Cursor cursor = db.query("shop", columns, selection, selectionArgs, null, null, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                shop1 = new Shop();
                shop1.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                shop1.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return shop1;
    }


    public Shop getNameShop(int idShop) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Shop shop1 = null;

        try {
            db = dbHelper.getWritableDatabase();

            String[] columns = {"name"};
            String selection = "idShop = ?";
            String[] selectionArgs = {String.valueOf(idShop)};

            cursor = db.query("shop", columns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                shop1 = new Shop();
                shop1.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return shop1;
    }
}
