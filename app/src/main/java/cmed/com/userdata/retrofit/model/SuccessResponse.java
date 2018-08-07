package cmed.com.userdata.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class SuccessResponse<T> {

    @SerializedName("success")
    public T data;
}
