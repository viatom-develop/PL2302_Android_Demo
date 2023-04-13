package com.example.pl2302_android.uart;



import static com.example.pl2302_android.uart.O2CRC.calCRC8;

public class O2Cmd {

    public static int O2_CMD_GET_PRODUCT_ID_INFO = 0x01;
    public static int O2_CMD_GET_PRODUCT_VERSION_INFO = 0x01;
    public static int O2_CMD_GET_STATUS_INFO = 0x02;



    public static byte[] getProductIDInfo() {
        int len = 2;
        byte[] cmd = new byte[4 + len];
        cmd[0] = (byte) 0xAA;
        cmd[1] = (byte) 0x55;
        cmd[2] = (byte) 0xFF;
        cmd[3] = (byte) len;
        cmd[4] = (byte) O2_CMD_GET_PRODUCT_ID_INFO;
        cmd[5] = calCRC8(cmd);
        return cmd;
    }

    public static byte[] getProductVersionInfo() {
        int len = 2;
        byte[] cmd = new byte[4 + len];
        cmd[0] = (byte) 0xAA;
        cmd[1] = (byte) 0x55;
        cmd[2] = (byte) 0x51;
        cmd[3] = (byte) len;
        cmd[4] = (byte) O2_CMD_GET_PRODUCT_VERSION_INFO;
        cmd[5] = calCRC8(cmd);
        return cmd;
    }


    public static byte[] getStatusInfo() {
        int len = 2;
        byte[] cmd = new byte[4 + len];
        cmd[0] = (byte) 0xAA;
        cmd[1] = (byte) 0x55;
        cmd[2] = (byte) 0x51;
        cmd[3] = (byte) len;
        cmd[4] = (byte) O2_CMD_GET_STATUS_INFO;
        cmd[5] = calCRC8(cmd);
        return cmd;
    }

    public static byte[] setWorkMode(int mode) {
        int len = 3;
        byte[] cmd = new byte[4 + len];
        cmd[0] = (byte) 0xAA;
        cmd[1] = (byte) 0x55;
        cmd[2] = (byte) 0x50;
        cmd[3] = (byte) len;
        cmd[4] = (byte) 0x01;
        cmd[5] = (byte) mode;
        cmd[6] = calCRC8(cmd);
        return cmd;
    }

}
