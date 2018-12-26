package demo.com.userdata.retrofit.endpoint;

import demo.com.userdata.retrofit.model.UserInfo;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface UserInfoEndPoint {
    @GET("intrvw/users.json")
    Single<UserInfo> getUserData();
}
