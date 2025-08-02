package com.example.resqalert;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    ImageView loginBtn;
    ImageButton cameraBtn, adoptionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Reference to views
        loginBtn = findViewById(R.id.loginIcon);
        cameraBtn = findViewById(R.id.cameraBtn);
        adoptionBtn = findViewById(R.id.adoptionBtn);

        // Login icon click
        loginBtn.setOnClickListener(v -> {
            // Replace LoginActivity.class with your actual login/signup activity
            Intent intent = new Intent(HomeActivity.this, SigninActivity.class);
            startActivity(intent);
        });

        // SOS camera button click
        cameraBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Adoption page button click
        adoptionBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AdoptionActivity.class);
            startActivity(intent);
        });
    }
}
