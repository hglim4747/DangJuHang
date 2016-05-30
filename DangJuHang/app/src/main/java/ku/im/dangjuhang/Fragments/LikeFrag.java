package ku.im.dangjuhang.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ku.im.dangjuhang.R;

/**
 * Created by Tazo on 2016-05-27.
 */
public class LikeFrag extends  Fragment {
    TextView text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.likefrag, container, false);
        text = (TextView) root.findViewById(R.id.test);
        text.setText("Like page");
        return root;

    }
}