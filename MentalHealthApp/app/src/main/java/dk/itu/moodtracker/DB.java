package dk.itu.moodtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import dk.itu.moodtracker.database.CursorWrapper;
import dk.itu.moodtracker.database.DBCreate;
import dk.itu.moodtracker.database.DBSchema;

public class DB extends ViewModel {

    private static SQLiteDatabase database;

    public static void initialize(Context context){
        if (database == null){
            database = new DBCreate(context.getApplicationContext()).getWritableDatabase();
        }
    }

    public ArrayList<Mood> getMoods(){
        ArrayList<Mood> moods = new ArrayList<>();
        CursorWrapper cursor = queryMoods(null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            moods.add(cursor.getMood());
            cursor.moveToNext();
        }
        cursor.close();
        return moods;
    }

    public ArrayList<Habit> getHabits(){
        ArrayList<Habit> habits = new ArrayList<>();
        CursorWrapper cursor = queryHabits(null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            habits.add(cursor.getHabit());
            cursor.moveToNext();
        }
        cursor.close();
        return habits;
    }

    public void addMood(String mood, String day, String tod, String des){
        Mood newMood = new Mood(mood, day, tod, des);
        ContentValues values = getMoodContentValues(newMood);
        database.insert(DBSchema.MoodTable.NAME, null, values);
    }

    public void addHabit(String habit, String day, String desc){
        Habit newHabit = new Habit(habit, day, desc);
        ContentValues values = getHabitContentValues(newHabit);
        database.insert(DBSchema.HabitTable.NAME, null, values);
    }

    public int size(){
        return getMoods().size();
    }

    private static ContentValues getMoodContentValues(Mood mood){
        ContentValues values = new ContentValues();
        values.put(DBSchema.MoodTable.Cols.MOOD,mood.getMood());
        values.put(DBSchema.MoodTable.Cols.DATE,mood.getDay());
        values.put(DBSchema.MoodTable.Cols.TOD,mood.getTOD());
        values.put(DBSchema.MoodTable.Cols.DESCRIPTION,mood.getDescription());

        return values;
    }

    private static ContentValues getHabitContentValues(Habit habit){
        ContentValues values = new ContentValues();
        values.put(DBSchema.HabitTable.Cols.HABIT,habit.getHabit());
        values.put(DBSchema.HabitTable.Cols.DATE,habit.getDay());
        values.put(DBSchema.HabitTable.Cols.DESCRIPTION,habit.getDescription());

        return values;
    }

    static private CursorWrapper queryMoods(String placeClause, String[] placeArgs){
        Cursor cursor = database.query(DBSchema.MoodTable.NAME,null,placeClause,
                placeArgs,null,null,null);
        return new CursorWrapper(cursor);
    }

    static private CursorWrapper queryHabits(String placeClause, String[] placeArgs){
        Cursor cursor = database.query(DBSchema.HabitTable.NAME,null,placeClause,
                placeArgs,null,null,null);
        return new CursorWrapper(cursor);
    }
}
