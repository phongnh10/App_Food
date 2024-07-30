package fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.du_an_1.R;

import java.util.List;

import adapter.OrderAdapter;
import dao.OrderDAO;
import model.Order;

public class OrderHistoryFragment extends Fragment {

    private OrderDAO orderDAO;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rcv_lits_order_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orderDAO = new OrderDAO(getContext());
        orderList = orderDAO.getOrderList(getRoleFromSharedPreferences(), 0);
        orderAdapter = new OrderAdapter(getContext(), orderList, orderDAO);
        recyclerView.setAdapter(orderAdapter);

        return view;
    }

    public int getRoleFromSharedPreferences() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User_Login", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("role", -1);
    }
}