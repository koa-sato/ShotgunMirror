package com.g13.shotgun;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.AWSConfiguration;
import com.g13.shotgun.driveboard.DriveBoard;
import com.g13.shotgun.driveboard.DriveBoardDataBaseInterface;
import com.g13.shotgun.driveboard.DriveBoardPost;

import java.util.ArrayList;

public class EditPostActivity extends AppCompatActivity {
    int pos;
    DriveBoardPost p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID,
                AWSConfiguration.AMAZON_COGNITO_REGION // Region
        );
        pos = i.getIntExtra("Position", - 1);
        final DriveBoardDataBaseInterface dbi =  new DriveBoardDataBaseInterface(credentialsProvider);
        ArrayList<DriveBoardPost> the_users_posts = new ArrayList<DriveBoardPost>(dbi.get_posts());
        ArrayList<DriveBoardPost> post = new ArrayList<>();
        for(int j = 0; j < the_users_posts.size(); j++)
            if(the_users_posts.get(j).get_user().equals(User.getInstance().getUsername()))
                post.add(the_users_posts.get(j));
        p = post.get(pos);
        TextView city = (TextView) findViewById(R.id.city);
        TextView date = (TextView) findViewById(R.id.date);
        TextView num = (TextView) findViewById(R.id.num);
        num.setText(Integer.toString(p.get_num_seats()));
        if(p.get_interested_users() == null)
             num.setText("NULL");
        ListView listView = (ListView) findViewById(R.id.list);
        ListView glistView = (ListView) findViewById(R.id.glist);
        if(p.get_interested_users() != null){
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, p.get_interested_users());
        listView.setAdapter(arrayAdapter);}
        if(p.get_going_users() != null){
            ArrayAdapter<String> garrayAdapter =
                    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, p.get_going_users());
        glistView.setAdapter(garrayAdapter);}
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ViewUserActivity.class);
                i.putExtra("User", p.get_interested_users().get(position));
                i.putExtra("Going", false);
                startActivity(i);
            }
        });
        glistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ViewUserActivity.class);
                i.putExtra("User", p.get_going_users().get(position));
                i.putExtra("Going", true);
                startActivity(i);
            }
        });
        city.setText(p.get_user());
        //city.setText(p.get_city());
        date.setText(p.date_to_string());
        //num.setText(p.get_time_of_day());
        Button delete = (Button) findViewById(R.id.delete);
       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbi.delete_post(p.get_key());
                finish();
            }
        });
    }

}
