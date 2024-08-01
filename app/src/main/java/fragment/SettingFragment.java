package fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.du_an_1.LoginActivity;
import com.example.du_an_1.R;

public class SettingFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);

        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
}