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

    //id communication
    private static int id_com;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        Bundle b = getIntent().getExtras();
        id_com = b.getInt("key");

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
                    //TODO créer des cards
                    Message t_message = new Message(message,Message.type_message.SEND,id_com);
                    mArrayAdapter.add(t_message);
                    SQL_message.addMessage(t_message);

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

        //initialisation avec la base de donnée
        ArrayList<Message> stringListe = SQL_message.getAllMessage(id_com);
       // ArrayList<Message> stringListe = new ArrayList<Message>();
        mArrayAdapter = new MessageAdapter(this, stringListe);
        l_message.setAdapter(mArrayAdapter);



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
