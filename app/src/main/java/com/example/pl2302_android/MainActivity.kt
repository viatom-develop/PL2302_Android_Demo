package com.example.pl2302_android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pl2302_android.databinding.ActivityMainBinding
import com.example.pl2302_android.uart.bean.*
import com.example.pl2302_android.uart.toUInt
import com.example.pl2302_android.uart.utils.O2CRC
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import com.hoho.android.usbserial.util.SerialInputOutputManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception


class MainActivity : AppCompatActivity(),SerialInputOutputManager.Listener {

    private var pool: ByteArray? = null
    var port:UsbSerialPort?=null

    lateinit var binding:ActivityMainBinding


    private lateinit var gUARTInfoList: Array<UARTSettingInfo?>
    private var iDeviceCount = 0
    private val bDeviceOpened = BooleanArray(MAX_DEVICE_COUNT)
    private val gThreadStop = BooleanArray(MAX_DEVICE_COUNT)
    private val gRunningReadThread = BooleanArray(MAX_DEVICE_COUNT)
    private val enableFixedCOMPortMode = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gUARTInfoList = arrayOfNulls(MAX_DEVICE_COUNT)
        for (i in 0 until MAX_DEVICE_COUNT) {
            gUARTInfoList[i] =
                UARTSettingInfo()
            gUARTInfoList[i]!!.iPortIndex = i
            gThreadStop[i] = false
            gRunningReadThread[i] = false
            bDeviceOpened[i] = false
        }

       binding.getId.setOnClickListener {
            writeToUartDevice(O2Cmd.getProductIDInfo())
        }

        binding.getVersion.setOnClickListener {
            writeToUartDevice(O2Cmd.getProductVersionInfo())
        }
    }

    override fun onDestroy() {
        port?.close()

        super.onDestroy()
    }


    /**
     * usb插入会自动调用onResume
     */
    public override fun onResume() {
        Log.e("vacax", "onResume")
        super.onResume()
        synchronized(this){
            // Find all available drivers from attached devices.
            // Find all available drivers from attached devices.
            val manager = getSystemService(USB_SERVICE) as UsbManager
            val availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager)
            if (availableDrivers.isEmpty()) {
                return
            }

            val driver = availableDrivers[0]
            val connection = manager.openDevice(driver.device)
                ?: // add UsbManager.requestPermission(driver.getDevice(), ..) handling here
                return
            port = driver.ports[0] // Most devices have just one port (port 0)
            port?.open(connection)
            port?.setParameters(38400, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE)
            val usbIoManager = SerialInputOutputManager(port, this);
            usbIoManager.start();
        }
    }

    private fun openDevice() {
        openUARTDevice(DeviceIndex1)
    }

    private fun writeToUartDevice(bytes: ByteArray) {
        try {
            port?.write(bytes, 1000)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }




    /**
     * usb拔出监听
     */
    private val pLMultiLibReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

        }
    }

    private fun openUARTDevice(index: Int) {
        DumpMsg("Enter OpenUARTDevice: $index")

        val res: Boolean
        val info = gUARTInfoList[index]
        DumpMsg("UARTSettingInfo: index:" + info!!.iPortIndex.toString())


        bDeviceOpened[index] = true
        if (!gRunningReadThread[index]) {
            updateDisplayView(index)
        }
    }

    private fun updateDisplayView(index: Int) {
        gThreadStop[index] = false
        gRunningReadThread[index] = true
        if (DeviceIndex1 == index) {
            Thread(readDataLoop).start()
        }
    }

    private var readLen1 = 0
    private val readBuf1 = ByteArray(ReadDataBufferSize)
    var mHandler1 = Handler(Looper.myLooper()!!)
    private fun bytesToHex(bytes: ByteArray, len: Int): String? {
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


    private fun handleDataPool(bytes: ByteArray?): ByteArray? {
        val bytesLeft: ByteArray? = bytes

        if (bytes == null || bytes.size < 6) {
            return bytes
        }
        loop@ for (i in 0 until bytes.size - 5) {
            if (bytes[i] != 0xAA.toByte() || bytes[i + 1] != 0x55.toByte()) {
                continue@loop
            }

            // need content length
            val len = toUInt(bytes.copyOfRange(i + 3, i + 4))
            if (i + 4 + len > bytes.size) {
                continue@loop
            }

            val temp: ByteArray = bytes.copyOfRange(i, i + 4 + len)
            if (temp.last() == O2CRC.calCRC8(temp)) {
                Log.e("vaca", "temp: ${bytesToHex(temp, temp.size)}")
                val o2Response = O2Response(temp)
                MainScope().launch {
                    onResponseReceived(o2Response)
                }
                val tempBytes: ByteArray? =
                    if (i + 4 + len == bytes.size) null else bytes.copyOfRange(
                        i + 4 + len,
                        bytes.size
                    )

                return handleDataPool(tempBytes)
            }
        }

        return bytesLeft
    }

    private fun onResponseReceived(response: O2Response) {
        when (response.token) {
            0x51->{
                when (response.len) {
                    0x04 -> {
                        when (response.type) {
                            0x01 -> {
                                val data = O2Version(response.content)
                                Log.e(
                                    "vaca",
                                    "response:software:${data.softVersion},hardware:${data.hardVersion}"
                                )
                                binding.info.text = "软件版本:${data.softVersion},硬件版本:${data.hardVersion}"
                            }
                        }
                    }
                }
            }

            0x53 -> {
                when (response.len) {
                    0x07 -> {
                        when (response.type) {
                            0x01 -> {
                                val data = O2Data(response.content)
                                Log.e(
                                    "vaca",
                                    "response:o2:${data.o2},pr:${data.pr} status:${data.state}  mode:${data.mode}"
                                )
                                binding.o2.text = "O2:${data.o2}"
                                binding.pr.text = "PR:${data.pr}"
                                binding.pi.text = "PI:${data.pi}"
                                if(data.state==0x01){
                                    binding.state.text = "状态:探头脱落"
                                }else{
                                    binding.state.text = "状态:正常"
                                }

                            }
                        }
                    }
                }
            }

            0xff -> {
                when (response.type) {
                    0x01 -> {
                        val data = response.content
                        val string=String(data)
                        Log.e("vaca", "response: $string")
                        binding.info.text = "版本ID: "+string
                    }
                }
            }

        }
    }


    /**
     * 这个地方能收到数据
     */
    private val readDataLoop = Runnable {
        while (true) {
            readLen1 = 0
            if (readLen1 > 0) {
                mHandler1.post {
                    val data = readBuf1.copyOfRange(0, readLen1)
                    data.apply {
                        pool = com.example.pl2302_android.uart.add(pool, this)
                    }
                    pool?.apply {
                        pool = handleDataPool(pool)
                    }
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

    override fun onNewData(data: ByteArray?) {
        data?.apply {
            pool = com.example.pl2302_android.uart.add(pool, this)
        }
        pool?.apply {
            pool = handleDataPool(pool)
        }
    }

    override fun onRunError(e: Exception?) {

    }
}