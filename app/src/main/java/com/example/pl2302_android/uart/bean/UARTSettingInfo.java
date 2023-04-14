package com.example.pl2302_android.uart.bean;


import com.example.pl2302_android.uart.lib.PL2303GMultiLib;

public class UARTSettingInfo {
    public int iPortIndex = 0;
    public PL2303GMultiLib.BaudRate mBaudrate = PL2303GMultiLib.BaudRate.B38400;
    public PL2303GMultiLib.DataBits mDataBits = PL2303GMultiLib.DataBits.D8;
    public PL2303GMultiLib.Parity mParity = PL2303GMultiLib.Parity.NONE;
    public PL2303GMultiLib.StopBits mStopBits = PL2303GMultiLib.StopBits.S1;
    public PL2303GMultiLib.FlowControl mFlowControl = PL2303GMultiLib.FlowControl.OFF;
}