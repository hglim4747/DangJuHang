//package ku.im.dangjuhang.Fragments;
//
//import android.app.Activity;
//import android.app.ListFragment;
//import android.app.PendingIntent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//import ku.im.dangjuhang.Client;
//import ku.im.dangjuhang.Hangsa;
//import ku.im.dangjuhang.HangsaAdapter;
//import ku.im.dangjuhang.SeoulXMLParser;
//
//public class HeadlinesFragmentForRec extends ListFragment {
//
//    OnArticleSelectedListener mListner;
//    HangsaAdapter adapter;
//    ListView listView;
//    ArrayList<Hangsa> arrayList = new ArrayList<Hangsa>();
//    String Url;
//    public static SeoulXMLParser mXMLParser;
//    int length;
//    int total = 0;
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        if (activity instanceof OnArticleSelectedListener) {
//            mListner = (OnArticleSelectedListener) activity;
//        }
//    }
//
//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//        mListner.onArticleSelected(position);
//        listView.setItemChecked(position, true);
//
//    }//itemClick 리스너를 달아줌
//
//    public interface OnArticleSelectedListener {
//        public void onArticleSelected(int position);
//    }//외않되?
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        readxml();
//
//
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    public void readxml() {
//        try {
//            String serviceUrl = "http://openAPI.seoul.go.kr:8088/";
//            String serviceKey = "70515570556d697235305778726e58/"; // 필수
//            String serviceType = "xml/"; // 필수
//            String service = "SearchConcertDetailService/"; //필수
//            String start_index = "1/"; //필수
//            String end_index = "3"; //필수
//
//            try {
//                Url = serviceUrl + serviceKey + serviceType + service + start_index + end_index;
//                mXMLParser = new SeoulXMLParser(Url, handler);
//                Thread thread = new Thread(mXMLParser);
//                thread.start();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            arrayList = mXMLParser.getArray();
//            int age = Integer.parseInt(Client.userdata.get("age"));
//            String userage = String.valueOf(age);
//
//            JSONObject j = null;
//            int num = 0;
//            String cultcode = "";
//            try {
//                for (int i = 0; i < arrayList.size(); i++) {
//                    cultcode = arrayList.get(i).getMcultcode();
//                    j = new Client().GetLikeNum(cultcode);
//                    num = j.getInt(userage);
//                    arrayList.get(i).setLikenum(num);
//                    total = total + num;
//                } // 사용자의 연령대가 좋아요 한 숫자 넣었음. 이제 소팅만 해주면 완벽데스요~
//                sort(0, arrayList.size()-1);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            adapter = new HangsaAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayList);
//            setListAdapter(adapter);
//        }
//    };
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        listView = getListView(); // listvew 객체를 얻음1
//        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//    }
//
//
//    public void sort(int lowerIndex, int highIndex) {
//        int pivot = total/length;
//
//        int i = lowerIndex;
//        int j = highIndex;
//
//        while(i <= j) {
//            while(arrayList.get(i).getLikenum() < pivot) {
//                i++;
//            }
//            while(arrayList.get(j).getLikenum() > pivot) {
//                j++;
//            }
//
//            if(i <= j) {
//                exchangeNum(i,j);
//                i++; j--;
//            }
//        }
//        if(0 < j) {
//            sort(0,j);
//        }
//        if(i < highIndex) {
//            sort(i,highIndex);
//        }
//    }
//
//    public void exchangeNum(int i , int j) {
//        Hangsa hangsa = arrayList.get(i);
//        hangsa.setLikenum(arrayList.get(i).getLikenum());
//        arrayList.set(i, arrayList.get(j));
//        arrayList.set(j, hangsa);
//    }
//}