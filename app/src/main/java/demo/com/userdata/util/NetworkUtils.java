package demo.com.userdata.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import demo.com.userdata.UserDataApplication;

public class NetworkUtils {

    private NetworkUtils() {}

    public static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) UserDataApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
