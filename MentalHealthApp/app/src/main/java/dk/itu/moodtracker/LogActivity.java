package dk.itu.moodtracker;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class LogActivity extends AppCompatActivity {
    private FragmentManager fm;
    Fragment fragmentMood, fragmentHabit;
    BottomNavigationView botNav;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        DB.initialize(this);
        setContentView(R.layout.log_activity);
        fm = getSupportFragmentManager();
        setUpFragments();
        setUpBottomNavigationView();
        if (botNav.getSelectedItemId() == R.id.log_mood){
            botNav.setSelectedItemId(R.id.log_mood);
        } else {
            botNav.setSelectedItemId(R.id.log_habit);
        }
    }

    private void setUpFragments() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (fragmentMood == null) {
                fragmentMood = new LogMoodFragment();
                fm.beginTransaction()
                        .add(R.id.container_log_activity_landscape, fragmentMood)
                        .commit();
            }
            if (fragmentHabit == null) {
                fragmentHabit = new LogHabitFragment();
                fm.beginTransaction()
                        .add(R.id.container_log_activity_landscape, fragmentHabit)
                        .commit();
            }
        } else {
            if (fragmentMood == null) {
                fragmentMood = new LogMoodFragment();
                fm.beginTransaction()
                        .add(R.id.container_log_activity, fragmentMood)
                        .commit();
            } else {
                fm.beginTransaction()
                        .show(fragmentMood)
                        .commit();
            }
            if (fragmentHabit == null) {
                fragmentHabit = new LogHabitFragment();
                fm.beginTransaction()
                        .add(R.id.container_log_activity, fragmentHabit)
                        .commit();
            } else {
                fm.beginTransaction()
                        .hide(fragmentHabit)
                        .commit();
            }
        }
    }

    private void setUpBottomNavigationView() {
        botNav = findViewById(R.id.bottomNavigation_log);
        botNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.log_mood:
                        if (fragmentMood == null) {
                            fragmentMood = new LogMoodFragment();
                            fm.beginTransaction()
                                    .add(R.id.container_log_activity, fragmentMood)
                                    .commit();
                        } else {
                            fm.beginTransaction()
                                    .show(fragmentMood)
                                    .commit();
                        }
                        if (fragmentHabit != null) {
                            fm.beginTransaction()
                                    .hide(fragmentHabit)
                                    .commit();
                        }
                        return true;
                    case R.id.log_habit:
                        if (fragmentHabit == null) {
                            fragmentHabit = new LogHabitFragment();
                            fm.beginTransaction()
                                    .add(R.id.container_log_activity, fragmentHabit)
                                    .commit();
                        } else {
                            fm.beginTransaction()
                                    .show(fragmentHabit)
                                    .commit();
                        }
                        if (fragmentMood != null) {
                            fm.beginTransaction()
                                    .hide(fragmentMood)
                                    .commit();
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_log, menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        return true;
    }
}