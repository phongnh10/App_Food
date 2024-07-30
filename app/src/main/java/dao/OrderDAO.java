package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import data.base.DbHelper;
import model.Order;

public class OrderDAO {
    private DbHelper dbHelper;
    public OrderDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public long addOrder( int idShop, int idUser, int quantity, Double totalPrice, String date, String note, int status) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

//        Cursor cursor = db.rawQuery("SELECT * FROM  OrderTable  WHERE idOrder = ?", new String[]{String.valueOf(idOrder)});
//        if (cursor.getCount() > 0) {
//            cursor.close();
//            db.close();
//            return 0;
//        }

        // Nếu sản phẩm chưa có, thêm vào bảng
        ContentValues contentValues = new ContentValues();
        contentValues.put("idShop", idShop);
        contentValues.put("idUser", idUser);
        contentValues.put("quantity", quantity);
        contentValues.put("totalPrice", totalPrice);
        contentValues.put("date", date);
        contentValues.put("note", note);
        contentValues.put("status", status);

        long check = db.insert("OrderTable", null, contentValues);
        db.close();

        return (check == -1) ? -1 : 1; // Trả về -1 nếu thêm không thành công, ngược lại trả về 1
    }

    public List<Order> getOrderList(int idUser, int status) {
        List<Order> orderList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT idOrder, idShop, idUser, quantity, totalPrice, date, note, status FROM OrderTable WHERE idUser = ? AND status = ?", new String[]{String.valueOf(idUser), String.valueOf(status)})) {
            while (cursor.moveToNext()) {
                Order order = new Order(
                        cursor.getInt(cursor.getColumnIndexOrThrow("idOrder")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("idShop")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("idUser")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("quantity")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("totalPrice")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("note")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("status"))
                );
                orderList.add(order);
            }
        } catch (Exception e) {
        } finally {
            db.close();
        }

        return orderList;
    }

}
