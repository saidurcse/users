package cmed.com.userdata.retrofit;


import cmed.com.userdata.retrofit.model.ResponseType;

public interface OnRestCompleteListener<T> {

    void onComplete(ResponseType responseType, T result, String message);
}
