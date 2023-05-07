package dk.itu.moodtracker;

import java.util.ArrayList;
import java.util.Date;

public class Mood {

    private String mood;
    private String day;
    private String timeOfDay;
    private String description;

    public Mood(String newMood, String date, String tod, String des) {
        mood = newMood;
        day = date;
        timeOfDay = tod;
        description = des;
    }
        public String getMood(){
            return mood;
        }

        public String getDay(){
            return day;
    }

    public String getTOD(){
        return timeOfDay;
    }
    public String getDescription() {return description;}
}
