package network;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
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

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Kexiang"
    })
    @GET("users/{user}/followers")
    Call<List<Follower>> getFollower(@Path("user") String user);

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Kexiang"
    })
    @GET("users/{user}/following")
    Call<List<Follower>> getFollowing(@Path("user") String user);

    @Headers( {
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Kexiang"
    })
    @GET("user/starred?access_token=1888016b4a70a0d271e61af09e4cfddabe21e0a4")
    Call<List<StarredRepo>> getStarred();

    @Headers( {
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Kexiang",
            "Content-Length: 0"
    })
    @PUT("user/starred/{user}/{repo}?access_token=1888016b4a70a0d271e61af09e4cfddabe21e0a4")
    Call<ResponseBody> starRepo(@Path("user") String user, @Path("repo") String repo);

    @DELETE("user/starred/{user}/{repo}?access_token=1888016b4a70a0d271e61af09e4cfddabe21e0a4")
    Call<ResponseBody> unstarRepo(@Path("user") String user, @Path("repo") String repo);

    @DELETE("user/following/{user}?access_token=1888016b4a70a0d271e61af09e4cfddabe21e0a4")
    Call<ResponseBody> unfollow(@Path("user") String user);
 }
