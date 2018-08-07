package cmed.com.userdata.retrofit.endpoint;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserInfoEndPoint {
    @GET("intrvw/users.json")
    Call<JsonObject> getUserData();
}
