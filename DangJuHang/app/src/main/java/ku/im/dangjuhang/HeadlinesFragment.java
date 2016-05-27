package ku.im.dangjuhang;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class HeadlinesFragment extends ListFragment {

    OnArticleSelectedListener mListner;
    HangsaAdapter adapter;
    ListView listView;
    ArrayList<Hangsa> arrayList;
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
//        setListAdapter(new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_1, NewsData.Headlines));

        arrayList = new ArrayList<Hangsa>();
        arrayList.add(new Hangsa(R.drawable.a,"1번리스트"));
        arrayList.add(new Hangsa(R.drawable.b,"2번리스트"));
        arrayList.add(new Hangsa(R.drawable.c,"3번리스트"));
        arrayList.add(new Hangsa(R.drawable.d,"4번리스트"));
        arrayList.add(new Hangsa(R.drawable.e,"5번리스트"));
        setListAdapter(new HangsaAdapter(getActivity(),android.R.layout.simple_list_item_1,arrayList));
        //HealinesFragment 자체가 listview 라 이렇게 달아줘도 됨
    }

    @Override
    public void onStart() {
        super.onStart();
        listView = getListView(); // listvew 객체를 얻음
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}