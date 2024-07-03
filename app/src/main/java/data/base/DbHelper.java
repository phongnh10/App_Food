package data.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "DuAn1", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //table
        db.execSQL("CREATE TABLE User (idUser INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT, pass TEXT, name TEXT,phone LONG, cccd LONG, role INTEGER)");
        db.execSQL("CREATE TABLE Shop (idShop INTEGER PRIMARY KEY AUTOINCREMENT, idUser INTEGER, name TEXT, address TEXT, FOREIGN KEY(idUser) REFERENCES User(idUser))");
        db.execSQL("CREATE TABLE Category (idCategory INTEGER PRIMARY KEY AUTOINCREMENT, idShop INTEGER, name TEXT, FOREIGN KEY(idShop) REFERENCES Shop (idShop))");

        //database
        db.execSQL("INSERT INTO User (user, pass, name, phone, cccd, role) VALUES " +
                "('admin', '1', 'Nguyễn Hồng Phong', '0999999999', '88888888888888', 0), " +
                "('sell1', '1', 'Nguyễn Thị Mỹ Huyền', '0123456789', '22222222222222', 1), " +
                "('sell2', '1', 'Phạm Anh Tuấn', '0978654123', '22222222222222', 1), " +
                "('sell3', '1', 'Nguyễn Nguyễn Thị Dung', '0972396789', '66666666666666', 1), " +
                "('buy1', '1', 'Nguyễn Văn A', '0123456789', '33333333333333', 2)," +
                "('buy2', '1', 'Lê Đức Thọ', '0123456789', '33333333333333', 2);");

        db.execSQL("INSERT INTO Shop (idUser, name, address) VALUES " +
                "(1, 'Shop Admin', 'Not Selle')," +
                "(2, 'Tiệm Cơm Mùa Thu', 'Tử Lâu 88')," +
                "(3, 'Tiềm Trà Tháng 4', 'Sảnh S7.02')," +
                "(4, 'Trà Sữa Trái Cây', 'Gohan 8')");

        db.execSQL("INSERT INTO Category (idShop, name) VALUES " +
                "(1, 'Bánh Tráng')," +
                "(2, 'Trà Sữa')," +
                "(3, 'Bún')," +
                "(4, 'Mỳ Tôm')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Shop");
        db.execSQL("DROP TABLE IF EXISTS Category");

        onCreate(db);
    }
}
