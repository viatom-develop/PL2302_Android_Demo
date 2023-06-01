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


    var o2=0;
    var linkOff=true;
    var wantO2=false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.getO2.setOnClickListener {
            if(linkOff){
                binding.o2.text="O2:连接断开"
                wantO2=true;
            }else{
                binding.o2.text = "O2:${o2}"
            }
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

    //get a bytes bit7
    private fun getBit7(byte: Byte): Int {
        return (byte.toUByte().toInt() and 0x80) shr 7
    }

    private fun getBit6(byte: Byte): Int {
        return (byte.toUByte().toInt() and 0x40) shr 6
    }

    fun getBit4(byte: Byte): Int {
        return (byte.toUByte().toInt() and 0x10) shr 4
    }

    private fun getInt0to7(byte: Byte): Int {
        return (byte.toUByte().toInt() and 0x7f)
    }


    private fun handleDataPool(bytes: ByteArray?): ByteArray? {
        val bytesLeft: ByteArray? = bytes

        if (bytes == null || bytes.size < 5) {
            return bytes
        }
        loop@ for (i in 0 until bytes.size - 4) {
            if (getBit7(bytes[i]) != 1) {
                continue@loop
            }

            val byte3=bytes[i+2]
            val byte4=bytes[i+3]
            val byte5=bytes[i+4]
            val pr=getInt0to7(byte4)+(getBit6(byte3) shl 7)
            o2=getInt0to7(byte5)

            linkOff=getBit4(byte3)==1
            if(o2!=0){
                if(!linkOff){
                    if(wantO2){
                        wantO2=false;
                        MainScope().launch {
                            binding.o2.text = "O2:${o2}"
                        }
                    }
                }
            }

            val tempBytes: ByteArray? =
                if (i+5 == bytes.size) null else bytes.copyOfRange(
                    i + 5,
                    bytes.size
                )

            return handleDataPool(tempBytes)

        }

        return bytesLeft
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