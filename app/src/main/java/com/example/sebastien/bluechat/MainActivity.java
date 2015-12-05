package com.example.sebastien.bluechat;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;




/**
 *
 */
public class MainActivity extends AppCompatActivity {

    private static ListView l_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);

        InitList();

    }

    /***************************************
     * LIST CONTACT
     **************************************/

    public void InitList() {
        l_contact = (ListView) findViewById(R.id.list_messages);

        //TODO initialisation avec la base de donnée

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


}
