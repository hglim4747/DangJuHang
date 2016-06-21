package ku.im.dangjuhang.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ku.im.dangjuhang.Client;
import ku.im.dangjuhang.Hangsa;
import ku.im.dangjuhang.HangsaAdapter;
import ku.im.dangjuhang.R;

/**
 * Created by Tazo on 2016-05-26.
 */
public class MyFrag extends Fragment{
    TextView text;
    ListView listView;
    ArrayList<Hangsa> arrayList;
    HangsaAdapter adapter;
    int clickPoint =-1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.myfrag,container,false);
        init(root);
        return root;
    }
    void init(final View v){
        listView = (ListView)v.findViewById(R.id.myfrag_listview);


//        arrayList.add();
        adapter = new HangsaAdapter(getActivity(), R.id.myfrag_listview , arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("삭제");

                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View layout = inflater.inflate(R.layout.alert_dialog,(ViewGroup)v.findViewById(R.id.mainActivity));

                builder.setView(layout);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        });
    }

    void deleteMyHangsa(){

    }

}
