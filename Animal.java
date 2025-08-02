package com.example.resqalert;
public class Animal {
    private String id;
    private String name;
    private String age;
    private String breed;
    private String description;
    private String imageUrl;

    public Animal() {}

    public Animal(String id, String name, String age, String breed, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}

