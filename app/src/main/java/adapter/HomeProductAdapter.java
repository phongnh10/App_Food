package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_1.R;

import java.text.DecimalFormat;
import java.util.List;

import dao.ProductDAO;
import dao.ShopDAO;
import model.Product;

public class HomeProductAdapter extends RecyclerView.Adapter<HomeProductAdapter.ViewHolder> {
    Context context;
    List<Product> productList;
    ProductDAO productDAO;
    ShopDAO shopDAO;

    public HomeProductAdapter(Context context, List<Product> productList, ProductDAO productDAO, ShopDAO shopDAO) {
        this.context = context;
        this.productList = productList;
        this.productDAO = productDAO;
        this.shopDAO = shopDAO;
    }

    @NonNull
    @Override
    public HomeProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_home,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeProductAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.img_product_home.setImageBitmap(convertByteArrayToBitmap(product.getImage()));
        holder.txt_name.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("#,### vnđ");
        holder.txt_price.setText(decimalFormat.format(product.getPrice()));
        int idShop = product.getIdShop();
        String nameShop = shopDAO.getShopByIdShop(idShop).getName();
        holder.txt_name_shop.setText(nameShop);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_price, txt_name_shop;
        ImageView img_product_home;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name_product_home);
            txt_price = itemView.findViewById(R.id.txt_price_product_home);
            txt_name_shop = itemView.findViewById(R.id.txt_name_shop_home);
            img_product_home = itemView.findViewById(R.id.img_product_home);
        }
    }

    //img
    private Bitmap convertByteArrayToBitmap(byte[] imageBytes) {
        if (imageBytes != null && imageBytes.length > 0) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } else {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.side_nav_bar);
        }
    }
}
