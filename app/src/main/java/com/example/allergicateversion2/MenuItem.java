package com.example.allergicateversion2;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    private String title;

    public String getDescription() {
        return description;
    }

    private String description;
    private ArrayList<Integer> allergens;

    public MenuItem( String title, String description, ArrayList<Integer> allergens) {
        this.title = title;
        this.description = description;
        this.allergens = allergens;
    }

    public ArrayList<Integer> getAllergens() {
        return allergens;
    }

    public String getTitle() {
        return title;
    }
}
