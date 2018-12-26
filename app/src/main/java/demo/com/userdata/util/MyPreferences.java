package demo.com.userdata.util;

import android.content.Context;
import android.content.SharedPreferences;
import static demo.com.userdata.util.Config.IS_FIRST_RUN;

public class MyPreferences {

    private static MyPreferences myPreferences;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private MyPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(Config.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static MyPreferences getPreferences(Context context) {
        if (myPreferences == null) myPreferences = new MyPreferences(context);
        return myPreferences;
    }

    public void setSyncStatus(boolean status){
        editor.putBoolean(IS_FIRST_RUN, status);
        editor.apply();
    }

    public boolean getFirstTimeSyncStatus(){
        return sharedPreferences.getBoolean(IS_FIRST_RUN, false);
    }
}