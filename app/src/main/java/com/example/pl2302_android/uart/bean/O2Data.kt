package com.example.pl2302_android.uart.bean

import com.example.pl2302_android.uart.toUInt
import com.example.pl2302_android.uart.unsigned

class O2Data(var bytes:ByteArray) {
    var o2: Int = bytes[0].unsigned()
    var pr: Int = toUInt(bytes.copyOfRange(1, 3))
    var pi: Int= bytes[3].unsigned()
    var state: Int= bytes[4].unsigned()
}