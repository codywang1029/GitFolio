package network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Follower {
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    public Follower() {
    }

    public Follower(String login, String avatarUrl) {
        this.login = login;
        this.avatarUrl = avatarUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
