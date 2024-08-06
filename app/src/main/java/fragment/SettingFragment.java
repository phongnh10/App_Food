package fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.du_an_1.ChangepassActivity;
import com.example.du_an_1.LoginActivity;
import com.example.du_an_1.RegisterShopActivity;
import com.example.du_an_1.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.txtLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        if(getRoleFromSharedPreferences() != 2){
            binding.txtRegisterShop.setVisibility(View.GONE);
        }

        binding.txtRegisterShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RegisterShopActivity.class);
                startActivity(intent);
            }
        });
        binding.txtChancePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangepassActivity.class);
                startActivity(intent);
            }
        });




        return view;
    }

    public int getRoleFromSharedPreferences() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User_Login", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("role", -1);
    }
}