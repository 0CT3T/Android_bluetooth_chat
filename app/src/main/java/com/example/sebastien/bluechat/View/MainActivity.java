package com.example.sebastien.bluechat.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sebastien.bluechat.DAO.MessageDAO;
import com.example.sebastien.bluechat.DAO.PersonneDAO;
import com.example.sebastien.bluechat.Modele.Message;
import com.example.sebastien.bluechat.Modele.Personne;
import com.example.sebastien.bluechat.R;
import com.example.sebastien.bluechat.View.Adapter.MessageAdapter;
import com.example.sebastien.bluechat.View.Adapter.PersonneAdapter;

import java.util.ArrayList;


/**
 *
 */
public class MainActivity extends AppCompatActivity {



    private enum state_view {
        PERSONNE,
        DETAIL
    }

    private static  ListView        l_contact;
    private         PersonneDAO     SQL_personne;
    private static  PersonneAdapter mArrayAdapter;
    private         state_view      state = state_view.PERSONNE;
    private         MessageDAO      SQL_message;
    private         Context         context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);

        context = this;

        SQL_personne = new PersonneDAO(this);
        SQL_personne.open();

        SQL_message = new MessageDAO(this);
        SQL_message.open();



        InitList();

    }

    /***************************************
     * LIST CONTACT
     **************************************/

    public void InitList() {
        l_contact = (ListView) findViewById(R.id.list_messages);

        l_contact.setStackFromBottom(false);
        //initialisation avec la base de donnée
        ArrayList<Personne> stringListe = SQL_personne.getAllPersonne();
        mArrayAdapter = new PersonneAdapter(this, stringListe);
        l_contact.setAdapter(mArrayAdapter);

        l_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Personne personne = (Personne) l_contact.getItemAtPosition(position);

                //init list message with id
                l_contact.setStackFromBottom(true);

                //initialisation avec la base de donnée
                ArrayList<Message> stringListe = SQL_message.getAllMessage(personne.getId());
                // ArrayList<Message> stringListe = new ArrayList<Message>();
                MessageAdapter temp = new MessageAdapter(context, stringListe);
                l_contact.setAdapter(temp);

                state = state_view.DETAIL;


                //Toast.makeText(getBaseContext(), personne.getName(), Toast.LENGTH_SHORT).show();
            }
        });


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
        Intent intent = new Intent(this, Wait.class);
        startActivity(intent);
    }

    private void startJoin(){
        Intent intent = new Intent(this, Chat.class);
        Bundle b = new Bundle();
        //TODO initialiser avec le bluethoot
        b.putInt("key", 0); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }



    /***************************************
     * Gestion du DAO
     **************************************/

    @Override
    protected void onResume() {
        SQL_personne.open();
        //Remettre la vue d'origine
        InitList();
        state = state_view.PERSONNE;

        super.onResume();
    }

    @Override
    protected void onPause() {
        SQL_personne.close();
        super.onPause();
    }


    /***************************************
     * Gestion du bouton retour de l'application
     **************************************/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Fais ton traitement
            if (state == state_view.DETAIL){
                //remettre la vue des Personne
                InitList();

                state=state_view.PERSONNE;
            }
            else{
                finish();
            }
        }


        return true;
    }

}
