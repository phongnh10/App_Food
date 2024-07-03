package fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.du_an_1.R;

import java.util.List;

import adapter.ShopAdapter;
import adapter.ShopNewAdapter;
import dao.ShopDAO;
import model.Shop;

public class ManegeBoothNewFragment extends Fragment {
    private ShopNewAdapter adapter;
    private ShopDAO shopDAO;
    RecyclerView rcv_shopListNew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manege_booth_new, container, false);

        rcv_shopListNew = view.findViewById(R.id.rcv_fragment_manage_booth_new);
        rcv_shopListNew.setLayoutManager(new LinearLayoutManager(getContext()));

        ShopDAO shopDao = new ShopDAO(getContext());
        List<Shop> shopList = shopDao.getListShopNew();

        adapter = new ShopNewAdapter(shopList, getContext());
        rcv_shopListNew.setAdapter(adapter);

        return view;
    }
}

