package ku.im.dangjuhang.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONObject;

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
    ToggleButton toggleButton;
    TextView howmany;
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
        View v =inflater.inflate(R.layout.fragment2, container, false);
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        hangsa = new Hangsa();
        arrayList =  new ArrayList<Hangsa>();
        // hangsa = arrayList.get(mCurrentPosition);
        return v;
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
            ImageView articleimg = (ImageView) getActivity().findViewById(R.id.fragment_img);
            TextView articletext = (TextView) getActivity().findViewById(R.id.fragment_text);
            TextView fragment_where = (TextView) getActivity().findViewById(R.id.fragment_where);
            TextView fragment_date = (TextView)getActivity().findViewById(R.id.fragment_date);
            TextView fragment_who = (TextView)getActivity().findViewById(R.id.fragment_who);
            howmany = (TextView)getActivity().findViewById(R.id.howmany);

            mCurrentPosition = position;
            new DownloadImageTask(articleimg).execute(SeoulXMLParser.getArray().get(position).getMmain_img());
            articletext.setText(SeoulXMLParser.getArray().get(position).getMtitle());
            fragment_where.setText(SeoulXMLParser.getArray().get(position).getMplace());
            fragment_date.setText(SeoulXMLParser.getArray().get(position).getMstart_date() + " ~ " + SeoulXMLParser.getArray().get(position).getMend_date());
            fragment_who.setText(SeoulXMLParser.getArray().get(position).getMorg_link());
            howmany.setText("");
            try {
                JSONObject o = new Client().GetLikeNum(SeoulXMLParser.getArray().get(position).getMcultcode());
                String num = o.getString("total");
                howmany.setText(num+"명이 가고팡!");
            }
            catch (Exception e){
                howmany.setText("0명이 가고팡!");
            }

            Toast.makeText(getActivity(), SeoulXMLParser.getArray().get(position).getMcultcode(),Toast.LENGTH_SHORT).show();
            toggleButton = (ToggleButton)getActivity().findViewById(R.id.wanna);
            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (toggleButton.isChecked()) {
                        //좋아요 추가
                        new Client().LikeEvent(SeoulXMLParser.getArray().get(mCurrentPosition).getMcultcode());
                        Toast.makeText(getActivity(), SeoulXMLParser.getArray().get(mCurrentPosition).getMcultcode().toString(), Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject o = new Client().GetLikeNum(SeoulXMLParser.getArray().get(mCurrentPosition).getMcultcode());
                            String num = o.getString("total");
                            howmany.setText(num+"명이 가고팡!");
                        }
                        catch (Exception e){
                            howmany.setText("0명이 가고팡!");
                        }
                    } else {
                        new Client().CancelLike(SeoulXMLParser.getArray().get(mCurrentPosition).getMcultcode());
                        //좋아요 취소
                    }
                }
            });
            if(new Client().GetLike(SeoulXMLParser.getArray().get(mCurrentPosition).getMcultcode().toString())){
                toggleButton.setChecked(true);
            }
            else{
                toggleButton.setChecked(false);
            }
        }
    }
}