package com.example.sebastien.bluechat.View;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.sebastien.bluechat.DAO.MessageDAO;
import com.example.sebastien.bluechat.Modele.Message;
import com.example.sebastien.bluechat.R;
import com.example.sebastien.bluechat.View.Adapter.MessageAdapter;

import java.util.ArrayList;

/**
 * Created by sebastien on 05/12/2015.
 */
public class Chat extends Activity {

    private static ListView  l_message;
    private static EditText  textmessage;
    private        ImageView send;
    private        MessageDAO SQL_message;

    //pour la liste
    private static MessageAdapter mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        SQL_message = new MessageDAO(this);
        SQL_message.open();

        //TODO initialisation du mode bluethoot
        InitList();
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
                    //TODO ajouter à la base de donnée
                    //TODO créer des cards
                    Message t_message = new Message(message,Message.type_message.SEND);
                    mArrayAdapter.add(t_message);
                    SQL_message.addMessage(1,t_message);

                    l_message.invalidate();

                    textmessage.setText("");

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textmessage.getWindowToken(), 0);

                }
                else{
                    //TODO Google voice ici
                }

            }
        });
    }

    public void InitList() {
        l_message = (ListView) findViewById(R.id.list_messages);

        ArrayList<Message> stringListe = SQL_message.getAllMessage();
       // ArrayList<Message> stringListe = new ArrayList<Message>();
        mArrayAdapter = new MessageAdapter(this, stringListe);
        l_message.setAdapter(mArrayAdapter);

        //TODO initialisation avec la base de donnée

    }


    @Override
    protected void onResume() {
        SQL_message.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        SQL_message.close();
        super.onPause();
    }
}
