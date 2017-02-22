package com.g13.shotgun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dominic on 2/16/2017.
 */

public class CreatePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post);

        final DatePicker dp = (DatePicker) findViewById(R.id.dpdp);
        Button b = (Button) findViewById(R.id.add_button);
        TimePicker tp = (TimePicker) findViewById(R.id.tptp);
        final TextView tx = (TextView) findViewById(R.id.city);
        int day = dp.getDayOfMonth();
        int month = dp.getMonth();
        int year =  dp.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        final Date the_date = calendar.getTime();
        final Time the_time = new Time(tp.getCurrentHour(), tp.getCurrentMinute(),0);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Post p = new Post(tx.getText().toString(), the_date, the_time, "FRED");
                Intent i = new Intent(getApplicationContext(), DriveBoard.class);
                i.putExtra("the_new_post", p);
                startActivity(i);

            }
        });

    }


}
