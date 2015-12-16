package com.example.sebastien.bluechat.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.widget.Toast;

import com.example.sebastien.bluechat.Global_variables.Global_variables;
import com.example.sebastien.bluechat.Modele.BTDevice;
import com.example.sebastien.bluechat.View.BaseActivity;
import com.example.sebastien.bluechat.View.Wait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by thomas on 14/12/2015.
 */
public class Bluetooth {

    /**
     * Liste de BT, permet à toutes les activités d'utiliser le même objet Bluetooth
     */
    public static ArrayList<Bluetooth> List = new ArrayList<>();
    private  BluetoothServerSocket mmServerSocket;

    Context             m_Context;
    BluetoothAdapter    bluetoothAdapter;
    BluetoothManager    bluetoothManager;
    BTCommunication     mCom;
    

    private final UUID MY_UUID = UUID.fromString("10001101-0000-1000-8000-00805f9b34fb");

    BaseActivity            mActivity;
    private BluetoothSocket mSocket;
    private BluetoothDevice mDevice;

    //Threads
    private HostThread      mHostThread;
    private ConnectThread   mConnectThread;


    public Bluetooth(Context context, BaseActivity acti){
        m_Context = context;
        mActivity = acti;
        List.add(this);
    }

    public void ToggleblueTooth(){
        if(!getBTEnabled())
            bluetoothAdapter.enable();
        else {
            bluetoothAdapter.disable();
        }
    }

    public void startSearching() {

        bluetoothAdapter.startDiscovery();
    }



    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BTDevice dev;
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                //Si le device ne fait pas partie de la liste déjà affichée
                if(!(BTDevice.getAllAdresses().contains(device.getAddress()))){

                    dev = new BTDevice(device);
                    if (mActivity.getClass().equals(Wait.class)&& ((Wait)mActivity).getmWaitType().equals(Global_variables.JOIN)){
                        //Toast.makeText(m_Context, "device trouvé blablabla", Toast.LENGTH_SHORT).show();
                        ((Wait)mActivity).updateList(dev);
                    }
                }
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    //mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)&&(mActivity.getClass().equals(Wait.class))) {
                if (((Wait)mActivity).getmWaitType().equals(Global_variables.JOIN)){
                    Toast.makeText(m_Context, "Fini de chercher", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public void stopDiscovery(){
        bluetoothAdapter.cancelDiscovery();
    }


    /*****************************
     * GETTERS and SETTERS
     *****************************/
    public void setmActivity(BaseActivity act){
        mActivity = act;
    }

    public void launchAdapter(){

        bluetoothManager = (BluetoothManager) m_Context.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter =  bluetoothManager.getAdapter();
    }

    public boolean getBTEnabled(){
        return bluetoothAdapter.isEnabled();
    }


    public String  getName(){
        return bluetoothAdapter.getName();
    }

    public void setM_Context(Context C){
        m_Context = C;
    }

    public BroadcastReceiver getmReceiver(){
        return mReceiver;
    }

    public String getDeviceAddress(){
        if (mDevice !=null){
            return mDevice.getAddress();
        }
        else
            return null;
    }

    public BluetoothDevice getmDevice(){
        return mDevice;
    }

    /*************************
     * Hosting part
     *************************/

    public void startHosting(){
        mHostThread = new HostThread();
        mHostThread.start();
    }

    public BluetoothSocket getmSocket() {
        return mSocket;
    }

    public BaseActivity getmActivity() {
        return mActivity;
    }

    public void initializeCom() {
        mCom = new BTCommunication(mActivity.getmHandler(),this);
        mCom.start();
    }

    public BTCommunication getmCom() {
        return mCom;
    }

    public BluetoothAdapter getAdapter() {
        return bluetoothAdapter;
    }

    public class HostThread extends Thread {


        public HostThread() {
            BluetoothServerSocket tmp = null;
            try {
                // UUID
                tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(bluetoothAdapter.getName(), MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            //On essaie d'accepter une connec.
            while (true) {
                try {
                    //((Wait)mActivity).showToast("Waiting for connection");
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    break;
                }


                if (socket != null) {

                    manageConnectedSocket(socket);
                    try {mmServerSocket.close();}
                    catch (IOException e) {
                        mActivity.showToast("problem with hosting system restarting hosting");
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }


    /*********************
     * Connecting part
     *********************/

    public void startConnecting(BluetoothDevice dev){
        mDevice = dev;
        mConnectThread = new ConnectThread();
        mConnectThread.start();
    }


    class ConnectThread extends Thread{

        int tries=0;
        boolean b_got_socket = false;



        public void run(){
            mSocket = null;

            mSocket= connection();

            if(!b_got_socket){
                mActivity.setmToToast("Problem with connecting");
                Message message = mActivity.getmHandler().obtainMessage(Global_variables.CMD_DISPLAY, null);
                message.sendToTarget();

            }else{
                manageConnectedSocket(mSocket);
            }
        }

        private BluetoothSocket connection(){

            BluetoothSocket temp = null;
            while(tries<3 && !b_got_socket){
                try {
                    //  UUID Universally Unique Identifier
                    temp = mDevice.createRfcommSocketToServiceRecord(MY_UUID);
                    temp.connect();
                    b_got_socket = temp.isConnected();
                } catch (IOException e){
                    e.printStackTrace();
                }
                tries++;
            }

            return temp;
        }
    }

    /*********************
     * going to the  chat
     *********************/

    public void manageConnectedSocket(BluetoothSocket socket) {
        mSocket = socket;
        mDevice = socket.getRemoteDevice();
        ((Wait)mActivity).startChat();
    }

}
