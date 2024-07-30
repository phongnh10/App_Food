package fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.du_an_1.R;
import com.example.du_an_1.databinding.FragmentOrdersCurrentBinding;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapter.OrderDetailsAdapter;
import dao.OrderDAO;
import dao.OrderDetailsDAO;
import dao.UserDAO;
import model.Order;
import model.OrderDetails;
import model.User;

public class OrdersCurrentFragment extends Fragment {
    private FragmentOrdersCurrentBinding binding;
    private OrderDetailsDAO orderDetailsDAO;
    private OrderDetailsAdapter adapter;
    private List<OrderDetails> orderDetailsList;
    private Order order;
    private OrderDAO orderDAO;
    private List<Order> orderList;
    private int idUser;
    private Double priceTotal;
    private int quantityProduct;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int idShopShare;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrdersCurrentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize DAO
        orderDetailsDAO = new OrderDetailsDAO(getContext());
        orderDAO = new OrderDAO(getContext());

        // Initialize lists
        orderList = new ArrayList<>();
        orderDetailsList = new ArrayList<>();

        // Set up RecyclerView
        loadlist();

        //address
        loadAddrest();

        // Check if orderDetailsList is null or empty
        binding.txtEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.diaglog_edit_address);

                // Tìm các view trong layout dialog
                final EditText editName = dialog.findViewById(R.id.txt_name_edit_address);
                final EditText editPhone = dialog.findViewById(R.id.txt_phone_edit_address);
                final EditText editAddress = dialog.findViewById(R.id.txt_address_edit_address);
                final TextView txtCancel = dialog.findViewById(R.id.txt_cancel_edit_address);
                final TextView txtUpdate = dialog.findViewById(R.id.txt_update_edit_address);

                final UserDAO userDAO = new UserDAO(getContext());
                final User user = userDAO.getUserByID(idUser);

                //set data
                editName.setText(user.getName());
                editPhone.setText("0" + String.valueOf(user.getPhone()));
                editAddress.setText(user.getAddress());


                // Thiết lập độ rộng của dialog
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(layoutParams);

                // Đặt sự kiện click cho nút OK
                txtUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editName.getText().toString().isEmpty() || editPhone.getText().toString().isEmpty() || editAddress.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if ((!editPhone.getText().toString().matches("\\d+")) || (editPhone.getText().toString().length() != 10)) {
                            Toast.makeText(getContext(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        user.setName(editName.getText().toString());
                        user.setPhone(Long.parseLong(editPhone.getText().toString()));
                        user.setAddress(editAddress.getText().toString());

                        boolean check = userDAO.upDateAddress(user);
                        if (check) {
                            loadAddrest();
                            Toast.makeText(getContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();
                    }
                });

                txtCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                // Hiển thị dialog
                dialog.show();
            }
        });


        binding.llOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderDetailsList == null || orderDetailsList.isEmpty()) {
                    Toast.makeText(getContext(), "Bạn chưa thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    return;
                }

                Order order = new Order();
                OrderDetails orderDetails1 = orderDetailsList.get(0);
                idShopShare = orderDetails1.getIdShop();

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = sdf.format(date);

                order.setIdShop(idShopShare);
                order.setIdUser(idUser);
                order.setQuantity(quantityProduct);
                order.setTotalPrice(priceTotal);
                order.setDate(formattedDate);
                order.setNote("Đặt Hàng");
                order.setStatus(0);

                long check = orderDAO.addOrder(order.getIdShop(), idUser, quantityProduct, priceTotal, formattedDate, "Đặt Hàng", 0);
                if (check == 1) {
                    orderList.add(order);

                    for (OrderDetails orderDetails : orderDetailsList) {
                        orderDetails.setIdOrder(order.getIdOrder());
                        orderDetails.setStatus(1);

                        boolean isUpdated = orderDetailsDAO.updateOrderDetailsToOrder(orderDetails);
                        if (!isUpdated) {
                            Toast.makeText(getContext(), "Cập nhật idOder trong OrderDetails thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    loadlist();
                    Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Update total price
        updateTotalPrice();

        // Setup the runnable to update the price periodically
        runnable = new Runnable() {
            @Override
            public void run() {
                updateTotalPrice();
                handler.postDelayed(this, 500);
            }
        };

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(runnable);
        loadlist();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    private int getRoleFromSharedPreferences() {
        if (getContext() != null) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("User_Login", Context.MODE_PRIVATE);
            return sharedPreferences.getInt("idUser", -1);
        }
        return -1;
    }

    private void updateTotalPrice() {
        priceTotal = 0.0;
        for (OrderDetails orderDetails : orderDetailsList) {
            priceTotal += orderDetails.getTotalPrice();
        }

        DecimalFormat decimalFormat = new DecimalFormat("đ #,###,###");
        binding.txtTotalPrice.setText(decimalFormat.format(priceTotal));

        quantityProduct = 0;
        for (OrderDetails orderDetails : orderDetailsList) {
            quantityProduct += orderDetails.getQuantity();
            binding.txtQuantityProduct.setText(String.valueOf(quantityProduct) + " Sản Phẩm");
        }
    }

    public void loadlist() {
        RecyclerView recyclerView = binding.rcvLitsOrderDetails;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize data and adapter
        idUser = getRoleFromSharedPreferences();
        orderDetailsList = orderDetailsDAO.getOrderDetailsIdUserStatus(idUser, 0);
        adapter = new OrderDetailsAdapter(getContext(), orderDetailsList, orderDetailsDAO);
        recyclerView.setAdapter(adapter);
        if(orderDetailsList.isEmpty() || (orderDetailsList == null)){
            binding.txtQuantityProduct.setText( "0 Sản Phẩm");
        }
    }

    public void loadAddrest() {
        final UserDAO userDAO = new UserDAO(getContext());
        final User user = userDAO.getUserByID(idUser);

        binding.txtNameOrderCurent.setText(user.getName());
        binding.txtPhoneOrderCurent.setText("0" + String.valueOf(user.getPhone()));
        binding.txtAddressOrderCurent.setText(user.getAddress());
    }
}
