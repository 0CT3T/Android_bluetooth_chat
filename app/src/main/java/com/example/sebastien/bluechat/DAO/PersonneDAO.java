package com.example.sebastien.bluechat.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

        //TODO test si n'existe pas

        ContentValues values = new ContentValues();
        //ligne à enlever
        values.put("name",personne.getName());

        database.insert("personnes", null, values);
        database.close();

    }

    public ArrayList<Personne> getAllPersonne() {
        ArrayList<Personne> Personne_list = new ArrayList<Personne>();

        String query = "SELECT * FROM personnes";

        Cursor cursor = database.rawQuery(query, null);

        Personne temp = null;
        if (cursor.moveToFirst()){
            do{



                temp = new Personne(cursor.getString(2));

                Personne_list.add(temp);
            }while (cursor.moveToNext());
        }


        return Personne_list;

    }

    public Personne getWithId(int id) {
        Personne personne = null;

        String query = "SELECT * FROM personnes WHERE id_personne="+id;

        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do{
                personne = new Personne(cursor.getString(2));
            }while (cursor.moveToNext());
        }


        return personne;
    }



}
