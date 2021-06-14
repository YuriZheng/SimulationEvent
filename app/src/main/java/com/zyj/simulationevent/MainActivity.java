package com.zyj.simulationevent;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("zyj", "Build.VERSION.RELEASE: " + Build.VERSION.RELEASE);
        Log.d("zyj", "Build.MANUFACTURER: " + Build.MANUFACTURER);
        Log.d("zyj", "Build.MODEL: " + Build.MODEL);
        Log.d("zyj", "Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT);
        Log.d("zyj", "Build.SERIAL: " + Build.SERIAL);
        Log.d("zyj", "Build.BRAND: " + Build.BRAND);
        Log.d("zyj", "Build.ID: " + Build.ID);
        Log.d("zyj", "Secure.ANDROID_ID: " + Settings.Secure.ANDROID_ID);

        Log.d("zyj", "IMEI: " + Arrays.toString(getImei()));
        Log.d("zyj", "MAC: " + getMAC());
        Log.d("zyj", "MNC: " + getMNC());
        Log.d("zyj", "MCC: " + getMCC());
        Log.d("zyj", "AndroidId: " + getAndroidId());
    }


    private String[] getImei() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            String[] result = new String[telephonyManager.getPhoneCount()];
            for (int i = 0; i < result.length; i++) {
                result[i] = telephonyManager.getDeviceId(i);
            }
            return result;
        } else {
            return new String[]{telephonyManager.getDeviceId()};
        }
    }

    private String[] getMAC() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            String[] result = new String[all.size()];
            for (int i = 0; i < result.length; i++) {
                result[i] = all.get(i).getName() + ": " + new String(all.get(i).getHardwareAddress(), "utf-8");
            }
            return result;
        } catch (Exception ex) {
            Log.e("zyj", "", ex);
        }
        return null;
    }

    private String getMNC() {
        TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = tel.getNetworkOperator();
        try {
            return networkOperator.substring(0, 3);
        } catch (Exception e) {
            Log.e("zyj", "", e);
        }
        return "Unknown";
    }

    private String getMCC() {
        TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = tel.getNetworkOperator();
        try {
            return networkOperator.substring(3);
        } catch (Exception e) {
            Log.e("zyj", "", e);
        }
        return "Unknown";
    }

    private String getAndroidId() {
        return Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
    }

}