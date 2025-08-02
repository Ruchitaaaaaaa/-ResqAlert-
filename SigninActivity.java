package com.example.resqalert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SigninActivity extends AppCompatActivity {

    private EditText mobileNumberField, passwordField;
    private Button signInButton;
    private TextView signUpLink;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_signin);

        mobileNumberField = findViewById(R.id.mobileNumberField);
        passwordField = findViewById(R.id.passwordField);
        signInButton = findViewById(R.id.signInButton);
        signUpLink = findViewById(R.id.signUpLink); // ✅ linking TextView

        dbHelper = new DatabaseHelper(this);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = mobileNumberField.getText().toString().trim();
                String password = passwordField.getText().toString();

                if (mobile.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SigninActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isValid = dbHelper.checkUser(mobile, password);
                if (isValid) {
                    Toast.makeText(SigninActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SigninActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SigninActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ✅ Set up click listener for "Sign up"
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
