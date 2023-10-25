package com.example.planmyday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class LosAngelesActivity extends Activity {

    // Declare CheckBoxes for each attraction
    private CheckBox checkbox_Crypto;
    private CheckBox checkbox_Disney;
    private CheckBox checkbox_Dodgers;
    private CheckBox checkbox_Griffith;
    private CheckBox checkbox_Hollywood;
    private CheckBox checkbox_LACMA;
    private CheckBox checkbox_SMP;
    private CheckBox checkbox_Broad;
    private CheckBox checkbox_Getty;
    private CheckBox checkbox_Grove;
    private CheckBox checkbox_Universal;
    private CheckBox checkbox_Venice;
    // Button to submit choices
    private Button submitButton;

    private ImageView popup_img;

    private ImageView icon_Crypto;
    private ImageView icon_Disney;
    private ImageView icon_Dodgers;
    private ImageView icon_Griffith;
    private ImageView icon_Hollywood;
    private ImageView icon_LACMA;
    private ImageView icon_SMP;
    private ImageView icon_Broad;
    private ImageView icon_Getty;
    private ImageView icon_Grove;
    private ImageView icon_Universal;
    private ImageView icon_Venice;

    private boolean isPopupOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_la_attractions);

        checkbox_Crypto = findViewById(R.id.checkbox_Crypto);
        checkbox_Disney = findViewById(R.id.checkbox_Disney);
        checkbox_Dodgers = findViewById(R.id.checkbox_Dodgers);
        checkbox_Griffith = findViewById(R.id.checkbox_Griffith);
        checkbox_Hollywood = findViewById(R.id.checkbox_Hollywood);
        checkbox_LACMA = findViewById(R.id.checkbox_LACMA);
        checkbox_SMP = findViewById(R.id.checkbox_SMP);
        checkbox_Broad = findViewById(R.id.checkbox_Broad);
        checkbox_Getty = findViewById(R.id.checkbox_Getty);
        checkbox_Grove = findViewById(R.id.checkbox_Grove);
        checkbox_Universal = findViewById(R.id.checkbox_Universal);
        checkbox_Venice = findViewById(R.id.checkbox_Venice);

        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(v -> {
            // Create an ArrayList to store selected attractions
            ArrayList<String> LAAttractions = new ArrayList<>();

            // Check each checkbox and add the selected attractions to the list
            if (checkbox_Crypto.isChecked()) {
                LAAttractions.add("Crypto.com Arena");
            }
            if (checkbox_Disney.isChecked()) {
                LAAttractions.add("Disneyland Park");
            }
            if (checkbox_Dodgers.isChecked()) {
                LAAttractions.add("Dodgers Stadium");
            }
            if (checkbox_Griffith.isChecked()) {
                LAAttractions.add("Griffith Observatory");
            }
            if (checkbox_Hollywood.isChecked()) {
                LAAttractions.add("Hollywood Sign");
            }
            if (checkbox_LACMA.isChecked()) {
                LAAttractions.add("Los Angeles County Museum of Art");
            }
            if (checkbox_SMP.isChecked()) {
                LAAttractions.add("Santa Monica Pier");
            }
            if (checkbox_Broad.isChecked()) {
                LAAttractions.add("The Broad");
            }
            if (checkbox_Getty.isChecked()) {
                LAAttractions.add("The Getty");
            }
            if (checkbox_Grove.isChecked()) {
                LAAttractions.add("The Grove");
            }
            if (checkbox_Universal.isChecked()) {
                LAAttractions.add("Universal Studios Hollywood");
            }
            if (checkbox_Venice.isChecked()) {
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
        });

        icon_Crypto = findViewById(R.id.icon_Crypto);
        icon_Disney = findViewById(R.id.icon_Disney);
        icon_Dodgers = findViewById(R.id.icon_Dodgers);
        icon_Griffith = findViewById(R.id.icon_Griffith);
        icon_Hollywood = findViewById(R.id.icon_Hollywood);
        icon_LACMA = findViewById(R.id.icon_LACMA);
        icon_SMP = findViewById(R.id.icon_SMP);
        icon_Broad = findViewById(R.id.icon_Broad);
        icon_Getty = findViewById(R.id.icon_Getty);
        icon_Grove = findViewById(R.id.icon_Grove);
        icon_Universal = findViewById(R.id.icon_Universal);
        icon_Venice = findViewById(R.id.icon_Venice);

        popup_img = findViewById(R.id.popup_img);

        icon_Crypto.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.crypto);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_Disney.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.disney);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_Dodgers.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.dodger);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_Griffith.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.griffith);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_Hollywood.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.hollywood);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_LACMA.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.lacma);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_SMP.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.smp);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_Broad.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.broad);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_Getty.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.getty);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_Grove.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.grove);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_Universal.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.universal);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_Venice.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.venice);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });


        popup_img.setOnClickListener(v -> {
            if (isPopupOpen) {
                popup_img.setVisibility(View.INVISIBLE);
                isPopupOpen = false;
            }
        });
    }
}
