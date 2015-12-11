package com.example.sebastien.bluechat.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sebastien.bluechat.BDD.Mysqllite;
import com.example.sebastien.bluechat.Modele.Message;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by sebastien on 11/12/2015.
 */
public class MessageDAO {

    private SQLiteDatabase database;
    private Mysqllite sqlitehelper;

    /***
     * Constructeur
     * @param context
     */
    public MessageDAO(Context context)
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
     * Ajouter un message dans la Base de donnée
     * @param name
     * @param message
     */
    public void addMessage(int name, Message message)
    {
        //TODO recupere id avec nom personne
        //id_personne = get

        ContentValues values = new ContentValues();
        //ligne à enlever
        values.put("id_personne",name);
        values.put("message", message.getMessage());

        switch (message.getType())
        {
            case RECEIVE:
                values.put("type", 1);
                break;
            case SEND:
                values.put("type", 2);
                break;
        }



        database.insert("messages", null, values);
        database.close();

    }

    public ArrayList<Message> getAllMessage() {
        ArrayList<Message> message_list = new ArrayList<Message>();

        //TODO query pour recuperer avec un nom
        String query = "SELECT * FROM messages";

        Cursor cursor = database.rawQuery(query, null);

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
