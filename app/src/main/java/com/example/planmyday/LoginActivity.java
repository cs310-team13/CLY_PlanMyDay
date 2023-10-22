package com.example.planmyday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends Activity {

    // UI Elements
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton, createAccountButton, forgotPasswordButton;

    // Firebase Authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        createAccountButton = findViewById(R.id.createAccountButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Login button logic
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill in both fields!", Toast.LENGTH_SHORT).show();
                } else {
                    // Firebase authentication check
                    mAuth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        if (mAuth.getCurrentUser().isEmailVerified()) {
                                            // Email is verified, login successful
                                            Toast.makeText(LoginActivity.this, "Login Successful! Directing to dashboard...", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                            startActivity(intent);
                                            finish(); // Close the LoginActivity
                                        } else {
                                            // Email is not verified
                                            Toast.makeText(LoginActivity.this, "Please verify your email before logging in.", Toast.LENGTH_SHORT).show();
                                            // FirebaseAuth.getInstance().signOut(); // THIS IS BAD!!!
                                            // direct user to VerificationActivity
                                            Intent intent = new Intent(LoginActivity.this, VerificationActivity.class);
                                            startActivity(intent);
                                        }
                                    } else {
                                        // Handle other login errors as before
                                        try {
                                            throw task.getException();
                                        } catch (FirebaseAuthInvalidUserException invalidEmail) {
                                            Toast.makeText(LoginActivity.this, "Invalid email address.", Toast.LENGTH_SHORT).show();
                                        } catch (FirebaseAuthInvalidCredentialsException wrongPassword) {
                                            Toast.makeText(LoginActivity.this, "Incorrect password.", Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            Toast.makeText(LoginActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });

                }
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Redirecting to password recovery page...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

    }
}
