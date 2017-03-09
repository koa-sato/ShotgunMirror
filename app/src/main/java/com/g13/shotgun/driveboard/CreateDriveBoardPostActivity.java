package com.g13.shotgun.driveboard;

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

public class CreateDriveBoardPostActivity extends AppCompatActivity {
    static final int DATE_DIALOG_ID = 999;
    DatePicker tp;
    DatePicker dp;
    Spinner seats;
    int day;
    int month;
    int year;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post);
        tp = (DatePicker) findViewById(R.id.tptp);
        dp = (DatePicker) findViewById(R.id.dpdp);
        final Button b = (Button) findViewById(R.id.add_button);
        final Button s = (Button) findViewById(R.id.set);
        seats = (Spinner) findViewById(R.id.seats);
        final TextView tx = (TextView) findViewById(R.id.city);
        day = dp.getDayOfMonth();
        month = dp.getMonth();
        year =  dp.getYear();

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.seat_choices, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        seats.setAdapter(adapter);

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
                num = Integer.parseInt(seats.getSelectedItem().toString());

                DriveBoardPost p = new DriveBoardPost(tx.getText().toString()
                        ,month, day, year, 10, 12, 0, User.getInstance().getUsername(), num);

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
