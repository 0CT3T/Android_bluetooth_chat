package com.example.sebastien.bluechat.Handler;

import android.app.Activity;
import android.os.Message;

import com.example.sebastien.bluechat.Global_variables.Global_variables;
import com.example.sebastien.bluechat.View.BaseActivity;
import com.example.sebastien.bluechat.View.Chat;
import com.example.sebastien.bluechat.View.MainActivity;
import com.example.sebastien.bluechat.View.Wait;

/**
 * Created by thomas on 16/12/2015.
 */
public class MyHandler extends android.os.Handler {

    private BaseActivity    mActivity;
    private int             mactType;

    public MyHandler(BaseActivity act){
        super();
        mActivity = act;
        if(mActivity.getClass().equals(MainActivity.class))
            mactType = Global_variables.MAINACT_TYPE;
        else if(mActivity.getClass().equals(Wait.class))
            mactType = Global_variables.WAITACT_TYPE;
        else
            mactType = Global_variables.CHATACT_TYPE;
    }
    @Override
    public void handleMessage(Message message) {
        // This is where you do your work in the UI thread.
        // Your worker tells you in the message what to do.

        switch (message.what){
            case  Global_variables.CMD_DISPLAY :
                mActivity.showToast(mActivity.getmToToast());
                break;
            case  Global_variables.CMD_MSG_RECEIVED :
                if(mactType == Global_variables.CHATACT_TYPE){

                }
                break;

            case  Global_variables.CMD_UPDATEVIEW :
                if(mactType == Global_variables.CHATACT_TYPE){

                    ((Chat) mActivity).updateDisplay();
                }
                break;

        }
    }


}
