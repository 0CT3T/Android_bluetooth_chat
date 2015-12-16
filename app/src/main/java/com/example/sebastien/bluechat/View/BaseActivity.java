package com.example.sebastien.bluechat.View;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.sebastien.bluechat.Bluetooth.Bluetooth;
import com.example.sebastien.bluechat.Handler.MyHandler;

/**
 * Created by thomas on 16/12/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private MyHandler  mHandler;
    private String     mToToast;
    protected Bluetooth mBluetooth;

    public BaseActivity(){
        super();
        mHandler = new MyHandler(this);

    }



    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    
    public String getmToToast(){
        return mToToast;
    }
    
    public void setmToToast(String msg){
        mToToast = msg;
    }

    public MyHandler getmHandler(){
        return mHandler;
    }
    
}
