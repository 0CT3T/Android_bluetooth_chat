package com.example.sebastien.bluechat.Modele;

/**
 * Created by sebastien on 11/12/2015.
 */
public class Personne {

    private String name;
    
    private String address;

    private int id;

    public Personne(String name, String Adress) {
        
        this.name   = name;
        this.address = Adress;
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

    public String getAddress() {
        return address;
    }
}
