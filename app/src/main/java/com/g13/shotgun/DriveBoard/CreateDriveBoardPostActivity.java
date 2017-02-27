package com.g13.shotgun.DriveBoard;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.amazonaws.auth.AWSSessionCredentials;
import com.amazonaws.mobile.AWSConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.g13.shotgun.R;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.g13.shotgun.SignIn.SignInActivity;

public class CreateDriveBoardPostActivity extends AppCompatActivity {
    static final int DATE_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID = 666;
    DatePicker tp;
    DatePicker dp;
    int day;
    int month;
    int year;
    int hour;
    int minute;
    boolean am;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post);
        tp = (DatePicker) findViewById(R.id.tptp);
        dp = (DatePicker) findViewById(R.id.dpdp);
        final Button b = (Button) findViewById(R.id.add_button);
        final Button s = (Button) findViewById(R.id.set);
        //TimePicker tp = (TimePicker) findViewById(R.id.tptp);
        final TextView tx = (TextView) findViewById(R.id.city);
        day = dp.getDayOfMonth();
        month = dp.getMonth();
        year =  dp.getYear();
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
                s.setText("Change Date");

                showDialog(DATE_DIALOG_ID);

            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialog(DATE_DIALOG_ID);
                String identityId = "";

                SharedPreferences prefs = getSharedPreferences("com.amazonaws.android.auth", SignInActivity.MODE_PRIVATE);
                identityId = prefs.getString("us-west-2:62219bfc-e563-454f-b3a5-4c36c1853c14.identityId", null);

                DriveBoardPost p = new DriveBoardPost(tx.getText().toString() ,month, day, year, 10, 12, 0, identityId);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("the_new_post", p);
                setResult(DriveBoard.RESULT_OK, returnIntent);
                finish();
            }
        });

    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);

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


}
