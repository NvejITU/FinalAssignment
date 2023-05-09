package dk.itu.moodtracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class LogHabitFragment extends Fragment {

    private static TrackerViewModel viewModel;
    Button logHabit;
    ImageButton newImage;
    TextView habit, description;
    private int logged_habits;
    Spinner daySpinner, monthSpinner, yearSpinner;

    private final static String ImageIntent= "android.media.action.IMAGE_CAPTURE";
    private final static int IMAGE_REQUEST= 2;

    private ImageView image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        final View v = inflater.inflate(R.layout.habit_fragment,container,false);
        viewModel = new ViewModelProvider(this).get(TrackerViewModel.class);
        logHabit = v.findViewById(R.id.log_habit_button);
        habit = v.findViewById(R.id.habit_completed);
        daySpinner = v.findViewById(R.id.Hday_spinner);
        monthSpinner = v.findViewById(R.id.Hmonth_spinner);
        yearSpinner = v.findViewById(R.id.Hyear_spinner);
        description = v.findViewById(R.id.description_habit);
        logged_habits = viewModel.habitsSize().getValue();

        viewModel.habitsSize().observe(getViewLifecycleOwner(), size -> {
            logged_habits = size;
            ((TextView) v.findViewById(R.id.habits_logged)).setText(Integer.toString(logged_habits));
        });

        image= v.findViewById(R.id.photo_view);
        newImage = v.findViewById(R.id.imageButton);


        Spinner[] spinners = {daySpinner, monthSpinner, yearSpinner};
        int[] spinnerArrays = {R.array.day_options, R.array.month_options, R.array.year_options};

        for (int i = 0; i < spinners.length; i++) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(), spinnerArrays[i], android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinners[i].setAdapter(adapter);
        }

        logHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nHabit = habit.getText().toString();
                String date = daySpinner.getSelectedItem().toString() + " " +
                        monthSpinner.getSelectedItem().toString() + " " +
                        yearSpinner.getSelectedItem().toString();
                String desc = description.getText().toString();

                if (!(nHabit.equals("")) && !(date.equals(""))) {
                    viewModel.addHabit(nHabit, date, desc, image);
                    viewModel.updateHabitsSize();
                    Toast.makeText(requireActivity(), "A new habit has been logged", Toast.LENGTH_SHORT).show();
                    habit.setText("");
                    description.setText("");
                    daySpinner.setSelection(0);
                    monthSpinner.setSelection(0);
                    yearSpinner.setSelection(0);
                    habit.onEditorAction(EditorInfo.IME_ACTION_DONE);
                } else {
                    Toast.makeText(requireActivity(), "Please provide a habit and a date", Toast.LENGTH_SHORT).show();
                }
            }
        });

        newImage.setOnClickListener(view -> {
            Intent intent= new Intent(ImageIntent);
            startActivityForResult(intent, IMAGE_REQUEST);
        });
        
        return v;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_REQUEST) {
                Bundle extras= intent.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                image.setVisibility(View.VISIBLE);
                image.setImageBitmap(imageBitmap);
            }
        }
    }
}
