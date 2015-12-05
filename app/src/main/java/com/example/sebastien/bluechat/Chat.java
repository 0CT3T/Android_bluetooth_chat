package com.example.sebastien.bluechat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by sebastien on 05/12/2015.
 */
public class Chat extends Activity {

    private static ListView  l_message;
    private static EditText  textmessage;
    private        ImageView send;

    //pour la liste
    private static MessageAdapter mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        //TODO initialisation du mode bluethoot

        Initcomponent();


    }

    public void Initcomponent()
    {
        textmessage = (EditText) findViewById(R.id.editText1);
        send = (ImageView) findViewById(R.id.Send);
        InitList();

        //interrupt sur send
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = textmessage.getText().toString();

                if(!message.isEmpty()){
                    //TODO envoyer en bluethoot
                    //TODO créer des cards
                    mArrayAdapter.add(message);


                    textmessage.setText("");
                }
                else{
                    //TODO Google voice ici
                }

            }
        });
    }

    public void InitList() {
        l_message = (ListView) findViewById(R.id.list_messages);

        ArrayList<String> stringListe = new ArrayList<String>();
        mArrayAdapter = new MessageAdapter(this, stringListe );
        l_message.setAdapter(mArrayAdapter);

        //TODO initialisation avec la base de donnée

    }
}
