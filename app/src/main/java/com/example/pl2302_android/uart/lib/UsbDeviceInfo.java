package com.example.pl2302_android.uart.lib;


import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;

class UsbDeviceInfo {
    private String e;
    private String f;
    private String g;
    private UsbDevice h;
    private UsbDeviceConnection i;
    private UsbInterface j;
    private UsbEndpoint k;
    private UsbEndpoint l;
    private UsbEndpoint m;
    private boolean n;
    private int o;
    private boolean p;
    private boolean q;
    private boolean r;
    private boolean s;
    private int t;
    private byte u;
    UsbUartDevice usbUartDevice1;
    UsbUartDevice b;
    UsbUartDevice c;
    UsbUartDevice d;

    UsbDeviceInfo() {
        this.a();
    }

    public void a() {
        if (this.i != null) {
            if (this.j != null) {
                this.i.releaseInterface(this.j);
                this.j = null;
                this.k = null;
                this.l = null;
                this.m = null;
            }

            this.i.close();
            this.h = null;
            this.i = null;
        }

        this.n = false;
        this.o = 0;
        this.p = false;
        this.e = "";
        this.f = "";
        this.g = "";
        this.q = false;
        this.r = true;
        this.s = false;
        if (this.usbUartDevice1 == null) {
            this.usbUartDevice1 = new UsbUartDevice();
        }

        if (this.b == null) {
            this.b = new UsbUartDevice();
        }

        if (this.c == null) {
            this.c = new UsbUartDevice();
        }

        if (this.d == null) {
            this.d = new UsbUartDevice();
        }

        this.usbUartDevice1.getP1(0);
        this.b.getP1(3);
        this.b.getP2(15);
        this.c.getP1(0);
        this.c.getP2(0);
        this.c.getP3(0);
        this.d.getP1(0);
        this.d.getP2(0);
        this.t = 0;
        this.u = 0;
    }

    public UsbDevice b() {
        return this.h;
    }

    public UsbEndpoint a(boolean var1) {
        return var1 ? this.k : this.l;
    }

    public UsbEndpoint c() {
        return this.m;
    }

    public String d() {
        return this.e;
    }

    public String e() {
        return this.f;
    }

    public String f() {
        return this.g;
    }

    public UsbDeviceConnection g() {
        return this.i;
    }

    public int h() {
        return this.o;
    }

    public boolean i() {
        return this.n;
    }

    public boolean j() {
        return this.p;
    }

    public boolean k() {
        return this.p;
    }

    public boolean l() {
        return this.q;
    }

    public boolean m() {
        return this.r;
    }

    public boolean n() {
        return this.s;
    }

    public int a(UsbDeviceInfo.GPIOPin var1) {
        if (var1 == UsbDeviceInfo.GPIOPin.GPIO_01) {
            return this.usbUartDevice1.getP2();
        } else if (var1 == UsbDeviceInfo.GPIOPin.GPIO_23) {
            return this.b.getP2();
        } else if (var1 == UsbDeviceInfo.GPIOPin.GPIO_4567) {
            return this.c.getP2();
        } else {
            return var1 == UsbDeviceInfo.GPIOPin.GPIO_TB_12 ? this.d.getP2() : -1;
        }
    }

    public int b(UsbDeviceInfo.GPIOPin var1) {
        if (var1 == UsbDeviceInfo.GPIOPin.GPIO_01) {
            return this.usbUartDevice1.getP1();
        } else if (var1 == UsbDeviceInfo.GPIOPin.GPIO_23) {
            return this.b.getP1();
        } else if (var1 == UsbDeviceInfo.GPIOPin.GPIO_4567) {
            return this.c.getP1();
        } else {
            return var1 == UsbDeviceInfo.GPIOPin.GPIO_TB_12 ? this.d.getP1() : -1;
        }
    }

    public int c(UsbDeviceInfo.GPIOPin var1) {
        if (var1 == UsbDeviceInfo.GPIOPin.GPIO_01) {
            return this.usbUartDevice1.getP3();
        } else if (var1 == UsbDeviceInfo.GPIOPin.GPIO_23) {
            return this.b.getP3();
        } else if (var1 == UsbDeviceInfo.GPIOPin.GPIO_4567) {
            return this.c.getP3();
        } else {
            return var1 == UsbDeviceInfo.GPIOPin.GPIO_TB_12 ? this.d.getP3() : -1;
        }
    }

