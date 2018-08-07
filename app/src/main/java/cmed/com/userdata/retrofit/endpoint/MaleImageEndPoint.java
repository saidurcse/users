package cmed.com.userdata.retrofit.endpoint;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface MaleImageEndPoint {

    @GET
    Call<ResponseBody> getMaleImage(@Url String url);
}
