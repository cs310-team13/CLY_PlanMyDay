package com.example.planmyday;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerificationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(VerificationActivity.this, "Verification email sent. Please check your email.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(VerificationActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(VerificationActivity.this, "Failed to send verification email: user is null", Toast.LENGTH_SHORT).show();
        }

        Button btnVerified = findViewById(R.id.btnVerified);
        Button btnCancel = findViewById(R.id.btnCancel);
        Button btnResendEmail = findViewById(R.id.btnResendEmail);

        btnVerified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    user.reload().addOnCompleteListener(new OnCompleteListener<Void>() { // needed a reload to update the user.isEmailVerified() value
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful() && user.isEmailVerified()) {
                                // User is verified
                                Toast.makeText(VerificationActivity.this, "Email verified! You can now login.", Toast.LENGTH_SHORT).show();
                                finish();  // This will close VerificationActivity and navigate back to RegistrationActivity or LoginActivity
                            } else {
                                Toast.makeText(VerificationActivity.this, "Email not verified. Please check your email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Navigate back to RegistrationActivity
            }
        });

        btnResendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(VerificationActivity.this, "Failed to resend verification email: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(VerificationActivity.this, "Verification email sent. Please check your email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(VerificationActivity.this, "Failed to resend verification email: user is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
