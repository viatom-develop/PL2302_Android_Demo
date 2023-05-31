package com.example.pl2302_android


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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



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
        Log.e("vaca", "onResume")
        super.onResume()
        synchronized(this){
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
            port?.setParameters(19200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_ODD)
            val usbIoManager = SerialInputOutputManager(port, this);
            usbIoManager.start();
        }
    }



    private fun writeToUartDevice(bytes: ByteArray) {
        try {
            port?.write(bytes, 1000)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }






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




    override fun onNewData(data: ByteArray?) {
        Log.e("vaca","receive")
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