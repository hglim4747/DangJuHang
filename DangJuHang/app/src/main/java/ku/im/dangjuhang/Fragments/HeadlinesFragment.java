package ku.im.dangjuhang.Fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import ku.im.dangjuhang.Client;
import ku.im.dangjuhang.Hangsa;
import ku.im.dangjuhang.HangsaAdapter;
import ku.im.dangjuhang.MainActivity;
import ku.im.dangjuhang.SeoulXMLParser;

public class HeadlinesFragment extends ListFragment {

    OnArticleSelectedListener mListner;
    HangsaAdapter adapter;
    ListView listView;
    ArrayList<Hangsa> arrayList = new ArrayList<Hangsa>();
    String Url;
    public static SeoulXMLParser mXMLParser;
    public static int type = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if( activity instanceof OnArticleSelectedListener) {
            mListner = (OnArticleSelectedListener)activity;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mListner.onArticleSelected(position);
        listView.setItemChecked(position,true);

    }//itemClick 리스너를 달아줌

    public interface OnArticleSelectedListener{
        public void onArticleSelected(int position);
    }//외않되?

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readxml();
    }

    public void readxml() {
        try {
            String serviceUrl = "http://openAPI.seoul.go.kr:8088/";
            String serviceKey = "70515570556d697235305778726e58/"; // 필수
            String serviceType = "xml/"; // 필수
            String service ="SearchConcertDetailService/"; //필수
            String start_index = "1/"; //필수
            String end_index = "10"; //필수

            try {
                Url = serviceUrl+serviceKey+serviceType+service+start_index+end_index;
                mXMLParser = new SeoulXMLParser(Url,handler);
                Thread thread = new Thread(mXMLParser);
                thread.start();
            }catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            arrayList = mXMLParser.getArray();

            ArrayList<Hangsa> list = new Client().GetAllEvent();
            if(list == null)return;
            list = new Client().GetAllEvent();

            arrayList.addAll(list);

            if (type == 1)
            {
                ArrayList<String> as = new Client().GetAllLikeEvent();
                for(int j=arrayList.size()-1; j>=0; j--)
                {
                    boolean exist = false;
                    for(int i=0; i<as.size(); i++)
                    {
                        if(as.get(i).equals(arrayList.get(j).getMcultcode()))
                        {
                            exist = true;
                            break;
                        }
                    }
                    if(exist == false) arrayList.remove(j);
                }
            }

            for(int i=0; i<list.size(); i++)
                list.get(i).updatePlace();

            if(type == 2)
            {
                for(int i=0; i<arrayList.size(); i++)
                {
                    for(int j = i+1; j<arrayList.size(); j++)
                    {
                        Hangsa a = arrayList.get(i);
                        Hangsa b = arrayList.get(j);
                        double aa = getDistance(a);
                        double bb = getDistance(b);
                        if(aa > bb)
                        {
                            arrayList.set(i, b);
                            arrayList.set(j, a);
                        }
                    }
                }
            }

            adapter = new HangsaAdapter(getActivity(),android.R.layout.simple_list_item_1,arrayList);
            setListAdapter(adapter);
        }
    };

    double getDistance(Hangsa p)
    {
        double dx = 0, dy = 0;
        if(p.x != 0 && p.y != 0) {
            dx = (p.x - MainActivity.ln) * 92;
            dy = (p.y - MainActivity.la) * 114;
        }
        else
        {
            dx = 1000;
            dy = 1000;
        }

        double dis = Math.sqrt(dx * dx + dy * dy);
        return dis;
    }
    @Override
    public void onStart() {
        super.onStart();
        getListView().setDivider(new ColorDrawable(0x99fff5b5));// listvew 객체를 얻음1
        listView = getListView();
        //listView.setDivider(new ColorDrawable(getActivity().getResources().getColor(R.color.list,)));
        listView.setDividerHeight(50);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}
