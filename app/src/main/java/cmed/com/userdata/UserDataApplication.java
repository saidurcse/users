package cmed.com.userdata;

import android.support.multidex.MultiDexApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class UserDataApplication extends MultiDexApplication {

    private static UserDataApplication instance;
    private static RealmConfiguration realmConfig;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        Realm.init(this);

        realmConfig = new RealmConfiguration.Builder()
                .name("userdata.realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    public static UserDataApplication getInstance() {
        return instance;
    }
}
