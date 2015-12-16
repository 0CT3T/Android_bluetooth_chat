package com.example.sebastien.bluechat.View.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sebastien.bluechat.Modele.BTDevice;
import com.example.sebastien.bluechat.R;

import java.util.ArrayList;

/**
 * Created by thomas on 15/12/2015.
 */
public class DeviceAdapter extends BaseAdapter {

    private ArrayList <BTDevice> mDevList;

    //le context dans lesquel il est utilisé
    private Context mContext;
    //
    private LayoutInflater mInflater;

    public DeviceAdapter(Context context, ArrayList<BTDevice> aListDev) {
        mContext = context;
        mDevList = aListDev;
        mInflater = LayoutInflater.from(mContext);
    }


    public void add(BTDevice dev){
        mDevList.add(dev);
    }

    @Override
    public int getCount() {
        return mDevList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDevList.get(position);
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
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.contentdevice, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        TextView dev_temp = (TextView)layoutItem.findViewById(R.id.dev);
        dev_temp.setText(mDevList.get(position).getName());

        //layoutItem.setBackgroundResource(R.color.chat_send);

        //On retourne l'item créé.
        return layoutItem;
    }
}
