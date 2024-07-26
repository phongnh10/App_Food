package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import data.base.DbHelper;
import model.Shop;

public class ShopDAO {
    private DbHelper dbHelper;

    public ShopDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<Shop> getAllShopsNew() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Shop> shopList = new ArrayList<>();

        String query = "SELECT shop.idShop, shop.idUser, shop.name, shop.address, shop.image, shop.status " + "FROM shop " + "INNER JOIN user ON shop.idUser = user.idUser " + "WHERE shop.status = 1 AND user.role = 2";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idShop = cursor.getInt(cursor.getColumnIndexOrThrow("idShop"));
                int idUser = cursor.getInt(cursor.getColumnIndexOrThrow("idUser"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
                int status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));

                Shop shop = new Shop(idShop, idUser, name, address, image, status);
                shopList.add(shop);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return shopList;
    }


    public List<Shop> getAllShops() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Shop> shopList = new ArrayList<>();

        String[] columns = {"idShop", "idUser", "name", "address", "image", "status"};

        Cursor cursor = db.query("shop", columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idShop = cursor.getInt(cursor.getColumnIndexOrThrow("idShop"));
                int idUser = cursor.getInt(cursor.getColumnIndexOrThrow("idUser"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
                int status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));

                Shop shop = new Shop(idShop, idUser, name, address, image, status);
                shopList.add(shop);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return shopList;
    }

    public int addShop(int idUser, String name, String address, byte[] image, int status) {
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
            contentValues.put("image", image);
            contentValues.put("status", status);

            check = db.insert("Shop", null, contentValues);
        }
        cursor.close();
        db.close();
        return (check == -1) ? -1 : 1;
    }

    public Shop getShopByIdUser(int idUser) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Shop shop = null;

        String[] columns = {"idShop", "idUser", "name", "address", "image", "status"};
        String selection = "idUser = ?";
        String[] selectionArgs = {String.valueOf(idUser)};

        Cursor cursor = db.query("shop", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idShop = cursor.getInt(cursor.getColumnIndexOrThrow("idShop"));
            int idUserFromDB = cursor.getInt(cursor.getColumnIndexOrThrow("idUser"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
            int status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));

            shop = new Shop(idShop, idUserFromDB, name, address, image, status);
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return shop;
    }


    public Shop getShopByIdShop(int idShop) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Shop shop = null;

        db = dbHelper.getWritableDatabase();

        String[] columns = {"idShop", "idUser", "address", "image", "status", "name"};
        String selection = "idShop = ?";
        String[] selectionArgs = {String.valueOf(idShop)};

        cursor = db.query("shop", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idUserFromDB = cursor.getInt(cursor.getColumnIndexOrThrow("idUser"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
            int status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));

            shop = new Shop(idShop, idUserFromDB, name, address, image, status);
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return shop;
    }

    public boolean SuspendShop(Shop shop) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("idShop", shop.getIdShop());
        contentValues.put("idUser", shop.getIdUser());
        contentValues.put("name", shop.getName());
        contentValues.put("address", shop.getAddress());
        contentValues.put("image", shop.getImage());
        contentValues.put("status", shop.getStatus());

        int check = sqLiteDatabase.update("Shop", contentValues, "idShop=?", new String[]{String.valueOf(shop.getIdShop())});
        if (check <= 0) return false;
        return true;
    }
}
