package adapter;

import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.startActivityForResult;


import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_1.R;

import java.text.DecimalFormat;
import java.util.List;

import dao.CategoriesDao;
import dao.ProductDAO;

import model.Categories;
import model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Context context;
    private List<Product> productList;
    private ProductDAO productDAO;
    private CategoriesDao categoriesDao;
    private Categories categories;
    private int statusProduct;


    public ProductAdapter(Context context, List<Product> productList, ProductDAO productDAO) {
        this.context = context;
        this.productList = productList;
        this.productDAO = productDAO;
        this.categoriesDao = new CategoriesDao(context);
        this.categories = new Categories();
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_manage_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.img_product.setImageBitmap(convertByteArrayToBitmap(product.getImage()));
        holder.txt_name.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("#,### vnđ");
        holder.txt_price.setText(decimalFormat.format(product.getPrice()));
        int idProduct = product.getIdProduct();
        int idUser = getIdUserFromSharedPreferences();
        int idShop = categoriesDao.getIdShop(idUser);


        int status = product.getStatus();
        if (status == 1) {
            holder.txt_status_product.setText("Còn Hàng");
            holder.txt_status_product.setTextColor(ContextCompat.getColor(context, R.color.selected_color));
        } else {
            holder.txt_status_product.setText("Hết Hàng");
            holder.txt_status_product.setTextColor(ContextCompat.getColor(context, R.color.default_color));

        }

        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Theme_AppCompat_Light);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.dialog_information_product, null);

                dialog.setContentView(dialogView);

                EditText edt_name = dialogView.findViewById(R.id.edt_name_product);
                EditText edt_price = dialogView.findViewById(R.id.edt_price_product);
                EditText edt_note = dialogView.findViewById(R.id.edt_note_product);
                ImageView imageView = dialogView.findViewById(R.id.img_selected);
                ImageView img_back = dialogView.findViewById(R.id.img_back_information);
                TextView txt_categories = dialogView.findViewById(R.id.txt_categories_product);
                Button btn_select_image = dialogView.findViewById(R.id.btn_select_image);
                Button btn_save = dialogView.findViewById(R.id.btn_save_product);
                Switch switch_status = dialogView.findViewById(R.id.sw_status_product);

                categories = categoriesDao.getCategoryById(product.getIdCategories());
                edt_name.setText(product.getName());
                edt_price.setText(String.valueOf(product.getPrice()));
                edt_note.setText(product.getNote());
                imageView.setImageBitmap(convertByteArrayToBitmap(product.getImage()));
                txt_categories.setText(String.valueOf(categories.getName()));
                statusProduct = product.getStatus();

                img_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                switch_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (switch_status.isChecked()) {
                            statusProduct = 1;
                            switch_status.setThumbTintList(ContextCompat.getColorStateList(context, R.color.green_color));
                            switch_status.setTrackTintList(ContextCompat.getColorStateList(context, R.color.green_color));
                            Toast.makeText(context, "Còn hàng", Toast.LENGTH_SHORT).show();
                        } else {
                            statusProduct = 0;
                            switch_status.setThumbTintList(ContextCompat.getColorStateList(context, R.color.default_color));
                            switch_status.setTrackTintList(ContextCompat.getColorStateList(context, R.color.default_color));
                            Toast.makeText(context, "Hết hàng", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                if (statusProduct == 1) {
                    switch_status.setThumbTintList(ContextCompat.getColorStateList(context, R.color.green_color));
                    switch_status.setTrackTintList(ContextCompat.getColorStateList(context, R.color.green_color));
                    switch_status.setChecked(true);
                }
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Product product1 = new Product(product.getIdProduct(),product.getIdCategories(),product.getIdShop(),edt_name.getText().toString(),null,Integer.parseInt(edt_price.getText().toString()),edt_note.getText().toString(),statusProduct,statusProduct);
                        boolean check = productDAO.upProduct(product1);
                        if(check){
                            productList.clear();
                            productList.addAll(productDAO.getProductsListAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Cập nhập sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context, "Cập nhập sản phẩm thất bại", Toast.LENGTH_SHORT).show();

                        }
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_status_product, txt_price;
        ImageView img_product;
        LinearLayout ll_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.txt_name_product);
            txt_status_product = itemView.findViewById(R.id.txt_status_product);
            img_product = itemView.findViewById(R.id.img_product);
            txt_price = itemView.findViewById(R.id.txt_price_product);
            ll_item = itemView.findViewById(R.id.ll_item_product);
        }
    }

    public void updateData(List<Product> newProductList) {
        productList.clear();
        productList.addAll(newProductList);
        notifyDataSetChanged();
    }

    public int getIdUserFromSharedPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("User_Login", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("idUser", -1);
    }
    private Bitmap convertByteArrayToBitmap(byte[] imageBytes) {
        if (imageBytes != null && imageBytes.length > 0) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } else {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.side_nav_bar);
        }
    }

}
