package xunsky.utils.android_utils.random;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import xunsky.utils.android_utils.random.RandomUtils;

public class RamdonColorFragment extends Fragment {
    public static RamdonColorFragment newInstance(){
        return new RamdonColorFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView iv = new ImageView(getContext());
        iv.setBackgroundColor(RandomUtils.color());
        return iv;
    }
}
