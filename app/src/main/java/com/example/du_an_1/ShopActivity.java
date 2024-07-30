package com.example.du_an_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.du_an_1.databinding.ActivityShopBinding;

import java.util.List;

import adapter.SearchAdapter;
import dao.ProductDAO;
import dao.ShopDAO;
import model.Product;

public class ShopActivity extends AppCompatActivity {
    private ActivityShopBinding binding;
    private RecyclerView rcvEat, rcvDrink;
    private ProductDAO productDAO;
    private SearchAdapter searchAdapter1, searchAdapter2;
    private int idUser, idShop;
    private ShopDAO shopDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityShopBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            binding.getRoot().setPadding(systemBarsInsets.left, systemBarsInsets.top, systemBarsInsets.right, systemBarsInsets.bottom);
            return insets;
        });

        Intent intent = getIntent();
        idShop = intent.getIntExtra("idShop", -1);


        rcvEat = binding.rcvShopEatBuy;
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        rcvEat.setLayoutManager(layoutManager);

        rcvDrink = binding.rcvShopDrinkBuy;
        StaggeredGridLayoutManager layoutManager1 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rcvDrink.setLayoutManager(layoutManager1);


        loadProductList();
        loadProductList1();

        binding.imgBackProduct.setOnClickListener(view -> finish());
    }

    public void loadProductList() {
        productDAO = new ProductDAO(this);
        List<Product> productList = productDAO.getProductsListEatShop(idShop);
        searchAdapter1 = new SearchAdapter(this, productList, productDAO);
        rcvEat.setAdapter(searchAdapter1);
    }

    public void loadProductList1() {
        productDAO = new ProductDAO(this);
        List<Product> productList = productDAO.getProductsListDrinksShop(idShop);
        searchAdapter2 = new SearchAdapter(this, productList, productDAO);
        rcvDrink.setAdapter(searchAdapter2);
    }


    public int getIdUserFromSharedPreferences() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("User_Login", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("idUser", -1);
    }
}