package com.example.pl2302_android

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager

import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

class LittlePuReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == UsbManager.ACTION_USB_DEVICE_ATTACHED) {
            val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
            EventBus.getDefault().post(UsbServer.scanUsbDevice());

        } else if (intent.action == UsbManager.ACTION_USB_DEVICE_DETACHED) {
            val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
            val i4 = device?.productName

        }


    }
}