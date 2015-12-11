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

    private static final int DATABASE_VERSION = 2;
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
                "name TEXT); ";

        db.execSQL(CREATE_PERSONNE);
        db.execSQL(CREATE_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS personnes");
        db.execSQL("DROP TABLE IF EXISTS messages");

        this.onCreate(db);
    }


    /**
     * Ajouter un message dans la Base de donnée
     * @param name
     * @param message
     * @param type
     */
    public void addMessage(int name, String message, Message.type_message type)
    {
        //TODO recupere id avec nom personne
        //id_personne = get

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //ligne à enlever
        values.put("id_personne",name);
        values.put("message", message);

        switch (type)
        {
            case RECEIVE:
                values.put("type", 1);
                break;
            case SEND:
                values.put("type", 2);
                break;
        }



        db.insert("messages", null, values);
        db.close();

    }

    public ArrayList<Message> getAllMessage() {
        ArrayList<Message> message_list = new ArrayList<Message>();

        //TODO query pour recuperer avec un nom
        String query = "SELECT * FROM messages";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Message temp = null;
        if (cursor.moveToFirst()){
            do{
                Message.type_message t_message = null;

                switch (Integer.parseInt(cursor.getString(3)))
                {
                    case 1: t_message = Message.type_message.RECEIVE;
                        break;
                    case 2: t_message = Message.type_message.SEND;
                        break;

                }


                temp = new Message(cursor.getString(2),t_message);

                message_list.add(temp);
            }while (cursor.moveToNext());
        }


        return message_list;

    }

}
