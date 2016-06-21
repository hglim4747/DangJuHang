package ku.im.dangjuhang.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    String cel,explane, imguri;

    Button regbtn, canclebtn;
    EditText telEdit, explaneEdit;

    TextView imageUriText;
    Button imagebtn;

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

        imageUriText = (TextView)root.findViewById(R.id.regfrag_imageUri);
        imagebtn = (Button) root.findViewById(R.id.regfrag_imagebtn);
        imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //여기서 사진첩으로 넘김
            }
        });

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegFrag_viewpager1 regfrag1 = page1;
                name2 = regfrag1.name.getText().toString();
                address2 = regfrag1.address.getText().toString();
                startdate2 = regfrag1.startdateEdit.getText().toString();
                enddate2 = regfrag1.enddateEdit.getText().toString();
                starttime2 = regfrag1.starttimeEdit.getText().toString();
                endtime2 = regfrag1.endtimeEdit.getText().toString();
                time = startdate2 + "~" + endtime2;
                cel = telEdit.getText().toString();
                explane = explaneEdit.getText().toString();
                imguri = imageUriText.getText().toString();


                if (TextUtils.isEmpty(name2) ||TextUtils.isEmpty(address2) ||TextUtils.isEmpty(startdate2) ||TextUtils.isEmpty(enddate2) ||
                        TextUtils.isEmpty(starttime2) ||TextUtils.isEmpty(endtime2) ||TextUtils.isEmpty(cel) ||TextUtils.isEmpty(explane)){
                    Toast.makeText(getActivity(), "전부 작성하였는지 \n다시한번 확인해주세요", Toast.LENGTH_SHORT).show();
                }else {
                    if(!TextUtils.isEmpty(imguri)){ // 사용자가 이미지를 선택했을 때때

                   }
                    Hangsa hangsa = new Hangsa(name2, startdate2, enddate2, time, address2, null,
                            null, null, null, explane, null);

                    String result = new Client().RegisterEvent(hangsa);
                    if (!TextUtils.isEmpty(result)) {
                        Toast.makeText(getActivity(), "등록성공ㅎㅎ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "등록실패ㅠㅠ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        canclebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                explaneEdit.setText("");
                telEdit.setText("");
                RegFrag_viewpager1  regfrag1 = page1;
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
