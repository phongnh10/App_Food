package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import data.base.DbHelper;
import model.Order;
import model.OrderDetails;

public class OrderDAO {
    private DbHelper dbHelper;

    public OrderDAO(Context context) {
        dbHelper = new DbHelper(context);
    }
    public long addOrder( int idShop,int idUser, int status, int quantity, double totalPrice, String date) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Nếu sản phẩm chưa có, thêm vào bảng
        ContentValues contentValues = new ContentValues();
        contentValues.put("idShop", idShop);
        contentValues.put("idUser", idUser);
        contentValues.put("status", status);
        contentValues.put("quantity", quantity);
        contentValues.put("totalPrice", totalPrice);
        contentValues.put("date", date);

        long check = db.insert("OrderTable", null, contentValues);
        db.close();

        return (check == -1) ? -1 : 1; // Trả về -1 nếu thêm không thành công, ngược lại trả về 1
    }

    public boolean updateOrder(int idOrder, Order order) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("status", order.getStatus());

        int rows = db.update("OrderTable", contentValues, "idOrder = ?", new String[]{String.valueOf(idOrder)});
        db.close();
        return rows > 0;
    }
}
