package ku.im.dangjuhang.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ku.im.dangjuhang.Client;
import ku.im.dangjuhang.DownloadImageTask;
import ku.im.dangjuhang.Hangsa;
import ku.im.dangjuhang.R;
import ku.im.dangjuhang.SeoulXMLParser;

public class ArticleFragment extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    String Url;
    Hangsa hangsa;
    ArrayList<Hangsa> arrayList;
    //public static SeoulXMLParser mXMLParser;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
    public static ArticleFragment newInstance(int position){
        ArticleFragment articleFragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ArticleFragment.ARG_POSITION, position);
        articleFragment.setArguments(args);
        return articleFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
// return super.onCreateView(inflater, container, savedInstanceState);

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        hangsa = new Hangsa();
        arrayList =  new ArrayList<Hangsa>();
       // hangsa = arrayList.get(mCurrentPosition);

        return inflater.inflate(R.layout.fragment2, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            updateArticleView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            updateArticleView(mCurrentPosition);

        }
    }

    public void updateArticleView(int position) {

        if(getActivity() != null) {
            //여기서 position에 해당하는 정보를 가져와서
            //arraylist.get[position]하면 되는데....

            ImageView articleimg = (ImageView) getActivity().findViewById(R.id.fragment_img);
            TextView articletext = (TextView) getActivity().findViewById(R.id.fragment_text);

            //hangsa = mXMLParser.getHansa(position);
            //hangsa.getTitle() 등등 해주며 ㄴ되는건데...
            //readxml(position);

            mCurrentPosition = position;
            new DownloadImageTask(articleimg).execute(SeoulXMLParser.getArray().get(position).getMmain_img());
            articletext.setText(SeoulXMLParser.getArray().get(position).getMtitle());
            Toast.makeText(getActivity(), SeoulXMLParser.getArray().get(position).getMcultcode(),Toast.LENGTH_SHORT).show();

            // Toast.makeText(getActivity(), Integer.toString(mCurrentPosition), Toast.LENGTH_SHORT).show();


            //position 받아ㅓ 인텐트 넘겨받은거냐
        }
    }

//    public void readxml(int pos) {
//        try {
//            String serviceUrl = "http://openAPI.seoul.go.kr:8088/";
//            String serviceKey = "70515570556d697235305778726e58/"; // 필수
//            String serviceType = "xml/"; // 필수
//            String service ="SearchConcertDetailService/"; //필수
//            String start_index = Integer.toString(pos) + "/"; //필수
//            String end_index = Integer.toString(pos); //필수
//
//            try {
//                Url = serviceUrl+serviceKey+serviceType+service+start_index+end_index;
//                mXMLParser = new SeoulXMLParser(Url,handler);
//                Thread thread = new Thread(mXMLParser);
//                thread.start();
//            }catch (Exception e) {
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
//        }
//    };

}