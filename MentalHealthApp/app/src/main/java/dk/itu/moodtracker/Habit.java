package dk.itu.moodtracker;

import android.media.Image;

import java.util.Date;

public class Habit {

    private String habit;
    private String day;
    private String description;
    private byte[] image;

    public Habit(String newHabit, String date, String desc){
        habit = newHabit;
        day = date;
        description = desc;
    }

    public String getHabit(){
        return habit;
    }

    public String getDay(){
        return day;
    }
    public String getDescription(){ return description; }

    public void addPicture(byte[] nImage){
        image = nImage;
    }

    public byte[] getImage(){
        return image;
    }
}
