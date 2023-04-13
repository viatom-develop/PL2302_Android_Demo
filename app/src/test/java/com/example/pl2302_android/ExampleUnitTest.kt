package com.example.pl2302_android

import android.util.Log
import com.example.pl2302_android.uart.O2Cmd
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    fun bytesToHex(bytes: ByteArray, len: Int): String {
        val sb = StringBuilder()
        for (i in 0 until len) {
            val v = bytes[i].toInt() and 0xFF
            val hv = Integer.toHexString(v)
            if (hv.length < 2) {
                sb.append(0)
            }
            sb.append(hv)
            sb.append(" ")
        }
        return sb.toString()
    }
    fun bytesToHex(bytes: ByteArray): String {
        val len=bytes.size
        val sb = StringBuilder()
        for (i in 0 until len) {
            val v = bytes[i].toInt() and 0xFF
            val hv = Integer.toHexString(v)
            if (hv.length < 2) {
                sb.append(0)
            }
            sb.append(hv)
            sb.append(" ")
        }
        return sb.toString()
    }

    @Test
    fun addition_isCorrect() {
        println("yes")
        val gg1=O2Cmd.getProductIDInfo()
        println(bytesToHex(gg1,gg1.size))
        val gg2=O2Cmd.getProductVersionInfo()
        println(bytesToHex(gg2,gg2.size))
        val gg=O2Cmd.getStatusInfo()
        println(bytesToHex(gg,gg.size))

        println(bytesToHex(O2Cmd.setWorkMode(1)))
        assertEquals(4, 2 + 2)
    }
}