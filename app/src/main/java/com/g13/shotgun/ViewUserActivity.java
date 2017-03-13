package com.g13.shotgun;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.AWSConfiguration;
import com.g13.shotgun.driveboard.DriveBoardDataBaseInterface;
import com.g13.shotgun.driveboard.DriveBoardPost;

public class ViewUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID,
                AWSConfiguration.AMAZON_COGNITO_REGION // Region
        );
        final DriveBoardDataBaseInterface dbi = new DriveBoardDataBaseInterface(credentialsProvider);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView name = (TextView) findViewById(R.id.the_name);
        Intent i = getIntent();
        final String the_user_name = i.getStringExtra("User");
        name.setText(the_user_name);
        boolean g = i.getBooleanExtra("Going", false);

        final Button b = (Button) findViewById(R.id.confirm);
        b.setText("Confirm Match");
        if(g)
            b.setVisibility(View.INVISIBLE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.setVisibility(View.INVISIBLE);
                User.getInstance().getD_highlighted_post().add_going_user(the_user_name);
                User.getInstance().getD_highlighted_post().get_interested_users().remove(the_user_name);
                dbi.push_post(User.getInstance().getD_highlighted_post());


            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
