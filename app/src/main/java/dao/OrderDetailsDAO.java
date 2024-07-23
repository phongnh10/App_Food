package dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import data.base.DbHelper;

public class OrderDetailsDAO {
    DbHelper dbHelper;
    public int addProductOrder(int idProduct, int idOrder, int quantity,double price ,byte[] image, String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Kiểm tra xem danh mục có tồn tại không
        Cursor cursor = db.rawQuery("SELECT * FROM OrderDetails WHERE name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return 0; // Trả về 0 nếu danh mục đã tồn tại
        }

        // Nếu danh mục chưa tồn tại, thêm mới vào cơ sở dữ liệu
        ContentValues contentValues = new ContentValues();
        contentValues.put("idProduct", idProduct);
        contentValues.put("idOrder", idOrder);
        contentValues.put("quantity", quantity);
        contentValues.put("price", price);
        contentValues.put("image", image);
        contentValues.put("name", name);

        long check = db.insert("OrderDetails", null, contentValues);

        db.close();

        return (check == -1) ? -1 : 1; // Trả về -1 nếu thêm mới không thành công, ngược lại trả về 1
    }
}
