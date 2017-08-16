package cn.cerc.summer.android.parts.permission;

import android.Manifest;

/**
 * Created by Clown on 2017/6/27.
 */

public class Constant {

    //读写sd
    public static final int READ_WRITE_SD_CARD = 1;
    public static final int SD_CARD_STATE = 2;
    public static boolean STORAGE_STATE;
    public static String[] STORAGE_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    //call phone
    public static final int REQUEST_CALL_CUSTOMER_PHONE = 12;
    public static final int REQUEST_CALL_PHONE = 13;
    public static final String[] PERMISSIONS_CALL_PHONE
            = {Manifest.permission.CALL_PHONE};

    //map
    public static final int REQUEST_NAVIGATION = 3;
    public static final String[] PERMISSIONS_NAVIGATION =
            {Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_WIFI_STATE};

}
