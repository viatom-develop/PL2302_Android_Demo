package com.example.pl2302_android.uart.lib;


class UsbUartDevice {
    private int p1 = 0;
    private int p2 = 0;
    private int p3 = 0;


    public int getP1() {
        return this.p1;
    }

    public int getP2() {
        return this.p2;
    }

    public int getP3() {
        return this.p3;
    }

    public void getP1(int var1) {
        this.p1 = var1;
    }

    public void getP2(int var1) {
        this.p2 = var1;
    }

    public void getP3(int var1) {
        this.p3 = var1;
    }
}
