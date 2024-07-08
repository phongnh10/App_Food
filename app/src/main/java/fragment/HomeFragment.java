package fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.example.du_an_1.R;

import java.util.List;

import adapter.CategoriesHomeAdapter;
import adapter.HomeProductAdapter;
import dao.CategoriesDao;
import dao.ProductDAO;
import dao.ShopDAO;
import model.Categories;
import model.Product;

public class HomeFragment extends Fragment {
    private ViewFlipper viewFlipper;
    private HomeProductAdapter homeProductAdapter;
    private CategoriesHomeAdapter categoriesHomeAdapter;
    private ProductDAO productDAO;
    private CategoriesDao categoriesDao;
    private ShopDAO shopDAO;
    private RecyclerView recyclerView, recyclerView1,recyclerView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewFlipper = view.findViewById(R.id.viewFlipper);
        if (viewFlipper != null) {
            viewFlipper.setFlipInterval(2000);
            viewFlipper.startFlipping();
        }

        recyclerView = view.findViewById(R.id.rcv_eat_home);
        recyclerView1 = view.findViewById(R.id.rcv_drink_home);
        recyclerView2 = view.findViewById(R.id.rcv_categories_home);
        StaggeredGridLayoutManager layoutManagerEat = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        StaggeredGridLayoutManager layoutManagerDrink = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        StaggeredGridLayoutManager layoutManagerCategories = new StaggeredGridLayoutManager(6, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManagerEat);
        recyclerView1.setLayoutManager(layoutManagerDrink);
        recyclerView2.setLayoutManager(layoutManagerCategories);

        loadProductList();
        loadProductList1();
        loadProductList2();

        return view;
    }

    public void loadProductList(){
        productDAO = new ProductDAO(getContext());
        shopDAO = new ShopDAO(getContext());
        List<Product> productList = productDAO.getProductsListEat();
        homeProductAdapter = new HomeProductAdapter(getContext(), productList, productDAO, shopDAO);
        recyclerView.setAdapter(homeProductAdapter);
    }
    public void loadProductList1(){
        productDAO = new ProductDAO(getContext());
        shopDAO = new ShopDAO(getContext());
        List<Product> productList = productDAO.getProductsListDrinks();
        homeProductAdapter = new HomeProductAdapter(getContext(), productList, productDAO, shopDAO);
        recyclerView1.setAdapter(homeProductAdapter);
    }
    public void loadProductList2(){
        categoriesDao = new CategoriesDao(getContext());
        shopDAO = new ShopDAO(getContext());
        List<Categories> categoriesList = categoriesDao.getAllCategories();
        categoriesHomeAdapter = new CategoriesHomeAdapter(getContext(), categoriesList, categoriesDao);
        recyclerView2.setAdapter(categoriesHomeAdapter);
    }
}
