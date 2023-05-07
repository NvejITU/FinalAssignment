package dk.itu.moodtracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrackerViewModel extends ViewModel {

    private static MutableLiveData<DB> activities;
    private MutableLiveData<Integer> mSize = new MutableLiveData<>();
    private MutableLiveData<Integer> loggedMoods = new MutableLiveData<>();
    private MutableLiveData<Integer> loggedHabits = new MutableLiveData<>();

    public TrackerViewModel(){
        activities = new MutableLiveData<>();
        activities.setValue(new DB());
        mSize.setValue(activities.getValue().size());
        loggedMoods.setValue(activities.getValue().getMoods().size());
        loggedHabits.setValue(activities.getValue().getHabits().size());
    }

    public void initialize(Context context){
        activities.getValue().initialize(context);
    }

    public MutableLiveData<DB> getActivities(){
        return activities;
    }

    public void addMood(String mood, String day, String tod, String des){
        DB temp = activities.getValue();
        temp.addMood(mood, day, tod, des);
        updateSize();
    }

    public void addHabit(String habit, String day, String desc){
        DB temp = activities.getValue();
        temp.addHabit(habit, day, desc);
        activities.setValue(temp);
    }

    public ArrayList<Mood> getMoods(){
        return activities.getValue().getMoods();
    }

    public ArrayList<Habit> getHabits(){
        return activities.getValue().getHabits();
    }

    public LiveData<Integer> size(){
        return mSize;
    }

    public void updateSize() {
        mSize.setValue(activities.getValue().size());
    }

    public LiveData<Integer> moodsSize() {
        return loggedMoods;
    }
    public void updateMoodsSize() {
        loggedMoods.setValue(activities.getValue().getMoods().size());
    }

    public LiveData<Integer> habitsSize() {
        return loggedHabits;
    }

    public void updateHabitsSize() {
        loggedHabits.setValue(activities.getValue().getHabits().size());
    }
}
