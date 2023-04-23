package com.example.pl2302_android

import android.content.Context
import android.hardware.usb.*
import android.os.PowerManager
import android.util.Log

import java.util.*

object UsbServer {

    fun intToHex(i: Int): String {
        return String.format("%04X", i)
    }

    fun scanUsbDevice():UsbInfo? {
       val  usbManager = MainApplication.instance.getSystemService(Context.USB_SERVICE) as UsbManager
        val deviceList: HashMap<String, UsbDevice> = usbManager.deviceList
        Log.e("usblist",deviceList.size.toString())
        for (device in deviceList.values) {
            val i1 = device.deviceId
            val i2 = device.productId
            val i3 = device.deviceName
            val i4 = device.productName

            val vid= device.vendorId
            val pid= device.productId

            Log.i("usbDeviceLP", "$i1    $i2     $i3     $i4   ${intToHex(vid)}     ${intToHex(pid)}")
            return UsbInfo(vid,pid)
        }
        return null
    }



}