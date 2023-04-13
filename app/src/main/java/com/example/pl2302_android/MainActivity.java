package com.example.pl2302_android;


import java.io.IOException;

import tw.com.prolific.pl2303gmultilib.PL2303GMultiLib;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.usb.UsbManager;
import android.os.Looper;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static boolean bDebugMesg = true;

    PL2303GMultiLib mSerialMulti;
    private static final int MENU_ABOUT = 0;

    private static enum DeviceOrderIndex {
        DevOrder1,
        DevOrder2,
        DevOrder3,
        DevOrder4
    };


    private static final int ReadDataBufferSize = 64;

    private static final int DeviceIndex1 = 0;
    private static final int DeviceIndex2 = 1;
    private static final int DeviceIndex3 = 2;
    private static final int DeviceIndex4 = 3;

    private Button btOpen1;


    private static final int MAX_DEVICE_COUNT = 1;
    private static final String ACTION_USB_PERMISSION = "com.prolific.pl2300G_multisimpletest.USB_PERMISSION";

    private static final String NULL = null;
    private UARTSettingInfo gUARTInfoList[];
    private int iDeviceCount = 0;
    private boolean bDeviceOpened[] = new boolean[MAX_DEVICE_COUNT];

    private boolean gThreadStop[] = new boolean[MAX_DEVICE_COUNT];
    private boolean gRunningReadThread[] = new boolean[MAX_DEVICE_COUNT];
    private boolean enableFixedCOMPortMode=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        mSerialMulti = new PL2303GMultiLib((UsbManager) getSystemService(Context.USB_SERVICE),
                this, ACTION_USB_PERMISSION);

        gUARTInfoList = new UARTSettingInfo[MAX_DEVICE_COUNT];



        for(int i=0;i<MAX_DEVICE_COUNT;i++) {
            gUARTInfoList[i] = new UARTSettingInfo();
            gUARTInfoList[i].iPortIndex = i;
            gThreadStop[i] = false;
            gRunningReadThread[i] = false;
            bDeviceOpened[i] = false;
        }
    }




    protected void onDestroy() {
        DumpMsg("Enter onDestroy");
        if(mSerialMulti!=null) {
            for(int i=0;i<MAX_DEVICE_COUNT;i++) {
                gThreadStop[i] = true;
            }//First to stop app view-thread
            if(iDeviceCount>0)
                unregisterReceiver(PLMultiLibReceiver);
            mSerialMulti.PL2303Release();
            mSerialMulti = null;
        }
        super.onDestroy();
    }
