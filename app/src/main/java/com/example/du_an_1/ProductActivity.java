package com.example.du_an_1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.du_an_1.databinding.ActivityProductBinding;

import java.text.DecimalFormat;

import dao.OrderDetailsDAO;
import dao.ProductDAO;
import model.OrderDetails;
import model.Product;


public class ProductActivity extends AppCompatActivity {
    private ProductDAO productDAO;
    private Product product;
    private OrderDetailsDAO orderDetailsDAO;
    private OrderDetails orderDetails;
    private ActivityProductBinding binding;
    private int quantity = 1;
    private int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        int productId = intent.getIntExtra("productId", -1);

        if (productId != -1) {
            productDAO = new ProductDAO(this);
            product = productDAO.getProductById(productId);
            binding.txtNameProduct.setText(product.getName());
            DecimalFormat decimalFormat = new DecimalFormat("đ #,###,###");
            String formattedPrice = decimalFormat.format(product.getPrice());
            price = product.getPrice();
            binding.txtPriceProduct.setText(formattedPrice);
            binding.imgProduct.setImageBitmap(convertByteArrayToBitmap(product.getImage()));
            binding.txtNoteProduct.setText(product.getNote());
        }

        binding.imgMinusQuantityProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 1) {
                    quantity--;
                    if (price > 0) {
                        price = price - product.getPrice();
                    }
                }
                DecimalFormat decimalFormat = new DecimalFormat("đ #,###,###");
                String formattedPrice = decimalFormat.format(price);
                binding.txtQuantityProduct.setText(String.valueOf(quantity));
                binding.txtPriceProduct.setText(String.valueOf(formattedPrice));
            }
        });
        binding.imgPlusQuantityProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                price = price + product.getPrice();

                DecimalFormat decimalFormat = new DecimalFormat("đ #,###,###");
                String formattedPrice = decimalFormat.format(price);
                binding.txtQuantityProduct.setText(String.valueOf(quantity));
                binding.txtPriceProduct.setText(String.valueOf(formattedPrice));
            }
        });


        binding.imgBackProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnAddProductCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDetails newOrderDetails = new OrderDetails();
                newOrderDetails.setIdProduct(product.getIdProduct());
                newOrderDetails.setIdOrder(1);
                newOrderDetails.setQuantity(quantity);
                newOrderDetails.setPrice(price);
            }
        });

    }


    private Bitmap convertByteArrayToBitmap(byte[] imageBytes) {
        if (imageBytes != null && imageBytes.length > 0) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } else {
            return BitmapFactory.decodeResource(this.getResources(), R.drawable.side_nav_bar);
        }
    }
}