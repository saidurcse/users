package demo.com.userdata.retrofit;


import demo.com.userdata.retrofit.model.ResponseType;

public interface OnRestCompleteListener<T> {

    void onComplete(ResponseType responseType, T result, String message);
}
