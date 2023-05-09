package dk.itu.moodtracker.database;

import android.database.Cursor;
import android.widget.ImageView;

import java.sql.Blob;

import dk.itu.moodtracker.Habit;
import dk.itu.moodtracker.Mood;

public class CursorWrapper extends android.database.CursorWrapper {
    public CursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Mood getMood() {
        String mood = getString(getColumnIndex(DBSchema.MoodTable.Cols.MOOD));
        String date = getString(getColumnIndex(DBSchema.MoodTable.Cols.DATE));
        String timeOfDay = getString(getColumnIndex(DBSchema.MoodTable.Cols.TOD));
        String description = getString(getColumnIndex(DBSchema.MoodTable.Cols.DESCRIPTION));
        return new Mood(mood, date, timeOfDay, description);
    }

    public Habit getHabit() {
        String habit = getString(getColumnIndex(DBSchema.HabitTable.Cols.HABIT));
        String date = getString(getColumnIndex(DBSchema.HabitTable.Cols.DATE));
        String desc = getString(getColumnIndex(DBSchema.HabitTable.Cols.DESCRIPTION));
        String image = getString(getColumnIndex(DBSchema.HabitTable.Cols.IMAGE));
        return new Habit(habit, date, desc);
    }
}