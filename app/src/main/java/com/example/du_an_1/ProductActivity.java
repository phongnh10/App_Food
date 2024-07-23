package com.example.du_an_1;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.du_an_1.databinding.ActivityProductBinding;

import dao.ProductDAO;
import model.Product;


public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        int productId = intent.getIntExtra("productId", -1);

        Product product = new Product();


    }
}