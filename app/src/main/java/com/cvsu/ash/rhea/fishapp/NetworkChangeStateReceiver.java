package com.cvsu.ash.rhea.fishapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;


public class NetworkChangeStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        boolean connected = checkInternetState(context);

        if (!connected) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setMessage("No Internet Connection, You are in offline mode.");
            alert.setCancelable(false);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });alert.show();
        }

    }


    public boolean checkInternetState(Context context) {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            //connected to internet, check if wifi or data
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                connected = true;
            }
            else {
                GlobalVariables.gPin = false;
            }
        }

        GlobalVariables.internet = connected;
        return connected;
    }
}
