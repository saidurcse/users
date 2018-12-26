package demo.com.userdata.realm.dao;

import java.util.List;
import demo.com.userdata.retrofit.model.User;
import demo.com.userdata.realm.OnCompleteListener;
import io.realm.Realm;
import io.realm.RealmResults;

public class UserInfoDao {

    public void updateUserInfoList(final List<User> users, OnCompleteListener listener) {

        Realm realmIns = null;
        try {
            realmIns = Realm.getDefaultInstance();
            realmIns.executeTransactionAsync(
                    realm -> {
                        RealmResults<User> results = realm.where(User.class).findAll();
                        results.deleteAllFromRealm();
                        realm.insertOrUpdate(users);
                    },
                    () -> listener.onComplete(true),
                    error -> listener.onComplete(false) );
        } finally {
            realmIns.close();
        }
    }

    public void setMaleImageByte(int id, byte[] imageByte, OnCompleteListener listener) {

        Realm realmIns = null;
        try {
            realmIns = Realm.getDefaultInstance();
            realmIns.executeTransactionAsync(realm -> {
                        User user = realm.where(User.class).equalTo("id", id).findFirst();
                        user.setImageByte(imageByte);
                    },
                    () -> listener.onComplete(true),
                    error -> listener.onComplete(false) );
        } finally {
            realmIns.close();
        }
    }

    public String getPhone(int id) {

        Realm realmIns = null;
        try {
            realmIns = Realm.getDefaultInstance();
            User user = realmIns.where(User.class).equalTo("id", id).findFirst();
            if (user != null) {
                User user1 = realmIns.copyFromRealm(user);
                return user1.getPhones().getMobile();
            }
            return null;
        } finally {
            realmIns.close();
        }
    }

}
