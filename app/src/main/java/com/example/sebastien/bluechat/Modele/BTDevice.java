package com.example.sebastien.bluechat.Modele;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;

/**
 * Created by thomas on 14/12/2015.
 */
public class BTDevice {

    public static ArrayList<BTDevice> BTdevList = new ArrayList<>();

    private int     id;
    private String  name;
    private String  adress;
    private BluetoothDevice mDev;

    public BTDevice(BluetoothDevice dev){
        mDev    = dev;
        name    = dev.getName();
        adress  = dev.getAddress();
        BTdevList.add(this);
    }

    public BTDevice(){

        name    = "test";
        adress  = "test";
        BTdevList.add(this);
    }
    
    public void setAdress (String add){
        adress = add;
    }
    
    public String getAdress(){
        return adress;
    }

    public static  ArrayList<String> getAllAdresses(){
        int i;
        ArrayList<String > temp = new ArrayList<>();

        for(i=0;i<BTdevList.size();i++){
            temp.add(BTdevList.get(i).getAdress());
        }
        return temp;
    }
    
    public void setName (String n){
        name = n;
    }
    public String getName(){
        return name;
    }

    public void setId(int i){
        id = i;
    }
    public int getId(){
        return id;
    }

    public  String getname() {
        return name;
    }

    public BluetoothDevice mDev() {
        return mDev;
    }
}
