package dk.itu.moodtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import dk.itu.moodtracker.database.DBSchema;

public class Tracker extends AppCompatActivity {

    Button logActivity, listActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tracker);
        DB.initialize(this);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        logActivity = findViewById(R.id.log_activity_button);
        listActivities = findViewById(R.id.list_activities_button);


        logActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tracker.this, LogActivity.class);
                startActivity(intent);
            }
        });

        listActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tracker.this, ActivitiesList.class);
                startActivity(intent);
            }
        });
    }
}
