package com.example.pl2302_android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pl2302_android.databinding.ActivityMainBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity(){


    lateinit var binding:ActivityMainBinding


    fun showInfo(usbDeviceInfo:UsbInfo){
        if(usbDeviceInfo!=null){
            if(usbDeviceInfo.vid==0x067B&&usbDeviceInfo.pid==0x23a3){
                showDialog("pl2303新版")
            }
            else if(usbDeviceInfo.vid==1659&&usbDeviceInfo.pid==8963){
                showDialog("pl2303旧版")
            }
            else{
                showDialog("未知设备")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        UsbServer.scanUsbDevice()?.let {
            showInfo(it)
        }
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: UsbInfo?) {
        event?.let {
            showInfo(it)
        }
    }

    fun showDialog(s:String){
        AlertDialog.Builder(this)
            .setTitle(s)
            .setMessage(s)
            .setPositiveButton(android.R.string.yes,
                DialogInterface.OnClickListener { dialog, which ->
                })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }



}