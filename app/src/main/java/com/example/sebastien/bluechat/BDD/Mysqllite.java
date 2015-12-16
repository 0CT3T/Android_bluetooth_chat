package com.example.sebastien.bluechat.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sebastien.bluechat.Modele.Message;

import java.util.ArrayList;

/**
 * Created by sebastien on 11/12/2015.
 */
public class Mysqllite extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "MessageDB";


    public Mysqllite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MESSAGE = "CREATE TABLE messages ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "id_personne INT, "+
                "message TEXT, "+
                "type INT );";

        String CREATE_PERSONNE = "CREATE TABLE personnes ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "name TEXT, " +
                "address TEXT );";

        db.execSQL(CREATE_PERSONNE);
        db.execSQL(CREATE_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS personnes");
        db.execSQL("DROP TABLE IF EXISTS messages");

        this.onCreate(db);
    }




}
