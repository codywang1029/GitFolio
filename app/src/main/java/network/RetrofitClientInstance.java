package network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * a simple wrapper for retrofit usage
 */
public class RetrofitClientInstance{
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.github.com";
    private static final String TOKEN = "1888016b4a70a0d271e61af09e4cfddabe21e0a4";




    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
