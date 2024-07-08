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
import model.Product;
import model.User;

public class ProductDAO {
    DbHelper dbHelper;

    public ProductDAO(Context context) {
        this.dbHelper = new DbHelper(context);
    }


    public int addProduct(int idCategories, int idShop, String name, byte[] image, int price, String note) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM Product WHERE name = ?", new String[]{name});
//        if (cursor.getCount() > 0) {
//            cursor.close();
//            db.close();
//            return 0;
//        }

        // Nếu danh mục chưa tồn tại, thêm mới vào cơ sở dữ liệu
        ContentValues contentValues = new ContentValues();
        contentValues.put("idCategories",idCategories);
        contentValues.put("idShop", idShop);
        contentValues.put("name", name);
        contentValues.put("image", image);
        contentValues.put("price",price);
        contentValues.put("note",note);

        long check = db.insert("Product", null, contentValues);

        db.close();

        return (check == -1) ? -1 : 1; // Trả về -1 nếu thêm mới không thành công, ngược lại trả về 1
    }

    public List<Product> getProducts(int idShop) {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT idProduct, idCategories, idShop, name, image, price, note, status FROM Product WHERE idShop = ? OR idShop = 1", new String[]{String.valueOf(idShop)});
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idProductIndex = cursor.getColumnIndex("idProduct");
                    int idCategoriesIndex = cursor.getColumnIndex("idCategories");
                    int idShopIndex = cursor.getColumnIndex("idShop");
                    int nameIndex = cursor.getColumnIndex("name");
                    int imageIndex = cursor.getColumnIndex("image");
                    int priceIndex = cursor.getColumnIndex("price");
                    int noteIndex = cursor.getColumnIndex("note");
                    int statusIndex = cursor.getColumnIndex("status");

                    if (idProductIndex >= 0 && idCategoriesIndex >= 0 && idShopIndex >= 0 && nameIndex >= 0 && imageIndex >= 0 && priceIndex >= 0 && noteIndex >= 0 && statusIndex >= 0) {
                        int idProduct = cursor.getInt(idProductIndex);
                        int idCategories = cursor.getInt(idCategoriesIndex);
                        int idShopValue = cursor.getInt(idShopIndex);
                        String name = cursor.getString(nameIndex);
                        byte[] imageBytes = cursor.getBlob(imageIndex);
                        int price = cursor.getInt(priceIndex);
                        String note = cursor.getString(noteIndex);
                        int status = cursor.getInt(statusIndex);

                        Product product = new Product(idProduct, idCategories, idShopValue, name, imageBytes, price, note, status);
                        productList.add(product);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("ProductAdapter", "Error while fetching products: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return productList;
    }

    public boolean deleteProduct(int idProduct, int idShop) {
        try (SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase()) {
            int check = sqLiteDatabase.delete("Product", "idProduct=? AND idShop=?", new String[]{String.valueOf(idProduct), String.valueOf(idShop)});
            if (check <= 0) {
                Log.e("ProductDAO", "Failed to delete category with idProduct: " + idProduct + " and idShop: " + idShop);
                return false;
            }
            return true;
        } catch (SQLiteException e) {
            Log.e("ProductDAO", "SQLiteException: " + e.getMessage());
            return false;
        }
    }
    public boolean upStatust(Product product){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("status", product.getStatus());

        int check = sqLiteDatabase.update("Product",values,"idProduct=?",new String[]{String.valueOf(product.getIdProduct())});
        if (check <= 0) return false;
        return true;
    }

    public List<Product> getProductsListEat() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT idProduct, idCategories, idShop, name, image, price, note, status FROM Product where status == 1 AND idCategories IN (1,2,3,4,5) ", null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idProductIndex = cursor.getColumnIndex("idProduct");
                    int idCategoriesIndex = cursor.getColumnIndex("idCategories");
                    int idShopIndex = cursor.getColumnIndex("idShop");
                    int nameIndex = cursor.getColumnIndex("name");
                    int imageIndex = cursor.getColumnIndex("image");
                    int priceIndex = cursor.getColumnIndex("price");
                    int noteIndex = cursor.getColumnIndex("note");
                    int statusIndex = cursor.getColumnIndex("status");

                    if (idProductIndex >= 0 && idCategoriesIndex >= 0 && idShopIndex >= 0 && nameIndex >= 0 && imageIndex >= 0 && priceIndex >= 0 && noteIndex >= 0 && statusIndex >= 0) {
                        int idProduct = cursor.getInt(idProductIndex);
                        int idCategories = cursor.getInt(idCategoriesIndex);
                        int idShopValue = cursor.getInt(idShopIndex);
                        String name = cursor.getString(nameIndex);
                        byte[] imageBytes = cursor.getBlob(imageIndex);
                        int price = cursor.getInt(priceIndex);
                        String note = cursor.getString(noteIndex);
                        int status = cursor.getInt(statusIndex);

                        Product product = new Product(idProduct, idCategories, idShopValue, name, imageBytes, price, note, status);
                        productList.add(product);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("ProductAdapter", "Error while fetching products: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return productList;
    }

    public List<Product> getProductsListDrinks() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT idProduct, idCategories, idShop, name, image, price, note, status FROM Product where status == 1 AND idCategories IN (6,7,8,9,10) ", null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idProductIndex = cursor.getColumnIndex("idProduct");
                    int idCategoriesIndex = cursor.getColumnIndex("idCategories");
                    int idShopIndex = cursor.getColumnIndex("idShop");
                    int nameIndex = cursor.getColumnIndex("name");
                    int imageIndex = cursor.getColumnIndex("image");
                    int priceIndex = cursor.getColumnIndex("price");
                    int noteIndex = cursor.getColumnIndex("note");
                    int statusIndex = cursor.getColumnIndex("status");

                    if (idProductIndex >= 0 && idCategoriesIndex >= 0 && idShopIndex >= 0 && nameIndex >= 0 && imageIndex >= 0 && priceIndex >= 0 && noteIndex >= 0 && statusIndex >= 0) {
                        int idProduct = cursor.getInt(idProductIndex);
                        int idCategories = cursor.getInt(idCategoriesIndex);
                        int idShopValue = cursor.getInt(idShopIndex);
                        String name = cursor.getString(nameIndex);
                        byte[] imageBytes = cursor.getBlob(imageIndex);
                        int price = cursor.getInt(priceIndex);
                        String note = cursor.getString(noteIndex);
                        int status = cursor.getInt(statusIndex);

                        Product product = new Product(idProduct, idCategories, idShopValue, name, imageBytes, price, note, status);
                        productList.add(product);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("ProductAdapter", "Error while fetching products: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return productList;
    }

}
