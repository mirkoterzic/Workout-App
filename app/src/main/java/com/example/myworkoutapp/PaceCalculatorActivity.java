package com.example.myworkoutapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.MotionEvent;
import android.graphics.Rect;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class PaceCalculatorActivity extends AppCompatActivity {


    private EditText distanceInput;


    private TextView time5k;
    private TextView time10k;
    private TextView timeHalfMarathon;
    private TextView timeMarathon;
    private Button calculateButton;
    private EditText minuteInputTime;
    private EditText hourInputTime;
    private EditText secondInputTime;
    private EditText minuteInputPace;
    private EditText secondInputPace;
    private Button resetTimeBtn;
    private Button resetPaceBtn;

    private double distance;
    private int  hour_time, minute_time, second_time, minute_pace, second_pace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pace_calculator);

        distanceInput =findViewById(R.id.distanceInput);
        hourInputTime =findViewById(R.id.hourInputTime);
        minuteInputTime =findViewById(R.id.minuteInputTime);
        secondInputTime =findViewById(R.id.secondInputTime);
        minuteInputPace=findViewById(R.id.minuteInputPace);
        secondInputPace=findViewById(R.id.secondInputPace);

        //reset Buttons
        resetTimeBtn=findViewById(R.id.resetTimeBtn);
        resetPaceBtn=findViewById(R.id.resetPaceBtn);



        time5k = findViewById(R.id.time5k);
        time10k = findViewById(R.id.time10k);
        timeHalfMarathon = findViewById(R.id.timeHalfMarathon);
        timeMarathon = findViewById(R.id.timeMarathon);
        calculateButton = findViewById(R.id.calculateButton);


        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatePaceAndTimes();
            }
        });
        resetTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTimeInputs(0);
            }
        });
        resetPaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePaceInputs(0);
            }
        });

        distanceInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE) {
                    //hide the keyboard
                    InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                    return true;
                }
                return false;

            }

        });
        distanceInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Hide the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    // Round the entered distance
                    roundDistance();
                    // You can also trigger the calculation if needed
                    calculatePaceAndTimes();
                    return true;
                }
                return false;
            }
        });
        distanceInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // Round the entered distance when the EditText loses focus
                    roundDistance();
                }
            }
        });




    }

    private void roundDistance() {
        String distanceStr = distanceInput.getText().toString();
        if (!distanceStr.isEmpty()) {
            double distanceValue = Double.parseDouble(distanceStr);
            DecimalFormat df = new DecimalFormat("#.##");
            distanceInput.setText(df.format(distanceValue));
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void showHourPicker(View v) {
        if (v instanceof EditText) {
            showHourInputDialog((EditText) v);
        }
    }
    public void showHourInputDialog(final EditText edittext){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Enter Hours");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setFilters(new InputFilter[]{new InputFilterMinMax(0,100)});

        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputText=input.getText().toString();
                int value;
                try{
                    value=Integer.parseInt(inputText);
                    if(value>100){
                        value=0;
                    }
                }
                    catch(NumberFormatException e){
                        value= 0;
                    }
                edittext.setText(String.format("%02d",value));


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void showMinuteOrSecondInputDialog(View v) {
        if (v instanceof EditText) {
            showNumberInputDialog((EditText) v);
        }
    }
    public void showNumberInputDialog(final EditText editText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Minutes/Seconds");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 59) });

        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputText = input.getText().toString();
                int value;
                try {
                    value = Integer.parseInt(inputText);
                    if (value > 59) {
                        value = 0;
                    }
                } catch (NumberFormatException e) {
                    value = 0;
                }
                editText.setText(String.format("%02d", value));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    private int getIntegerFromEditText(EditText editText) {
        String text = editText.getText().toString();
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            // Handle the case where the EditText is empty or contains non-numeric characters
            return 0; // or any other default value
        }
    }
    private double getDoubleFromEditText(EditText editText) {
        String text = editText.getText().toString();
        try {
            double value = Double.parseDouble(text);
            return Math.round(value * 100.0) / 100.0; // round to two decimal places
        } catch (NumberFormatException e) {
            // Handle the case where the EditText is empty or contains non-numeric characters
            return 0.0; // or any other default value
        }
    }

    private void calculatePaceAndTimes() {
        InitializeValues();

        double totalTimeInMinutes = hour_time * 60 + minute_time + second_time / 60.0;
        double totalPaceInMinutes = minute_pace + second_pace / 60.0;

        if (distance != 0 && totalPaceInMinutes != 0 && totalTimeInMinutes == 0) {
            // Calculate time
            totalTimeInMinutes = distance * totalPaceInMinutes;
            updateTimeInputs(totalTimeInMinutes);
        } else if (distance != 0 && totalTimeInMinutes != 0 && totalPaceInMinutes == 0) {
            // Calculate pace
            totalPaceInMinutes = totalTimeInMinutes / distance;
            updatePaceInputs(totalPaceInMinutes);
        } else if (totalPaceInMinutes != 0 && totalTimeInMinutes != 0 && distance == 0) {
            // Calculate distance
            distance = totalTimeInMinutes / totalPaceInMinutes;
            distanceInput.setText(String.format("%.2f", distance));
        }else if(totalPaceInMinutes != 0 && totalTimeInMinutes != 0 && distance != 0){
            //if nothing is empty calculate pace
            totalPaceInMinutes = totalTimeInMinutes / distance;
            updatePaceInputs(totalPaceInMinutes);
        }else {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_container));


            TextView text = layout.findViewById(R.id.custom_toast_text);
            text.setText("Invalid input!\n (must at least enter two fields)");

            // Create and show the toast
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }

        // Calculate estimated times for different distances
        calculateEstimatedTimes(totalPaceInMinutes);
    }

    private void updateTimeInputs(double totalTimeInMinutes) {
        int hours = (int) totalTimeInMinutes / 60;
        int minutes = (int) totalTimeInMinutes % 60;
        int seconds = (int) ((totalTimeInMinutes - (hours * 60 + minutes)) * 60);

        hourInputTime.setText(String.format("%02d", hours));
        minuteInputTime.setText(String.format("%02d", minutes));
        secondInputTime.setText(String.format("%02d", seconds));
    }
    private void updatePaceInputs(double totalPaceInMinutes) {
        int minutes = (int) totalPaceInMinutes;
        int seconds = (int) ((totalPaceInMinutes - minutes) * 60);

        minuteInputPace.setText(String.format("%02d", minutes));
        secondInputPace.setText(String.format("%02d", seconds));
    }

    public void InitializeValues(){
         distance = getDoubleFromEditText(distanceInput);
         hour_time=getIntegerFromEditText(hourInputTime);
         minute_time=getIntegerFromEditText(minuteInputTime);
         second_time= getIntegerFromEditText(secondInputTime);
         minute_pace= getIntegerFromEditText(minuteInputPace);
         second_pace= getIntegerFromEditText(secondInputPace);
    }

    private void calculateEstimatedTimes(double pace) {
        double timeFor5k = 5 * pace;
        double timeFor10k = 10 * pace;
        double timeForHalfMarathon = 21.0975 * pace;  // Half marathon is 21.0975 km
        double timeForMarathon = 42.195 * pace;       // Marathon is 42.195 km

        time5k.setText(formatTime(timeFor5k));
        time10k.setText(formatTime(timeFor10k));
        timeHalfMarathon.setText(formatTime(timeForHalfMarathon));
        timeMarathon.setText(formatTime(timeForMarathon));
    }

    private String formatTime(double timeInMinutes) {
        int hours = (int) timeInMinutes / 60;
        int minutes = (int) timeInMinutes % 60;
        return String.format("%d hr %02d min", hours, minutes);
    }
    private void clearTime(){
        updateTimeInputs(0);
    }


}
