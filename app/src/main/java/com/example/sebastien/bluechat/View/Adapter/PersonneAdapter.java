package com.example.sebastien.bluechat.View.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sebastien.bluechat.Modele.Message;
import com.example.sebastien.bluechat.Modele.Personne;
import com.example.sebastien.bluechat.R;

import java.util.ArrayList;


/**
 * Created by sebastien on 05/12/2015.
 */
public class PersonneAdapter extends BaseAdapter {

    private ArrayList<Personne> personne;
    //le context dans lesquel il est utilisé
    private Context mContext;
    //
    private LayoutInflater mInflater;




    public PersonneAdapter(Context context, ArrayList<Personne> aListM) {
        mContext = context;
        personne = aListM;
        mInflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return personne.size();
    }

    public void add(Personne value) {
        personne.add(value);
    }


    @Override
    public Object getItem(int position) {
        return personne.get(position);
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
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.contentpersonne, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        TextView per_temp = (TextView)layoutItem.findViewById(R.id.personne);
        per_temp.setText(personne.get(position).getName());


        //On retourne l'item créé.
        return layoutItem;
    }

}
