package dk.itu.moodtracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DBCreate extends  SQLiteOpenHelper{
    private static final int VERSION = 1;
    public static final String DATABASE_NAME = "test.db";

    public DBCreate(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + DBSchema.MoodTable.NAME + "(" +
                DBSchema.MoodTable.Cols.MOOD + ", " + DBSchema.MoodTable.Cols.DATE + ", "
                + DBSchema.MoodTable.Cols.TOD + ", " + DBSchema.MoodTable.Cols.DESCRIPTION + ")"
        );

        db.execSQL("create table " + DBSchema.HabitTable.NAME + "(" +
                DBSchema.HabitTable.Cols.HABIT + ", " + DBSchema.HabitTable.Cols.DATE + ", "
                + DBSchema.MoodTable.Cols.DESCRIPTION + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}