package dk.itu.moodtracker;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.ImageView;

import java.util.Date;

public class Habit {

    private String habit;
    private String day;
    private String description;
    private ImageView image;

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

    public void addPicture(ImageView nImage){
        image = nImage;
    }

    public ImageView getImage(){
        return image;
    }
}
