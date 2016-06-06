package ku.im.dangjuhang.Fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import ku.im.dangjuhang.Hangsa;
import ku.im.dangjuhang.HangsaAdapter;
import ku.im.dangjuhang.MainActivity;
import ku.im.dangjuhang.R;
import ku.im.dangjuhang.SeoulXMLParser;

public class HeadlinesFragment extends ListFragment {

    OnArticleSelectedListener mListner;
    HangsaAdapter adapter;
    ListView listView;
    ArrayList<Hangsa> arrayList = new ArrayList<Hangsa>();
    String Url;
    public static SeoulXMLParser mXMLParser;

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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readxml();

        //arrayList.add(new Hangsa(R.drawable.a,"1번리스트"));

        //HealinesFragment 자체가 listview 라 이렇게 달아줘도 됨
    }

    public void readxml() {
        try {
            String serviceUrl = "http://openAPI.seoul.go.kr:8088/";
            String serviceKey = "70515570556d697235305778726e58/"; // 필수
            String serviceType = "xml/"; // 필수
            String service ="SearchConcertDetailService/"; //필수
            String start_index = "1/"; //필수
            String end_index = "8"; //필수

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
            adapter = new HangsaAdapter(getActivity(),android.R.layout.simple_list_item_1,arrayList);
            setListAdapter(adapter);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        listView = getListView(); // listvew 객체를 얻음1
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}