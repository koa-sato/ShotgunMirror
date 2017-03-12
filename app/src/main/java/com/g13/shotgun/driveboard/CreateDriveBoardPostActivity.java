package com.g13.shotgun.driveboard;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.AWSConfiguration;
import com.g13.shotgun.R;
import com.g13.shotgun.signIn.SignInActivity;
import com.g13.shotgun.User;

import java.util.ArrayList;

public class CreateDriveBoardPostActivity extends AppCompatActivity {
    static final int DATE_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID = 666;
    DatePicker tp;
    DatePicker dp;
    Spinner seats;
    Spinner time;
    int day;
    int month;
    int year;
    int hour;
    int minute;
    boolean am;
    int num;
    EditText e_num;
    String t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post);
        tp = (DatePicker) findViewById(R.id.tptp);
        dp = (DatePicker) findViewById(R.id.dpdp);
        final Button b = (Button) findViewById(R.id.add_button);
        final Button s = (Button) findViewById(R.id.set);
        seats = (Spinner) findViewById(R.id.seats);
        time = (Spinner) findViewById(R.id.time);
        ArrayAdapter<CharSequence> timeadapter = ArrayAdapter.createFromResource(this, R.array.Times, android.R.layout.simple_spinner_item);
        final TextView tx = (TextView) findViewById(R.id.city);
        day = dp.getDayOfMonth();
        month = dp.getMonth();
        year =  dp.getYear();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.seat_choices, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        seats.setAdapter(adapter);
        time.setAdapter(timeadapter);

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
                if (tx.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter All The Information", Toast.LENGTH_LONG);
                    return;
                }
                String identityId = "";
                t = time.getSelectedItem().toString();
                num = Integer.parseInt(seats.getSelectedItem().toString());
                SharedPreferences prefs = getSharedPreferences("com.amazonaws.android.auth", SignInActivity.MODE_PRIVATE);
                identityId = prefs.getString(AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID + ".identityId", null);

                DriveBoardPost p = new DriveBoardPost(tx.getText().toString()
                        ,month, day, year, 10, 12, 0, User.getInstance().getUsername(), num,t);

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
        }
        return null;
    }



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
