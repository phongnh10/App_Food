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

import com.example.du_an_1.LoginActivity;
import com.example.du_an_1.MainActivity;
import com.example.du_an_1.ProductActivity;
import com.example.du_an_1.R;

import java.text.DecimalFormat;
import java.util.List;

import dao.ProductDAO;
import model.Product;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Context context;
    List<Product> productList;
    ProductDAO productDAO;

    public SearchAdapter(Context context, List<Product> productList, ProductDAO productDAO) {
        this.context = context;
        this.productList = productList;
        this.productDAO = productDAO;
    }
    public void updateProductList(List<Product> newProductList) {
        this.productList = newProductList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.img.setImageBitmap(convertByteArrayToBitmap(product.getImage()));
        holder.txt_name.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("đ #,###,###");
        holder.txt_price.setText(decimalFormat.format(product.getPrice()));

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("productId", product.getIdProduct());
                // Thêm các dữ liệu khác nếu cần
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name,txt_price;
        ImageView img;
        LinearLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name_product_search);
            txt_price = itemView.findViewById(R.id.txt_price_product_search);
            img = itemView.findViewById(R.id.img_product_search);
            item = itemView.findViewById(R.id.ll_item_product_search);
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
