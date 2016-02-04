package techgravy.imageuploadtest.generator;

/**
 * Created by aditlal on 03/02/16.
 */

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import techgravy.imageuploadtest.utils.Logger;

/**
 * Created by aditlal on 24/11/15.
 */
public class ApiGenerator {

    public static String BASE_URL = "https://api.imgur.com/";

    // No need to instantiate this class.
    public ApiGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass) {


        OkHttpClient okHttpClient = new OkHttpClient();
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String msg) {
                        Logger.i("Retro", msg);
                    }
                })
                .setClient(new OkClient(okHttpClient));

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }

}
