package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_1.R;

import java.util.List;

import dao.UserDAO;
import model.Shop;
import model.User;

public class ShopNewAdapter extends RecyclerView.Adapter<ShopNewAdapter.ShopViewHolder> {
    private List<User> userList;
    private UserDAO userDAO;
    private List<Shop> shopList;
    private Context context;

    public ShopNewAdapter(List<Shop> shopList, Context context) {
        this.shopList = shopList;
        this.context = context;
    }

    public ShopNewAdapter(List<User> userList, UserDAO userDAO, List<Shop> shopList, Context context) {
        this.userList = userList;
        this.userDAO = userDAO;
        this.shopList = shopList;
        this.context = context;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_manage_booth_new, parent, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Shop shop = shopList.get(position);
        holder.txtIdShop.setText("idShop: " + shop.getIdShop());
        holder.txtIdUser.setText("idUser: " + shop.getIdUser());
        holder.txtNameShop.setText("Name: " + shop.getName());
        holder.txtAddressShop.setText("Address: " + shop.getAddress());
        holder.image.setImageBitmap(convertByteArrayToBitmap(shop.getImage()));

    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        //shop
        private LinearLayout ll_manage_booth;
        private TextView txtIdShop;
        private TextView txtIdUser;
        private TextView txtNameShop;
        private TextView txtAddressShop;
        private ImageView image;


        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            // view item
            txtIdShop = itemView.findViewById(R.id.txt_idShop_new);
            txtIdUser = itemView.findViewById(R.id.txt_idUser_new);
            txtNameShop = itemView.findViewById(R.id.txt_nameShop_new);
            txtAddressShop = itemView.findViewById(R.id.txt_addressShop_new);
            image = itemView.findViewById(R.id.img_image_shop_new);


            //click item
            ll_manage_booth = itemView.findViewById(R.id.ll_magane_booth_new);
            ll_manage_booth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View dialogView = inflater.inflate(R.layout.dialog_information_user, null);
                    builder.setView(dialogView);
                }
            });

        }
    }
    // Helper method to convert byte[] to Bitmap
    private Bitmap convertByteArrayToBitmap(byte[] imageBytes) {
        if (imageBytes != null && imageBytes.length > 0) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } else {
            // Return a default image or handle null case
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.side_nav_bar);
        }
    }

}

