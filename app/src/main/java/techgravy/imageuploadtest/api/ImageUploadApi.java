package techgravy.imageuploadtest.api;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;
import techgravy.imageuploadtest.models.ImageResponse;

/**
 * Created by aditlal on 03/02/16.
 */
public interface ImageUploadApi {


    @POST("/3/image")
    @FormUrlEncoded
    void postImage(
            @Header("Authorization") String clientId,
            @Field("type") String type,
            @Field("title") String title,
            @Field("description") String desc,
            @Field("image") String base64,
            Callback<ImageResponse> cb
    );
}
