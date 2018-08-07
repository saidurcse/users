package cmed.com.userdata.presenter;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import cmed.com.userdata.UserDataApplication;
import cmed.com.userdata.contact.MainContact;
import cmed.com.userdata.realm.dao.UserInfoDao;
import cmed.com.userdata.retrofit.model.ResponseType;
import cmed.com.userdata.retrofit.model.User;
import cmed.com.userdata.retrofit.model.UserInfo;
import cmed.com.userdata.retrofit.repo.ImageRepo;
import cmed.com.userdata.retrofit.repo.UserInfoRepo;
import cmed.com.userdata.util.MyPreferences;
import cmed.com.userdata.util.NetworkUtils;

public class MainPresenter implements MainContact.UserActionListener{

    private MainContact.View view;
    private Context context;
    private Boolean imageDownload;
    private MyPreferences myPreferences;

    public MainPresenter(MainContact.View view){
        this.view = view;
        myPreferences = MyPreferences.getPreferences(UserDataApplication.getInstance());
    }

    @Override
    public void getUserData() {
        if (NetworkUtils.isOnline()) {

            view.showProgressIndicator(true);
            view.removeUserButton(false);

            UserInfoRepo userInfoRepo = new UserInfoRepo();
            userInfoRepo.getUserInfoList((responseType, result, message) -> {

                if (responseType == ResponseType.Success) {

                    List<User> userList = ((UserInfo) result).getUsers();

                    UserInfoDao userInfoDao = new UserInfoDao();
                    userInfoDao.updateUserInfoList(userList, isSuccess -> {
                        if (isSuccess) {
                            downloadImage(userList);
                        }
                    });
                }
            });
        }else{
            Toast.makeText(context, "Please check connection and try again.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void downloadImage(List<User> userList) {
        if (userList.size() == 0) {
            imageDownload = true;
            view.showProgressIndicator(false);
            view.forwardToShowDataFromRealm();
            return;
        }

        User user = userList.remove(0);

        if (user == null || user.getPhoto() == null) {
            downloadImage(userList);
        }

        if(user.getGender().equals("male")) {
            ImageRepo maleImageRepo = new ImageRepo();
            String photoId = String.valueOf(user.getPhoto());
            maleImageRepo.getMaleImageByte(photoId.replace("/images/", ""), (responseType, result, message) -> {

                if (responseType == ResponseType.Success) {
                    UserInfoDao userInfoDao1 = new UserInfoDao();
                    userInfoDao1.setMaleImageByte(user.getId(), result, isSuccess -> {
                        if (isSuccess) {
                            downloadImage(userList);
                        }
                    });
                }

            });
        }else{
            ImageRepo femaleImageRepo = new ImageRepo();
            String photoId = String.valueOf(user.getPhoto());
            femaleImageRepo.getFemaleImageByte(photoId.replace("/images/", ""), (responseType, result, message) -> {

                if (responseType == ResponseType.Success) {
                    UserInfoDao userInfoDao1 = new UserInfoDao();
                    userInfoDao1.setMaleImageByte(user.getId(), result, isSuccess -> {
                        if (isSuccess) {
                            downloadImage(userList);
                        }
                    });
                }

            });
        }
    }
}
