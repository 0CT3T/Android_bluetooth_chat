package com.example.sebastien.bluechat.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.sebastien.bluechat.DAO.PersonneDAO;
import com.example.sebastien.bluechat.Modele.Personne;
import com.example.sebastien.bluechat.R;
import com.example.sebastien.bluechat.View.Adapter.PersonneAdapter;

import java.util.ArrayList;


/**
 *
 */
public class MainActivity extends AppCompatActivity {

    private static  ListView l_contact;
    private         PersonneDAO SQL_message;
    private static  PersonneAdapter mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);


        SQL_message = new PersonneDAO(this);
        SQL_message.open();

        InitList();

    }

    /***************************************
     * LIST CONTACT
     **************************************/

    public void InitList() {
        l_contact = (ListView) findViewById(R.id.list_messages);

        //TODO initialisation avec la base de donnée
        ArrayList<Personne> stringListe = SQL_message.getAllPersonne();
        // ArrayList<Message> stringListe = new ArrayList<Message>();
        mArrayAdapter = new PersonneAdapter(this, stringListe);
        l_contact.setAdapter(mArrayAdapter);



    }


    /***************************************
     * MENU
     **************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Join:
                startJoin();
                return true;
            case R.id.host:
                startHost();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startHost(){
        startActivity(new Intent(this, Wait.class));
    }

    private void startJoin(){
        startActivity(new Intent(this, Chat.class));
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
