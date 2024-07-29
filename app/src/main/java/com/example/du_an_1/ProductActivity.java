package com.example.du_an_1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.du_an_1.databinding.ActivityProductBinding;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import adapter.OrderDetailsAdapter;
import dao.OrderDetailsDAO;
import dao.ProductDAO;
import model.OrderDetails;
import model.Product;


public class ProductActivity extends AppCompatActivity {
    private ProductDAO productDAO;
    private Product product;
    private OrderDetailsDAO orderDetailsDAO;
    private ActivityProductBinding binding;
    private List<OrderDetails> orderDetailsList;
    private OrderDetailsAdapter adapter;
    private int quantity = 1;
    private int price;
    private int totalPrice;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        int productId = intent.getIntExtra("productId", -1);

        if (productId != -1) {
            productDAO = new ProductDAO(this);
            product = productDAO.getProductById(productId);
            binding.txtNameProduct.setText(product.getName());
            DecimalFormat decimalFormat = new DecimalFormat("đ #,###,###");
            String formattedPrice = decimalFormat.format(product.getPrice());
            price = product.getPrice();
            totalPrice = quantity * price;
            binding.txtPriceProduct.setText(formattedPrice);
            binding.imgProduct.setImageBitmap(convertByteArrayToBitmap(product.getImage()));
            binding.txtNoteProduct.setText(product.getNote());
            binding.txtSoldProduct.setText(String.valueOf(product.getSold()+" Lượt mua"));
        }

        binding.imgMinusQuantityProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 1) {
                    quantity--;
                    if (price > 0) {
                        totalPrice = quantity * price;
                    }
                }
                DecimalFormat decimalFormat = new DecimalFormat("đ #,###,###");
                String formattedTotalPrice = decimalFormat.format(totalPrice);
                binding.txtQuantityProduct.setText(String.valueOf(quantity));
                binding.txtPriceProduct.setText(formattedTotalPrice);
            }
        });
        binding.imgPlusQuantityProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                if (price > 0) {
                    totalPrice = quantity * price;
                }

                DecimalFormat decimalFormat = new DecimalFormat("đ #,###,###");
                String formattedTotalPrice = decimalFormat.format(totalPrice);
                binding.txtQuantityProduct.setText(String.valueOf(quantity));
                binding.txtPriceProduct.setText(formattedTotalPrice);
            }
        });


        binding.imgBackProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        binding.btnAddProductCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (orderDetailsList == null) {
//                    orderDetailsList = new ArrayList<>();
//                }
//                if (adapter == null) {
//                    adapter = new OrderDetailsAdapter(ProductActivity.this, orderDetailsList, orderDetailsDAO);
//                }
//                OrderDetails orderDetails = new OrderDetails();
//                orderDetailsDAO = new OrderDetailsDAO(ProductActivity.this);
//
//                idUser = getIdUserFromSharedPreferences();
//                orderDetails.setIdProduct(product.getIdProduct());
//                orderDetails.setIdOrder(idUser);
//                orderDetails.setIdShop(product.getIdShop());
//                orderDetails.setQuantity(quantity);
//                orderDetails.setPrice(price);
//                orderDetails.setTotalPrice(totalPrice);
//                orderDetails.setImage(product.getImage());
//                orderDetails.setName(product.getName());
//
//
//
//                long check = orderDetailsDAO.addOrderDetails(idUser,product.getIdShop(), product.getIdProduct(), price, totalPrice, quantity, product.getName(), product.getImage());
//                if (check == 1) {
//                    orderDetailsList.add(orderDetails);
//                    adapter.notifyDataSetChanged();
//                    Toast.makeText(ProductActivity.this, "Thêm sản phẩm vào giỏ hàng " +product.getIdShop(), Toast.LENGTH_SHORT).show();
//                } else if (check == 0) {
//                    Toast.makeText(ProductActivity.this, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
//                } else if (check == -1) {
//                    Toast.makeText(ProductActivity.this, "Lỗi khi thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
//                }
//
//                finish();
//            }
//
//        });

    }


    public int getIdUserFromSharedPreferences() {
        SharedPreferences sharedPreferences = ProductActivity.this.getSharedPreferences("User_Login", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("idUser", -1);
    }

    private Bitmap convertByteArrayToBitmap(byte[] imageBytes) {
        if (imageBytes != null && imageBytes.length > 0) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } else {
            return BitmapFactory.decodeResource(this.getResources(), R.drawable.side_nav_bar);
        }
    }

    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }
}