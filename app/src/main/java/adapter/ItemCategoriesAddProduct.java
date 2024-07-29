package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_1.R;

import java.util.List;

import model.Categories;

public class ItemCategoriesAddProduct extends RecyclerView.Adapter<ItemCategoriesAddProduct.AdapterViewHolder> {

    private Context context;
    private List<Categories> categoriesList;
    private OnItemClickListener listener;

    public ItemCategoriesAddProduct(Context context, List<Categories> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_categories_add_product, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        Categories categories = categoriesList.get(position);
        holder.txt_name.setText(categories.getName());

        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(categories);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        LinearLayout ll_item;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name_categories_add_product);
            ll_item = itemView.findViewById(R.id.ll_item_categories_add_product);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Categories categories);
    }
}
