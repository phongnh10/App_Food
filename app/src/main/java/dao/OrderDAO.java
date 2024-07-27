package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import data.base.DbHelper;

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

}
