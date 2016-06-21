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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ku.im.dangjuhang.Client;
import ku.im.dangjuhang.Hangsa;
import ku.im.dangjuhang.HangsaAdapter;
import ku.im.dangjuhang.MyRegAdapter;
import ku.im.dangjuhang.R;

/**
 * Created by Tazo on 2016-05-26.
 */
public class MyFrag extends Fragment{
    TextView text;
    ListView listView;
    ArrayList<Hangsa> hangsaList;
    ArrayList<String> templist;
    ArrayAdapter<String> adapter;
    MyRegAdapter myRegAdapter;
    AlertDialog alertDialog;
    HangsaAdapter hangsaAdapter;
    int clickPoint =-1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.myfrag,container,false);
        init(root);
        return root;
    }
    void init(View v){
    //    templist = new ArrayList<String>();
        hangsaList = new Client().GetMyEvent();
        if(hangsaList.size() <= 0){
            text = (TextView)v.findViewById(R.id.myfrag_NoHangsatext);
            text.setText("\n\n\n\n\n\n\n\n\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b" +
                    "내가 등록한 행사가\b\b\b\b\b\b\n\b\b\b\b\b\b\b\b\b\b\b" +
                    "\b\b\b\b\b\b\b\b\b\b\b\b없습니다");
        }else {
            listView = (ListView)v.findViewById(R.id.myfrag_listview);
//            for (int i = 0; i < hangsaList.size(); i++) {
//                templist.add(hangsaList.get(i).getMtitle());
//            }
//
            myRegAdapter = new MyRegAdapter(getActivity(),android.R.layout.simple_list_item_1,hangsaList);
            listView.setAdapter(myRegAdapter);
//          adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, templist);
//          listView.setAdapter(adapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    alertDialog.show();
                }
            });
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("삭제");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View layout = inflater.inflate(R.layout.alert_dialog, (ViewGroup) getActivity().findViewById(R.id.mainActivity));

        builder.setView(layout);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 삭제하는 부분
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //걍 취소
            }
        });
        alertDialog = builder.create();
    }

    void deleteMyHangsa(){

    }


}
