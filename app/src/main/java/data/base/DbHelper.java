package data.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.example.du_an_1.R;

import java.io.ByteArrayOutputStream;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DuAn1";
    private static final int DATABASE_VERSION = 1;

    // Table and Column names
    private static final String TABLE_USER = "User";
    private static final String TABLE_SHOP = "Shop";
    private static final String TABLE_CATEGORIES = "Categories";
    private static final String TABLE_PRODUCT = "Product";



    private static final String COLUMN_ID_USER = "idUser";
    private static final String COLUMN_USER = "user";
    private static final String COLUMN_PASS = "pass";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_CCCD = "cccd";
    private static final String COLUMN_ROLE = "role";

    private static final String COLUMN_ID_SHOP = "idShop";
    private static final String COLUMN_ADDRESS = "address";

    private static final String COLUMN_ID_CATEGORIES = "idCategories";
    private static final String COLUMN_IMAGE = "image";

    // Product table columns
    private static final String COLUMN_ID_PRODUCT = "idProduct";
    private static final String COLUMN_ID_CATEGORIES_PRODUCT = "idCategories";
    private static final String COLUMN_ID_SHOP_PRODUCT = "idShop";
    private static final String COLUMN_NAME_PRODUCT = "name";
    private static final String COLUMN_IMAGE_PRODUCT = "image";
    private static final String COLUMN_PRICE_PRODUCT = "price";
    private static final String COLUMN_NOTE_PRODUCT = "note";
    private static final String COLUMN_STATUS_PRODUCT = "status";

    // Categories table columns

    private Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL("CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER + " TEXT, " +
                COLUMN_PASS + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " LONG, " +
                COLUMN_CCCD + " LONG, " +
                COLUMN_ROLE + " INTEGER)");

        db.execSQL("CREATE TABLE " + TABLE_SHOP + " (" +
                COLUMN_ID_SHOP + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ID_USER + " INTEGER, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_ID_USER + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID_USER + "))");

        db.execSQL("CREATE TABLE " + TABLE_CATEGORIES + " (" +
                COLUMN_ID_CATEGORIES + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ID_SHOP + " INTEGER, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_IMAGE + " BLOB, " +
                "FOREIGN KEY(" + COLUMN_ID_SHOP + ") REFERENCES " + TABLE_SHOP + "(" + COLUMN_ID_SHOP + "))");

        // Create Product table
        db.execSQL("CREATE TABLE " + TABLE_PRODUCT + "("
                + COLUMN_ID_PRODUCT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ID_CATEGORIES_PRODUCT + " INTEGER,"
                + COLUMN_ID_SHOP_PRODUCT + " INTEGER,"
                + COLUMN_NAME_PRODUCT + " TEXT,"
                + COLUMN_IMAGE_PRODUCT + " BLOB,"
                + COLUMN_PRICE_PRODUCT + " INTEGER,"
                + COLUMN_NOTE_PRODUCT + " TEXT,"
                + COLUMN_STATUS_PRODUCT + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_ID_CATEGORIES_PRODUCT + ") REFERENCES " + TABLE_CATEGORIES + "(" + COLUMN_ID_CATEGORIES + "),"
                + "FOREIGN KEY(" + COLUMN_ID_SHOP_PRODUCT + ") REFERENCES " + TABLE_SHOP + "(" + COLUMN_ID_SHOP + ")"
                + ")");



        // Insert initial data
        db.execSQL("INSERT INTO " + TABLE_USER + " (" + COLUMN_USER + ", " + COLUMN_PASS + ", " + COLUMN_NAME + ", " + COLUMN_PHONE + ", " + COLUMN_CCCD + ", " + COLUMN_ROLE + ") VALUES " +
                "('admin', '1', 'Nguyễn Hồng Phong', '0999999999', '88888888888888', 0), " +
                "('sell1', '1', 'Nguyễn Thị Mỹ Huyền', '0123456789', '22222222222222', 1), " +
                "('sell2', '1', 'Phạm Anh Tuấn', '0978654123', '22222222222222', 1), " +
                "('sell3', '1', 'Nguyễn Nguyễn Thị Dung', '0972396789', '66666666666666', 1), " +
                "('buy1', '1', 'Nguyễn Văn A', '0123456789', '33333333333333', 2), " +
                "('buy2', '1', 'Lê Đức Thọ', '0123456789', '33333333333333', 2)");

        db.execSQL("INSERT INTO " + TABLE_SHOP + " (" + COLUMN_ID_USER + ", " + COLUMN_NAME + ", " + COLUMN_ADDRESS + ") VALUES " +
                "(1, 'Shop Admin', 'Not Selle'), " +
                "(2, 'Tiệm Cơm Mùa Thu', 'Tử Lâu 88'), " +
                "(3, 'Tiềm Trà Tháng 4', 'Sảnh S7.02'), " +
                "(4, 'Trà Sữa Trái Cây', 'Gohan 8')");

        // Insert initial categories with image
        insertInitialCategories(db);
        //product
        insertProduct(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        onCreate(db);
    }

    public Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = ((float) maxWidth) / width;
        float scaleHeight = ((float) maxHeight) / height;
        float scale = Math.min(scaleWidth, scaleHeight);

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return resizedBitmap;
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, stream);
        return stream.toByteArray();
    }

    private void insertInitialCategories(SQLiteDatabase db) {
        // Load images from resources and resize them
        Bitmap bitmap1 = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.image_comtron), 400, 200);
        Bitmap bitmap2 = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.img_mytom), 400, 200);
        Bitmap bitmap3 = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.img_banhmy), 400, 200);
        Bitmap bitmap4 = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.img_doanvat), 400, 200);
        Bitmap bitmap5 = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.img_khac), 400, 200);
        Bitmap bitmap6 = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.img_trasua), 400, 200);
        Bitmap bitmap7 = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.img_caphe), 400, 200);
        Bitmap bitmap8 = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.img_nuocngot), 400, 200);
        Bitmap bitmap9 = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.img_sua), 400, 200);
        Bitmap bitmap10 = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.img_khac_1), 400, 200);

        // Convert bitmaps to byte arrays
        byte[] image1 = getBytesFromBitmap(bitmap1, 50);
        byte[] image2 = getBytesFromBitmap(bitmap2, 50);
        byte[] image3 = getBytesFromBitmap(bitmap3, 50);
        byte[] image4 = getBytesFromBitmap(bitmap4, 50);
        byte[] image5 = getBytesFromBitmap(bitmap5, 50);
        byte[] image6 = getBytesFromBitmap(bitmap6, 50);
        byte[] image7 = getBytesFromBitmap(bitmap7, 50);
        byte[] image8 = getBytesFromBitmap(bitmap8, 50);
        byte[] image9 = getBytesFromBitmap(bitmap9, 50);
        byte[] image10 = getBytesFromBitmap(bitmap10, 50);

        // Insert categories into the database
        insertCategory(db, 1, "Cơm", image1);
        insertCategory(db, 1, "Mỳ", image2);
        insertCategory(db, 1, "Bánh Mỳ", image3);
        insertCategory(db, 1, "Đồ Ăn Vặt", image4);
        insertCategory(db, 1, "Đồ Ăn Khác", image5);
        insertCategory(db, 1, "Trà Sữa", image6);
        insertCategory(db, 1, "Cà Phê", image7);
        insertCategory(db, 1, "Nước Ngọt", image8);
        insertCategory(db, 1, "Sữa", image9);
        insertCategory(db, 1, "Nước Khác", image10);
    }

    private void insertCategory(SQLiteDatabase db, int shopId, String name, byte[] image) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_SHOP, shopId);
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_IMAGE, image);
        db.insert(TABLE_CATEGORIES, null, contentValues);
    }


    private void insertProduct(SQLiteDatabase db) {
        // Load images from resources
        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.img_bundaumamtom);
        Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.img_trasua);

        // Convert bitmaps to byte arrays
        byte[] image1 = getBytesFromBitmap(bitmap1,50);
        byte[] image2 = getBytesFromBitmap(bitmap2,50);

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_CATEGORIES_PRODUCT, 6);
        values.put(COLUMN_ID_SHOP_PRODUCT, 1);
        values.put(COLUMN_NAME_PRODUCT, "Trà Sữa Trái Cây");
        values.put(COLUMN_IMAGE_PRODUCT, image2);
        values.put(COLUMN_PRICE_PRODUCT, 20000);
        values.put(COLUMN_NOTE_PRODUCT, "Ngon nhé");
        values.put(COLUMN_STATUS_PRODUCT, 1);
        db.insert(TABLE_PRODUCT, null, values);


        ContentValues values1 = new ContentValues();
        values1.put(COLUMN_ID_CATEGORIES_PRODUCT, 1);
        values1.put(COLUMN_ID_SHOP_PRODUCT, 1);
        values1.put(COLUMN_NAME_PRODUCT, "Bún Đậu Mắm Tôm");
        values1.put(COLUMN_IMAGE_PRODUCT, image1);
        values1.put(COLUMN_PRICE_PRODUCT, 100000);
        values1.put(COLUMN_NOTE_PRODUCT, "Ngon nhé");
        values1.put(COLUMN_STATUS_PRODUCT, 1);
        db.insert(TABLE_PRODUCT, null, values1);

    }
    public byte[] getBytesFromBitmapp(Bitmap bitmap, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, stream);
        return stream.toByteArray();
    }


}
