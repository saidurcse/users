package demo.com.userdata.retrofit.repo;

import java.io.IOException;

import demo.com.userdata.retrofit.OnRestCompleteListener;
import demo.com.userdata.retrofit.ServiceGenerator;
import demo.com.userdata.retrofit.endpoint.MaleImageEndPoint;
import demo.com.userdata.retrofit.model.ResponseType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageRepo {

    private MaleImageEndPoint endPoint = ServiceGenerator.createService(MaleImageEndPoint.class);

    public void getMaleImageByte(String imagePath, OnRestCompleteListener<byte[]> listener) {

        String str = "https://randomuser.me/api/portraits/men/"+imagePath+".jpg".trim();
        Call<ResponseBody> call = endPoint.getMaleImage(str);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        listener.onComplete(ResponseType.Success, response.body().bytes(), null);
                    } catch (IOException e) {
                        listener.onComplete(ResponseType.Error, null, "Byte conversion error");
                    }
                } else {
                    listener.onComplete(ResponseType.Error, null, "Unauthorised");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onComplete(ResponseType.Failure, null, t.getMessage());
            }
        });
    }

    public void getFemaleImageByte(String imagePath, OnRestCompleteListener<byte[]> listener) {

        String str = "https://randomuser.me/api/portraits/women/"+imagePath+".jpg".trim();
        Call<ResponseBody> call = endPoint.getMaleImage(str);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        listener.onComplete(ResponseType.Success, response.body().bytes(), null);
                    } catch (IOException e) {
                        listener.onComplete(ResponseType.Error, null, "Byte conversion error");
                    }
                } else {
                    listener.onComplete(ResponseType.Error, null, "Unauthorised");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onComplete(ResponseType.Failure, null, t.getMessage());
            }
        });
    }
}
