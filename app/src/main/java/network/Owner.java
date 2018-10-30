
package network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Owner {

    @SerializedName("login")
    @Expose
    private String login;

    public Owner(String login) {
        this.login = login;
    }

    public Owner() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}