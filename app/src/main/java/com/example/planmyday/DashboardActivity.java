package com.example.planmyday;  // Use your actual package name!

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button LAButton = findViewById(R.id.LAButton);
        LAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Los Angeles activity
                Intent intent = new Intent(DashboardActivity.this, LosAngelesActivity.class);
                startActivity(intent);
            }
        });


        Button USCButton = findViewById(R.id.USCButton);
        USCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the USC activity
                Intent intent = new Intent(DashboardActivity.this, USCActivity.class);
                startActivity(intent);
            }
        });

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the LoginActivity
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                // Clear the activity stack to prevent the back button from navigating back to Dashboard
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