    public int a(UsbDeviceInfo.MODEMCtrlPin var1) {
        if (UsbDeviceInfo.MODEMCtrlPin.MODEM_DTRRTS == var1) {
            return this.t;
        } else {
            return UsbDeviceInfo.MODEMCtrlPin.MODEM_DSRCTSDCDRI == var1 ? this.u : -1;
        }
    }

    public boolean o() {
        return this.h != null && this.k != null && this.l != null;
    }

    public void a(String var1, boolean var2) {
        this.e = var1;
        this.n = var2;
    }

    public void a(String var1) {
        this.f = var1;
    }

    public void b(String var1) {
        this.g = var1;
    }

    public void a(UsbDevice var1, UsbDeviceConnection var2, UsbInterface var3) {
        this.h = var1;
        this.i = var2;
        this.j = var3;
    }

    public void a(UsbEndpoint var1, boolean var2) {
        if (var2) {
            this.k = var1;
        } else {
            this.l = var1;
        }

    }

    public void a(UsbEndpoint var1) {
        this.m = var1;
    }

    public void a(int var1) {
        this.o = var1;
    }

    public void b(boolean var1) {
        this.p = var1;
    }

    public void c(boolean var1) {
        this.p = var1;
    }

    public void d(boolean var1) {
        this.q = var1;
    }

    public void e(boolean var1) {
        this.r = var1;
    }

    public void f(boolean var1) {
        this.s = var1;
    }

    public void a(UsbDeviceInfo.GPIOPin var1, int var2) {
        if (var1 == UsbDeviceInfo.GPIOPin.GPIO_01) {
            this.usbUartDevice1.getP2(var2);
        } else if (var1 == UsbDeviceInfo.GPIOPin.GPIO_23) {
            this.b.getP2(var2);
        } else if (var1 == UsbDeviceInfo.GPIOPin.GPIO_4567) {
            this.c.getP2(var2);
        } else if (var1 == UsbDeviceInfo.GPIOPin.GPIO_TB_12) {
            this.d.getP2(var2);
        }

    }

    public void b(UsbDeviceInfo.GPIOPin var1, int var2) {
        if (var1 == UsbDeviceInfo.GPIOPin.GPIO_01) {
            this.usbUartDevice1.getP1(var2);
        } else if (var1 == UsbDeviceInfo.GPIOPin.GPIO_23) {
            this.b.getP1(var2);
        } else if (var1 == UsbDeviceInfo.GPIOPin.GPIO_4567) {
            this.c.getP1(var2);
        } else if (var1 == UsbDeviceInfo.GPIOPin.GPIO_TB_12) {
            this.d.getP1(var2);
        }

    }

    public void c(UsbDeviceInfo.GPIOPin var1, int var2) {
        if (var1 == UsbDeviceInfo.GPIOPin.GPIO_01) {
            this.usbUartDevice1.getP3(var2);
        } else if (var1 == UsbDeviceInfo.GPIOPin.GPIO_23) {
            this.b.getP3(var2);
        } else if (var1 == UsbDeviceInfo.GPIOPin.GPIO_4567) {
            this.c.getP3(var2);
        } else if (var1 == UsbDeviceInfo.GPIOPin.GPIO_TB_12) {
            this.d.getP3(var2);
        }

    }

    public void a(UsbDeviceInfo.MODEMCtrlPin var1, int var2) {
        if (UsbDeviceInfo.MODEMCtrlPin.MODEM_DTRRTS == var1) {
            this.t = var2;
        } else {
            if (UsbDeviceInfo.MODEMCtrlPin.MODEM_DSRCTSDCDRI != var1) {
                return;
            }

            this.u = (byte)(var2 & 255);
        }

    }

    public static enum GPIOPin {
        GPIO_01,
        GPIO_23,
        GPIO_4567,
        GPIO_TB_12;

        private GPIOPin() {
        }
    }

    public static enum MODEMCtrlPin {
        MODEM_DTRRTS,
        MODEM_DSRCTSDCDRI;

        private MODEMCtrlPin() {
        }
    }
}
