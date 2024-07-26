package data.base;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

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
    private static final String TABLE_ORDER_DETAILS = "OrderDetails";
    private static final String TABLE_ORDER = "OrderTable";

    // User table columns
    private static final String COLUMN_ID_USER = "idUser";
    private static final String COLUMN_USER = "user";
    private static final String COLUMN_PASS = "pass";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_CCCD = "cccd";
    private static final String COLUMN_ROLE = "role";

    // Shop table columns
    private static final String COLUMN_ID_SHOP = "idShop";
    private static final String COLUMN_NAME_SHOP = "name";
    private static final String COLUMN_ADDRESS_SHOP = "address";
    private static final String COLUMN_IMAGE_SHOP = "image";
    private static final String COLUMN_STATUS_SHOP = "status";

    // Categories table columns
    private static final String COLUMN_ID_CATEGORIES = "idCategories";
    private static final String COLUMN_NAME_CATEGORIES = "name";
    private static final String COLUMN_IMAGE_CATEGORIES = "image";

    // Product table columns
    private static final String COLUMN_ID_PRODUCT = "idProduct";
    private static final String COLUMN_ID_SHOP_PRODUCT = "idShop";
    private static final String COLUMN_NAME_PRODUCT = "name";
    private static final String COLUMN_IMAGE_PRODUCT = "image";
    private static final String COLUMN_PRICE_PRODUCT = "price";
    private static final String COLUMN_NOTE_PRODUCT = "note";
    private static final String COLUMN_STATUS_PRODUCT = "status";

    // OrderDetails table columns
    private static final String COLUMN_ID_ORDER_DETAILS = "idOrderDetails";
    private static final String COLUMN_ID_SHOP_DETAILS = "idShop";
    private static final String COLUMN_ID_PRODUCT_DETAILS = "idProduct";
    private static final String COLUMN_PRICE_ORDER_DETAILS = "price";
    private static final String COLUMN_TOTAL_PRICE_ORDER_DETAILS = "totalPrice";
    private static final String COLUMN_QUANTITY_ORDER_DETAILS = "quantity";
    private static final String COLUMN_NAME_ORDER_DETAILS = "name";
    private static final String COLUMN_IMAGE_ORDER_DETAILS = "image";
    private static final String COLUMN_STATUS_ORDER_DETAILS = "status";


    // Order table columns
    private static final String COLUMN_ID_ORDER = "idOrder";
    private static final String COLUMN_TOTAL_PRICE_ORDER = "totalPrice";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_ORDER_DATE_ORDER= "date";
    private static final String COLUMN_NOTE_ORDER = "note";
    private static final String COLUMN_STATUS_ORDER = "status";

    ///
    private Context context;
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create User table
        db.execSQL("CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER + " TEXT, " +
                COLUMN_PASS + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " LONG, " +
                COLUMN_CCCD + " LONG, " +
                COLUMN_ROLE + " INTEGER)");

        // Create Shop table
        db.execSQL("CREATE TABLE " + TABLE_SHOP + " (" +
                COLUMN_ID_SHOP + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ID_USER + " INTEGER, " +
                COLUMN_NAME_SHOP + " TEXT, " +
                COLUMN_ADDRESS_SHOP + " TEXT, " +
                COLUMN_IMAGE_SHOP + " BLOB, " +
                COLUMN_STATUS_SHOP + " INTEGER, " +

                "FOREIGN KEY(" + COLUMN_ID_USER + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID_USER + "))");

        // Create Categories table
        db.execSQL("CREATE TABLE " + TABLE_CATEGORIES + " (" +
                COLUMN_ID_CATEGORIES + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ID_SHOP + " INTEGER, " +
                COLUMN_NAME_CATEGORIES + " TEXT, " +
                COLUMN_IMAGE_CATEGORIES + " BLOB, " +
                "FOREIGN KEY(" + COLUMN_ID_SHOP + ") REFERENCES " + TABLE_SHOP + "(" + COLUMN_ID_SHOP + "))");

        // Create Product table
        db.execSQL("CREATE TABLE " + TABLE_PRODUCT + " (" +
                COLUMN_ID_PRODUCT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ID_CATEGORIES + " INTEGER, " +
                COLUMN_ID_SHOP + " INTEGER, " +
                COLUMN_NAME_PRODUCT + " TEXT, " +
                COLUMN_IMAGE_PRODUCT + " BLOB, " +
                COLUMN_PRICE_PRODUCT + " INTEGER, " +
                COLUMN_NOTE_PRODUCT + " TEXT, " +
                COLUMN_STATUS_PRODUCT + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_ID_CATEGORIES + ") REFERENCES " + TABLE_CATEGORIES + "(" + COLUMN_ID_CATEGORIES + "), " +
                "FOREIGN KEY(" + COLUMN_ID_SHOP + ") REFERENCES " + TABLE_SHOP + "(" + COLUMN_ID_SHOP + "))");

        // Create OrderDetails table
        db.execSQL("CREATE TABLE " + TABLE_ORDER_DETAILS + " (" +
                COLUMN_ID_ORDER_DETAILS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ID_SHOP_DETAILS + " INTEGER, " +
                COLUMN_ID_PRODUCT_DETAILS + " INTEGER, " +
                COLUMN_ID_ORDER + " INTEGER, " +
                COLUMN_QUANTITY_ORDER_DETAILS + " INTEGER, " +
                COLUMN_PRICE_ORDER_DETAILS + " DOUBLE, " +
                COLUMN_TOTAL_PRICE_ORDER_DETAILS + " DOUBLE, " +
                COLUMN_IMAGE_ORDER_DETAILS + " BLOB, " +
                COLUMN_NAME_ORDER_DETAILS + " TEXT, " +
                COLUMN_STATUS_ORDER_DETAILS + " INTEGER, " +

                "FOREIGN KEY(" + COLUMN_ID_PRODUCT + ") REFERENCES " + TABLE_PRODUCT + "(" + COLUMN_ID_PRODUCT + "), " +
                "FOREIGN KEY(" + COLUMN_ID_ORDER + ") REFERENCES " + TABLE_ORDER + "(" + COLUMN_ID_ORDER + "))");

        // Create Order table
        db.execSQL("CREATE TABLE " + TABLE_ORDER + " (" +
                COLUMN_ID_ORDER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ID_SHOP_PRODUCT + " INTEGER, " +
                COLUMN_ID_USER + " INTEGER, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_TOTAL_PRICE_ORDER + " DOUBLE, " +
                COLUMN_ORDER_DATE_ORDER + " Text, " +
                COLUMN_NOTE_ORDER + " TEXT, " +
                COLUMN_STATUS_ORDER + " INTEGER, " +

                "FOREIGN KEY(" + COLUMN_ID_USER + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID_USER + "))");


        // Insert initial data
        db.execSQL("INSERT INTO " + TABLE_USER + " (" + COLUMN_USER + ", " + COLUMN_PASS + ", " + COLUMN_NAME + ", " + COLUMN_PHONE + ", " + COLUMN_CCCD + ", " + COLUMN_ROLE + ") VALUES " +
                "('admin', '1', 'Nguyễn Hồng Phong', '0999999999', '88888888888888', 0), " +
                "('sell1', '1', 'Nguyễn Thị Mỹ Huyền', '0123456789', '22222222222222', 1), " +
                "('sell2', '1', 'Phạm Anh Tuấn', '0978654123', '22222222222222', 1), " +
                "('sell3', '1', 'Nguyễn Nguyễn Thị Dung', '0972396789', '66666666666666', 1), " +
                "('buy1', '1', 'Nguyễn Văn A', '0123456789', '33333333333333', 2), " +
                "('buy2', '1', 'Lê Đức Thọ', '0123456789', '33333333333333', 2)");


        // Insert initial categories with image
        insertInitialCategories(db);
        //product
        insertProduct(db);
       //insertshop
        insertShops(db);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        onCreate(db);
    }

    public void insertShops(SQLiteDatabase db) {
        // Load images from resources and resize them
        Bitmap bitmap1 = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.image_shop_1), 400, 400);
        Bitmap bitmap2 = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.image_shop_2), 400, 400);

        // Convert bitmaps to byte arrays
        byte[] image1 = getBytesFromBitmap(bitmap1, 50);
        byte[] image2 = getBytesFromBitmap(bitmap2, 50);

        // Insert data
        insertShop(db, 2, "Cơm Mẹ Nga", "Lầu 8, Toà T", image2, 1);
        insertShop(db, 3, "Trà Sữa 68", "Lầu 3, Toà T", image1, 1);

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

    private void insertProduct(SQLiteDatabase db) {
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
        // Giả sử bạn đã có đối tượng SQLiteDatabase db

// Cơm
        insertProduct(db, 1, 1, "Cơm Tấm Sườn Nướng", image1, 30000.0, "Cơm tấm với sườn nướng thơm ngon, kèm trứng ốp la và dưa leo, cà chua. Món ăn truyền thống đầy hương vị.", 1);
        insertProduct(db, 1, 1, "Cơm Chiên Dương Châu", image1, 32000.0, "Cơm chiên với tôm, thịt heo, rau củ và trứng. Hương vị đậm đà và đầy đủ dinh dưỡng.", 1);

// Mỳ
        insertProduct(db, 2, 1, "Mỳ Xào Thập Cẩm", image2, 35000.0, "Mỳ xào với nhiều loại hải sản tươi ngon và rau củ, nước sốt đặc biệt.", 1);
        insertProduct(db, 2, 1, "Mỳ Ý Bolognese", image2, 37000.0, "Mỳ Ý với sốt Bolognese truyền thống, thịt bằm và phô mai parmesan.", 1);

// Bánh Mỳ
        insertProduct(db, 3, 1, "Bánh Mỳ Kẹp Thịt Nướng", image3, 20000.0, "Bánh mì kẹp thịt nướng với rau sống tươi ngon và sốt đặc biệt.", 1);
        insertProduct(db, 3, 1, "Bánh Mỳ Pate Đặc Biệt", image3, 22000.0, "Bánh mì với pate và thịt nguội, kèm rau sống và sốt mayonnaise.", 1);

// Đồ Ăn Vặt
        insertProduct(db, 4, 1, "Khoai Tây Chiên Giòn", image4, 15000.0, "Khoai tây chiên giòn rụm, kèm sốt mayonnaise và ketchup.", 1);
        insertProduct(db, 4, 1, "Chả Giò Hải Sản", image4, 18000.0, "Chả giò với hải sản tươi ngon, rau củ và nước chấm chua ngọt.", 1);

// Đồ Ăn Khác
        insertProduct(db, 5, 1, "Gỏi Cuốn Tôm Thịt", image5, 20000.0, "Gỏi cuốn với tôm, thịt heo, rau sống và nước chấm đậu phộng.", 1);
        insertProduct(db, 5, 1, "Bánh Xèo", image5, 22000.0, "Bánh xèo với nhân thịt heo, tôm và giá đỗ, ăn kèm rau sống và nước chấm.", 1);

// Trà Sữa
        insertProduct(db, 6, 1, "Trà Sữa Đen", image6, 35000.0, "Trà sữa đen với trân châu mềm mịn và vị ngọt thanh.", 1);
        insertProduct(db, 6, 1, "Trà Sữa Matcha", image6, 38000.0, "Trà sữa matcha thơm ngon với lớp bọt sữa mịn và trân châu.", 1);

// Cà Phê
        insertProduct(db, 7, 1, "Cà Phê Espresso", image7, 25000.0, "Cà phê espresso đậm đà, phục vụ với một lớp crema dày và mịn.", 1);
        insertProduct(db, 7, 1, "Cà Phê Americano", image7, 27000.0, "Cà phê Americano với hương vị mạnh mẽ, pha với nước nóng.", 1);

// Nước Ngọt
        insertProduct(db, 8, 1, "Nước Ngọt Cola", image8, 12000.0, "Nước ngọt cola với hương vị đặc trưng và sủi bọt.", 1);
        insertProduct(db, 8, 1, "Nước Ngọt Cam", image8, 13000.0, "Nước ngọt cam với vị chua ngọt thanh mát.", 1);

// Sữa
        insertProduct(db, 9, 1, "Sữa Tươi", image9, 15000.0, "Sữa tươi nguyên chất, giàu canxi và vitamin.", 1);
        insertProduct(db, 9, 1, "Sữa Socola", image9, 17000.0, "Sữa socola ngọt ngào với hương vị socola đậm đà.", 1);

// Nước Khác
        insertProduct(db, 10, 1, "Nước Dừa", image10, 20000.0, "Nước dừa tươi mát, giàu vitamin và khoáng chất.", 1);
        insertProduct(db, 10, 1, "Nước Chanh", image10, 18000.0, "Nước chanh tươi với hương vị chua ngọt và tinh khiết.", 1);

    }
    private void insertProduct(SQLiteDatabase db, int idCategories, int idShop,String name, byte[] image,Double price,String note, int status) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID_CATEGORIES, idCategories);
        values.put(COLUMN_ID_SHOP, idShop);
        values.put(COLUMN_NAME_PRODUCT, name);
        values.put(COLUMN_IMAGE_PRODUCT, image);
        values.put(COLUMN_PRICE_PRODUCT, price);
        values.put(COLUMN_NOTE_PRODUCT, note);
        values.put(COLUMN_STATUS_PRODUCT, status);

        db.insert(TABLE_PRODUCT, null, values);


    }

    private void insertShop(SQLiteDatabase db, int idUser, String name, String address, byte[] image, int status) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_USER, idUser);
        values.put(COLUMN_NAME_SHOP, name);
        values.put(COLUMN_ADDRESS_SHOP, address);
        values.put(COLUMN_IMAGE_SHOP, image);
        values.put(COLUMN_STATUS_SHOP, status);

        db.insert(TABLE_SHOP, null, values);
    }

    private void insertCategory(SQLiteDatabase db, int shopId, String name, byte[] image) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_SHOP, shopId);
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_IMAGE_CATEGORIES, image);
        db.insert(TABLE_CATEGORIES, null, contentValues);
    }




    // img fofmat
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
}
