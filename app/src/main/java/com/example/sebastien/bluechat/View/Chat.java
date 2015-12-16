package com.example.sebastien.bluechat.View;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sebastien.bluechat.Bluetooth.BTCommunication;
import com.example.sebastien.bluechat.Bluetooth.Bluetooth;
import com.example.sebastien.bluechat.DAO.MessageDAO;
import com.example.sebastien.bluechat.DAO.PersonneDAO;
import com.example.sebastien.bluechat.Global_variables.Global_variables;
import com.example.sebastien.bluechat.Modele.Message;
import com.example.sebastien.bluechat.Modele.Personne;
import com.example.sebastien.bluechat.R;
import com.example.sebastien.bluechat.View.Adapter.MessageAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sebastien on 05/12/2015.
 */
public class Chat extends BaseActivity {

    private static ListView     l_message;
    private static EditText     textmessage;
    private        ImageView    send;
    private        MessageDAO   SQL_message;
    private        PersonneDAO  SQL_personne;
    private        Personne     mcontact;
    private        BluetoothDevice  mBlueDev;
    private        BTCommunication  mCom;

    //pour la liste
    private static MessageAdapter mArrayAdapter;

    
    // device info
    private String              mAddress;
    private int                 id_com;

    //Message to show
    private Message             mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean existing = false;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        Bundle b = getIntent().getExtras();
        

        SQL_message = new MessageDAO(this);
        SQL_message.open();
        
        SQL_personne = new PersonneDAO(this);
        SQL_personne.open();


        mBluetooth = Bluetooth.List.get(0);
        mBluetooth.setmActivity(this);
        
        mAddress = b.getString(Global_variables.ADDRESS);

        //récup contact
        mBlueDev = mBluetooth.getmDevice();
        mcontact = SQL_personne.getWithAddress(mAddress);

        //verif contact déjà existant ou non
        if(mcontact == null){
            mcontact = new Personne(mBlueDev.getName(),mAddress);
            SQL_personne.addPersonnage(mcontact);
        }

        SQL_personne.getId(mcontact);
        id_com = mcontact.getId();
        mCom = mBluetooth.getmCom();

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

                if (!message.isEmpty()) {
                    mBluetooth.getmCom().write(message.getBytes());

                    Message t_message = new Message(message, Message.type_message.SEND, id_com);
                    mArrayAdapter.add(t_message);
                    SQL_message.addMessage(t_message);

                    l_message.invalidate();

                    textmessage.setText("");

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textmessage.getWindowToken(), 0);
                } else {
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
        SQL_personne.open();
        super.onResume();

        mBluetooth.setmActivity(this);

        mBluetooth.initializeCom();
    }



    @Override
    protected void onPause() {
        SQL_message.close();
        SQL_personne.close();
        try {
            mBluetooth.getmSocket().close();
        } catch (IOException e) {
                e.printStackTrace();
        }
        super.onPause();
    }



    public void setmMessage(String msg ){
        mMessage = new Message(msg,Message.type_message.RECEIVE,id_com);
        SQL_message.addMessage(mMessage);
    }


    public void updateDisplay() {
        InitList();
    }
}
