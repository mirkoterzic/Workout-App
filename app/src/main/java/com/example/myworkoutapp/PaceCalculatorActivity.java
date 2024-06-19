package com.example.myworkoutapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.view.MotionEvent;
import android.graphics.Rect;

import androidx.appcompat.app.AppCompatActivity;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pace_calculator);

        distanceInput =findViewById(R.id.distanceInput);
        hourInputTime =findViewById(R.id.hourInputTime);
        minuteInputTime =findViewById(R.id.minuteInputTime);
        secondInputTime =findViewById(R.id.secondInputTime);


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

    public void showHourPicker(View V){
        final NumberPicker hourPicker= new NumberPicker(this);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(99);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Hours");
        builder.setView(hourPicker);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hourInputTime.setText(String.format("%02d",hourPicker.getValue()));
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.show();
    }
    public void showMinutePicker(View v) {
        final NumberPicker minutePicker = new NumberPicker(this);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Minutes");
        builder.setView(minutePicker);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                minuteInputTime.setText(String.format("%02d", minutePicker.getValue()));
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void showSecondPicker(View v) {
        final NumberPicker secondPicker = new NumberPicker(this);
        secondPicker.setMinValue(0);
        secondPicker.setMaxValue(59);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Seconds");
        builder.setView(secondPicker);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                secondInputTime.setText(String.format("%02d", secondPicker.getValue()));
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void calculatePaceAndTimes() {

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
}
