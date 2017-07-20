package com.example.mond.rx.models.simpl_models;

/**
 * Created by mond on 20.07.17.
 */

public class Store {
    int id;

    String name;

    public Store(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
