package com.cvsu.ash.rhea.fishapp;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

public class GlobalFunction {
    public static boolean isLocationEnabled(Context context) throws Settings.SettingNotFoundException{
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
}
