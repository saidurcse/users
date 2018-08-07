package cmed.com.userdata.contact;

import java.util.List;

import cmed.com.userdata.retrofit.model.User;

public interface MainContact {

    interface View {

        void forwardToShowDataFromRealm();
        void showProgressIndicator(boolean show);
        void removeUserButton(boolean show);
    }

    interface UserActionListener {

        void getUserData();
        void downloadImage(List<User> userList);
    }
}
