package com.example.du_an_1;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adapter.OrderDetailsAdapter;
import dao.OrderDetailsDAO;
import model.OrderDetails;

public class OrderActivity extends AppCompatActivity {

    private OrderDetailsDAO orderDetailsDAO;
    private List<OrderDetails> orderDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);

        // Thiết lập nút quay lại
        ImageView image = findViewById(R.id.img_back_order);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayout parentLinearLayout = findViewById(R.id.ll_1);

        // Khởi tạo DAO và lấy danh sách chi tiết đơn hàng cho người dùng
        orderDetailsDAO = new OrderDetailsDAO(this);
        orderDetailsList = orderDetailsDAO.getOrderDetailsForUser(getIdUserFromSharedPreferences());

        // Lấy danh sách các shop duy nhất
        Set<Integer> uniqueShops = new HashSet<>();
        for (OrderDetails orderDetails : orderDetailsList) {
            uniqueShops.add(orderDetails.getIdShop());
        }

        int countShop = uniqueShops.size();
        Log.d(TAG, "onCreate: " + countShop);

        // Tạo LinearLayout dựa trên số lượng shop
        for (Integer shopId : uniqueShops) {
            // Tạo LinearLayout mới
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            // Tạo TextView mới
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setText("SHOP " + shopId);
            textView.setTextSize(20);

            // Tạo RecyclerView mới
            RecyclerView recyclerView = new RecyclerView(this);
            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Lọc danh sách các đơn hàng cho shop hiện tại
            List<OrderDetails> shopOrderDetails = filterOrderDetailsByShopId(orderDetailsList, shopId);

            // Tạo adapter cho RecyclerView
            OrderDetailsAdapter orderDetailsAdapter = new OrderDetailsAdapter(this, shopOrderDetails, orderDetailsDAO);
            recyclerView.setAdapter(orderDetailsAdapter);

            // Thêm TextView và RecyclerView vào LinearLayout
            ll.addView(textView);
            ll.addView(recyclerView);

            // Thêm LinearLayout vào LinearLayout cha
            parentLinearLayout.addView(ll);
        }
    }

    // Lấy id người dùng từ SharedPreferences
    public int getIdUserFromSharedPreferences() {
        SharedPreferences sharedPreferences = OrderActivity.this.getSharedPreferences("User_Login", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("idUser", -1);
    }

    // Lọc danh sách chi tiết đơn hàng theo id shop
    private List<OrderDetails> filterOrderDetailsByShopId(List<OrderDetails> orderDetailsList, int shopId) {
        List<OrderDetails> filteredList = new ArrayList<>();
        for (OrderDetails orderDetails : orderDetailsList) {
            if (orderDetails.getIdShop() == shopId) {
                filteredList.add(orderDetails);
            }
        }
        return filteredList;
    }
}
