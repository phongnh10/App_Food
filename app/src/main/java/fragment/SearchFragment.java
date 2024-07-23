package fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.du_an_1.R;
import com.example.du_an_1.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.HomeProductAdapter;
import adapter.ProductAdapter;
import adapter.SearchAdapter;
import adapter.ShopAdapter;
import dao.ProductDAO;
import dao.ShopDAO;
import model.Product;
import model.Shop;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private ProductDAO productDAO;
    private SearchAdapter searchAdapter;
    private ShopAdapter shopAdapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.showMenu1Textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), v, Gravity.RIGHT);
                popupMenu.getMenuInflater().inflate(R.menu.menu_search, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.item1) {
                            binding.showMenu1Textview.setText("Sản Phẩm");
                            loadProductList();
                            return true;
                        } else if (item.getItemId() == R.id.item2) {
                            binding.showMenu1Textview.setText("Shop");
                            loadProductList1();
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
        binding.showMenu2Textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), v, Gravity.RIGHT);
                popupMenu.getMenuInflater().inflate(R.menu.menu_search_2, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.item1) {
                            binding.showMenu2Textview.setText("Ăn Vặt");
                            return true;
                        } else if (item.getItemId() == R.id.item2) {
                            binding.showMenu2Textview.setText("Ăn Chính");
                            return true;
                        } else if (item.getItemId() == R.id.item3) {
                            binding.showMenu2Textview.setText("Combo Đồ Ăn");
                            return true;
                        } else if (item.getItemId() == R.id.item4) {
                            binding.showMenu2Textview.setText("Đồ Ăn Khác");
                            return true;
                        } else if (item.getItemId() == R.id.item5) {
                            binding.showMenu2Textview.setText("Nước Ngọt");
                            return true;
                        } else if (item.getItemId() == R.id.item6) {
                            binding.showMenu2Textview.setText("Cà Phê");
                            return true;
                        } else if (item.getItemId() == R.id.item7) {
                            binding.showMenu2Textview.setText("Trà Sữa");
                            return true;
                        } else if (item.getItemId() == R.id.item8) {
                            binding.showMenu2Textview.setText("Nước Khác");
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
        binding.showMenu3Textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), v, Gravity.RIGHT);
                popupMenu.getMenuInflater().inflate(R.menu.menu_search_3, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.item1) {
                            binding.showMenu3Textview.setText("Mới Nhất");
                            return true;
                        } else if (item.getItemId() == R.id.item2) {
                            binding.showMenu3Textview.setText("Giá Thấp Nhất");
                            return true;
                        } else if (item.getItemId() == R.id.item3) {
                            binding.showMenu3Textview.setText("Giá Cao Nhất");
                            return true;
                        } else if (item.getItemId() == R.id.item4) {
                            binding.showMenu3Textview.setText("A-Z");
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

        recyclerView = binding.rcvProductSearch;
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        loadProductList();

        binding.edtSearchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProductList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
        return view;
    }

    public void loadProductList(){
        productDAO = new ProductDAO(getContext());
        List<Product> productList = productDAO.getProductsListAll();
        searchAdapter = new SearchAdapter(getContext(), productList, productDAO);
        recyclerView.setAdapter(searchAdapter);
    }
    public void loadProductList1(){

    }
    private void filterProductList(String query) {
        List<Product> filteredList = productDAO.getProductsByName(query);
        searchAdapter.updateProductList(filteredList);
    }
}
