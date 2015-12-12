package com.example.sebastien.bluechat.Modele;

/**
 * Created by sebastien on 11/12/2015.
 */
public class Message {



    public enum type_message {
        RECEIVE,
        SEND
    }


    private String message;
    private type_message type;
    private int id;

    /**
     * Constructeur de la classe Message
     * @param message
     * @param type
     */
    public Message(String message, type_message type,int id) {
        this.message = message;
        this.type = type;
        this.id = id;
    }


    public String getMessage() {
        return message;
    }

    public type_message getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}
