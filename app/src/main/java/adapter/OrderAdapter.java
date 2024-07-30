package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_1.R;

import java.util.List;

import dao.OrderDAO;
import dao.ShopDAO;
import dao.UserDAO;
import model.Order;
import model.Shop;
import model.User;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private List<Order> orderList;
    private OrderDAO orderDAO;

    public OrderAdapter(Context context, List<Order> orderList, OrderDAO orderDAO) {
        this.context = context;
        this.orderList = orderList;
        this.orderDAO = orderDAO;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        Order order = orderList.get(position);

        Shop shop = new Shop();
        User user = new User();

        ShopDAO shopDAO = new ShopDAO(context);
        UserDAO userDAO = new UserDAO(context);

        shop = shopDAO.getShopByIdShop(order.getIdShop());
        user = userDAO.getUserByID(order.getIdUser());

        shop.setIdShop(order.getIdShop());
        user.setIdUser(order.getIdUser());


        holder.txt_shop_order.setText(shop.getName());
        holder.txt_status_order.setText(order.getStatus());
        holder.txt_id_order.setText("Đơn Hàng số: " + order.getIdOrder());
        holder.txt_name_user_order.setText("Người Mua: " + user.getName());
        holder.txt_quantity_order.setText("Số Lượng: " + order.getQuantity());
        holder.txt_total_price_order.setText("Thành tiền: " + order.getTotalPrice() + " VND");
        holder.txt_date_order.setText("Ngày: " + order.getDate());
        holder.img_image_order.setImageBitmap(convertByteArrayToBitmap(shop.getImage()));





    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_shop_order, txt_status_order, txt_id_order, txt_name_user_order, txt_name_order, txt_quantity_order, txt_total_price_order,txt_date_order;
        ImageView img_image_order;
        Button btn_canel_order, btn_confirm_order;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_shop_order = itemView.findViewById(R.id.txt_shop_order);
            txt_status_order = itemView.findViewById(R.id.txt_status_order);
            txt_id_order = itemView.findViewById(R.id.txt_id_order);
            txt_name_user_order = itemView.findViewById(R.id.txt_name_user_order);
            txt_name_order = itemView.findViewById(R.id.txt_name_order);
            txt_quantity_order = itemView.findViewById(R.id.txt_quantity_order);
            txt_total_price_order = itemView.findViewById(R.id.txt_total_price_order);
            txt_date_order = itemView.findViewById(R.id.txt_date_order);
            img_image_order = itemView.findViewById(R.id.img_image_order);
            btn_canel_order = itemView.findViewById(R.id.btn_canel_order);
            btn_confirm_order = itemView.findViewById(R.id.btn_confirm_order);
        }
    }
    private Bitmap convertByteArrayToBitmap(byte[] imageBytes) {
        if (imageBytes != null && imageBytes.length > 0) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } else {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.side_nav_bar);
        }
    }
}
