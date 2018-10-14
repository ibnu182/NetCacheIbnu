package ibnu21.com.retrofitroom;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface Api {

    @Headers("Accept: application/json")
    @GET("posts")
    Call<List<DataResponse>>getData();
}
