package cmed.com.userdata.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;
import java.net.UnknownHostException;

import cmed.com.userdata.UserDataApplication;

public class NetworkUtils {

    private NetworkUtils() {}

    public static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) UserDataApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

}
