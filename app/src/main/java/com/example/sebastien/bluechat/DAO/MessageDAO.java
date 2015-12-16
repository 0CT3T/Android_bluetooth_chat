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

    private SQLiteDatabase database = null;
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
     * @param message
     */
    public void addMessage(Message message)
    {

        ContentValues values = new ContentValues();
        //ligne à enlever
        values.put("id_personne",message.getId());
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

    }

    public ArrayList<Message> getAllMessage(int id) {
        ArrayList<Message> message_list = new ArrayList<Message>();


        String query = "SELECT * FROM messages WHERE id_personne = " + id;

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


                temp = new Message(cursor.getString(2),t_message,Integer.parseInt(cursor.getString(1)));

                message_list.add(temp);
            }while (cursor.moveToNext());
        }


        return message_list;

    }



}
