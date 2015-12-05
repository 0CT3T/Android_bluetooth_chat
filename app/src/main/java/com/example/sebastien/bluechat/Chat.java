package com.example.sebastien.bluechat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by sebastien on 05/12/2015.
 */
public class Chat extends Activity {

    private static ListView l_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        InitList();

    }

    public void InitList() {
        l_message = (ListView) findViewById(R.id.list_messages);

        //TODO initialisation avec la base de donnée

    }
}
