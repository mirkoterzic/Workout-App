package com.example.myworkoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button pace_calculator_btn;
    private Button workout_tracker_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        pace_calculator_btn = findViewById(R.id.pace_calculator_btn);
        workout_tracker_btn= findViewById(R.id.workout_tracker_btn);

        pace_calculator_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PaceCalculatorIntent = new Intent(MainActivity.this, PaceCalculatorActivity.class);
                startActivity(PaceCalculatorIntent);
                finish();
            }
        });
        workout_tracker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent WorkoutTrackerIntent= new Intent(MainActivity.this,WorkoutTrackerActivity.class);
                startActivity(WorkoutTrackerIntent);
                finish();
            }
        });
    }
}