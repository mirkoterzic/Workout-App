package com.example.myworkoutapp;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class PaceCalculatorActivity extends AppCompatActivity {


    private EditText distanceInput;


    private TextView time5k;
    private TextView time10k;
    private TextView timeHalfMarathon;
    private TextView timeMarathon;
    private Button calculateButton;
    private EditText minuteInput;
    private EditText hourInput;
    private EditText secondInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pace_calculator);

        distanceInput = findViewById(R.id.distanceInput);
        hourInput=findViewById(R.id.hourInput);
        minuteInput=findViewById(R.id.minuteInput);
        secondInput=findViewById(R.id.secondInput);


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
                hourInput.setText(String.format("%02d",hourPicker.getValue()));
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
                minuteInput.setText(String.format("%02d", minutePicker.getValue()));
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
                secondInput.setText(String.format("%02d", secondPicker.getValue()));
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
