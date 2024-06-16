package com.example.myworkoutapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PaceCalculatorActivity extends AppCompatActivity {


    private EditText distanceInput;
    private EditText timeInput;
    private TextView resultTextView;
    private TextView time5k;
    private TextView time10k;
    private TextView timeHalfMarathon;
    private TextView timeMarathon;
    private Button calculateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pace_calculator);

        distanceInput = findViewById(R.id.distanceInput);
        timeInput = findViewById(R.id.timeInput);
        resultTextView = findViewById(R.id.resultTextView);
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
    private void calculatePaceAndTimes() {
        String distanceStr = distanceInput.getText().toString();
        String timeStr = timeInput.getText().toString();

        if (distanceStr.isEmpty() || timeStr.isEmpty()) {
            resultTextView.setText("Please enter both distance and time.");
            return;
        }

        double distance = Double.parseDouble(distanceStr);
        double time = Double.parseDouble(timeStr);

        if (distance == 0 || time == 0) {
            resultTextView.setText("Distance and time must be greater than zero.");
            return;
        }

        double pace = time / distance;
        resultTextView.setText(String.format("Pace: %.2f min/km", pace));

        calculateEstimatedTimes(pace);
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
