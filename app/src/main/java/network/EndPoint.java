package network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface EndPoint {
    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Kexiang"
    })
    @GET("users/{user}")
    Call<UserInfo> getUser(@Path("user") String user);

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Kexiang"
    })
    @GET("users/{user}/repos")
    Call<List<Repo>> getRepo(@Path("user") String user);
}
