package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_1.R;

import java.util.List;

import dao.ShopDAO;
import model.Shop;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {

    private List<Shop> shopList;
    private Context context;
    private ShopDAO shopDAO;

    public ShopAdapter(List<Shop> shopList, Context context) {
        this.shopList = shopList;
        this.context = context;
        this.shopDAO = new ShopDAO(context);
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
        holder.bind(shop);
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout ll_manage_booth;
        private TextView txtIdShop;
        private TextView txtIdUser;
        private TextView txtNameShop;
        private TextView txtAddressShop;
        private ImageView imgShop;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_manage_booth = itemView.findViewById(R.id.ll_magane_booth);
            txtIdShop = itemView.findViewById(R.id.txt_idShop);
            txtIdUser = itemView.findViewById(R.id.txt_idUser);
            txtNameShop = itemView.findViewById(R.id.txt_nameShop);
            txtAddressShop = itemView.findViewById(R.id.txt_addressShop);
            imgShop = itemView.findViewById(R.id.img_image_shop);

            ll_manage_booth.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    showDeleteDialog(shopList.get(position));
                    return false;
                }

            });
        }

        public void bind(Shop shop) {
            txtIdShop.setText("idShop: " + shop.getIdShop());
            txtIdUser.setText("idUser: " + shop.getIdUser());
            txtNameShop.setText("Name: " + shop.getName());
            txtAddressShop.setText("Address: " + shop.getAddress());
            imgShop.setImageBitmap(convertByteArrayToBitmap(shop.getImage()));
        }

        private void showDeleteDialog(Shop shop) {

            int status = -1;
            Shop shop1 = new Shop(shop.getIdShop(),shop.getIdUser(),shop.getAddress(),shop.getImage(),status);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Thông Báo");
            builder.setMessage("Bạn có muốn tạm ngưng hoạt động của shop  \"" + shop.getName() + "\" with idShop \"" + shop.getIdShop() + "\"không ?");

            builder.setPositiveButton("ngưng", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    boolean check = shopDAO.SuspendShop(shop1);
                    if (check) {
                        shopList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
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
