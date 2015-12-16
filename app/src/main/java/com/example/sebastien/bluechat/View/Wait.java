package com.example.sebastien.bluechat.View;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sebastien.bluechat.Bluetooth.Bluetooth;
import com.example.sebastien.bluechat.Global_variables.Global_variables;
import com.example.sebastien.bluechat.Modele.BTDevice;
import com.example.sebastien.bluechat.Modele.Personne;
import com.example.sebastien.bluechat.R;
import com.example.sebastien.bluechat.View.Adapter.DeviceAdapter;
import com.example.sebastien.bluechat.View.Adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.logging.Handler;

/**
 * Created by sebastien on 05/12/2015.
 */
public class Wait extends BaseActivity{
    private String          mHostName;
    private String          mWaitType;
    private Bluetooth       mBluetooth;
    private static ListView l_device;

    private static DeviceAdapter mArrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Bluetooth.List.size()>0) {
            mBluetooth = Bluetooth.List.get(0);
        }
        else
            mBluetooth = new Bluetooth(this,this);
        mBluetooth.setM_Context(this);
        mBluetooth.setmActivity(this);


        mWaitType   =  getIntent().getStringExtra(Global_variables.WAIT_TYPE);



        //TODO a completer
    }

    @Override
    protected void onResume() {
        super.onResume();

        mBluetooth.setmActivity(this);
        BTDevice.BTdevList.clear();

        mBluetooth.startSearching();

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mBluetooth.getmReceiver(), filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mBluetooth.getmReceiver(), filter);

        if(mWaitType.equals(Global_variables.HOST)){
            hostwaitinit();
        }
        else{
            joinwaitinit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBluetooth !=null){
            mBluetooth.stopDiscovery();
        }
    }

    private void joinwaitinit() {
        setContentView(R.layout.joinwait);
        InitList();


    }

    private void InitList() {
        l_device = (ListView)findViewById(R.id.list_devices);

        mArrayAdapter = new DeviceAdapter(this, BTDevice.BTdevList);

        l_device.setAdapter(mArrayAdapter);


        l_device.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BTDevice dev = (BTDevice) l_device.getItemAtPosition(position);
                mBluetooth.stopDiscovery();
                mBluetooth.startConnecting(dev.mDev());
                //Toast.makeText(getBaseContext(), "Tentative  de connec avec " + dev.getname(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hostwaitinit() {



        setContentView(R.layout.hostwait);

        /* Problème, provoque unu infinité de demandes à l'utilisateur.
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,300);
        startActivity(discoverableIntent);
        */

        mHostName   =  getIntent().getStringExtra(Global_variables.HOST_NAME);
        ((TextView)findViewById(R.id.NameReseau)).setText(mHostName);
        mBluetooth.startHosting();
    }



    public ListView getl_device() {
        return l_device;
    }

    public DeviceAdapter getmArrayAdapter() {
        return mArrayAdapter;
    }

    public void updateList(BTDevice dev) {
        //mArrayAdapter.add(dev);
        //l_device.invalidate();
        InitList();
    }

    public String getmWaitType() {
        return mWaitType;
    }



    public void startChat() {
        Intent intent = new Intent(this, Chat.class);
        Bundle b = new Bundle();
        b.putString(Global_variables.ADDRESS, mWaitType);

        intent.putExtras(b);
        startActivity(intent);
    }

    public void connection_failed() {
        showToast("Problem while connecting");
    }
}

