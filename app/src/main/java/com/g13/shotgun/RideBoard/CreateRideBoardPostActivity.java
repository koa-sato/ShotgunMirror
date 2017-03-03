package com.g13.shotgun.rideboard;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.amazonaws.mobile.AWSConfiguration;
import com.g13.shotgun.R;
import com.g13.shotgun.signIn.SignInActivity;

public class CreateRideBoardPostActivity extends AppCompatActivity {
    static final int S_DATE_DIALOG_ID = 999;
    static final int E_DATE_DIALOG_ID = 888;
    static final int TIME_DIALOG_ID = 666;
    DatePicker tp;
    DatePicker dp;
    int day;
    int month;
    int year;
    int hour;
    int minute;
    boolean am;
    int end_day;
    int end_month;
    int end_year;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_ride);
       // final Calendar c = Calendar.getInstance();
        tp = (DatePicker) findViewById(R.id.tptp);
        dp = (DatePicker) findViewById(R.id.dpdp);
        final Button b = (Button) findViewById(R.id.add_button);
        final Button s = (Button) findViewById(R.id.sets);
        final Button se = (Button) findViewById(R.id.sete);
        //TimePicker tp = (TimePicker) findViewById(R.id.tptp);
        final TextView tx = (TextView) findViewById(R.id.city);
        day = dp.getDayOfMonth();
        month = dp.getMonth();
        year =  dp.getYear();
        end_day = dp.getDayOfMonth();
        end_month = dp.getMonth();
        end_year = dp.getYear();
        //hour = tp.getHour();
        //minute = tp.getMinute();
        //am = STUB;
        // Calendar calendar = Calendar.getInstance();
        //calendar.set(year, month, day);
        //final Date the_date = calendar.getTime();
        //final Time the_time = new Time(tp.getCurrentHour(), tp.getCurrentMinute(),0);

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s.setText("Change Start Date");

                showDialog(S_DATE_DIALOG_ID);


            }
        });

        se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                se.setText("Change End Date");

                showDialog(E_DATE_DIALOG_ID);

            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialog(DATE_DIALOG_ID);
                String identityId = "";
                SharedPreferences prefs = getSharedPreferences("com.amazonaws.android.auth", SignInActivity.MODE_PRIVATE);
                identityId = prefs.getString(AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID + ".identityId", null);
                RideBoardPost p = new RideBoardPost(tx.getText().toString() ,month, day, year, 10, 12, 0, identityId,
                end_year, end_month, end_day);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("the_new_post", p);
                setResult(RideBoard.RESULT_OK, returnIntent);
                finish();
            }
        });

    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case S_DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);

            case E_DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListenerE,
                        end_year, end_month,end_day);

            // case TIME_DIALOG_ID:
            //   return new TimePickerDialog(this, TimePickerFragment, hour, minute, am);
        }
        return null;
    }

   /* public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
        }


    }*/

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;


            // set selected date into textview
            tp.updateDate(year, month, day);

        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListenerE
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            end_year = selectedYear;
            end_month = selectedMonth;
            end_day = selectedDay;


            // set selected date into textview
            tp.updateDate(end_year, end_month, end_day);

        }
    };


}
