package ku.im.dangjuhang;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Tazo on 2016-05-27.
 */
public class RcmFrag extends  Fragment {
    TextView text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.rcmfrag, container, false);
        text = (TextView) root.findViewById(R.id.test);
        text.setText("Recommand page");
        return root;

    }
}