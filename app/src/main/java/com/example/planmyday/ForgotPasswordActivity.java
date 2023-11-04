package com.example.planmyday;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText resetEmailEditText, dateOfBirthEditText;
    private ImageButton infoButton;
    private Button resetPasswordButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        resetEmailEditText = findViewById(R.id.resetEmailEditText);
        dateOfBirthEditText = findViewById(R.id.dateOfBirthEditText); // Make sure you have this EditText in your XML
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        infoButton = findViewById(R.id.infoButton);

        mAuth = FirebaseAuth.getInstance();
        infoButton = findViewById(R.id.infoButton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the toast with the information about the date of birth
                Toast.makeText(ForgotPasswordActivity.this, "We asked for your date of birth upon registration as a mean to verify your identity when recovering account, but no constraint was imposed on the format. This can in fact be any string you chose when you registered.", Toast.LENGTH_LONG).show();
            }
        });

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = resetEmailEditText.getText().toString().trim();
                final String dateOfBirth = dateOfBirthEditText.getText().toString().trim();

                if (email.isEmpty() || dateOfBirth.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter your email and date of birth!", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
                mDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Check the date of birth
                            for (DataSnapshot user : dataSnapshot.getChildren()) {
                                User userObj = user.getValue(User.class);
                                if (userObj != null && userObj.getDateOfBirth().equals(dateOfBirth)) {
                                    // Date of birth matches, send reset email
                                    mAuth.sendPasswordResetEmail(email)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(ForgotPasswordActivity.this, "Password reset link sent. Please check your email.", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    } else {
                                                        Toast.makeText(ForgotPasswordActivity.this, "Error sending password reset email: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(ForgotPasswordActivity.this, "Date of birth does not match our records.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "No user found with that email address.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ForgotPasswordActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
