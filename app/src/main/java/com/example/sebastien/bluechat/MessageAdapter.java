package com.example.sebastien.bluechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sebastien on 05/12/2015.
 */
public class MessageAdapter extends BaseAdapter {

    private ArrayList<String> message;
    //le context dans lesquel il est utilisé
    private Context mContext;
    //
    private LayoutInflater mInflater;


    public MessageAdapter(Context context, ArrayList<String> aListM) {
        mContext = context;
        message = aListM;
        mInflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return message.size();
    }

    public void add(String value) {
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
        mes_temp.setText(message.get(position));

        layoutItem.setBackgroundResource(R.color.colorPrimary);

        //On retourne l'item créé.
        return layoutItem;
    }

}
