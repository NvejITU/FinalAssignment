package dk.itu.moodtracker;

import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class LogMoodFragment extends Fragment {

    Button logMood;
    Spinner moodSpinner, daySpinner, monthSpinner, yearSpinner, todSpinner;
    TextView description;
    private int logged_moods;
    private static TrackerViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        final View v = inflater.inflate(R.layout.mood_fragment,container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(TrackerViewModel.class);

        logMood = v.findViewById(R.id.log_mood_but);
        moodSpinner = v.findViewById(R.id.mood_spinner);
        todSpinner = v.findViewById(R.id.tod_spinner);
        daySpinner = v.findViewById(R.id.day_spinner);
        monthSpinner = v.findViewById(R.id.month_spinner);
        yearSpinner = v.findViewById(R.id.year_spinner);
        description = v.findViewById(R.id.description_habit);
        logged_moods = viewModel.moodsSize().getValue();

        viewModel.moodsSize().observe(getViewLifecycleOwner(), size -> {
            logged_moods = size;
            ((TextView) v.findViewById(R.id.moods_logged)).setText(Integer.toString(logged_moods));
        });

        Spinner[] spinners = {moodSpinner, todSpinner, daySpinner, monthSpinner, yearSpinner};
        int[] spinnerArrays = {R.array.mood_options, R.array.tod_options, R.array.day_options, R.array.month_options, R.array.year_options};

        for (int i = 0; i < spinners.length; i++) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(), spinnerArrays[i], android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinners[i].setAdapter(adapter);
        }

        logMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mood = moodSpinner.getSelectedItem().toString();
                String tod = todSpinner.getSelectedItem().toString();
                String date = daySpinner.getSelectedItem().toString() + " " +
                        monthSpinner.getSelectedItem().toString() + " " +
                        yearSpinner.getSelectedItem().toString();
                String des = description.getText().toString();

                if (!(mood.equals("")) && !(tod.equals("")) && !(date.equals(""))) {
                    viewModel.addMood(mood, date, tod, des);
                    viewModel.updateMoodsSize();
                    description.setText("");
                    description.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    moodSpinner.setSelection(0);
                    todSpinner.setSelection(0);
                    daySpinner.setSelection(0);
                    moodSpinner.setSelection(0);
                    yearSpinner.setSelection(0);
                    Toast.makeText(requireActivity(), "A new mood has been logged", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(requireActivity(), "Please choose a mood, date and time of day", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}
