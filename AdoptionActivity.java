package com.example.resqalert;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdoptionActivity extends AppCompatActivity {

    private RecyclerView animalRecyclerView;
    private AnimalAdapter animalAdapter;
    private List<Animal> animalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption);

        animalRecyclerView = findViewById(R.id.animalRecyclerView);
        animalRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample data
        animalList = new ArrayList<>();
        animalList.add(new Animal("1", "Tommy", "2 years", "Labrador", "Friendly and playful", "https://cdn.pixabay.com/photo/2016/02/19/10/00/labrador-1209587_1280.jpg"));
        animalList.add(new Animal("2", "Lucy", "1.5 years", "Beagle", "Loves cuddles", "https://cdn.pixabay.com/photo/2018/04/12/16/45/beagle-3312551_1280.jpg"));
        animalList.add(new Animal("3", "Max", "3 years", "Indian Stray", "Calm and loyal", "https://cdn.pixabay.com/photo/2020/11/26/13/58/dog-5778005_1280.jpg"));
        animalList.add(new Animal("4", "Milo", "8 months", "Golden Retriever", "Energetic and affectionate", "https://cdn.pixabay.com/photo/2017/09/25/13/12/dog-2785074_1280.jpg"));

        // Set adapter
        animalAdapter = new AnimalAdapter(this, animalList);
        animalRecyclerView.setAdapter(animalAdapter);
    }
}

