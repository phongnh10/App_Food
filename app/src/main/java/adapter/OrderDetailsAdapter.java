package adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.du_an_1.R;
import java.text.DecimalFormat;
import java.util.List;
import dao.OrderDetailsDAO;
import model.OrderDetails;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {
    private Context context;
    private List<OrderDetails> orderDetailsList;
    private OrderDetailsDAO orderDetailsDAO;
    private int idUser;

    public OrderDetailsAdapter(Context context, List<OrderDetails> orderDetailsList, OrderDetailsDAO orderDetailsDAO) {
        this.context = context;
        this.orderDetailsList = orderDetailsList;
        this.orderDetailsDAO = orderDetailsDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetails orderDetails = orderDetailsList.get(position);


        holder.img_product.setImageBitmap(convertByteArrayToBitmap(orderDetails.getImage()));
        holder.txt_name_product.setText(orderDetails.getName());
        DecimalFormat decimalFormat = new DecimalFormat("#,###,### đ");
        holder.txt_price_product.setText(decimalFormat.format(orderDetails.getTotalPrice()));
        holder.txt_quantity_product.setText(String.valueOf(orderDetails.getQuantity()));

        idUser = getIdUserFromSharedPreferences();

        holder.img_minus_quantity_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int[] quantity = {orderDetails.getQuantity()};
                final double[] totalPrice = {orderDetails.getTotalPrice()};
                if (quantity[0] > 1) {
                    quantity[0]--;
                    totalPrice[0] = totalPrice[0] - orderDetails.getPrice();

                    // Cập nhật TextView
                    holder.txt_price_product.setText(decimalFormat.format(totalPrice[0]));
                    holder.txt_quantity_product.setText(String.valueOf(quantity[0]));

                    // Cập nhật đối tượng OrderDetails
                    orderDetails.setQuantity(quantity[0]);
                    orderDetails.setTotalPrice(totalPrice[0]);

                    // Cập nhật cơ sở dữ liệu
                    boolean isUpdated = orderDetailsDAO.updateOrderDetails(orderDetails);
                    if (!isUpdated) {
                        Toast.makeText(view.getContext(), "Failed to update the order detail in the database", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        holder.img_plus_quantity_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int[] quantity = {orderDetails.getQuantity()};
                final double[] totalPrice = {orderDetails.getTotalPrice()};
                if (quantity[0] > 0) {
                    quantity[0]++;
                    totalPrice[0] = totalPrice[0] + orderDetails.getPrice();

                    // Cập nhật TextView
                    holder.txt_price_product.setText(decimalFormat.format(totalPrice[0]));
                    holder.txt_quantity_product.setText(String.valueOf(quantity[0]));

                    // Cập nhật đối tượng OrderDetails
                    orderDetails.setQuantity(quantity[0]);
                    orderDetails.setTotalPrice(totalPrice[0]);


                    boolean isUpdated = orderDetailsDAO.updateOrderDetails(orderDetails);
                    if (!isUpdated) {
                        Toast.makeText(view.getContext(), "Giảm số lượng sản phẩm faild", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        int currentPosition = holder.getAdapterPosition();
        holder.img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isDeleted = orderDetailsDAO.deleteOrderDetails(orderDetails.getIdOrderDetails());
                if (isDeleted) {
                    orderDetailsList.remove(currentPosition);
                    notifyItemRemoved(currentPosition);
                    notifyItemRangeChanged(currentPosition, orderDetailsList.size());
                } else {
                    Toast.makeText(view.getContext(), "Xoá sản phẩm thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });


   }

    @Override
    public int getItemCount() {
        return orderDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_product, img_back, img_minus_quantity_product, img_plus_quantity_product;
        TextView txt_name_product, txt_price_product, txt_quantity_product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_product = itemView.findViewById(R.id.img_product_details);
            txt_name_product = itemView.findViewById(R.id.txt_name_product_details);
            txt_price_product = itemView.findViewById(R.id.txt_price_product_details);
            img_back = itemView.findViewById(R.id.img_back_details);
            txt_quantity_product = itemView.findViewById(R.id.txt_quantity_product_details);
            img_minus_quantity_product = itemView.findViewById(R.id.img_minus_quantity_product_details);
            img_plus_quantity_product = itemView.findViewById(R.id.img_plus_quantity_product_details);
        }
    }


    private Bitmap convertByteArrayToBitmap(byte[] imageBytes) {
        if (imageBytes != null && imageBytes.length > 0) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } else {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.side_nav_bar);
        }
    }

    public int getIdUserFromSharedPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("User_Login", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("idUser", -1);
    }
}
