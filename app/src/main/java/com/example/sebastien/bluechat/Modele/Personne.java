package com.example.sebastien.bluechat.Modele;

/**
 * Created by sebastien on 11/12/2015.
 */
public class Personne {

    private String name;

    private int id;

    public Personne(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
