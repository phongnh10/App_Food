package fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.du_an_1.MainActivity;
import com.example.du_an_1.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

import adapter.CategoriesHomeAdapter;
import adapter.HomeProductAdapter;
import adapter.PagerHomeAdapter;
import bottomRecycleview.RightSpaceItemDecoration;
import dao.CategoriesDao;
import dao.ProductDAO;
import dao.ShopDAO;
import dao.UserDAO;
import model.Categories;
import model.Product;
import model.User;

public class HomeFragment extends Fragment {
    private HomeProductAdapter homeProductAdapter;
    private CategoriesHomeAdapter categoriesHomeAdapter;
    private ProductDAO productDAO;
    private CategoriesDao categoriesDao;
    private ShopDAO shopDAO;
    private RecyclerView recyclerView, recyclerView1, recyclerView2;
    private Handler handler = new Handler();
    private Runnable runnable;
    private final int PAGE_DELAY = 3000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView img_search = view.findViewById(R.id.img_search_home);
        TextView home_location = view.findViewById(R.id.home_location);

        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tabDots);

        List<Integer> imageList = Arrays.asList(R.mipmap.slide1, R.mipmap.slide2, R.mipmap.slide3);
        PagerHomeAdapter adapter = new PagerHomeAdapter(imageList);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // Cài đặt tiêu đề hoặc biểu tượng cho các tab nếu cần
        }).attach();


        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("SearchFragment", "SearchFragment");
                startActivity(intent);
            }
        });

        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int nextItem = (currentItem + 1) % imageList.size(); // Chuyển đến trang tiếp theo
                viewPager.setCurrentItem(nextItem, true); // Chuyển trang với hiệu ứng cuộn
                handler.postDelayed(this, PAGE_DELAY); // Đặt lại Runnable sau một khoảng thời gian
            }
        };

        handler.postDelayed(runnable, PAGE_DELAY); // Bắt đầu tự động chuyển trang


        User user = new User();
        UserDAO userDAO = new UserDAO(getContext());
        user = userDAO.getUserByID(getIdUserFromSharedPreferences());
        home_location.setText("Location: "+user.getAddress());


        recyclerView = view.findViewById(R.id.rcv_eat_home);
        recyclerView1 = view.findViewById(R.id.rcv_drink_home);
        recyclerView2 = view.findViewById(R.id.rcv_categories_home);
        StaggeredGridLayoutManager layoutManagerEat = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        StaggeredGridLayoutManager layoutManagerDrink = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        StaggeredGridLayoutManager layoutManagerCategories = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(layoutManagerEat);
        recyclerView1.setLayoutManager(layoutManagerDrink);
        recyclerView2.setLayoutManager(layoutManagerCategories);
        int rightSpaceWidth = getResources().getDimensionPixelSize(R.dimen.right_space_width);
        recyclerView2.addItemDecoration(new RightSpaceItemDecoration(rightSpaceWidth));
        recyclerView1.addItemDecoration(new RightSpaceItemDecoration(rightSpaceWidth));
        recyclerView.addItemDecoration(new RightSpaceItemDecoration(rightSpaceWidth));
        loadProductList();
        loadProductList1();
        loadProductList2();

        return view;
    }

    public void loadProductList() {
        productDAO = new ProductDAO(getContext());
        shopDAO = new ShopDAO(getContext());
        List<Product> productList = productDAO.getProductsListEat();
        homeProductAdapter = new HomeProductAdapter(getContext(), productList, productDAO, shopDAO);
        recyclerView.setAdapter(homeProductAdapter);
    }

    public void loadProductList1() {
        productDAO = new ProductDAO(getContext());
        shopDAO = new ShopDAO(getContext());
        List<Product> productList = productDAO.getProductsListDrinks();
        homeProductAdapter = new HomeProductAdapter(getContext(), productList, productDAO, shopDAO);
        recyclerView1.setAdapter(homeProductAdapter);
    }

    public void loadProductList2() {
        categoriesDao = new CategoriesDao(getContext());
        shopDAO = new ShopDAO(getContext());
        List<Categories> categoriesList = categoriesDao.getAllCategories();
        categoriesHomeAdapter = new CategoriesHomeAdapter(getContext(), categoriesList, categoriesDao);
        recyclerView2.setAdapter(categoriesHomeAdapter);
    }

    public int getIdUserFromSharedPreferences() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User_Login", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("idUser", -1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
