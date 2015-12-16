package com.example.sebastien.bluechat.Bluetooth;

import android.bluetooth.BluetoothSocket;
import android.os.Message;

import com.example.sebastien.bluechat.Global_variables.Global_variables;
import com.example.sebastien.bluechat.Handler.MyHandler;
import com.example.sebastien.bluechat.Modele.BTDevice;
import com.example.sebastien.bluechat.View.BaseActivity;
import com.example.sebastien.bluechat.View.Chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by thomas on 16/12/2015.
 */
public class BTCommunication extends Thread {

    private BluetoothSocket mSocket;

    private MyHandler       mHandler;
    private Bluetooth       mBluetooth;

    //Streams
    private InputStream     mIn;
    private OutputStream    mOut;


    ArrayList<Character> chars = new ArrayList<>();

    public BTCommunication(MyHandler h, Bluetooth BT){
        mBluetooth = BT;
        mSocket     = BT.getmSocket();
        mHandler    = h;

        InputStream tempmIn     = null;
        OutputStream tempmOut    = null;

        try{
            tempmIn     = mSocket.getInputStream();
            tempmOut    = mSocket.getOutputStream();
        }
        catch (Exception e){
            mBluetooth.getmActivity().setmToToast("Problem opening streams in BT connec");
            Message message = mBluetooth.getmActivity().getmHandler().obtainMessage(Global_variables.CMD_DISPLAY, null);
            message.sendToTarget();
            e.printStackTrace();
        }

        mIn     = tempmIn;
        mOut    = tempmOut;

        mBluetooth.getmActivity().setmToToast("Streams opened");
        Message message = mBluetooth.getmActivity().getmHandler().obtainMessage(Global_variables.CMD_DISPLAY, null);
        message.sendToTarget();
    }

    public void run() {

        int iByte;
        boolean bStatus = true;


        while (bStatus) {
            try {
                if (mSocket.isConnected()) {
                    iByte = mIn.read();
                    processingMessage(iByte);
                }
                else {
                    mBluetooth.getmActivity().setmToToast("Socket closed");
                    Message message = mBluetooth.getmActivity().getmHandler().obtainMessage(Global_variables.CMD_DISPLAY, null);
                    message.sendToTarget();
                }



            } catch (IOException e) {

                bStatus = false;
            }
        }
    }

    public void write(byte[] bytes) {
        try {

            mOut.write(bytes);
            mOut.write("\n".getBytes());
        } catch (IOException e) {
            mBluetooth.getmActivity().setmToToast("Problem while sending a message");
            Message message = mBluetooth.getmActivity().getmHandler().obtainMessage(Global_variables.CMD_DISPLAY, null);
            message.sendToTarget();
        }
    }


    public void processingMessage(int iByte) {

        chars.add((char) iByte);

        //Si un message a été envoyé
        if (chars.get(chars.size()-1)=='\n') {
            StringBuilder str = new StringBuilder(chars.size());
            for (Character c : chars) {
                str.append(c);
            }

            ((Chat) mBluetooth.getmActivity()).setmMessage(str.toString());

            Message message = mBluetooth.getmActivity().getmHandler().obtainMessage(Global_variables.CMD_UPDATEVIEW, null);
            mHandler.sendMessage(message);


        }

    }
}


