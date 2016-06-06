package ku.im.dangjuhang.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ku.im.dangjuhang.R;

/**
 * Created by user on 2016-06-02.
 */
public class RegFrag_viewpager2 extends Fragment{
    EditText explane, tel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.viewpager_childview2,container,false);
        init(root);
        return root;
    }
    void init(View root){
        explane = (EditText)root.findViewById(R.id.regfrag_Explane);
        tel = (EditText)root.findViewById(R.id.regfrag_tel);

    }
}