//  OpenUARTDevice(DeviceIndex1);

    public void onResume() {
        super.onResume();
        synchronized (this) {
            ReSetStatus();
            iDeviceCount = mSerialMulti.PL2303Enumerate();
            DelayTime(60);
            DumpMsg("enumerate Count="+iDeviceCount);
            if( 0==iDeviceCount ) {
                Toast.makeText(this, "no more devices found", Toast.LENGTH_SHORT).show();
                DumpMsg("no more devices found");
            } else {
                openDevice();
                DumpMsg("DevOpen[0]="+bDeviceOpened[DeviceIndex1]);

                if(!bDeviceOpened[DeviceIndex1]) {
                    DumpMsg("iDeviceCount(=1)="+iDeviceCount);
                    if(enableFixedCOMPortMode) {
                        if(mSerialMulti.PL2303getCOMNumber(0)!=NULL || mSerialMulti.PL2303getCOMNumber(0)!="" ) {
                            btOpen1.setText(mSerialMulti.PL2303getCOMNumber(0));
                            DumpMsg("Button1_COM Number: "+ mSerialMulti.PL2303getCOMNumber(0));
                        }
                    }
                }
                IntentFilter filter = new IntentFilter();
                filter.addAction(mSerialMulti.PLUART_MESSAGE);
                registerReceiver(PLMultiLibReceiver, filter);
                Toast.makeText(this, "The "+iDeviceCount+" devices are attached", Toast.LENGTH_SHORT).show();
                DumpMsg("The "+iDeviceCount+" devices are attached");
            }

        }
    }

    void openDevice(){
        OpenUARTDevice(DeviceIndex1);
    }

    private final BroadcastReceiver PLMultiLibReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(mSerialMulti.PLUART_MESSAGE)){
                Bundle extras = intent.getExtras();
                if(extras!=null) {
                    String str = (String)extras.get(mSerialMulti.PLUART_DETACHED);
                    DumpMsg("receive data:"+str);
                    int index = Integer.valueOf(str);
                    if(DeviceIndex1==index) {
                        bDeviceOpened[DeviceIndex1] = false;
                        if(enableFixedCOMPortMode) btOpen1.setText("Open");
                    }
                }
            }
        }
    };


    private void OpenUARTDevice(int index) {
        DumpMsg("Enter OpenUARTDevice: "+String.valueOf(index));
        if(mSerialMulti==null) {
            DumpMsg("Error: mSerialMulti==null");
            return;
        }
        if(!mSerialMulti.PL2303IsDeviceConnectedByIndex(index)) {
            DumpMsg("Error: !AP_mSerialMulti.PL2303IsDeviceConnectedByIndex(index)");
            return;
        }
        boolean res;
        UARTSettingInfo info = gUARTInfoList[index];
        DumpMsg("UARTSettingInfo: index:"+String.valueOf(info.iPortIndex));
        res = mSerialMulti.PL2303OpenDevByUARTSetting(index, info.mBaudrate, info.mDataBits, info.mStopBits,
                info.mParity, info.mFlowControl);
        if( !res ) {
            DumpMsg("Error: fail to PL2303OpenDevByUARTSetting");
            return;
        }
        bDeviceOpened[index] = true;

        if(!gRunningReadThread[index]) {
            UpdateDisplayView(index);
        }
    }
    private void UpdateDisplayView(int index) {
        gThreadStop[index] = false;
        gRunningReadThread[index] = true;
        if( DeviceIndex1==index ) {
            new Thread(ReadLoop1).start();
        }
    }

    private int ReadLen1;
    private byte[] ReadBuf1 = new byte[ReadDataBufferSize];
    Handler mHandler1 = new Handler(Looper.myLooper());

    String bytesToHex(byte[] bytes,int len) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[len * 2];
        for ( int j = 0; j < len; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private Runnable ReadLoop1 = new Runnable() {
        public void run() {
            for (;;) {
                ReadLen1 = mSerialMulti.PL2303Read(DeviceIndex1, ReadBuf1);
                if (ReadLen1 > 0) {
                    DumpMsg("Read  Length : " + ReadLen1);
                    mHandler1.post(new Runnable() {
                        public void run() {
                            StringBuffer sbHex=new StringBuffer();
                            for (int j = 0; j < ReadLen1; j++) {
                                sbHex.append((char) (ReadBuf1[j]&0x000000FF));
                            }
                            String result=bytesToHex(ReadBuf1,ReadLen1);
                            Log.e("vaca", "result: "+result);
                        }
                    });
                }

                DelayTime(60);

                if (gThreadStop[DeviceIndex1]) {
                    gRunningReadThread[DeviceIndex1] = false;
                    return;
                }
            }

        }
    };



    private void ReSetStatus(){
        mSerialMulti.PL2303G_ReSetStatus();
        if(bDeviceOpened[DeviceIndex1]) {
            DumpMsg("DeviceIndex1 is open");
            if(!mSerialMulti.PL2303IsDeviceConnectedByIndex(0)) {
                DumpMsg("DeviceIndex1: disconnect");
                bDeviceOpened[DeviceIndex1] = false;
                if(enableFixedCOMPortMode) btOpen1.setText("Open");
            }
        }
    }



    private void DelayTime(int dwTimeMS) {
        long StartTime, CheckTime;

        if(0==dwTimeMS) {
            Thread.yield();
            return;
        }

        StartTime = System.currentTimeMillis();
        do {
            CheckTime=System.currentTimeMillis();
            Thread.yield();
        } while( (CheckTime-StartTime)<=dwTimeMS);
    }

    private static void DumpMsg(Object s) {
        if(true==bDebugMesg) {
            Log.d("PL2303MultiUSBApp", "A: " + s.toString());
        }
    }
}
