package com.example.sebastien.bluechat;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sebastien.bluechat.Modele.Message;

import java.util.ArrayList;


/**
 * Created by sebastien on 05/12/2015.
 */
public class MessageAdapter extends BaseAdapter {

    private ArrayList<Message> message;
    //le context dans lesquel il est utilisé
    private Context mContext;
    //
    private LayoutInflater mInflater;




    public MessageAdapter(Context context, ArrayList<Message> aListM) {
        mContext = context;
        message = aListM;
        mInflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return message.size();
    }

    public void add(Message value) {
        message.add(value);
    }


    @Override
    public Object getItem(int position) {
        return message.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;
        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML "personne_layout.xml"
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.contentchat, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        TextView mes_temp = (TextView)layoutItem.findViewById(R.id.message);
        mes_temp.setText(message.get(position).getMessage());



        //affichage en fonction du type
        switch(message.get(position).getType()) {
            case RECEIVE:

                    layoutItem.setBackgroundResource(R.color.chat_receive);
                    layoutItem.setGravity(Gravity.LEFT);

                break;
            case SEND:
                    layoutItem.setBackgroundResource(R.color.chat_send);
                    layoutItem.setGravity(Gravity.RIGHT);
                break;
        }


        //On retourne l'item créé.
        return layoutItem;
    }

}
