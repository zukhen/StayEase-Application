package com.example.qlks.Receiver;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

import com.example.qlks.R;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private Dialog dialog;

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // check internet 3G/wifi
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        if (networkCapabilities != null) {
            isConnected = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        }

        if (dialog == null) {
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_lost_connect);
            dialog.setCancelable(false);
        }

        if (isConnected) {
            dialog.dismiss();
        } else {
            dialog.show();
        }
    }
}