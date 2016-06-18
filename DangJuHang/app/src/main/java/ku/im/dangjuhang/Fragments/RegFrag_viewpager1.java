package ku.im.dangjuhang.Fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ku.im.dangjuhang.R;

/**
 * Created by user on 2016-06-02.
 */
public class RegFrag_viewpager1 extends Fragment {
    EditText name , address;
    int year, month, day, hour, minute;
    EditText startdateEdit, enddateEdit, starttimeEdit, endtimeEdit;
    Button startdatebtn, enddatebtn, starttimebtn, endtimebtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.viewpager_childview,container,false);
        init(root);
        return root;

    }
    void init(View root){
        name = (EditText)root.findViewById(R.id.regfrag_name);
        address = (EditText)root.findViewById(R.id.regfrag_Address);
        startdateEdit = (EditText)root.findViewById(R.id.regfrag_edit_startdate);
        enddateEdit = (EditText)root.findViewById(R.id.regfrag_edit_enddate);
        startdatebtn = (Button)root.findViewById(R.id.startdatebtn);
        enddatebtn = (Button)root.findViewById(R.id.enddatebtn);
        starttimeEdit = (EditText)root.findViewById(R.id.regfrag_edit_starttime);
        endtimeEdit = (EditText)root.findViewById(R.id.regfrag_edit_endtime);
        starttimebtn = (Button)root.findViewById(R.id.starttimebtn);
        endtimebtn = (Button)root.findViewById(R.id.endtimebtn);

        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        startdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), startdateSetListener, year, month, day).show();
            }
        });
        enddatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), enddateSetListener, year, month, day).show();
            }
        });
        starttimebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(getActivity(),  starttimeSetListener, hour, minute, false).show();
            }
        });
        endtimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(getActivity(), endtimeSetListener, hour, minute, false).show();
            }
        });
    }
    private DatePickerDialog.OnDateSetListener startdateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            String msg = String.format("%d / %d / %d", year,monthOfYear+1, dayOfMonth);
                startdateEdit.setText(msg);
        }
    };
    private DatePickerDialog.OnDateSetListener enddateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            String msg = String.format("%d / %d / %d", year,monthOfYear+1, dayOfMonth);
                enddateEdit.setText(msg);
        }
    };
    private TimePickerDialog.OnTimeSetListener starttimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            String msg = String.format("%d : %d", hourOfDay, minute);
                starttimeEdit.setText(msg);
        }
    };
    private TimePickerDialog.OnTimeSetListener endtimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            String msg = String.format("%d : %d", hourOfDay, minute);
                endtimeEdit.setText(msg);
        }
    };

}

