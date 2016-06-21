package ku.im.dangjuhang.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ku.im.dangjuhang.Client;
import ku.im.dangjuhang.Hangsa;
import ku.im.dangjuhang.R;

/**
 * Created by user on 2016-06-02.
 */
public class RegFrag_viewpager2 extends Fragment{
    String name2, address2;
    String startdate2, enddate2, starttime2, endtime2 , time;
    String cel,explane;

    Button regbtn, canclebtn;
    EditText telEdit, explaneEdit;

    RegFrag_viewpager1 page1;
    public RegFrag_viewpager2(){}
    public RegFrag_viewpager2(RegFrag_viewpager1 page1)
    {
        this.page1 = page1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.viewpager_childview2,container,false);
        init(root);
        return root;
    }
    void init(View root){
        explaneEdit = (EditText)root.findViewById(R.id.regfrag_Explane);
        telEdit = (EditText)root.findViewById(R.id.regfrag_tel);
        regbtn = (Button)root.findViewById(R.id.regfrag_insertbtn);
        canclebtn= (Button)root.findViewById(R.id.regfrag_cancelbtn);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegFrag_viewpager1  regfrag1 = page1;
                name2= regfrag1.name.getText().toString();
                address2= regfrag1.address.getText().toString();
                startdate2= regfrag1.startdateEdit.getText().toString();
                enddate2= regfrag1.enddateEdit.getText().toString();
                starttime2= regfrag1.starttimeEdit.getText().toString();
                endtime2= regfrag1.endtimeEdit.getText().toString();
                time= startdate2+"~"+endtime2;
                cel = telEdit.getText().toString();
                explane = explaneEdit.getText().toString();



                Hangsa hangsa = new Hangsa(name2,startdate2,enddate2,time,address2,null,
                        null,null,null,explane,null);

                boolean result = new Client().RegisterEvent(hangsa);
                if(result == true)
                {
                    Toast.makeText(getActivity(),"등록성공ㅎㅎ",Toast.LENGTH_SHORT).show();
                    // 성공했습니다 토스트
                }
                else
                {
                    Toast.makeText(getActivity(),"등록실패ㅠㅠ",Toast.LENGTH_SHORT).show();

                    // 실패했습니다 토스트
                }

            }
        });

        canclebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                explaneEdit.setText("");
                telEdit.setText("");
                RegFrag_viewpager1  regfrag1 = new RegFrag_viewpager1();
                regfrag1.name.setText("");
                regfrag1.address.setText("");
                regfrag1.startdateEdit.setText("");
                regfrag1.enddateEdit.setText("");
                regfrag1.starttimeEdit.setText("");
                regfrag1.endtimeEdit.setText("");
            }
        });

    }
}
