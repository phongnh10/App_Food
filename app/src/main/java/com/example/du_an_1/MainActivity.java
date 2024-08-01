package com.example.du_an_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
        String fragmentName = intent.getStringExtra("fragment");
        if (fragmentName != null && !fragmentName.isEmpty()) {
            replaceFragment(getFragmentByName(fragmentName));
        } else {
            replaceFragment(new HomeFragment());
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
                break;
            default:
                break;
        }
    }
}