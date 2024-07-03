package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        View view = LayoutInflater.from(context).inflate(R.layout.item_manage_booth, parent, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Shop shop = shopList.get(position);
        holder.txtIdShop.setText("idShop: " + shop.getIdShop());
        holder.txtIdUser.setText("idUser: " + shop.getIdUser());
        holder.txtNameShop.setText("Name: " + shop.getName());
        holder.txtAddressShop.setText("Address: " + shop.getAddress());
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


        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            // view item
            txtIdShop = itemView.findViewById(R.id.txt_idShop);
            txtIdUser = itemView.findViewById(R.id.txt_idUser);
            txtNameShop = itemView.findViewById(R.id.txt_nameShop);
            txtAddressShop = itemView.findViewById(R.id.txt_addressShop);

            //click item
            ll_manage_booth = itemView.findViewById(R.id.ll_magane_booth);
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

}

