package social.video.downloader.app.Api;

import androidx.annotation.Keep;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

@Keep
public interface RestApi {

    String BASE_URL = "https://app.appinstallearn.co.in/api/whatsapp/";

    @GET
    Call<ResponseBody> login3(@Url String url);

    @Headers("Content-Type: application/json")
    @POST
    Call<ResponseBody> adddata(@Header("x-api-key") String xapikey, @Url String url, @Body RequestBody body);

    @FormUrlEncoded
    @POST()
    Call<ResponseBody> checkUser(@Url String url, @Field("email") String email, @Field("password") String password);

}

