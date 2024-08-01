package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_1.R;
import com.example.du_an_1.ShopActivity;

import java.util.List;

import dao.ShopDAO;
import model.Shop;

public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ViewHolder> {
    private Context context;
    private List<Shop> shopList;
    private ShopDAO shopDAO = new ShopDAO(context);

    public ShopItemAdapter(Context context, List<Shop> shopList, ShopDAO shopDAO) {
        this.context = context;
        this.shopList = shopList;
        this.shopDAO = shopDAO;
    }

    @NonNull
    @Override
    public ShopItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop, parent, false);
        return new ShopItemAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemAdapter.ViewHolder holder, int position) {
        Shop shop = shopList.get(position);

        if (shop.getImage() == null) {
            holder.img_image_shop.setImageResource(R.mipmap.image_shop_default);
        }
        holder.img_image_shop.setImageBitmap(convertByteArrayToBitmap(shop.getImage()));
        holder.txt_name_shop.setText(shop.getName());

        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShopActivity.class);
                intent.putExtra("idShop", shop.getIdShop());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_item;
        ImageView img_image_shop;
        TextView txt_name_shop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_item = itemView.findViewById(R.id.ll_item_shop);
            img_image_shop = itemView.findViewById(R.id.img_image_shop);
            txt_name_shop = itemView.findViewById(R.id.txt_name_shop);

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
