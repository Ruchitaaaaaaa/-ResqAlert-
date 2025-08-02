package com.example.resqalert;


import android.os.Bundle;
import android.widget.*;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText nameField, emailField, phoneField, passwordField;
    Spinner userTypeSpinner;
    Button signUpButton;
    DatabaseHelper dbHelper;

    String[] userTypes = {"NGO", "Normal User", "Vet"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameField = findViewById(R.id.nameField);
        emailField = findViewById(R.id.emailField);
        phoneField = findViewById(R.id.mobileNumberField);
        passwordField = findViewById(R.id.passwordField);
        userTypeSpinner = findViewById(R.id.userTypeSpinner);
        signUpButton = findViewById(R.id.signUpButton);

        dbHelper = new DatabaseHelper(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapter);

        signUpButton.setOnClickListener(v -> {
            String name = nameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String phone = phoneField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            String userType = userTypeSpinner.getSelectedItem().toString();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = dbHelper.insertUser(name, email, phone, password, userType);
            if (inserted) {
                Toast.makeText(this, "User registered successfully!", Toast.LENGTH_LONG).show();
                // Optionally navigate to login activity
                // startActivity(new Intent(this, SignInActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Phone already registered!", Toast.LENGTH_LONG).show();
            }
        });
    }
}

