package com.g13.shotgun.rideboard;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.g13.shotgun.R;
import com.g13.shotgun.User;

public class CreateRideBoardPostActivity extends AppCompatActivity {
    static final int S_DATE_DIALOG_ID = 999;
    static final int E_DATE_DIALOG_ID = 888;
    DatePicker tp;
    DatePicker dp;
    Spinner bags;
    int day;
    int month;
    int year;
    int end_day;
    int end_month;
    int end_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_ride);
        tp = (DatePicker) findViewById(R.id.tptp);
        dp = (DatePicker) findViewById(R.id.dpdp);
        final Button b = (Button) findViewById(R.id.add_button);
        final Button s = (Button) findViewById(R.id.sets);
        final Button se = (Button) findViewById(R.id.sete);
        bags = (Spinner) findViewById(R.id.bags);

        final TextView tx = (TextView) findViewById(R.id.city);
        day = dp.getDayOfMonth();
        month = dp.getMonth();
        year =  dp.getYear();
        end_day = dp.getDayOfMonth();
        end_month = dp.getMonth();
        end_year = dp.getYear();

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bags_choices, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        bags.setAdapter(adapter);

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
                RideBoardPost p = new RideBoardPost(tx.getText().toString() ,month, day, year, 10, 12, 0, User.getInstance().getUsername(),

                        end_year, end_month, end_day, "Morning");

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
