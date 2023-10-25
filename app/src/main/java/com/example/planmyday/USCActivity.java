package com.example.planmyday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;

public class USCActivity extends Activity {
    private CheckBox checkbox_Annenberg;
    private CheckBox checkbox_Doheny;
    private CheckBox checkbox_GW;
    private CheckBox checkbox_KDC;
    private CheckBox checkbox_Leavey;
    private CheckBox checkbox_Mudd;
    private CheckBox checkbox_PE;
    private CheckBox checkbox_SAL;
    private CheckBox checkbox_THH;
    private CheckBox checkbox_Tommy;
    private CheckBox checkbox_Traveler;
    private CheckBox checkbox_Bookstore;

    // Button to submit choices
    private Button submitButton;

    // pop up images
    private ImageView popup_img;

    // icons
    private ImageView icon_Annenberg;
    private ImageView icon_Doheny;
    private ImageView icon_GW;
    private ImageView icon_KDC;
    private ImageView icon_Leavey;
    private ImageView icon_Mudd;
    private ImageView icon_PE;
    private ImageView icon_SAL;
    private ImageView icon_THH;
    private ImageView icon_Tommy;
    private ImageView icon_Traveler;
    private ImageView icon_Bookstore;

    private boolean isPopupOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usc_attractions);

        checkbox_Annenberg = findViewById(R.id.checkbox_Annenberg);
        checkbox_Doheny = findViewById(R.id.checkbox_Doheny);
        checkbox_GW = findViewById(R.id.checkbox_GW);
        checkbox_KDC = findViewById(R.id.checkbox_KDC);
        checkbox_Leavey = findViewById(R.id.checkbox_Leavey);
        checkbox_Mudd = findViewById(R.id.checkbox_Mudd);
        checkbox_PE = findViewById(R.id.checkbox_PE);
        checkbox_SAL = findViewById(R.id.checkbox_SAL);
        checkbox_THH = findViewById(R.id.checkbox_THH);
        checkbox_Tommy = findViewById(R.id.checkbox_Tommy);
        checkbox_Traveler = findViewById(R.id.checkbox_Traveler);
        checkbox_Bookstore = findViewById(R.id.checkbox_Bookstore);

        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an ArrayList to store selected attractions
                ArrayList<String> USCAttractions = new ArrayList<>();

                // Check each checkbox and add the selected attractions to the list
                if (checkbox_Annenberg.isChecked()) {
                    USCAttractions.add("Annenberg School of Communication and Journalism");
                }
                if (checkbox_Doheny.isChecked()) {
                    USCAttractions.add("Doheny Memorial Library");
                }
                if (checkbox_GW.isChecked()) {
                    USCAttractions.add("Gwynn Wilson Student Union");
                }
                if (checkbox_KDC.isChecked()) {
                    USCAttractions.add("Kaufman International Dance Center");
                }
                if (checkbox_Leavey.isChecked()) {
                    USCAttractions.add("Leavey Library");
                }
                if (checkbox_Mudd.isChecked()) {
                    USCAttractions.add("Mudd Hall of Philosophy");
                }
                if (checkbox_PE.isChecked()) {
                    USCAttractions.add("Physical Education Building");
                }
                if (checkbox_SAL.isChecked()) {
                    USCAttractions.add("Salvatori Computer Science Center");
                }
                if (checkbox_THH.isChecked()) {
                    USCAttractions.add("Taper Hall of Humanities");
                }
                if (checkbox_Tommy.isChecked()) {
                    USCAttractions.add("Tommy Trojan Sculpture");
                }
                if (checkbox_Traveler.isChecked()) {
                    USCAttractions.add("Traveler the Horse Sculpture");
                }
                if (checkbox_Bookstore.isChecked()) {
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

        icon_Annenberg = findViewById(R.id.icon_Annenberg);
        icon_Doheny = findViewById(R.id.icon_Doheny);
        icon_GW = findViewById(R.id.icon_GW);
        icon_KDC = findViewById(R.id.icon_KDC);
        icon_Leavey = findViewById(R.id.icon_Leavey);
        icon_Mudd = findViewById(R.id.icon_Mudd);
        icon_PE = findViewById(R.id.icon_PE);
        icon_SAL = findViewById(R.id.icon_SAL);
        icon_THH = findViewById(R.id.icon_THH);
        icon_Tommy = findViewById(R.id.icon_Tommy);
        icon_Traveler = findViewById(R.id.icon_Traveler);
        icon_Bookstore = findViewById(R.id.icon_Bookstore);

        popup_img = findViewById(R.id.popup_img);

        icon_Annenberg.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.annenberg);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_Doheny.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.doheny);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_GW.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.gw);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_KDC.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.kdc);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_Leavey.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.leavey);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_Mudd.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.mudd);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_PE.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.pe);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_SAL.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.sal);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_THH.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.thh);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_Tommy.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.tommy);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_Traveler.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.traveler);
                popup_img.setVisibility(View.VISIBLE);
                isPopupOpen = true;
            }
        });

        icon_Bookstore.setOnClickListener(v -> {
            if (!isPopupOpen) {
                popup_img.setImageResource(R.drawable.bookstore);
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
