package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import data.base.DbHelper;
import model.OrderDetails;

public class OrderDetailsDAO {
    private DbHelper dbHelper;
    public OrderDetailsDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void createOrderDetailsTable(int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName = "OrderDetails_" + userId;
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "idOrderDetails INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idShop INTEGER, " +
                "idProduct INTEGER, " +
                "price DOUBLE, " +
                "totalPrice DOUBLE, " +
                "quantity INTEGER, " +
                "name TEXT, " +
                "image BLOB)";
        db.execSQL(createTableQuery);
        db.close();
    }

    public long addOrderDetails(int userId, int idShop,int idProduct, double price, double totalPrice, int quantity, String name, byte[] image) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName = "OrderDetails_" + userId;

        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE idProduct = ?", new String[]{String.valueOf(idProduct)});
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return 0;
        }

        // Nếu sản phẩm chưa có, thêm vào bảng
        ContentValues contentValues = new ContentValues();
        contentValues.put("idShop", idShop);
        contentValues.put("idProduct", idProduct);
        contentValues.put("price", price);
        contentValues.put("totalPrice", totalPrice);
        contentValues.put("quantity", quantity);
        contentValues.put("name", name);
        contentValues.put("image", image);

        long check = db.insert(tableName, null, contentValues);
        db.close();

        return (check == -1) ? -1 : 1; // Trả về -1 nếu thêm không thành công, ngược lại trả về 1
    }


    public List<OrderDetails> getOrderDetailsForUser(int userId) {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String tableName = "OrderDetails_" + userId;
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);

        if (cursor.moveToFirst()) {
            do {
                OrderDetails orderDetails = new OrderDetails();

                // Sử dụng getColumnIndexOrThrow để đảm bảo rằng cột tồn tại
                orderDetails.setIdOrderDetails(cursor.getInt(cursor.getColumnIndexOrThrow("idOrderDetails")));
                orderDetails.setIdProduct(cursor.getInt(cursor.getColumnIndexOrThrow("idShop")));
                orderDetails.setIdProduct(cursor.getInt(cursor.getColumnIndexOrThrow("idProduct")));
                orderDetails.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow("price")));
                orderDetails.setTotalPrice(cursor.getDouble(cursor.getColumnIndexOrThrow("totalPrice")));
                orderDetails.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow("quantity")));
                orderDetails.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                orderDetails.setImage(cursor.getBlob(cursor.getColumnIndexOrThrow("image")));

                orderDetailsList.add(orderDetails);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return orderDetailsList;
    }

    public List<OrderDetails> getOrderDetailsForUserShop(int userId, int idShop) {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String tableName = "OrderDetails_" + userId;

        // Correct SQL query with parameterized arguments
        String query = "SELECT * FROM " + tableName + " WHERE idShop = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idShop)});

        if (cursor.moveToFirst()) {
            do {
                OrderDetails orderDetails = new OrderDetails();

                // Use getColumnIndexOrThrow to ensure the column exists
                orderDetails.setIdOrderDetails(cursor.getInt(cursor.getColumnIndexOrThrow("idOrderDetails")));
                orderDetails.setIdShop(cursor.getInt(cursor.getColumnIndexOrThrow("idShop")));
                orderDetails.setIdProduct(cursor.getInt(cursor.getColumnIndexOrThrow("idProduct")));
                orderDetails.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow("price")));
                orderDetails.setTotalPrice(cursor.getDouble(cursor.getColumnIndexOrThrow("totalPrice")));
                orderDetails.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow("quantity")));
                orderDetails.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                orderDetails.setImage(cursor.getBlob(cursor.getColumnIndexOrThrow("image")));

                orderDetailsList.add(orderDetails);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return orderDetailsList;
    }


    public boolean deleteOrderDetails(int userId, int idOrderDetails) {
        try (SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase()) {
            String tableName = "OrderDetails_" + userId;

            int rowsDeleted = sqLiteDatabase.delete(tableName, "idOrderDetails = ?", new String[]{String.valueOf(idOrderDetails)});

            return rowsDeleted > 0;
        } catch (SQLiteException e) {
            Log.e("OrderDetailsDAO", "Failed to delete order detail: " + e.getMessage());
            return false;
        }
    }

    public boolean updateOrderDetails(int userId, OrderDetails orderDetails) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName = "OrderDetails_" + userId;
        ContentValues contentValues = new ContentValues();
        contentValues.put("quantity", orderDetails.getQuantity());
        contentValues.put("totalPrice", orderDetails.getTotalPrice());

        int rows = db.update(tableName, contentValues, "idOrderDetails = ?", new String[]{String.valueOf(orderDetails.getIdOrderDetails())});
        db.close();
        return rows > 0;
    }




}
