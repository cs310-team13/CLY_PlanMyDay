package com.example.planmyday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class DaySelectionActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_selection);

        Button oneButton = findViewById(R.id.oneButton);
        oneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the ArrayList of strings stored in the Intent
                Intent intent = getIntent();

                // either one of these does not exist because user can only choose one between USC and LA
                ArrayList<String> USCAttractions = intent.getStringArrayListExtra("USCAttractions");
                ArrayList<String> LAAttractions = intent.getStringArrayListExtra("LAAttractions");

                Intent dayPlannerIntent = new Intent(DaySelectionActivity.this, DayPlannerActivity.class);
                // not sure if this works, Clark better checks!!!
                if (USCAttractions != null) {
                    dayPlannerIntent.putStringArrayListExtra("attractions", USCAttractions);
                } else if (LAAttractions != null) {
                    dayPlannerIntent.putStringArrayListExtra("attractions", LAAttractions);
                }
                startActivity(dayPlannerIntent);
            }
        });


        Button multiButton = findViewById(R.id.multiButton);
        multiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the ArrayList of strings stored in the Intent
                Intent intent = getIntent();
                // either one of these does not exist because user can only choose one between USC and LA
                ArrayList<String> USCAttractions = intent.getStringArrayListExtra("USCAttractions");
                ArrayList<String> LAAttractions = intent.getStringArrayListExtra("LAAttractions");

                Intent dayPlannerIntent = new Intent(DaySelectionActivity.this, DayPlannerActivity.class);
                // not sure if this works, Clark better checks!!!
                if (USCAttractions != null) {
                    dayPlannerIntent.putStringArrayListExtra("attractions", USCAttractions);
                } else if (LAAttractions != null) {
                    dayPlannerIntent.putStringArrayListExtra("attractions", LAAttractions);
                }
                startActivity(dayPlannerIntent);
            }
        });
    }
}
