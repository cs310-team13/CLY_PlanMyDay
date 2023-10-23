package com.example.planmyday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

public class USCActivity extends Activity {

    // Declare CheckBoxes for each attraction
    private CheckBox checkBoxAnnenberg;
    private CheckBox checkBoxDoheny;
    private CheckBox checkBoxGwynnWilson;
    private CheckBox checkBoxKDC;
    private CheckBox checkBoxLeavey;
    private CheckBox checkBoxMudd;
    private CheckBox checkBoxPE;
    private CheckBox checkBoxSalvatori;
    private CheckBox checkBoxTaper;
    private CheckBox checkBoxTommyTrojan;
    private CheckBox checkBoxTravelerHorse;
    private CheckBox checkBoxUSCBookstore;

    // Button to submit choices
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usc_attractions);

        checkBoxAnnenberg = findViewById(R.id.checkBoxAnnenberg);
        checkBoxDoheny = findViewById(R.id.checkBoxDoheny);
        checkBoxGwynnWilson = findViewById(R.id.checkBoxGwynnWilson);
        checkBoxKDC = findViewById(R.id.checkBoxKDC);
        checkBoxLeavey = findViewById(R.id.checkBoxLeavey);
        checkBoxMudd = findViewById(R.id.checkBoxMudd);
        checkBoxPE = findViewById(R.id.checkBoxPE);
        checkBoxSalvatori = findViewById(R.id.checkBoxSalvatori);
        checkBoxTaper = findViewById(R.id.checkBoxTaper);
        checkBoxTommyTrojan = findViewById(R.id.checkBoxTommyTrojan);
        checkBoxTravelerHorse = findViewById(R.id.checkBoxTravelerHorse);
        checkBoxUSCBookstore = findViewById(R.id.checkBoxUSCBookstore);

        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an ArrayList to store selected attractions
                ArrayList<String> USCAttractions = new ArrayList<>();

                // Check each checkbox and add the selected attractions to the list
                if (checkBoxAnnenberg.isChecked()) {
                    USCAttractions.add("Annenberg School of Communication and Journalism");
                }
                if (checkBoxDoheny.isChecked()) {
                    USCAttractions.add("Doheny Memorial Library");
                }
                if (checkBoxGwynnWilson.isChecked()) {
                    USCAttractions.add("Gwynn Wilson Student Union");
                }
                if (checkBoxKDC.isChecked()) {
                    USCAttractions.add("Kaufman International Dance Center");
                }
                if (checkBoxLeavey.isChecked()) {
                    USCAttractions.add("Leavey Library");
                }
                if (checkBoxMudd.isChecked()) {
                    USCAttractions.add("Mudd Hall of Philosophy");
                }
                if (checkBoxPE.isChecked()) {
                    USCAttractions.add("Physical Education Building");
                }
                if (checkBoxSalvatori.isChecked()) {
                    USCAttractions.add("Salvatori Computer Science Center");
                }
                if (checkBoxTaper.isChecked()) {
                    USCAttractions.add("Taper Hall of Humanities");
                }
                if (checkBoxTommyTrojan.isChecked()) {
                    USCAttractions.add("Tommy Trojan Sculpture");
                }
                if (checkBoxTravelerHorse.isChecked()) {
                    USCAttractions.add("Traveler the Horse Sculpture");
                }
                if (checkBoxUSCBookstore.isChecked()) {
                    USCAttractions.add("USC Bookstore");
                }

                if (USCAttractions.isEmpty()) {
                    // Show a warning or toast message to the user
                    Toast.makeText(USCActivity.this, "Please select at least one attraction.", Toast.LENGTH_SHORT).show();
                } else {
                    // At least one attraction is selected, proceed to the next activity
                    Intent intent = new Intent(USCActivity.this, DaySelectionActivity.class);
                    intent.putStringArrayListExtra("USCAttractions", USCAttractions);
                    startActivity(intent);
                }
            }
        });
    }
}
