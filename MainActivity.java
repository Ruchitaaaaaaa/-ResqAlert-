package com.example.resqalert;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ImageView imagePreview;
    private Bitmap capturedImage;
    private FusedLocationProviderClient fusedLocationClient;
    private double latitude, longitude;

    private final ActivityResultLauncher<Intent> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    capturedImage = (Bitmap) extras.get("data");
                    imagePreview.setImageBitmap(capturedImage);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagePreview = findViewById(R.id.imagePreview);
        Button btnCapture = findViewById(R.id.btnCapture);
        Button btnAddDetails = findViewById(R.id.btnAddDetails);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Request permissions at runtime
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 1000);
        }

        btnCapture.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureLauncher.launch(takePictureIntent);
        });

        btnAddDetails.setOnClickListener(v -> {
            if (capturedImage != null) {
                fetchLocationAndShowDialog();
            } else {
                Toast.makeText(this, "Please capture an image first.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchLocationAndShowDialog() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        showDetailsDialog();
                    } else {
                        Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showDetailsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setHint("Enter animal details");
        builder.setView(input);
        builder.setTitle("Animal Details");
        builder.setPositiveButton("Submit", (dialog, which) -> {
            String details = input.getText().toString();
            uploadImageToFirebase(details);
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void uploadImageToFirebase(String details) {
        if (capturedImage == null) return;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        capturedImage.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] data = baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference()
                .child("animal_reports/" + UUID.randomUUID() + ".jpg");

        ref.putBytes(data)
                .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri -> {
                    saveReportToFirestore(details, uri.toString());
                }))
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void saveReportToFirestore(String details, String imageUrl) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> report = new HashMap<>();
        report.put("details", details);
        report.put("latitude", latitude);
        report.put("longitude", longitude);
        report.put("imageUrl", imageUrl);
        report.put("timestamp", System.currentTimeMillis());

        db.collection("animal_reports")
                .add(report)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Report submitted successfully", Toast.LENGTH_SHORT).show();
                    openGoogleMapsNearbySearch();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Firestore error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void openGoogleMapsNearbySearch() {
        Uri uri = Uri.parse("geo:" + latitude + "," + longitude + "?q=animal+ngo+or+veterinary");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }
}
