package ku.im.dangjuhang.Fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import ku.im.dangjuhang.R;
import ku.im.dangjuhang.ViewpagerAdapter;

/**
 * Created by Tazo on 2016-05-27.
 */
public class RegFrag extends Fragment {

    ViewPager pager;
    RegFrag_viewpager1 page1;
    RegFrag_viewpager2 page2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.regfrag, container, false);
       init(root);
        return root;
    }
    void init(View root) {

        pager = (ViewPager) root.findViewById(R.id.pager);

        /////////////////////리시트 프레그먼트 연결, pager와 연결//////////////////////
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(page1 = new RegFrag_viewpager1());
        fragmentList.add(page2 = new RegFrag_viewpager2(page1));
        ViewpagerAdapter adapter = new ViewpagerAdapter(getChildFragmentManager(), fragmentList);
        pager.setAdapter(adapter);


        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip)root.findViewById(R.id.tabs);
        tabs.setShouldExpand(true); //중간 사이즈
        tabs.setViewPager(pager);

        /////////////////////////////////////////////////////////////////////////////////
    }

}