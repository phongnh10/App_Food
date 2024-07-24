package fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.du_an_1.OrderActivity;
import com.example.du_an_1.databinding.FragmentOrdersCurrentBinding;

import java.text.DecimalFormat;
import java.util.List;

import adapter.OrderDetailsAdapter;
import dao.OrderDetailsDAO;
import model.Order;
import model.OrderDetails;


public class OrdersCurrentFragment extends Fragment {
    private FragmentOrdersCurrentBinding binding;
    private OrderDetailsDAO orderDetailsDAO;
    private OrderDetailsAdapter adapter;
    private List<OrderDetails> orderDetailsList;
    private int idUser;
    private int idShop;
    private Double priceTotal;
    private Order order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrdersCurrentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize DAO
        orderDetailsDAO = new OrderDetailsDAO(getContext());

        // Set up RecyclerView
        RecyclerView recyclerView = binding.rcvLitsOrderDetails;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize data and adapter
        idUser = getRoleFromSharedPreferences();
        orderDetailsList = orderDetailsDAO.getOrderDetailsForUser(idUser);
        adapter = new OrderDetailsAdapter(getContext(), orderDetailsList, orderDetailsDAO);
        recyclerView.setAdapter(adapter);

        updateTotal();
        updateTotalPrice();

        binding.llOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getActivity();
                if (context != null) {
                    Intent intent = new Intent(context, OrderActivity.class);
                    startActivity(intent);
                }
            }
        });




        return view;
    }

    public int getRoleFromSharedPreferences() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User_Login", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("idUser", -1);
    }

    private void updateTotalPrice() {
        priceTotal = 0.0;
        for (OrderDetails orderDetails : orderDetailsList) {
            priceTotal += orderDetails.getTotalPrice();
        }
        idShop = orderDetailsList.get(0).getIdShop();
        DecimalFormat decimalFormat = new DecimalFormat("đ #,###,###");
        binding.txtTotalPrice.setText(decimalFormat.format(priceTotal));
    }

    private void updateTotal() {

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updateTotalPrice();
                handler.postDelayed(this, 500);
            }
        };
        handler.postDelayed(runnable, 500);
    }

}

