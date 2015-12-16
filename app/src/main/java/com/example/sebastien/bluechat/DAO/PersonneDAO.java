package com.example.sebastien.bluechat.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.sebastien.bluechat.BDD.Mysqllite;
import com.example.sebastien.bluechat.Modele.Message;
import com.example.sebastien.bluechat.Modele.Personne;

import java.util.ArrayList;

/**
 * Created by sebastien on 11/12/2015.
 */
public class PersonneDAO {

    private SQLiteDatabase database;
    private Mysqllite sqlitehelper;

    /***
     * Constructeur
     * @param context
     */
    public PersonneDAO(Context context)
    {
        sqlitehelper = new Mysqllite(context);
    }

    public void open(){
        database = sqlitehelper.getWritableDatabase();
    }

    public void close(){
        sqlitehelper.close();
    }

    /**
     * Ajouter une personne dans la Base de donnée
     * @param personne
     */
    public void addPersonnage(Personne personne)
    {

        // test si n'existe pas
        if (getWithName(personne.getName())==null) {

            ContentValues values = new ContentValues();
            //ligne à enlever
            values.put("name", personne.getName());
            values.put("address", personne.getAddress());

            database.insert("personnes", null, values);
        }

    }

    public ArrayList<Personne> getAllPersonne() {
        ArrayList<Personne> Personne_list = new ArrayList<Personne>();

        String query = "SELECT * FROM personnes";


            Cursor cursor = database.rawQuery(query, null);

            Personne temp = null;
            if (cursor.moveToFirst()) {
                do {

                    temp = new Personne(cursor.getString(1), cursor.getString(2));
                    temp.setId(cursor.getInt(0));

                    Personne_list.add(temp);
                    System.out.println(temp.getName());
                } while (cursor.moveToNext());
            }




        return Personne_list;

    }

    /**
     * Afin de tester si existe
     * @param name
     * @return
     */
    public Personne getWithName(String name) {
        Personne personne = null;

        String query = "SELECT * FROM personnes WHERE name = '"+name +"'";

        try {
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    personne = new Personne(cursor.getString(1),cursor.getString(2));
                } while (cursor.moveToNext());
            }
        }
        catch (SQLiteException ex){
            ex.printStackTrace();
        }


        return personne;
    }

    /**
     * Afin de tester si existe
     * @param address
     * @return
     */
    public Personne getWithAddress(String address) {
        Personne personne = null;

        String query = "SELECT * FROM personnes WHERE address = '"+address +"'";

        try {
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    personne = new Personne(cursor.getString(1),cursor.getString(2));
                } while (cursor.moveToNext());
            }
        }
        catch (SQLiteException ex){
            ex.printStackTrace();
        }

        return personne;
    }

    public int getId(Personne p){
        int id = -1;
        String query = "SELECT * FROM personnes WHERE address = '"+p.getAddress()+"'" ;

        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do{
                if(p.getAddress().equals(cursor.getString(2))&&p.getName().equals(cursor.getString(1)))
                p.setId(cursor.getInt(0));
            }while (cursor.moveToNext());
        }
        id = p.getId();
        return id;
    }


    public Personne getWithId(int id) {
        Personne personne = null;

        String query = "SELECT * FROM personnes WHERE id = "+id;

        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do{
                personne = new Personne(cursor.getString(1),cursor.getString(2));
            }while (cursor.moveToNext());
        }


        return personne;
    }



}
