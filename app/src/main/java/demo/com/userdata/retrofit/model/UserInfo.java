package demo.com.userdata.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class UserInfo extends RealmObject{

    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("version")
    @Expose
    private Float version;
    @SerializedName("releaseDate")
    @Expose
    private String releaseDate;
    @SerializedName("demo")
    @Expose
    private Boolean demo;
    @SerializedName("users")
    @Expose
    private RealmList<User> users = null;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Float getVersion() {
        return version;
    }

    public void setVersion(Float version) {
        this.version = version;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean getDemo() {
        return demo;
    }

    public void setDemo(Boolean demo) {
        this.demo = demo;
    }

    public RealmList<User> getUsers() {
        return users;
    }

    public void setUsers(RealmList<User> users) {
        this.users = users;
    }
}
