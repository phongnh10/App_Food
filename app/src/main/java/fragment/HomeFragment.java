package fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;
import android.util.Log;

import com.example.du_an_1.R;

public class HomeFragment extends Fragment {
    private ViewFlipper viewFlipper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewFlipper = view.findViewById(R.id.viewFlipper);
        if (viewFlipper != null) {
            viewFlipper.setFlipInterval(2000);
            viewFlipper.startFlipping();
        }

        return view;
    }
}
