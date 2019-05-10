package xunsky.utils.android_utils.random;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class RandomColorFragment extends Fragment {
    public static RandomColorFragment newInstance(){
        return new RandomColorFragment();
    }

    private int color=RandomUtils.color();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView iv = new ImageView(getContext());
        iv.setBackgroundColor(color);
        return iv;
    }
}
