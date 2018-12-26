package demo.com.userdata.contact;

import java.util.List;
import demo.com.userdata.retrofit.model.User;

public interface MainContact {

    interface View {

        void forwardToShowDataFromRealm();
        void showProgressIndicator(boolean show);
        void removeUserButton(boolean show);
        void showToastMsg(String msg);
    }

    interface UserActionListener {

        void getUserData();
        void downloadImage(List<User> userList);
    }
}
