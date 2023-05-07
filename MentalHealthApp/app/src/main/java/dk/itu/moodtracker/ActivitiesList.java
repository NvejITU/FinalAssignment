package dk.itu.moodtracker;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;

public class ActivitiesList extends AppCompatActivity {

    private TextView activitiesList;
    TrackerViewModel viewModel;
    BottomNavigationView botNav;
    ActivityAdapter nAdapter;

    public ActivitiesList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        viewModel = new ViewModelProvider(this).get(TrackerViewModel.class);
        RecyclerView aList = findViewById(R.id.list_activities);
        aList.setLayoutManager((new LinearLayoutManager(this)));
        nAdapter = new ActivityAdapter();
        aList.setAdapter(nAdapter);
        setUpBottomNavigationView();

        viewModel.getActivities().observe(this, activity -> nAdapter.notifyDataSetChanged());
        nAdapter.setMoodList(viewModel.getMoods());
    }

    private void setUpBottomNavigationView() {
        botNav = findViewById(R.id.bottomNavigation_list);
        botNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mood_list:
                        nAdapter.setMoodList(viewModel.getMoods());
                        nAdapter.updateList(true);
                        return true;
                    case R.id.habit_list:
                        nAdapter.setHabitList(viewModel.getHabits());
                        nAdapter.updateList(false);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_list, menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        return true;
    }

    private class ActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView activity, date, noView;

        public ActivityHolder(View activityView) {
            super(activityView);
            noView = activityView.findViewById(R.id.activity_no);
            activity = activityView.findViewById(R.id.activity);
            date = activityView.findViewById(R.id.activity_date);
            activityView.setOnClickListener(this);
        }

        public void bindMood(Mood mood, int position) {
            noView.setText(" " + position + " ");
            activity.setText(mood.getMood());
            date.setText(mood.getDay());
        }

        public void bindHabit(Habit habit, int position) {
            noView.setText(" " + position + " ");
            activity.setText(habit.getHabit());
            date.setText(habit.getDay());
        }

        @Override
        public void onClick(View v) {
        }
    }

    private class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder> {

        private ArrayList<Mood> mMoods;
        private ArrayList<Habit> mHabits;
        private boolean isMoodSelected;

        public ActivityAdapter() {
            mMoods = new ArrayList<>();
            mHabits = new ArrayList<>();
            isMoodSelected = true;
        }

        public void updateList(boolean isMoodSelected) {
            this.isMoodSelected = isMoodSelected;
            notifyDataSetChanged();
        }

        public void setMoodList(ArrayList<Mood> moods) {
            this.mMoods = moods;
            notifyDataSetChanged();
        }

        public void setHabitList(ArrayList<Habit> habits) {
            this.mHabits = habits;
            notifyDataSetChanged();
        }

        @Override
        public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(ActivitiesList.this);
            View v = layoutInflater.inflate(R.layout.one_row, parent, false);
            return new ActivityHolder(v);
        }

        @Override
        public void onBindViewHolder(ActivityHolder holder, int position){

            if (isMoodSelected) {
                Mood mMood = mMoods.get(position);
                holder.bindMood(mMood,position);
            } else {
                Habit mHabit = mHabits.get(position);
                holder.bindHabit(mHabit,position);
            }
        }

        @Override
        public int getItemCount(){
            return isMoodSelected ? mMoods.size() : mHabits.size();
        }
    }
}
