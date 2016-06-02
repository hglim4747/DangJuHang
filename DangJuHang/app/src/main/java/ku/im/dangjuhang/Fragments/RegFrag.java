package ku.im.dangjuhang.Fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ku.im.dangjuhang.R;

/**
 * Created by Tazo on 2016-05-27.
 */
public class RegFrag extends  Fragment{
    EditText name , address, explane;
    int year, month, day, hour, minute;
    EditText startdateEdit, enddateEdit;
    Button startdatebtn, enddatebtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.regfrag, container, false);
       init(root);
        return root;
    }
    void init(View root){
        name = (EditText)root.findViewById(R.id.regfrag_name);
        address = (EditText)root.findViewById(R.id.regfrag_Address);
        explane = (EditText)root.findViewById(R.id.regfrag_Explane);
        startdateEdit = (EditText)root.findViewById(R.id.regfrag_edit_startdate);
        enddateEdit = (EditText)root.findViewById(R.id.regfrag_edit_enddate);
        startdatebtn = (Button)root.findViewById(R.id.startdatebtn);
        enddatebtn = (Button)root.findViewById(R.id.enddatebtn);


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
                new DatePickerDialog(getActivity(), dateSetListener, year, month, day).show();
            }
        });
        enddatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(getActivity(), timeSetListener, hour, minute, false).show();
            }
        });
    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            String msg = String.format("%d / %d / %d", year,monthOfYear+1, dayOfMonth);
            startdateEdit.setText(msg);
//            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            String msg = String.format("%d / %d / %d", year, hourOfDay, minute);
            enddateEdit.setText(msg);

//            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    };


}