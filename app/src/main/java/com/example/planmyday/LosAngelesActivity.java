package com.example.planmyday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

public class LosAngelesActivity extends Activity {

    // Declare CheckBoxes for each attraction
    private CheckBox checkBoxCrypto;
    private CheckBox checkBoxDisney;
    private CheckBox checkBoxDodgers;
    private CheckBox checkBoxGriffith;
    private CheckBox checkBoxHollywood;
    private CheckBox checkBoxLAMOA;
    private CheckBox checkBoxSantaMonica;
    private CheckBox checkBoxBroad;
    private CheckBox checkBoxGetty;
    private CheckBox checkBoxGrove;
    private CheckBox checkBoxUniversal;
    private CheckBox checkBoxVenice;
    // Button to submit choices
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_la_attractions);

        checkBoxCrypto = findViewById(R.id.checkBoxCrypto);
        checkBoxDisney = findViewById(R.id.checkBoxDisney);
        checkBoxDodgers = findViewById(R.id.checkBoxDodgers);
        checkBoxGriffith = findViewById(R.id.checkBoxGriffith);
        checkBoxHollywood = findViewById(R.id.checkBoxHollywood);
        checkBoxLAMOA = findViewById(R.id.checkBoxLAMOA);
        checkBoxSantaMonica = findViewById(R.id.checkBoxSantaMonica);
        checkBoxBroad = findViewById(R.id.checkBoxBroad);
        checkBoxGetty = findViewById(R.id.checkBoxGetty);
        checkBoxGrove = findViewById(R.id.checkBoxGrove);
        checkBoxVenice = findViewById(R.id.checkBoxVenice);
        checkBoxUniversal = findViewById(R.id.checkBoxUniversal);

        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an ArrayList to store selected attractions
                ArrayList<String> LAAttractions = new ArrayList<>();

                // Check each checkbox and add the selected attractions to the list
                if (checkBoxCrypto.isChecked()) {
                    LAAttractions.add("Crypto.com Arena");
                }
                if (checkBoxDisney.isChecked()) {
                    LAAttractions.add("Disneyland Park");
                }
                if (checkBoxDodgers.isChecked()) {
                    LAAttractions.add("Dodgers Stadium");
                }
                if (checkBoxGriffith.isChecked()) {
                    LAAttractions.add("Griffith Observatory");
                }
                if (checkBoxHollywood.isChecked()) {
                    LAAttractions.add("Hollywood Sign");
                }
                if (checkBoxLAMOA.isChecked()) {
                    LAAttractions.add("Los Angeles County Museum of Art");
                }
                if (checkBoxSantaMonica.isChecked()) {
                    LAAttractions.add("Santa Monica Pier");
                }
                if (checkBoxBroad.isChecked()) {
                    LAAttractions.add("The Broad");
                }
                if (checkBoxGetty.isChecked()) {
                    LAAttractions.add("The Getty");
                }
                if (checkBoxGrove.isChecked()) {
                    LAAttractions.add("The Grove");
                }
                if (checkBoxUniversal.isChecked()) {
                    LAAttractions.add("Universal Studios Hollywood");
                }
                if (checkBoxVenice.isChecked()) {
                    LAAttractions.add("Venice Beach");
                }

                if (LAAttractions.isEmpty()) {
                    // Show a warning or toast message to the user
                    Toast.makeText(LosAngelesActivity.this, "Please select at least one attraction.", Toast.LENGTH_SHORT).show();
                } else {
                    // At least one attraction is selected, proceed to the next activity
                    Intent intent = new Intent(LosAngelesActivity.this, DaySelectionActivity.class);
                    intent.putStringArrayListExtra("LAAttractions", LAAttractions);
                    startActivity(intent);
                }
            }
        });
    }
}
