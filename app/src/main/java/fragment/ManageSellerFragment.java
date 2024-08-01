package fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.du_an_1.R;
import com.example.du_an_1.databinding.FragmentManageSellerBinding;
import dao.ShopDAO;
import model.Shop;

public class ManageSellerFragment extends Fragment {

    private LinearLayout ll_content, ll_menu_booth,ll_parent;
    private FragmentManageSellerBinding binding;
    private ShopDAO shopDAO;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentManageSellerBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        // layout
        ll_menu_booth = view.findViewById(R.id.ll_menu);
        ll_content = view.findViewById(R.id.ll_content);
        ll_parent = view.findViewById(R.id.ll_parent);
        setHeight();

        //fragment
        replaceFragment(new ManageProductFragment());
        binding.imgProduct.setVisibility(View.VISIBLE);
        binding.imgCategory.setVisibility(View.GONE);

        binding.txtProduct.setOnClickListener(view1 -> {
            replaceFragment(new ManageProductFragment());
            binding.imgProduct.setVisibility(View.VISIBLE);
            binding.imgCategory.setVisibility(View.GONE);
        });
        binding.txtCategory.setOnClickListener(view1 -> {
            replaceFragment(new ManageCategoryFragment());
            binding.imgProduct.setVisibility(View.GONE);
            binding.imgCategory.setVisibility(View.VISIBLE);
        });
        getShop();

        return view;
    }

    public void setHeight(){
        ll_parent.post(new Runnable() {
            @Override
            public void run() {
                int heightMax = ll_parent.getHeight();

                int menuHeight = ll_menu_booth.getHeight();

                int heightContent = heightMax - menuHeight;

                ViewGroup.LayoutParams params = ll_content.getLayoutParams();
                params.height = heightContent;
                ll_content.setLayoutParams(params);
            }
        });
    }

    // Replace fragment method
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragme_manage_sell, fragment);
        fragmentTransaction.commit();
    }
    public void getShop() {
        int idUser = getIdUserFromSharedPreferences();
        if (idUser != -1) {
            shopDAO = new ShopDAO(requireContext());
            Shop shop = shopDAO.getShopByIdUser(idUser);
            if (shop != null) {
                String shopName = shop.getName();
                String shopAddress = shop.getAddress();
                binding.txtNameShop.setText(shopName); ;
                binding.txtAddressShop.setText(shopAddress);
            }
        }
    }
    public int getIdUserFromSharedPreferences() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User_Login", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("idUser", -1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Giải phóng binding để tránh rò rỉ bộ nhớ
        binding = null;
    }


}
