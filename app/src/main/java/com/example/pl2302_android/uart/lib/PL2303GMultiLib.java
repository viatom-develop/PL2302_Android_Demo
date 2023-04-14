package com.example.pl2302_android.uart.lib;

import static android.app.PendingIntent.FLAG_IMMUTABLE;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Build.VERSION;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class PL2303GMultiLib {
    private static final boolean b = false;
    private static final boolean c = false;
    private static final boolean d = false;
    private static String e = "2.0.0.1";
    public static final int BAUD0 = 0;
    public static final int BAUD75 = 75;
    public static final int BAUD150 = 150;
    public static final int BAUD300 = 300;
    public static final int BAUD600 = 600;
    public static final int BAUD1200 = 1200;
    public static final int BAUD1800 = 1800;
    public static final int BAUD2400 = 2400;
    public static final int BAUD4800 = 4800;
    public static final int BAUD9600 = 9600;
    public static final int BAUD14400 = 14400;
    public static final int BAUD19200 = 19200;
    public static final int BAUD38400 = 38400;
    public static final int BAUD57600 = 57600;
    public static final int BAUD115200 = 115200;
    public static final int BAUD230400 = 230400;
    public static final int BAUD460800 = 460800;
    public static final int BAUD614400 = 614400;
    public static final int BAUD921600 = 921600;
    public static final int BAUD1228800 = 1228800;
    public static final int BAUD2457600 = 2457600;
    public static final int BAUD3000000 = 3000000;
    public static final int BAUD6000000 = 6000000;
    private final int f = 2;
    private final int g = 3;
    private final int h = 4;
    private final int i = 5;
    private final int j = 6;
    private final int k = 7;
    private final int l = 8;
    private final int m = 9;
    private final int n = 10;
    private final int o = 4;
    private final int p = 5;
    public static final int PL2303G_DCD_ON = 1;
    public static final int PL2303G_DSR_ON = 2;
    public static final int PL2303G_RI_ON = 8;
    public static final int PL2303G_CTS_ON = 128;
    private static final int q = 33;
    private static final int r = 32;
    private static final int s = 33;
    private static final int t = 35;
    private static final int u = 0;
    private static final int v = 161;
    private static final int w = 33;
    private static final int x = 64;
    private static final int y = 192;
    private static final int z = 128;
    private static final int A = 129;
    private static final int B = 130;
    private static final int C = 130;
    private static final int D = 131;
    private static final int E = 33;
    private static final int F = 34;
    private static final int G = 2056;
    private static final int H = 2313;
    private final boolean I = true;
    private final boolean J = false;
    private final int K = 17;
    private final int L = 19;
    private static final int M = 1;
    private static final int N = 2;
    private byte[] O = new byte[7];
    private int P;
    private int Q;
    private int R;
    private static final int S = 4096;
    private static final int T = 1228800;
    private static final String U = "PL2303GMultiLib";
    private UsbManager V;
    private String W;
    private ArrayList<String> X = new ArrayList();
    private int Y;
    Context a;
    private static final int Z = 10;
    private static final String aa = null;
    private boolean[] ab = new boolean[10];
    private boolean[] ac = new boolean[10];
    private UsbDeviceInfo[] ad;
    private int ae;
    public String PLUART_MESSAGE = "tw.PL2303MultiUSBMessage";
    public String PLUART_DETACHED = "MultiUSB.Detached";
    public Hashtable<String, UsbDevice> ProlificUSBCurrentDeviceList;
    public Hashtable<String, String> Prolific_DevicePath_2_COMNumber;
    private boolean af;
    private PL2303GMultiLib.a[] ag;
    private boolean[] ah = new boolean[10];
    private final BroadcastReceiver ai;
    private static UsbDevice aj = null;
    private Runnable ak;

    private void a(UsbManager var1, Context var2, String var3, boolean var4) {
        this.V = var1;
        this.a = var2;
        this.W = var3;
        this.X.add("067B:23A3");
        this.X.add("067B:23B3");
        this.X.add("067B:23C3");
        this.X.add("067B:23D3");
        this.X.add("067B:23E3");
        this.X.add("067B:23F3");
        this.X.add("067B:2320");
        this.X.add("067B:2321");
        this.X.add("067B:2322");
        this.X.add("067B:2323");
        this.Y = this.X.size();
        this.ad = new UsbDeviceInfo[10];
        this.ag = new PL2303GMultiLib.a[10];
        this.ProlificUSBCurrentDeviceList = new Hashtable();
        this.Prolific_DevicePath_2_COMNumber = new Hashtable();
        this.af = false;

        for(int var5 = 0; var5 < 10; ++var5) {
            this.ab[var5] = false;
            this.ac[var5] = false;
            this.ad[var5] = new UsbDeviceInfo();
            this.ag[var5] = new PL2303GMultiLib.a();
            this.ah[var5] = var4;
        }

        this.ae = 0;
        this.P = 100;
        this.Q = 100;
        this.R = 100;
    }

    public PL2303GMultiLib(UsbManager manager, Context context, String sAppName) {
        class NamelessClass_1 extends BroadcastReceiver {
            NamelessClass_1() {
            }

            public void onReceive(Context context, Intent intent) {
                String var3 = intent.getAction();
                UsbDevice var4 = (UsbDevice)intent.getParcelableExtra("device");
                if (!"android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(var3)) {
                    int var6;
                    if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(var3)) {
                        String var5 = var4.getDeviceName();
                        var6 = PL2303GMultiLib.this.a(var4);
                        if (var6 >= 0) {
                            if (PL2303GMultiLib.this.ad[var6].e().equals(var5)) {
                                PL2303GMultiLib.this.e(var4);
                                UsbDevice var10000 = (UsbDevice)PL2303GMultiLib.this.ProlificUSBCurrentDeviceList.remove(var4.getDeviceName());
                                Intent var8 = new Intent(PL2303GMultiLib.this.PLUART_MESSAGE);
                                var8.putExtra(PL2303GMultiLib.this.PLUART_DETACHED, String.valueOf(var6));
                                PL2303GMultiLib.this.a.sendBroadcast(var8);
                            }

                            if (PL2303GMultiLib.this.ae == 0) {
                                PL2303GMultiLib.this.a.unregisterReceiver(PL2303GMultiLib.this.ai);
                            }
                        }

                        Toast.makeText(PL2303GMultiLib.this.a, var5 + " disconnected", 0).show();
                    } else if (var3.equals(PL2303GMultiLib.this.W)) {
                        synchronized(this) {
                            if (intent.getBooleanExtra("permission", false) && var4 != null) {
                                for(var6 = 0; var6 < PL2303GMultiLib.this.Y; ++var6) {
                                    if (String.format("%04X:%04X", var4.getVendorId(), var4.getProductId()).equals(PL2303GMultiLib.this.X.get(var6))) {
                                        PL2303GMultiLib.this.b(var4);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
        class NamelessClass_2 implements Runnable {
            NamelessClass_2() {
            }

            public void run() {
                UsbDevice var1 = PL2303GMultiLib.aj;
                if (var1 != null) {
                    if (!PL2303GMultiLib.this.d(var1)) {
                        PL2303GMultiLib.this.c(var1);
                    }
                }
            }
        }
        this.ai = new NamelessClass_1();
        this.ak = new NamelessClass_2();
        this.a(manager, context, sAppName, true);
    }

    public PL2303GMultiLib(UsbManager manager, Context context, String sAppName, boolean bWithQueue) {
        class NamelessClass_1 extends BroadcastReceiver {
            NamelessClass_1() {
            }

            public void onReceive(Context context, Intent intent) {
                String var3 = intent.getAction();
                UsbDevice var4 = (UsbDevice)intent.getParcelableExtra("device");
                if (!"android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(var3)) {
                    int var6;
                    if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(var3)) {
                        String var5 = var4.getDeviceName();
                        var6 = PL2303GMultiLib.this.a(var4);
                        if (var6 >= 0) {
                            if (PL2303GMultiLib.this.ad[var6].e().equals(var5)) {
                                PL2303GMultiLib.this.e(var4);
                                UsbDevice var10000 = (UsbDevice)PL2303GMultiLib.this.ProlificUSBCurrentDeviceList.remove(var4.getDeviceName());
                                Intent var8 = new Intent(PL2303GMultiLib.this.PLUART_MESSAGE);
                                var8.putExtra(PL2303GMultiLib.this.PLUART_DETACHED, String.valueOf(var6));
                                PL2303GMultiLib.this.a.sendBroadcast(var8);
                            }

                            if (PL2303GMultiLib.this.ae == 0) {
                                PL2303GMultiLib.this.a.unregisterReceiver(PL2303GMultiLib.this.ai);
                            }
                        }

                        Toast.makeText(PL2303GMultiLib.this.a, var5 + " disconnected", 0).show();
                    } else if (var3.equals(PL2303GMultiLib.this.W)) {
                        synchronized(this) {
                            if (intent.getBooleanExtra("permission", false) && var4 != null) {
                                for(var6 = 0; var6 < PL2303GMultiLib.this.Y; ++var6) {
                                    if (String.format("%04X:%04X", var4.getVendorId(), var4.getProductId()).equals(PL2303GMultiLib.this.X.get(var6))) {
                                        PL2303GMultiLib.this.b(var4);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        this.ai = new NamelessClass_1();

        class NamelessClass_2 implements Runnable {
            NamelessClass_2() {
            }

            public void run() {
                UsbDevice var1 = PL2303GMultiLib.aj;
                if (var1 != null) {
                    if (!PL2303GMultiLib.this.d(var1)) {
                        PL2303GMultiLib.this.c(var1);
                    }
                }
            }
        }

        this.ak = new NamelessClass_2();
        this.a(manager, context, sAppName, bWithQueue);
    }

    public void PL2303Release() {
        if (this.ae != 0) {
            for(int var1 = 0; var1 < 10; ++var1) {
                if (this.ah[var1] && this.ag[var1].d) {
                    this.b(this.ag[var1]);
                }

                this.ad[var1].a();
                this.ab[var1] = false;
                this.ac[var1] = false;
                this.ah[var1] = false;
            }

            this.a.unregisterReceiver(this.ai);
            this.X.clear();
            this.ae = 0;
        }

    }

    private void a(String var1) {
        this.X.add(var1);
        this.Y = this.X.size();
    }

    public boolean PL2303USBFeatureSupported() {
        boolean var1 = this.a.getPackageManager().hasSystemFeature("android.hardware.usb.host");
        return var1;
    }

    public int PL2303LibGetVersion(byte[] byVersion) {
        boolean var2 = false;
        int var5;
        if (byVersion.length < e.length()) {
            var5 = byVersion.length;
        } else {
            var5 = e.length();
        }

        char[] var3 = e.toCharArray();

        for(int var4 = 0; var4 < var5; ++var4) {
            byVersion[var4] = (byte)var3[var4];
        }

        return 0;
    }

    public void PL2303G_ReSetStatus() {
        if (this.ae >= 1) {
            Enumeration var1 = this.ProlificUSBCurrentDeviceList.elements();

            while(var1.hasMoreElements()) {
                UsbDevice var2 = (UsbDevice)var1.nextElement();
                String var3 = var2.getDeviceName();
                if (!this.b(var3)) {
                    this.e(var2);
                    int var4 = this.a(var2);
                    UsbDevice var5 = (UsbDevice)this.ProlificUSBCurrentDeviceList.remove(var3);
                    Intent var6 = new Intent(this.PLUART_MESSAGE);
                    var6.putExtra(this.PLUART_DETACHED, String.valueOf(var4));
                    this.a.sendBroadcast(var6);
                }
            }
        }

    }

    public int PL2303Enumerate() {
        int var1 = 0;
        this.V = (UsbManager)this.a.getSystemService(Context.USB_SERVICE);
        HashMap var2 = this.V.getDeviceList();
        Iterator var3 = var2.values().iterator();
        PendingIntent var4 = PendingIntent.getBroadcast(this.a, 0, new Intent(this.W), 0|FLAG_IMMUTABLE);

        while(var3.hasNext()) {
            UsbDevice var5 = (UsbDevice)var3.next();

            for(int var6 = 0; var6 < this.Y; ++var6) {
                if (String.format("%04X:%04X", var5.getVendorId(), var5.getProductId()).equals(this.X.get(var6)) && this.b(var5.getDeviceName())) {
                    IntentFilter var7 = new IntentFilter(this.W);
                    var7.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
                    this.a.registerReceiver(this.ai, var7);
                    if (!this.V.hasPermission(var5)) {
                        this.V.requestPermission(var5, var4);
                    } else if (this.ae <= 10) {
                        ++var1;
                        this.b(var5);
                    }
                }
            }
        }

        if (var1 == 0) {
        }

        return var1;
    }

    private int a(UsbDevice var1) {
        if (this.ae == 0) {
            return -1;
        } else {
            for(int var2 = 0; var2 < 10; ++var2) {
                if (this.ab[var2]) {
                    UsbDevice var3 = this.ad[var2].b();
                    if (var3.equals(var1)) {
                        return var2;
                    }
                }
            }

            return -1;
        }
    }

    private boolean b(String var1) {
        String var2 = "";
        boolean var4 = true;
        if (VERSION.SDK_INT >= 21) {
            return true;
        } else {
            try {
                String var3 = "toolbox ls " + var1;
                Process var5 = Runtime.getRuntime().exec(var3);
                BufferedReader var6 = new BufferedReader(new InputStreamReader(var5.getInputStream()));

                for(String var7 = null; (var7 = var6.readLine()) != null; var2 = var2 + var7) {
                }

                if (var1.compareTo(var2) != 0) {
                    var4 = false;
                }
            } catch (IOException var8) {
                var8.printStackTrace();
                var4 = false;
            }

            return var4;
        }
    }

    private void b(UsbDevice var1) {
        if (var1 != null) {
            if (!this.d(var1)) {
                this.c(var1);
            }
        }
    }

    private boolean a(UsbDeviceInfo var1, UsbInterface var2) {
        if (var2 == null) {
            return false;
        } else {
            for(int var3 = 0; var3 < var2.getEndpointCount(); ++var3) {
                if (var2.getEndpoint(var3).getType() == 2) {
                    if (var2.getEndpoint(var3).getDirection() == 128) {
                        var1.a(var2.getEndpoint(var3), true);
                    } else {
                        var1.a(var2.getEndpoint(var3), false);
                    }
                } else if (var2.getEndpoint(var3).getType() == 3 && var2.getEndpoint(var3).getDirection() == 128) {
                    var1.a(var2.getEndpoint(var3));
                }
            }

            return true;
        }
    }

    private void c(UsbDevice var1) {
        int var2 = 0;
        UsbDeviceInfo var3 = new UsbDeviceInfo();
        if (var1 != null) {
            if (var1.getInterfaceCount() != 0) {
                for(int var4 = 0; var4 < var1.getInterfaceCount(); ++var4) {
                    UsbInterface var5 = var1.getInterface(var4);
                    if (255 == var5.getInterfaceClass() && var5.getInterfaceProtocol() == 0 && var5.getInterfaceSubclass() == 0) {
                        var2 = var4;
                        break;
                    }
                }

                UsbInterface var10 = var1.getInterface(var2);
                boolean var6 = false;
                if (var1 != null && var10 != null) {
                    UsbDeviceConnection var7 = this.V.openDevice(var1);
                    if (var7 != null) {
                        if (var7.claimInterface(var10, true)) {
                            if (this.a(var3, var10)) {
                                int var12;
                                for(var12 = 0; var12 < 10 && this.ab[var12]; ++var12) {
                                }

                                if (var12 < 10) {
                                    String var11 = String.format("%04X:%04X", var1.getVendorId(), var1.getProductId());
                                    if (!var11.equals("067B:2551") && !var11.equals("067B:2503")) {
                                        var3.a(var11, false);
                                    } else {
                                        var3.a(var11, true);
                                    }

                                    var3.a(var1.getDeviceName());
                                    var3.a(var1, var7, var10);
                                    synchronized(this) {
                                        this.ad[var12] = var3;
                                        this.ab[var12] = true;
                                        this.ac[var12] = false;
                                        ++this.ae;
                                        this.ProlificUSBCurrentDeviceList.put(var1.getDeviceName(), var1);
                                    }
                                }

                                return;
                            }
                        } else {
                            var7.close();
                        }
                    }
                }

            }
        }
    }

    private boolean d(UsbDevice var1) {
        if (this.ae > 0) {
            int var2 = this.a(var1);
            if (var2 >= 0) {
                return this.ad[var2].o();
            }
        }

        return false;
    }

    private void e(UsbDevice var1) {
        if (this.ae > 0) {
            int var2 = this.a(var1);
            if (var2 >= 0) {
                this.a(var2);
            }
        }

    }

    public boolean PL2303IsDeviceConnectedByIndex(int index) {
        int[] var2 = new int[2];
        boolean var3 = false;
        if (this.ae > 0 && 10 > index && index >= 0 && this.ab[index]) {
            var3 = this.ad[index].o();
            return var3;
        } else {
            return false;
        }
    }

    private void a(int var1) {
        if (this.ae > 0 && 10 > var1 && var1 >= 0) {
            if (this.ah[var1] && this.ag[var1].d) {
                this.b(this.ag[var1]);
                this.ag[var1] = new PL2303GMultiLib.a();
            }

            this.ab[var1] = false;
            this.ac[var1] = false;
            this.ad[var1].a();
            --this.ae;
        }

    }

    public String PL2303getDevicePathByIndex(int index) {
        return this.PL2303IsDeviceConnectedByIndex(index) && 10 > index ? this.ad[index].e() : new String("");
    }

    public String PL2303getCOMNumber(int index) {
        if (index > 8) {
            return new String("");
        } else {
            return this.PL2303IsDeviceConnectedByIndex(index) && 10 > index ? this.ad[index].f() : new String("");
        }
    }

    public boolean EnableFixed_COMPort_Mode() {
        this.af = true;
        return this.af;
    }

    public String PL2303getSerialByIndex(int index) {
        return this.PL2303IsDeviceConnectedByIndex(index) && 10 > index ? this.ad[index].g().getSerial() : new String("");
    }

    public int PL2303getProlificUSBDeviceCount() {
        return this.ae;
    }

    private void a(PL2303GMultiLib.a var1) {
        if (!var1.d && var1.c != null) {
            var1.c.start();
            var1.d = var1.c.isAlive();
        }

    }

    private void b(PL2303GMultiLib.a var1) {
        if (var1.d && var1.c != null) {
            var1.c.a();
            var1.d = var1.c.isAlive();
            var1.c = null;
        }

    }
    private static /* synthetic */ int[] al;

    static /* synthetic */ int[] b() {
        int[] iArr = al;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[BaudRate.valuesCustom().length];
        try {
            iArr2[BaudRate.B0.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[BaudRate.B115200.ordinal()] = 15;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[BaudRate.B1200.ordinal()] = 6;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[BaudRate.B1228800.ordinal()] = 20;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[BaudRate.B14400.ordinal()] = 11;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            iArr2[BaudRate.B150.ordinal()] = 3;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            iArr2[BaudRate.B1800.ordinal()] = 7;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            iArr2[BaudRate.B19200.ordinal()] = 12;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            iArr2[BaudRate.B230400.ordinal()] = 16;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            iArr2[BaudRate.B2400.ordinal()] = 8;
        } catch (NoSuchFieldError unused10) {
        }
        try {
            iArr2[BaudRate.B2457600.ordinal()] = 21;
        } catch (NoSuchFieldError unused11) {
        }
        try {
            iArr2[BaudRate.B300.ordinal()] = 4;
        } catch (NoSuchFieldError unused12) {
        }
        try {
            iArr2[BaudRate.B3000000.ordinal()] = 22;
        } catch (NoSuchFieldError unused13) {
        }
        try {
            iArr2[BaudRate.B38400.ordinal()] = 13;
        } catch (NoSuchFieldError unused14) {
        }
        try {
            iArr2[BaudRate.B460800.ordinal()] = 17;
        } catch (NoSuchFieldError unused15) {
        }
        try {
            iArr2[BaudRate.B4800.ordinal()] = 9;
        } catch (NoSuchFieldError unused16) {
        }
        try {
            iArr2[BaudRate.B57600.ordinal()] = 14;
        } catch (NoSuchFieldError unused17) {
        }
        try {
            iArr2[BaudRate.B600.ordinal()] = 5;
        } catch (NoSuchFieldError unused18) {
        }
        try {
            iArr2[BaudRate.B6000000.ordinal()] = 23;
        } catch (NoSuchFieldError unused19) {
        }
        try {
            iArr2[BaudRate.B614400.ordinal()] = 18;
        } catch (NoSuchFieldError unused20) {
        }
        try {
            iArr2[BaudRate.B75.ordinal()] = 2;
        } catch (NoSuchFieldError unused21) {
        }
        try {
            iArr2[BaudRate.B921600.ordinal()] = 19;
        } catch (NoSuchFieldError unused22) {
        }
        try {
            iArr2[BaudRate.B9600.ordinal()] = Z;
        } catch (NoSuchFieldError unused23) {
        }
        al = iArr2;
        return iArr2;
    }

    private void a(PL2303GMultiLib.a var1, PL2303GMultiLib.BaudRate var2) throws IOException {
        int[] var3 = new int[]{3, 5, 10, 25, 100, 200};
        int var4 = var3[3];
        switch(PL2303GMultiLib.b()[var2.ordinal()]) {
            case 1:
                var4 = 0;
                break;
            case 2:
            case 3:
                var4 = var3[5];
                break;
            case 4:
            case 5:
                var4 = var3[4];
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                var4 = var3[3];
                break;
            case 11:
            case 12:
            case 13:
            case 14:
                var4 = var3[2];
                break;
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                var4 = var3[1];
                break;
            case 20:
            case 21:
            case 22:
            case 23:
                var4 = var3[0];
                break;
            default:
                return;
        }

        if (var1.c != null) {
            var1.c.a(var4);
        }

    }

    public int PL2303Write(int index, byte[] buf) {
        UsbDeviceInfo var3 = null;
        if (this.ae != 0 && index >= 0 && buf.length != 0) {
            if (!this.ab[index] && !this.ac[index]) {
                return -1;
            } else {
                if (index >= 0) {
                    var3 = this.ad[index];
                }

                if (var3 != null && var3.b() != null) {
                    return var3.n() && !var3.m() ? 0 : this.a(var3, buf, buf.length);
                } else {
                    return -2;
                }
            }
        } else {
            return -1;
        }
    }

    private int a(UsbDeviceInfo var1, byte[] var2, int var3) {
        int var4 = 0;
        byte[] var6 = new byte[4096];
        UsbDeviceConnection var7 = var1.g();

        int var5;
        for(UsbEndpoint var8 = var1.a(false); var4 < var3; var4 += var5) {
            int var9 = 4096;
            if (var4 + var9 > var3) {
                var9 = var3 - var4;
            }

            System.arraycopy(var2, var4, var6, 0, var9);
            var5 = var7.bulkTransfer(var8, var6, var9, this.Q);
            if (var5 < 0) {
                return -1;
            }
        }

        return var4;
    }

    public int PL2303Read(int index, byte[] buf) {
        UsbDeviceInfo var3 = null;
        PL2303GMultiLib.a var4 = null;
        int var9 = buf.length;
        if (this.ae != 0 && index >= 0 && var9 != 0) {
            if (var9 > 4096) {
                buf = new byte[4096];
            }

            if (!this.ab[index] && !this.ac[index]) {
                return -1;
            } else {
                if (index >= 0) {
                    var3 = this.ad[index];
                }

                if (var3 != null && var3.b() != null) {
                    var4 = this.ag[index];
                    synchronized(var4.b) {
                        int var7 = var4.a.size();
                        int var5;
                        if (var7 > 0) {
                            if (var9 >= var7) {
                                var5 = var7;
                            } else {
                                var5 = var9;
                            }

                            for(int var6 = 0; var6 < var5; ++var6) {
                                Integer var8 = (Integer)var4.a.poll();
                                if (var8 == null) {
                                    break;
                                }

                                buf[var6] = (byte)(var8 & 255);
                            }
                        } else {
                            var5 = 0;
                        }

                        return var5;
                    }
                } else {
                    return -2;
                }
            }
        } else {
            return -1;
        }
    }

    private int b(UsbDeviceInfo var1, byte[] var2, int var3) {
        if (var2.length != 0 && var3 != 0) {
            int var4 = 0;
            byte var5 = 0;
            byte var6 = 64;
            byte[] var7 = new byte[4096];
            int var8 = 0;
            byte var9 = 0;
            boolean var10 = false;
            boolean var11 = false;
            UsbDeviceConnection var12 = var1.g();
            UsbEndpoint var13 = var1.a(true);
            int var14;
            int var10000;
            if (var5 > 0 && var3 <= var5) {
                if (!var11) {
                    System.arraycopy(var7, var4, var2, 0, var3);
                } else {
                    for(var14 = 0; var14 < var3; ++var14) {
                        var2[var14] = var7[var4++];
                        ++var8;

                        while((var8 - 1) % 10 != Byte.valueOf(var2[var14]) - 48) {
                            ++var8;
                        }
                    }

                    var10000 = var9 + var3;
                    var10 = true;
                }

                var10000 = var5 - var3;
                return var3;
            } else {
                var14 = 0;
                int var15 = var3;
                if (var5 > 0) {
                    var15 = var3 - var5;
                    System.arraycopy(var7, var4, var2, var14, var5);
                }

                int var16 = var12.bulkTransfer(var13, var7, var7.length, this.P);
                if (var16 < 0) {
                    return var16;
                } else if (var16 == 0) {
                    return 0;
                } else {
                    int var17 = var16 / var6;
                    int var18 = var16 % var6;
                    if (var18 > 0) {
                        ++var17;
                    }

                    int var23 = var16;
                    int var19 = 0;

                    for(int var20 = 0; var20 < var17; ++var20) {
                        int var21 = var20 * var6;

                        for(int var22 = 0; var22 < var6; ++var22) {
                            var7[var19++] = var7[var21 + var22];
                        }
                    }

                    for(var4 = 0; var23 > 0 && var15 > 0; --var15) {
                        var2[var14++] = var7[var4++];
                        if (var11) {
                            ++var8;

                            while((var8 - 1) % 10 != Byte.valueOf(var2[var14 - 1]) - 48) {
                                ++var8;
                            }
                        }

                        --var23;
                    }

                    if (var11) {
                        if (var14 > 0) {
                            var10000 = var9 + var14;
                            var10 = true;
                        }

                        if (var10) {
                            var10 = false;
                        }
                    }

                    return var14;
                }
            }
        } else {
            return -1;
        }
    }

    private boolean b(int var1) {
        UsbDeviceInfo var2 = null;
        if (!this.ab[var1]) {
            return false;
        } else {
            var2 = this.ad[var1];
            int var3 = this.a(var1, var2);
            if (var3 < 0) {
                return false;
            } else if (var2.h() != 4 && var2.h() != 10) {
                return false;
            } else {
                if (this.ah[var1]) {
                    if (this.ag[var1].c == null) {
                        this.ag[var1].c = new PL2303GMultiLib.b(var1, this.ag[var1]);
                    }
                } else {
                    this.ag[var1].c = null;
                }

                return true;
            }
        }
    }

    public boolean PL2303OpenDevByDefualtSetting(int index) {
        UsbDeviceInfo var2 = null;
        if (this.ae != 0 && index >= 0 && this.ae > index) {
            if (!this.ab[index]) {
                return false;
            } else {
                if (index >= 0) {
                    var2 = this.ad[index];
                }

                if (var2 != null && var2.b() != null) {
                    if (this.ac[index]) {
                        return false;
                    } else {
                        this.ac[index] = true;
                        if (!this.b(index)) {
                            return false;
                        } else {
                            if (this.ah[index]) {
                                this.a(this.ag[index]);
                            }

                            String var3 = var2.b().getDeviceName();
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public boolean PL2303OpenDevByBaudRate(int index, PL2303GMultiLib.BaudRate R) {
        UsbDeviceInfo var3 = null;
        if (this.ae != 0 && index >= 0 && this.ae > index) {
            if (index >= 0) {
                var3 = this.ad[index];
            }

            if (var3 != null && var3.b() != null) {
                if (this.ac[index]) {
                    return false;
                } else {
                    this.ac[index] = true;
                    if (!this.b(index)) {
                        return false;
                    } else {
                        int var4 = 0;

                        try {
                            var4 = this.a(var3.g(), index, var3.h(), R, PL2303GMultiLib.DataBits.D8, PL2303GMultiLib.StopBits.S1, PL2303GMultiLib.Parity.NONE, PL2303GMultiLib.FlowControl.OFF);
                        } catch (IOException var6) {
                            var6.printStackTrace();
                        }

                        if (var4 < 0) {
                            return false;
                        } else {
                            var3.f(false);
                            if (this.ah[index]) {
                                this.a(this.ag[index]);
                            }

                            String var5 = var3.b().getDeviceName();
                            return true;
                        }
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean PL2303OpenDevByUARTSetting(int index, PL2303GMultiLib.BaudRate R, PL2303GMultiLib.DataBits D, PL2303GMultiLib.StopBits S, PL2303GMultiLib.Parity P, PL2303GMultiLib.FlowControl F) {
        UsbDeviceInfo var7 = null;
        if (this.ae != 0 && index >= 0 && this.ae > index) {
            if (index >= 0) {
                var7 = this.ad[index];
            }

            if (var7 != null && var7.b() != null) {
                if (this.ac[index]) {
                    return false;
                } else {
                    this.ac[index] = true;
                    if (!this.b(index)) {
                        return false;
                    } else {
                        int var8 = 0;

                        try {
                            var8 = this.a(var7.g(), index, var7.h(), R, D, S, P, F);
                        } catch (IOException var10) {
                            var10.printStackTrace();
                        }

                        if (var8 < 0) {
                            return false;
                        } else {
                            if (PL2303GMultiLib.FlowControl.XONXOFF == F) {
                                var7.f(true);
                            } else {
                                var7.f(false);
                            }

                            if (this.ah[index]) {
                                this.a(this.ag[index]);
                            }

                            String var9 = var7.b().getDeviceName();
                            return true;
                        }
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void PL2303CloseDeviceByIndex(int index) {
        UsbDeviceInfo var2 = null;
        if (this.ae != 0 && index >= 0 && this.ae > index) {
            if (index >= 0) {
                var2 = this.ad[index];
            }

            if (var2 != null && var2.b() != null) {
                this.ac[index] = false;
                if (this.ah[index]) {
                    this.b(this.ag[index]);
                }

            }
        }
    }

    private int a(int var1, UsbDeviceInfo var2) {
        int var4 = 0;
        int[] var5 = new int[2];
        UsbDeviceConnection var6 = var2.g();
        if (var2.i()) {
            var2.a(10);
            if ((var4 = this.a(var6)) < 0) {
                return var4;
            }
        } else {
            if (!this.a(var6, var2) && (var4 = this.a(var6)) < 0) {
                return var4;
            }

            var2.a(10);
        }

        if (4 != var2.h() && 10 != var2.h()) {
            return -1;
        } else {
            try {
                var4 = this.a(var6, var1, var2.h(), PL2303GMultiLib.BaudRate.B9600, PL2303GMultiLib.DataBits.D8, PL2303GMultiLib.StopBits.S1, PL2303GMultiLib.Parity.NONE, PL2303GMultiLib.FlowControl.OFF);
            } catch (IOException var8) {
                var8.printStackTrace();
            }

            if (var4 < 0) {
                return var4;
            } else {
                var2.f(false);
                return 0;
            }
        }
    }

    private int a(UsbDeviceInfo var1) {
        int[] var2 = new int[2];
        short var3 = 148;
        UsbDeviceConnection var4 = var1.g();
        var2 = this.a((UsbDeviceConnection)var4, 148);
        if (var2[0] < 0) {
            return var2[0];
        } else {
            if ((var2[1] & var3) == var3) {
                var1.a(6);
            } else {
                var1.a(2);
            }

            return 0;
        }
    }

    private int b(UsbDeviceInfo var1) {
        int[] var2 = new int[2];
        short var3 = 255;
        int[] var4 = new int[2];
        UsbDeviceConnection var5 = var1.g();
        var2 = this.a((UsbDeviceConnection)var5, 129);
        if (var2[0] < 0) {
            return var2[0];
        } else {
            int var6 = var2[1];
            var2[0] = this.a(var5, 1, var3);
            if (var2[0] < 0) {
                return var2[0];
            } else {
                var2 = this.a((UsbDeviceConnection)var5, 129);
                if (var2[0] < 0) {
                    return var2[0];
                } else {
                    if ((var2[1] & 15) == 15) {
                        var1.a(4);
                        var2 = this.b(var5, 250);
                        if (var2[0] < 0) {
                            return var2[0];
                        }

                        var4[0] = var2[1];
                        var2 = this.b(var5, 251);
                        if (var2[0] < 0) {
                            return var2[0];
                        }

                        var4[1] = var2[1];
                        if (var4[0] == 1 && var4[1] == 4) {
                            var1.a(2);
                        } else if ((var4[0] != 2 || var4[1] != 4) && (var4[0] != 3 || var4[1] != 4) && var4[0] == 1 && var4[1] == 3) {
                            var1.a(2);
                        }
                    } else {
                        var1.a(2);
                    }

                    var2[0] = this.a(var5, 1, var6);
                    return var2[0] < 0 ? var2[0] : 0;
                }
            }
        }
    }

    private int c(UsbDeviceInfo var1) {
        int[] var2 = new int[2];
        UsbDeviceConnection var3 = var1.g();
        var2 = this.a((UsbDeviceConnection)var3, 129);
        if (var2[0] < 0) {
            return var2[0];
        } else {
            if ((var2[1] & 8) == 8) {
                var2[0] = this.a(var3, 0, 49);
                if (var2[0] < 0) {
                    return var2[0];
                }

                var2[0] = this.a(var3, 1, 8);
                if (var2[0] < 0) {
                    return var2[0];
                }

                var1.c(true);
            }

            return var2[0];
        }
    }

    public int PL2303SetupCOMPort(int index, PL2303GMultiLib.BaudRate R, PL2303GMultiLib.DataBits D, PL2303GMultiLib.StopBits S, PL2303GMultiLib.Parity P, PL2303GMultiLib.FlowControl F) throws IOException {
        UsbDeviceInfo var7 = null;
        int[] var8 = new int[2];
        if (this.ae != 0 && index >= 0) {
            if (index >= 0) {
                var7 = this.ad[index];
            }

            if (var7 != null && var7.b() != null) {
                if (var7.n() && !var7.m()) {
                    return 0;
                } else {
                    int var9 = 0;

                    try {
                        var9 = this.a(var7.g(), index, var7.h(), R, D, S, P, F);
                    } catch (IOException var11) {
                        var11.printStackTrace();
                    }

                    if (var9 < 0) {
                        return -3;
                    } else {
                        if (PL2303GMultiLib.FlowControl.XONXOFF == F) {
                            var7.f(true);
                        } else {
                            var7.f(false);
                        }

                        if (var7.k()) {
                            var8[0] = this.a(var7.g(), 0, 49);
                            if (var8[0] < 0) {
                                return var8[0];
                            }

                            var8[0] = this.a(var7.g(), 1, 8);
                            if (var8[0] < 0) {
                                return var8[0];
                            }
                        }

                        return var9;
                    }
                }
            } else {
                return -2;
            }
        } else {
            return -1;
        }
    }
    private static /* synthetic */ int[] am;
    private static /* synthetic */ int[] an;
    private static /* synthetic */ int[] ao;
    private static /* synthetic */ int[] ap;

    static /* synthetic */ int[] c() {
        int[] iArr = am;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[StopBits.valuesCustom().length];
        try {
            iArr2[StopBits.S1.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[StopBits.S2.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        am = iArr2;
        return iArr2;
    }

    static /* synthetic */ int[] d() {
        int[] iArr = an;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[Parity.valuesCustom().length];
        try {
            iArr2[Parity.EVEN.ordinal()] = 3;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[Parity.NONE.ordinal()] = 1;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[Parity.ODD.ordinal()] = 2;
        } catch (NoSuchFieldError unused3) {
        }
        an = iArr2;
        return iArr2;
    }

    static /* synthetic */ int[] e() {
        int[] iArr = ao;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[DataBits.valuesCustom().length];
        try {
            iArr2[DataBits.D5.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[DataBits.D6.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[DataBits.D7.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[DataBits.D8.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        ao = iArr2;
        return iArr2;
    }

    static /* synthetic */ int[] f() {
        int[] iArr = ap;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[FlowControl.valuesCustom().length];
        try {
            iArr2[FlowControl.DTRDSR.ordinal()] = 4;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[FlowControl.OFF.ordinal()] = 1;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[FlowControl.RFRCTS.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[FlowControl.RTSCTS.ordinal()] = 2;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[FlowControl.RTSCTSDTRDSR.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            iArr2[FlowControl.XONXOFF.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
        ap = iArr2;
        return iArr2;
    }


    private int a(UsbDeviceConnection var1, int var2, int var3, PL2303GMultiLib.BaudRate var4, PL2303GMultiLib.DataBits var5, PL2303GMultiLib.StopBits var6, PL2303GMultiLib.Parity var7, PL2303GMultiLib.FlowControl var8) throws IOException {
        boolean var9 = false;
        if (var1 == null) {
            return -1;
        } else {
            int var11 = var1.controlTransfer(161, 33, 0, 0, this.O, 7, this.R);
            if (var11 < 0) {
                return var11;
            } else {
                boolean var10 = false;
                int var12;
                switch(b()[var4.ordinal()]) {
                    case 1:
                        var12 = 0;
                        break;
                    case 2:
                        var12 = 75;
                        break;
                    case 3:
                        var12 = 150;
                        break;
                    case 4:
                        var12 = 300;
                        break;
                    case 5:
                        var12 = 600;
                        break;
                    case 6:
                        var12 = 1200;
                        break;
                    case 7:
                        var12 = 1800;
                        break;
                    case 8:
                        var12 = 2400;
                        break;
                    case 9:
                        var12 = 4800;
                        break;
                    case 10:
                        var12 = 9600;
                        break;
                    case 11:
                        var12 = 14400;
                        break;
                    case 12:
                        var12 = 19200;
                        break;
                    case 13:
                        var12 = 38400;
                        break;
                    case 14:
                        var12 = 57600;
                        break;
                    case 15:
                        var12 = 115200;
                        break;
                    case 16:
                        var12 = 230400;
                        break;
                    case 17:
                        var12 = 460800;
                        break;
                    case 18:
                        var12 = 614400;
                        break;
                    case 19:
                        var12 = 921600;
                        break;
                    case 20:
                        var12 = 1228800;
                        break;
                    case 21:
                        var12 = 2457600;
                        break;
                    case 22:
                        var12 = 3000000;
                        break;
                    case 23:
                        var12 = 6000000;
                        break;
                    default:
                        return -2;
                }

                if (var12 > 1228800 && var3 == 0) {
                    return -2;
                } else {
                    if (this.ag[var2].c != null) {
                        this.a(this.ag[var2], var4);
                    }

                    this.O[0] = (byte)(var12 & 255);
                    this.O[1] = (byte)(var12 >> 8 & 255);
                    this.O[2] = (byte)(var12 >> 16 & 255);
                    this.O[3] = (byte)(var12 >> 24 & 255);
                    switch(c()[var6.ordinal()]) {
                        case 1:
                            this.O[4] = 0;
                            break;
                        case 2:
                            this.O[4] = 2;
                            break;
                        default:
                            return -3;
                    }

                    switch(d()[var7.ordinal()]) {
                        case 1:
                            this.O[5] = 0;
                            break;
                        case 2:
                            this.O[5] = 1;
                            break;
                        case 3:
                            this.O[5] = 2;
                            break;
                        default:
                            return -4;
                    }

                    switch(e()[var5.ordinal()]) {
                        case 1:
                            this.O[6] = 5;
                            break;
                        case 2:
                            this.O[6] = 6;
                            break;
                        case 3:
                            this.O[6] = 7;
                            break;
                        case 4:
                            this.O[6] = 8;
                            break;
                        default:
                            return -5;
                    }

                    var11 = var1.controlTransfer(33, 32, 0, 0, this.O, 7, this.R);
                    if (var11 < 0) {
                        return var11;
                    } else {
                        var11 = var1.controlTransfer(33, 35, 0, 0, (byte[])null, 0, this.R);
                        if (var11 < 0) {
                            return var11;
                        } else {
                            switch(f()[var8.ordinal()]) {
                                case 1:
                                    var11 = var1.controlTransfer(64, 128, 8, 254, (byte[])null, 0, this.R);
                                    if (var11 < 0) {
                                        return var11;
                                    }
                                    break;
                                case 2:
                                    var11 = var1.controlTransfer(64, 128, 8, 250, (byte[])null, 0, this.R);
                                    if (var11 < 0) {
                                        return var11;
                                    }
                                case 3:
                                    break;
                                case 4:
                                    var11 = var1.controlTransfer(64, 128, 8, 246, (byte[])null, 0, this.R);
                                    if (var11 < 0) {
                                        return var11;
                                    }
                                    break;
                                case 5:
                                    var11 = var1.controlTransfer(64, 128, 8, 242, (byte[])null, 0, this.R);
                                    if (var11 < 0) {
                                        return var11;
                                    }
                                    break;
                                case 6:
                                    var11 = var1.controlTransfer(64, 128, 8, 238, (byte[])null, 0, this.R);
                                    if (var11 < 0) {
                                        return var11;
                                    }
                                    break;
                                default:
                                    return -6;
                            }

                            return 0;
                        }
                    }
                }
            }
        }
    }

    private static void a(Object var0) {
        Log.d("PL2303Multi_USB", "L: " + var0.toString());
    }

    public boolean PL2303Device_IsSupportChip(int index) {
        boolean var2 = false;
        UsbDeviceInfo var3 = null;
        var3 = this.ad[index];
        if (4 == var3.h()) {
            var2 = true;
        } else if (6 == var3.h()) {
            var2 = true;
        }

        return var2;
    }

    private String c(int var1) {
        char[] var2 = new char[]{Character.forDigit(var1 >> 4 & 15, 16), Character.forDigit(var1 & 15, 16)};
        String var3 = new String(var2);
        return var3;
    }

    private static String a(byte[] var0, int var1) {
        StringBuffer var3 = null;

        try {
            MessageDigest var2 = MessageDigest.getInstance("SHA-512");
            var2.reset();
            byte[] var4 = var2.digest(var0);
            var3 = new StringBuffer();
            byte[] var8 = var4;
            int var7 = var4.length;

            for(int var6 = 0; var6 < var7; ++var6) {
                byte var5 = var8[var6];
                var3.append(String.format("%02X", var5));
            }
        } catch (NoSuchAlgorithmException var9) {
            var9.printStackTrace();
        }

        return var3.toString();
    }

    private int a(UsbDeviceConnection var1, int var2, int var3) {
        boolean var4 = false;
        if (var1 == null) {
            return -1;
        } else {
            int var5 = var1.controlTransfer(64, 128, var2, var3, (byte[])null, 0, this.R);
            return var5 < 0 ? var5 : var5;
        }
    }

    private int[] a(UsbDeviceConnection var1, int var2) {
        int[] var3 = new int[2];
        byte[] var4 = new byte[1];
        var3[0] = 0;
        if (var1 == null) {
            var3[0] = -1;
            return var3;
        } else {
            var3[0] = var1.controlTransfer(192, 129, var2, 0, var4, 1, this.R);
            if (var3[0] < 0) {
                return var3;
            } else {
                var3[1] = var4[0];
                return var3;
            }
        }
    }

    private int[] b(UsbDeviceConnection var1, int var2) {
        int[] var3 = new int[]{0, 0};
        var3 = this.a((UsbDeviceConnection)var1, 132);
        if (var3[0] < 0) {
            return var3;
        } else {
            var3[0] = this.a(var1, 4, var2);
            if (var3[0] < 0) {
                return var3;
            } else {
                var3 = this.a((UsbDeviceConnection)var1, 132);
                if (var3[0] < 0) {
                    return var3;
                } else {
                    var3 = this.a((UsbDeviceConnection)var1, 131);
                    return var3[0] < 0 ? var3 : var3;
                }
            }
        }
    }

    private boolean a(UsbDeviceConnection var1, UsbDeviceInfo var2) {
        int[] var3 = new int[2];
        int[] var4 = new int[2];
        var2.d(false);
        return var2.l();
    }

    private int a(UsbDeviceConnection var1) {
        boolean var2 = false;
        int var3 = this.a(var1, 8, 254);
        if (var3 < 0) {
        }

        return var3;
    }

    public boolean PL2303Device_SetCommTimeouts(int TimeoutConstant) {
        if (TimeoutConstant < 0) {
            return false;
        } else {
            this.P = TimeoutConstant;
            this.Q = TimeoutConstant;
            return true;
        }
    }

    public boolean PL2303Device_GetCommTimeouts(int TimeoutConstant) {
        TimeoutConstant = this.Q;
        return true;
    }

    public String PL2303Device_GetSerialNumber(int index) {
        UsbDeviceInfo var2 = null;
        UsbDeviceConnection var3 = null;
        if (this.ae != 0 && index >= 0) {
            if (index >= 0) {
                var2 = this.ad[index];
            }

            if (var2 != null && var2.b() != null) {
                var3 = var2.g();
                return var3 == null ? null : var3.getSerial();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private String a(int[] var1) {
        String var2 = "";
        if (var1[2] == 0 && var1[1] == 0 && var1[0] == 1) {
            var2 = "COM1";
        }

        if (var1[2] == 0 && var1[1] == 1 && var1[0] == 0) {
            var2 = "COM2";
        }

        if (var1[2] == 0 && var1[1] == 1 && var1[0] == 1) {
            var2 = "COM3";
        }

        if (var1[2] == 1 && var1[1] == 0 && var1[0] == 0) {
            var2 = "COM4";
        }

        if (var1[2] == 1 && var1[1] == 0 && var1[0] == 1) {
            var2 = "COM5";
        }

        if (var1[2] == 1 && var1[1] == 1 && var1[0] == 0) {
            var2 = "COM6";
        }

        if (var1[2] == 1 && var1[1] == 1 && var1[0] == 1) {
            var2 = "COM7";
        }

        if (var1[2] == 0 && var1[1] == 0 && var1[0] == 0) {
            var2 = "COM8";
        }

        return var2;
    }

    private String b(int[] var1) {
        String var2 = "";
        if (var1[1] == 0 && var1[0] == 0) {
            var2 = "COM1";
        }

        if (var1[1] == 1 && var1[0] == 0) {
            var2 = "COM2";
        }

        if (var1[1] == 0 && var1[0] == 1) {
            var2 = "COM3";
        }

        if (var1[1] == 1 && var1[0] == 1) {
            var2 = "COM4";
        }

        return var2;
    }

    public int PL2303G_SetDTR(int index, boolean state) {
        UsbDeviceInfo var4 = null;
        UsbDeviceConnection var5 = null;
        if (this.ae != 0 && index >= 0 && this.ae > index) {
            if (index >= 0) {
                var4 = this.ad[index];
            }

            if (var4 != null && var4.b() != null) {
                var5 = var4.g();
                int var3 = var4.a(UsbDeviceInfo.MODEMCtrlPin.MODEM_DTRRTS);
                if (state && 1 != (var3 & 1)) {
                    ++var3;
                }

                if (!state && 1 == (var3 & 1)) {
                    --var3;
                }

                int var6 = var5.controlTransfer(33, 34, var3, 0, (byte[])null, 0, this.R);
                if (var6 < 0) {
                    return -3;
                } else {
                    var4.a(UsbDeviceInfo.MODEMCtrlPin.MODEM_DTRRTS, var3);
                    return 0;
                }
            } else {
                return -2;
            }
        } else {
            return -1;
        }
    }

    public int PL2303G_SetRTS(int index, boolean state) {
        UsbDeviceInfo var4 = null;
        UsbDeviceConnection var5 = null;
        if (this.ae != 0 && index >= 0 && this.ae > index) {
            if (index >= 0) {
                var4 = this.ad[index];
            }

            if (var4 != null && var4.b() != null) {
                var5 = var4.g();
                int var3 = var4.a(UsbDeviceInfo.MODEMCtrlPin.MODEM_DTRRTS);
                if (state && 2 != (var3 & 2)) {
                    var3 += 2;
                }

                if (!state && 2 == (var3 & 2)) {
                    var3 -= 2;
                }

                int var6 = var5.controlTransfer(33, 34, var3, 0, (byte[])null, 0, this.R);
                if (var6 < 0) {
                    return var6;
                } else {
                    var4.a(UsbDeviceInfo.MODEMCtrlPin.MODEM_DTRRTS, var3);
                    return 0;
                }
            } else {
                return -2;
            }
        } else {
            return -1;
        }
    }

    public int[] PL2303G_GetCommModemStatus(int index) {
        int[] var2 = new int[2];
        byte var3 = 0;
        short var4 = 135;
        UsbDeviceInfo var5 = null;
        UsbDeviceConnection var6 = null;
        if (this.ae != 0 && index >= 0 && this.ae > index) {
            if (index >= 0) {
                var5 = this.ad[index];
            }

            if (var5 != null && var5.b() != null) {
                var6 = var5.g();
                if (var6 == null) {
                    var2[0] = -1;
                    return var2;
                } else {
                    var2[0] = 0;
                    var2 = this.a((UsbDeviceConnection)var6, var4);
                    if (var2[0] < 0) {
                        return var2;
                    } else {
                        int var7;
                        if ((var2[1] & 1) == 1) {
                            var7 = var3 & -9;
                        } else {
                            var7 = var3 | 8;
                        }

                        if ((var2[1] & 2) == 2) {
                            var7 &= -2;
                        } else {
                            var7 |= 1;
                        }

                        if ((var2[1] & 4) == 4) {
                            var7 &= -3;
                        } else {
                            var7 |= 2;
                        }

                        if ((var2[1] & 8) == 8) {
                            var7 &= -129;
                        } else {
                            var7 |= 128;
                        }

                        var2[1] = var7;
                        return var2;
                    }
                }
            } else {
                var2[0] = -2;
                return var2;
            }
        } else {
            var2[0] = -1;
            return var2;
        }
    }

    public enum BaudRate {
        B0,
        B75,
        B150,
        B300,
        B600,
        B1200,
        B1800,
        B2400,
        B4800,
        B9600,
        B14400,
        B19200,
        B38400,
        B57600,
        B115200,
        B230400,
        B460800,
        B614400,
        B921600,
        B1228800,
        B2457600,
        B3000000,
        B6000000;

        /* renamed from: values  reason: to resolve conflict with enum method */
        public static BaudRate[] valuesCustom() {
            BaudRate[] valuesCustom = values();
            int length = valuesCustom.length;
            BaudRate[] baudRateArr = new BaudRate[length];
            System.arraycopy(valuesCustom, 0, baudRateArr, 0, length);
            return baudRateArr;
        }
    }

    public static enum DataBits {
        D5,
        D6,
        D7,
        D8;

        public static DataBits[] valuesCustom() {
            DataBits[] valuesCustom = values();
            int length = valuesCustom.length;
            DataBits[] dataBitsArr = new DataBits[length];
            System.arraycopy(valuesCustom, 0, dataBitsArr, 0, length);
            return dataBitsArr;
        }

    }

    public static enum FlowControl {
        OFF,
        RTSCTS,
        RFRCTS,
        DTRDSR,
        RTSCTSDTRDSR,
        XONXOFF;

        public static FlowControl[] valuesCustom() {
            FlowControl[] valuesCustom = values();
            int length = valuesCustom.length;
            FlowControl[] flowControlArr = new FlowControl[length];
            System.arraycopy(valuesCustom, 0, flowControlArr, 0, length);
            return flowControlArr;
        }

    }

    class a {
        public ArrayBlockingQueue<Integer> a = new ArrayBlockingQueue(4096, true);
        public Object b = new Object();
        public PL2303GMultiLib.b c = null;
        public boolean d = false;

        a() {
        }
    }

    public static enum Parity {
        NONE,
        ODD,
        EVEN;

        public static Parity[] valuesCustom() {
            Parity[] valuesCustom = values();
            int length = valuesCustom.length;
            Parity[] parityArr = new Parity[length];
            System.arraycopy(valuesCustom, 0, parityArr, 0, length);
            return parityArr;
        }

    }

    class b extends Thread {
        private int b = 0;
        private int c = 0;
        private boolean d = true;
        private boolean e = false;
        private AtomicInteger f = new AtomicInteger(500);
        private UsbDeviceInfo g = null;
        private PL2303GMultiLib.a h = null;
        private int i = 0;

        public b(int var2, PL2303GMultiLib.a var3) {
            this.e = false;
            var3.a.clear();
            if (PL2303GMultiLib.this.PL2303IsDeviceConnectedByIndex(var2)) {
                this.g = PL2303GMultiLib.this.ad[var2];
                this.i = var2;
                this.h = var3;
            }

        }

        public void a(int var1) {
            this.f.set(var1);
        }

        public void a() {
            this.e = true;

            while(this.isAlive()) {
            }

            this.h.a.clear();
        }

        private void b(int var1) {
            if (var1 == 0) {
                Thread.yield();
            } else {
                long var2 = System.currentTimeMillis();

                long var4;
                do {
                    var4 = System.currentTimeMillis();
                    Thread.yield();
                } while(var4 - var2 <= (long)var1);

            }
        }

        public void run() {
            if (PL2303GMultiLib.this.PL2303IsDeviceConnectedByIndex(this.i)) {
                try {
                    byte[] var1 = new byte[4096];

                    while(!this.e) {
                        this.b = PL2303GMultiLib.this.b(this.g, var1, var1.length);
                        if (this.b > 0) {
                            synchronized(this.h.b) {
                                this.c = this.h.a.size();
                                if (4096 != this.c) {
                                    for(int var4 = 0; var4 < this.b && this.c < 4096; ++var4) {
                                        int var2 = Integer.valueOf(var1[var4]);
                                        if (this.g.n()) {
                                            if (19 == var2) {
                                                this.g.e(false);
                                                continue;
                                            }

                                            if (17 == var2) {
                                                this.g.e(true);
                                                continue;
                                            }
                                        }

                                        this.d = this.h.a.offer(var2);
                                        if (!this.d) {
                                            break;
                                        }

                                        this.c = this.h.a.size();
                                    }
                                }
                            }
                        }

                        int var3 = this.f.get();
                        this.b(var3);
                    }
                } catch (Exception var6) {
                }

            }
        }
    }

    public static enum StopBits {
        S1,
        S2;

        public static StopBits[] valuesCustom() {
            StopBits[] valuesCustom = values();
            int length = valuesCustom.length;
            StopBits[] stopBitsArr = new StopBits[length];
            System.arraycopy(valuesCustom, 0, stopBitsArr, 0, length);
            return stopBitsArr;
        }

    }
}
