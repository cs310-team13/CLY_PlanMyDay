package com.example.planmyday;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

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
                dayPlannerIntent.putExtra("numDays", 1);
                startActivity(dayPlannerIntent);
            }
        });


        Button multiButton = findViewById(R.id.multiButton);

        multiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the number picker dialog layout
                LayoutInflater inflater = LayoutInflater.from(DaySelectionActivity.this);
                final View numberPickerView = inflater.inflate(R.layout.number_picker, null);
                final NumberPicker numberPicker = numberPickerView.findViewById(R.id.numberPicker);
                numberPicker.setMinValue(2); // Minimum days
                numberPicker.setMaxValue(7); // Maximum days

                // Build and show the AlertDialog
                new AlertDialog.Builder(DaySelectionActivity.this)
                        .setTitle("Choose number of days")
                        .setView(numberPickerView)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int numDays = numberPicker.getValue();

                                Intent intent = getIntent();
                                ArrayList<String> USCAttractions = intent.getStringArrayListExtra("USCAttractions");
                                ArrayList<String> LAAttractions = intent.getStringArrayListExtra("LAAttractions");

                                Intent dayPlannerIntent = new Intent(DaySelectionActivity.this, DayPlannerActivity.class);
                                // Make sure you handle both USCAttractions and LAAttractions
                                if (USCAttractions != null) {
                                    dayPlannerIntent.putStringArrayListExtra("attractions", USCAttractions);
                                } else if (LAAttractions != null) {
                                    dayPlannerIntent.putStringArrayListExtra("attractions", LAAttractions);
                                }
                                dayPlannerIntent.putExtra("numDays", numDays);
                                startActivity(dayPlannerIntent);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
            }
        });
    }
}
