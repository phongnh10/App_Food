package fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.du_an_1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import adapter.ProductAdapter;
import adapter.ItemCategoriesAddProduct;
import dao.CategoriesDao;
import dao.ProductDAO;
import model.Categories;
import model.Product;

public class ManageProductFragment extends Fragment implements ItemCategoriesAddProduct.OnItemClickListener {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ProductDAO productDAO;
    private FloatingActionButton floatingActionButton;
    private Context context;
    private Bitmap bitmap;
    private ImageView imageView;
    private int idShop;
    private int idUser;
    private int idCategories;
    private String sidCategories;
    private String nameCategories;
    private List<Product> productList;
    private List<Categories> categoriesList;
    private RecyclerView recyclerView, recyclerView1;
    private ProductAdapter adapter;
    private ItemCategoriesAddProduct adapter1;
    private CategoriesDao categoriesDao;
    TextView txt_categories;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manage_product, container, false);

        productDAO = new ProductDAO(context);
        categoriesDao = new CategoriesDao(context);
        floatingActionButton = view.findViewById(R.id.fab_add_product);
        recyclerView = view.findViewById(R.id.rcv_manage_product);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.dialog_information_product, null);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();

                idUser = getIdUserFromSharedPreferences();
                idShop = categoriesDao.getIdShop(idUser);

                EditText edt_name = dialogView.findViewById(R.id.edt_name_product);
                EditText edt_price = dialogView.findViewById(R.id.edt_price_product);
                EditText edt_note = dialogView.findViewById(R.id.edt_note_product);
                imageView = dialogView.findViewById(R.id.img_selected);
                ImageView img_back = dialogView.findViewById(R.id.img_back_information);

                txt_categories = dialogView.findViewById(R.id.txt_categories_product);
                Button btn_select_image = dialogView.findViewById(R.id.btn_select_image);
                Button btn_save = dialogView.findViewById(R.id.btn_save_product);

                img_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                txt_categories.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View dialogView = inflater.inflate(R.layout.dialog_add_categories_product, null);
                        ImageView img_back1 = dialogView.findViewById(R.id.img_add_categories_back);
                        builder.setView(dialogView);
                        AlertDialog alertDialog1 = builder.create();

                        img_back1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog1.dismiss();
                            }
                        });

                        recyclerView1 = dialogView.findViewById(R.id.rcv_item_categories_add_product);
                        recyclerView1.setLayoutManager(new GridLayoutManager(context, 1));

                        categoriesList = categoriesDao.getAllCategoriesName();
                        adapter1 = new ItemCategoriesAddProduct(context, categoriesList);
                        adapter1.setOnItemClickListener(ManageProductFragment.this);
                        recyclerView1.setAdapter(adapter1);


                        alertDialog1.show();

                    }

                });


                btn_select_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openImagePicker();
                    }
                });

                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String productName = edt_name.getText().toString();
                        String sproductPrice = edt_price.getText().toString();
                        int productPrice = 0;
                        try {
                            productPrice = Integer.parseInt(sproductPrice);
                        } catch (NumberFormatException e) {
                            Toast.makeText(context, "Invalid price format!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String productNote = edt_note.getText().toString();
                        int status = 0;
                        if (bitmap != null && !productName.isEmpty() && !sproductPrice.isEmpty() && sidCategories != null) {
                            int targetWidth = 800;
                            int targetHeight = 600;
                            bitmap = resizeBitmap(bitmap, targetWidth, targetHeight);
                            byte[] imageBytes = getBitmapAsByteArray(bitmap);
                            Product product = new Product();
                            product.setName(productName);
                            product.setImage(imageBytes);
                            product.setStatus(status);
                            product.setPrice(productPrice);
                            product.setIdShop(idShop);
                            product.setNote(productNote);

                            long check = productDAO.addProduct(idCategories, idShop, productName, imageBytes, productPrice, productNote);

                            if (check == 1) {
                                productList.add(product);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(context, "Product saved successfully!", Toast.LENGTH_SHORT).show();
                            } else if (check == 0) {
                                Toast.makeText(context, "Failed to save product! The product name already exists.", Toast.LENGTH_SHORT).show();
                            }
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context, "Please select an image and enter product details.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alertDialog.show();
            }
        });

        return view;
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    public Bitmap resizeBitmap(Bitmap originalBitmap, int newWidth, int newHeight) {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, false);
        return resizedBitmap;
    }

    public int getIdUserFromSharedPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("User_Login", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("idUser", -1);
    }

    private void loadProductList() {
        idUser = getIdUserFromSharedPreferences();
        idShop = categoriesDao.getIdShop(idUser);
        productList = productDAO.getProducts(idShop);
        adapter = new ProductAdapter(context, productList, productDAO);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(Categories categories) {
        idCategories = categories.getIdCategories();
        nameCategories = categories.getName();
        txt_categories.setText(nameCategories);
        sidCategories = String.valueOf(idCategories);


        Toast.makeText(context, "SelectedID: " + idCategories + " SelectedName: " + nameCategories, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResume() {
        super.onResume();
        loadProductList();
    }
}
