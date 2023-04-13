package com.example.pl2302_android

import androidx.appcompat.app.AppCompatActivity
import tw.com.prolific.pl2303gmultilib.PL2303GMultiLib
import com.example.pl2302_android.uart.UARTSettingInfo
import android.os.Bundle
import android.hardware.usb.UsbManager
import android.widget.Toast
import android.content.IntentFilter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import java.lang.Thread
import android.os.Looper
import java.lang.Runnable
import java.lang.StringBuffer
import android.util.Log

class MainActivity : AppCompatActivity() {
    var mSerialMulti: PL2303GMultiLib? = null
    private lateinit var gUARTInfoList: Array<UARTSettingInfo?>
    private var iDeviceCount = 0
    private val bDeviceOpened = BooleanArray(MAX_DEVICE_COUNT)
    private val gThreadStop = BooleanArray(MAX_DEVICE_COUNT)
    private val gRunningReadThread = BooleanArray(MAX_DEVICE_COUNT)
    private val enableFixedCOMPortMode = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mSerialMulti = PL2303GMultiLib(
            getSystemService(USB_SERVICE) as UsbManager,
            this, ACTION_USB_PERMISSION
        )
        gUARTInfoList = arrayOfNulls(MAX_DEVICE_COUNT)
        for (i in 0 until MAX_DEVICE_COUNT) {
            gUARTInfoList[i] = UARTSettingInfo()
            gUARTInfoList[i]!!.iPortIndex = i
            gThreadStop[i] = false
            gRunningReadThread[i] = false
            bDeviceOpened[i] = false
        }
    }

    override fun onDestroy() {
        DumpMsg("Enter onDestroy")
        if (mSerialMulti != null) {
            for (i in 0 until MAX_DEVICE_COUNT) {
                gThreadStop[i] = true
            }
            if (iDeviceCount > 0) unregisterReceiver(pLMultiLibReceiver)
            mSerialMulti!!.PL2303Release()
            mSerialMulti = null
        }
        super.onDestroy()
    }

    public override fun onResume() {
        Log.e("vacax","onResume")
        super.onResume()
        synchronized(this) {
            resetStatus()
            iDeviceCount = mSerialMulti!!.PL2303Enumerate()
            delayTime(60)
            Log.e("vacax","device count=$iDeviceCount")
            DumpMsg("enumerate Count=$iDeviceCount")
            if (0 == iDeviceCount) {
                Toast.makeText(this, "no more devices found", Toast.LENGTH_SHORT).show()
                DumpMsg("no more devices found")
            } else {
                openDevice()
                DumpMsg("DevOpen[0]=" + bDeviceOpened[DeviceIndex1])
                if (!bDeviceOpened[DeviceIndex1]) {
                    DumpMsg("iDeviceCount(=1)=$iDeviceCount")
                    if (enableFixedCOMPortMode) {
                        if (mSerialMulti!!.PL2303getCOMNumber(0) !== NULL || mSerialMulti!!.PL2303getCOMNumber(
                                0
                            ) !== ""
                        ) {
                            DumpMsg("Button1_COM Number: " + mSerialMulti!!.PL2303getCOMNumber(0))
                        }
                    }
                }
                val filter = IntentFilter()
                filter.addAction(mSerialMulti!!.PLUART_MESSAGE)
                registerReceiver(pLMultiLibReceiver, filter)
                Toast.makeText(this, "The $iDeviceCount devices are attached", Toast.LENGTH_SHORT)
                    .show()
                DumpMsg("The $iDeviceCount devices are attached")
            }
        }
    }

    private fun openDevice() {
        openUARTDevice(DeviceIndex1)
    }

    private val pLMultiLibReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == mSerialMulti!!.PLUART_MESSAGE) {
                val extras = intent.extras
                if (extras != null) {
                    val str=extras.getString(mSerialMulti!!.PLUART_DETACHED)
                    DumpMsg("receive data:$str")
                    val index = str?.let { Integer.valueOf(it) }
                    if (DeviceIndex1 == index) {
                        bDeviceOpened[DeviceIndex1] = false
                    }
                }
            }
        }
    }

    private fun openUARTDevice(index: Int) {
        DumpMsg("Enter OpenUARTDevice: $index")
        if (mSerialMulti == null) {
            DumpMsg("Error: mSerialMulti==null")
            return
        }
        if (!mSerialMulti!!.PL2303IsDeviceConnectedByIndex(index)) {
            DumpMsg("Error: !AP_mSerialMulti.PL2303IsDeviceConnectedByIndex(index)")
            return
        }
        val res: Boolean
        val info = gUARTInfoList[index]
        DumpMsg("UARTSettingInfo: index:" + info!!.iPortIndex.toString())
        res = mSerialMulti!!.PL2303OpenDevByUARTSetting(
            index, info.mBaudrate, info.mDataBits, info.mStopBits,
            info.mParity, info.mFlowControl
        )
        if (!res) {
            DumpMsg("Error: fail to PL2303OpenDevByUARTSetting")
            return
        }
        bDeviceOpened[index] = true
        if (!gRunningReadThread[index]) {
            updateDisplayView(index)
        }
    }

    private fun updateDisplayView(index: Int) {
        gThreadStop[index] = false
        gRunningReadThread[index] = true
        if (DeviceIndex1 == index) {
            Thread(readLoop1).start()
        }
    }

    private var readLen1 = 0
    private val readBuf1 = ByteArray(ReadDataBufferSize)
    var mHandler1 = Handler(Looper.myLooper()!!)
    fun bytesToHex(bytes: ByteArray, len: Int): String? {
        val sb = StringBuffer()
        for (i in 0 until len) {
            val hex = Integer.toHexString(bytes[i].toInt() and 0xFF)
            if (hex.length == 1) {
                sb.append('0')
            }
            sb.append(hex)
        }
        return sb.toString()
    }

    private val readLoop1 = Runnable {
        while (true) {
            readLen1 = mSerialMulti!!.PL2303Read(DeviceIndex1, readBuf1)
            if (readLen1 > 0) {
                mHandler1.post {
                    val result = bytesToHex(readBuf1, readLen1)
                    Log.e("vaca", "result: $result")
                }
            }
            delayTime(60)
            if (gThreadStop[DeviceIndex1]) {
                gRunningReadThread[DeviceIndex1] = false
                return@Runnable
            }
        }
    }

    private fun resetStatus() {
        mSerialMulti!!.PL2303G_ReSetStatus()
        if (bDeviceOpened[DeviceIndex1]) {
            DumpMsg("DeviceIndex1 is open")
            if (!mSerialMulti!!.PL2303IsDeviceConnectedByIndex(0)) {
                DumpMsg("DeviceIndex1: disconnect")
                bDeviceOpened[DeviceIndex1] = false
            }
        }
    }

    private fun delayTime(dwTimeMS: Int) {
        var checkTime: Long
        if (0 == dwTimeMS) {
            Thread.yield()
            return
        }
        val startTime: Long = System.currentTimeMillis()
        do {
            checkTime = System.currentTimeMillis()
            Thread.yield()
        } while (checkTime - startTime <= dwTimeMS)
    }

    companion object {
        private const val bDebugMesg = true
        private const val ReadDataBufferSize = 64
        private const val DeviceIndex1 = 0
        private const val MAX_DEVICE_COUNT = 1
        private const val ACTION_USB_PERMISSION =
            "com.prolific.pl2300G_multisimpletest.USB_PERMISSION"
        private val NULL: String? = null
        private fun DumpMsg(s: Any) {
            if (true == bDebugMesg) {
                Log.d("PL2303MultiUSBApp", "A: $s")
            }
        }
    }
}