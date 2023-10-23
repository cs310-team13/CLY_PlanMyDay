package com.example.planmyday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class DayPlannerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_planner);

        // Retrieve the selected choices from the Intent
        Intent intent = getIntent();
        if (intent != null) {
            ArrayList<String> attractions = intent.getStringArrayListExtra("attractions");
            TextView displayChoicesText = findViewById(R.id.displayChoices);

            StringBuilder choicesBuilder = new StringBuilder();
            for (String choice : attractions) {
                choicesBuilder.append(choice).append("\n");
            }
            displayChoicesText.setText(choicesBuilder.toString());
        }
    }
}
