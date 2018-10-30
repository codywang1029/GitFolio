package network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StarredRepo {

    @SerializedName("name")
    @Expose
    private String name;

    public StarredRepo() {
    }

    public StarredRepo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
