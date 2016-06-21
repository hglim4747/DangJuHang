package ku.im.dangjuhang.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ku.im.dangjuhang.Client;
import ku.im.dangjuhang.Hangsa;
import ku.im.dangjuhang.R;

/**
 * Created by user on 2016-06-02.
 */
public class RegFrag_viewpager2 extends Fragment{
    String name , address;
    String startdate, enddate, starttime, endtime , time;

    Button regbtn, canclebtn;
    EditText tel, explane;
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
        regbtn = (Button)root.findViewById(R.id.regfrag_insertbtn);
        canclebtn= (Button)root.findViewById(R.id.regfrag_cancelbtn);

        RegFrag_viewpager1  regfrag1 = new RegFrag_viewpager1();
        name= regfrag1.name.getText().toString();
        address= regfrag1.address.getText().toString();
        startdate= regfrag1.startdateEdit.getText().toString();
        enddate= regfrag1.enddateEdit.getText().toString();
        starttime= regfrag1.starttimeEdit.getText().toString();
        endtime= regfrag1.endtimeEdit.getText().toString();
        time= startdate+"~"+endtime;


        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hangsa hangsa = new Hangsa(name,startdate,enddate,time,address,null,
                        null,null,null,explane.getText().toString(),null);
                boolean result = new Client().RegisterEvent(hangsa);
                if(result == true)
                {
                    // 성공했습니다 토스트
                }
                else
                {
                    // 실패했습니다 토스트
                }
            }
        });

        canclebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                explane.setText("");
                tel.setText("");
            }
        });

    }
}
