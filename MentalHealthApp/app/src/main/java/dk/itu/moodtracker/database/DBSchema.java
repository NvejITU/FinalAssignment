package dk.itu.moodtracker.database;

import java.security.PublicKey;
import java.sql.Blob;
import java.util.Date;

public class DBSchema {

    public static final class MoodTable {
        public static final String NAME = "Mood";
        public static final class Cols {
            public static final String MOOD = "mood";
            public static final String DATE = "date"; //should probably be changed to something other than string
            public static final String TOD = "TimeOfDay";
            public static final String DESCRIPTION = "Description";
            public static final String IMAGE = null;
        }
    }

    public static final class HabitTable {
        public static final String NAME = "Habit";
        public static final class Cols {
            public static final String HABIT = "habit";
            public static final String DATE = "date";
            public static final String DESCRIPTION = "Description";
            public static final Blob IMAGE = null;
        }
    }
}