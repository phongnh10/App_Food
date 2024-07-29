package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_1.R;

import java.text.DecimalFormat;
import java.util.List;

import dao.CategoriesDao;
import dao.ProductDAO;

import model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    List<Product> productList;
    ProductDAO productDAO;
    CategoriesDao categoriesDao;

    public ProductAdapter(Context context, List<Product> productList, ProductDAO productDAO) {
        this.context = context;
        this.productList = productList;
        this.productDAO = productDAO;
        this.categoriesDao = new CategoriesDao(context);
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
        DecimalFormat decimalFormat = new DecimalFormat("#,### VND");
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
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.dialog_statust_product, null);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                ImageView img_back = dialogView.findViewById(R.id.img_back_statust);
                Switch sw_statust = dialogView.findViewById(R.id.sw_statuts);

                img_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                if (status == 1) {
                    sw_statust.setChecked(true);
                    sw_statust.setThumbTintList(ContextCompat.getColorStateList(context, R.color.selected_color));

                } else {
                    sw_statust.setChecked(false);
                    sw_statust.setThumbTintList(ContextCompat.getColorStateList(context, R.color.default_color));
                }

                sw_statust.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            product.setStatus(1);
                            holder.txt_status_product.setText("Còn Hàng");
                        } else {
                            product.setStatus(0);
                            holder.txt_status_product.setText("Hết Hàng");
                        }

                        boolean check = productDAO.upStatust(product);
                        if (check) {
                            Toast.makeText(context, "Edited successfully", Toast.LENGTH_SHORT).show();
                            productList.clear();
                            productList.addAll(productDAO.getProducts(product.getIdShop()));
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Edit failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });

        holder.ll_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int adapterPosition = holder.getAdapterPosition();
                                if (adapterPosition != RecyclerView.NO_POSITION) {
                                    Product product1 = productList.get(adapterPosition);
                                    boolean check = productDAO.deleteProduct(idProduct, idShop);
                                    if (check) {
                                        productList.remove(adapterPosition);
                                        notifyItemRemoved(adapterPosition);
                                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                        .setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
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

    // Helper method to convert byte[] to Bitmap
    private Bitmap convertByteArrayToBitmap(byte[] imageBytes) {
        if (imageBytes != null && imageBytes.length > 0) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } else {
            // Return a default image or handle null case
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.side_nav_bar);
        }
    }

    // Method to update the dataset with new products
    public void updateData(List<Product> newProductList) {
        productList.clear();
        productList.addAll(newProductList);
        notifyDataSetChanged();
    }

    public int getIdUserFromSharedPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("User_Login", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("idUser", -1);
    }
}
