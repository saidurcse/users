package demo.com.userdata.presenter;

import android.content.Context;
import android.widget.Toast;
import java.util.List;
import demo.com.userdata.UserDataApplication;
import demo.com.userdata.contact.MainContact;
import demo.com.userdata.realm.dao.UserInfoDao;
import demo.com.userdata.retrofit.ServiceGenerator;
import demo.com.userdata.retrofit.endpoint.UserInfoEndPoint;
import demo.com.userdata.retrofit.model.ResponseType;
import demo.com.userdata.retrofit.model.User;
import demo.com.userdata.retrofit.model.UserInfo;
import demo.com.userdata.retrofit.repo.ImageRepo;
import demo.com.userdata.util.MyPreferences;
import demo.com.userdata.util.NetworkUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContact.UserActionListener{

    private MainContact.View view;
    private Context context;
    private Boolean imageDownload;
    private MyPreferences myPreferences;
    private CompositeDisposable disposable = new CompositeDisposable();
    private UserInfoEndPoint endPoint;

    public MainPresenter(MainContact.View view){
        this.view = view;
        myPreferences = MyPreferences.getPreferences(UserDataApplication.getInstance());
    }

    @Override
    public void getUserData() {
        if (NetworkUtils.isOnline()) {

            view.showProgressIndicator(true);
            view.removeUserButton(false);

            endPoint = ServiceGenerator.createService(UserInfoEndPoint.class);
            disposable.add(
                endPoint
                    .getUserData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<UserInfo>() {
                        @Override
                        public void onSuccess(UserInfo userInfos) {

                            List<User> userList = userInfos.getUsers();

                            UserInfoDao userInfoDao = new UserInfoDao();
                            userInfoDao.updateUserInfoList(userList, isSuccess -> {
                                if (isSuccess) {
                                    downloadImage(userList);
                                }
                            });
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            view.showToastMsg(" Server is busy. Please try again.");
                        }
                    }));
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
