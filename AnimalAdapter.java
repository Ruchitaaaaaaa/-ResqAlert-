package com.example.resqalert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private Context context;
    private List<Animal> animalList;

    public AnimalAdapter(Context context, List<Animal> animalList) {
        this.context = context;
        this.animalList = animalList;
    }

    public static class AnimalViewHolder extends RecyclerView.ViewHolder {
        ImageView animalImage;
        TextView animalName, animalBreed, animalAge;
        Button adoptBtn;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            animalImage = itemView.findViewById(R.id.animalImage);
            animalName = itemView.findViewById(R.id.animalName);
            animalBreed = itemView.findViewById(R.id.animalBreed);
            animalAge = itemView.findViewById(R.id.animalAge);
            adoptBtn = itemView.findViewById(R.id.adoptBtn);
        }
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.animal_item, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = animalList.get(position);

        holder.animalName.setText(animal.getName());
        holder.animalBreed.setText("Breed: " + animal.getBreed());
        holder.animalAge.setText("Age: " + animal.getAge());

        // Load image using Picasso (add it to your project via Gradle)
        Picasso.get().load(animal.getImageUrl()).placeholder(R.drawable.sample_animal).into(holder.animalImage);

        holder.adoptBtn.setOnClickListener(v -> {
            Toast.makeText(context, "Adoption request sent for " + animal.getName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }
}
