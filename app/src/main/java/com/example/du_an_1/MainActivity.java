package com.example.du_an_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.du_an_1.databinding.ActivityMainBinding;

import fragment.CartBuyFragment;
import fragment.CartSellFragment;
import fragment.HomeFragment;
import fragment.ManageAdminFragment;
import fragment.ManageBuyerFragment;
import fragment.ManageSellerFragment;
import fragment.SearchFragment;
import fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkRole();

        Intent intent = getIntent();
        String fragmentName = null;
        if (intent.hasExtra("CartBuyFragment")) {
            fragmentName = intent.getStringExtra("CartBuyFragment");
        } else if (intent.hasExtra("SearchFragment")) {
            fragmentName = intent.getStringExtra("SearchFragment");
        } else if (intent.hasExtra("SettingFragment")) {
            fragmentName = intent.getStringExtra("SettingFragment");
        }

        if (fragmentName != null && !fragmentName.isEmpty()) {
            replaceFragment(getFragmentByName(fragmentName));
            binding.bottomNavigation.setSelectedItemId(getMenuItemIdByFragmentName(fragmentName));
        } else {
            replaceFragment(new HomeFragment());
            binding.bottomNavigation.setSelectedItemId(R.id.nav_home);
        }

        int role = getRoleFromSharedPreferences();

        // Handle bottom navigation item selection
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.nav_search) {
                replaceFragment(new SearchFragment());
            } else if (item.getItemId() == R.id.nav_shop) {
                if (role == 0) {
                    replaceFragment(new ManageAdminFragment());
                } else if (role == 1) {
                    replaceFragment(new ManageSellerFragment());
                } else if (role == 2) {
                    replaceFragment(new ManageBuyerFragment());
                }
            } else if (item.getItemId() == R.id.nav_cart) {
                if (role == 1) {
                    replaceFragment(new CartSellFragment());
                } else if (role == 2) {
                    replaceFragment(new CartBuyFragment());
                }
            } else if (item.getItemId() == R.id.nav_setting) {
                replaceFragment(new SettingFragment());
            } else {
                replaceFragment(new HomeFragment());
            }
            return true;
        });

        int[][] states = new int[][]{new int[]{android.R.attr.state_checked}, // trạng thái được chọn
                new int[]{-android.R.attr.state_checked} // trạng thái không được chọn
        };

        int[] colors = new int[]{getResources().getColor(R.color.orange), getResources().getColor(R.color.default_color)};

        ColorStateList colorStateList = new ColorStateList(states, colors);

        // Thiết lập màu sắc cho các mục trong BottomNavigationView
        binding.bottomNavigation.setItemIconTintList(colorStateList);
        binding.bottomNavigation.setItemTextColor(colorStateList);
    }

    // Method to get Fragment by name
    private Fragment getFragmentByName(String fragmentName) {
        switch (fragmentName) {
            case "CartSellFragment":
                return new CartSellFragment();
            case "CartBuyFragment":
                return new CartBuyFragment();
            case "ManageAdminFragment":
                return new ManageAdminFragment();
            case "ManageBuyerFragment":
                return new ManageBuyerFragment();
            case "ManageSellerFragment":
                return new ManageSellerFragment();
            case "SearchFragment":
                return new SearchFragment();
            case "SettingFragment":
                return new SettingFragment();
            case "HomeFragment":
            default:
                return new HomeFragment();
        }
    }

    // Method to get MenuItem ID by Fragment name
    private int getMenuItemIdByFragmentName(String fragmentName) {
        switch (fragmentName) {
            case "CartSellFragment":
                return R.id.nav_cart;
            case "CartBuyFragment":
                return R.id.nav_cart;
            case "ManageAdminFragment":
            case "ManageBuyerFragment":
            case "ManageSellerFragment":
                return R.id.nav_shop;
            case "SearchFragment":
                return R.id.nav_search;
            case "SettingFragment":
                return R.id.nav_setting;
            case "HomeFragment":
            default:
                return R.id.nav_home;
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public int getRoleFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("User_Login", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("role", -1);
    }

    public void checkRole() {
        int role = getRoleFromSharedPreferences();

        switch (role) {
            case 0: // admin
                MenuItem nav_shop = binding.bottomNavigation.getMenu().findItem(R.id.nav_shop);
                if (nav_shop != null) {
                    nav_shop.setTitle("Manage");
                    nav_shop.setIcon(R.drawable.ic_item_manage);
                }
                MenuItem nav_cart = binding.bottomNavigation.getMenu().findItem(R.id.nav_cart);
                nav_cart.setVisible(false);
                break;
            case 1: // seller

                break;
            case 2: // buyer
                MenuItem nav_shop1 = binding.bottomNavigation.getMenu().findItem(R.id.nav_shop);
                nav_shop1.setVisible(false);
                break;
            default:
                break;
        }
    }
}