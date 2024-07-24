package fragment;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.du_an_1.R;
import com.example.du_an_1.databinding.FragmentCartBuyBinding;

public class CartBuyFragment extends Fragment {
    private FragmentCartBuyBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBuyBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        replaceFragment(new OrdersCurrentFragment());

        RelativeLayout.LayoutParams paramsLeft = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        RelativeLayout.LayoutParams paramsRight = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        // Handle TextView clicks to change fragments

        binding.txtCurrentOrders.setOnClickListener(v -> {
            replaceFragment(new OrdersCurrentFragment());

            binding.txtCurrentOrders.setTypeface(null, Typeface.BOLD);
            binding.txtHistoryOrders.setTypeface(null, Typeface.NORMAL);
            binding.txtCurrentOrders.setLayoutParams(paramsLeft);
            binding.txtHistoryOrders.setLayoutParams(paramsRight);

        });

        binding.txtHistoryOrders.setOnClickListener(v -> {
            replaceFragment(new OrderHistoryFragment());

            binding.txtCurrentOrders.setTypeface(null, Typeface.NORMAL);
            binding.txtHistoryOrders.setTypeface(null, Typeface.BOLD);
            binding.txtCurrentOrders.setLayoutParams(paramsRight);
            binding.txtHistoryOrders.setLayoutParams(paramsLeft);

        });

        





        return view;

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_oder, fragment);
        fragmentTransaction.commit();
    }
}