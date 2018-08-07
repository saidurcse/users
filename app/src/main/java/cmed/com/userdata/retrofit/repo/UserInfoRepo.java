package cmed.com.userdata.retrofit.repo;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import cmed.com.userdata.retrofit.OnRestCompleteListener;
import cmed.com.userdata.retrofit.ServiceGenerator;
import cmed.com.userdata.retrofit.endpoint.UserInfoEndPoint;
import cmed.com.userdata.retrofit.model.ResponseType;
import cmed.com.userdata.retrofit.model.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoRepo {

    private UserInfoEndPoint endPoint = ServiceGenerator.createService(UserInfoEndPoint.class);

    public void getUserInfoList(OnRestCompleteListener listener) {

        Call<JsonObject> call = endPoint.getUserData();

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    UserInfo userInfo = gson.fromJson(response.body(), UserInfo.class);
                    listener.onComplete(ResponseType.Success, userInfo, null);
                } else {
                    listener.onComplete(ResponseType.Error, null, response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.onComplete(ResponseType.Failure, null, t.getMessage());
            }
        });
    }
}
